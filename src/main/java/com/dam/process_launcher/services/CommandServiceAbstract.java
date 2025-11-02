package com.dam.process_launcher.services;

import com.dam.process_launcher.domain.Job;
import com.dam.process_launcher.repositories.interfaces.JobRepository;
import com.dam.process_launcher.services.interfaces.CommandService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Clase abstracta que implementa parte común de los servicios de comandos.
 * 
 * Define la lógica base de validación, ejecución y lectura de salida.
 */
public abstract class CommandServiceAbstract implements CommandService {
    private String command;
    private Job jobType;
    private String regexExpression;

    @Autowired
    protected JobRepository jobRepository;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Job getJobType() {
        return jobType;
    }

    public void setJobType(Job jobType) {
        this.jobType = jobType;
    }

    public String getRegexExpression() {
        return regexExpression;
    }

    public void setRegexExpression(String regex) {
        this.regexExpression = regex;
    }

    public JobRepository getJobRepository() {
        return jobRepository;
    }

    public void setJobRepository(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    /**
     * Procesa una línea de comando: valida su formato y la ejecuta si es correcta.
     * 
     * @param line línea de comando introducida por el usuario
     * @return true si la ejecución fue exitosa, false si hubo error
     */
    @Override
    public boolean processLine(String line) {
        String[] commandArray = line.split("\\s+");
        this.setCommand(commandArray[0]);

        if (!validate(commandArray)) {
            //System.out.println("El comando es inválido");
            return false;
        }

        try {
            Process process = new ProcessBuilder("bash", "-c", line).start();
            processOutput(process);
            executeProcess(process);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Procesa la salida estándar y de error del proceso en tiempo real.
     * 
     * Muestra las líneas en consola con los prefijos [OUT] y [ERR],
     * y las almacena en el fichero mediante el JobRepository.
     * 
     * @param process proceso del sistema operativo a leer
     */
    public void processOutput(Process process) {
        try (
            BufferedReader outReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))
            ) {
                String lineOut;
                while ((lineOut = outReader.readLine()) != null) {
                    System.out.println("[OUT] " + lineOut);
                    jobRepository.add("[OUT] " + lineOut + "\n");
                }

                String lineErr;
                while ((lineErr = errReader.readLine()) != null) {
                    System.err.println("[ERR] " + lineErr);
                    jobRepository.add("[ERR] " + lineErr + "\n");
                }

        } catch (Exception e) {
            System.err.println("[ERROR] Al leer la salida del proceso: " + e.getMessage());
        }
    }

    /**
     * Espera la finalización del proceso del sistema operativo.
     * 
     * @param process proceso del sistema a ejecutar
     * @return true si el proceso terminó correctamente, false si hubo error
     */
    @Override
    public boolean executeProcess(Process process) {
        try {
            process.waitFor();
        } catch (Exception e) {
            System.err.println("[ERROR] Esperando la finalización del proceso: " + e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Valida el comando y sus posibles parámetros según la expresión regular
     * definida.
     * 
     * @param arrayCommand comando separado por espacios
     * @return true si el comando es válido, false si no cumple las reglas
     */
    @Override
    public boolean validate(String[] arrayCommand) {
        if (!validateCommand()) {
            return false;
        }

        /*if (arrayCommand.length - 1 == 0) {
            return true;
        }*/

        String parameter = String.join(" ", Arrays.copyOfRange(arrayCommand, 1, arrayCommand.length));

        Pattern pattern = Pattern.compile(regexExpression);
        Matcher matcher = pattern.matcher(parameter);
        if (!matcher.find()) {
            System.out.println("[ERR] Los parámetros no cumplen el formato esperado");
            jobRepository.add("[ERR] Los parámetros no cumplen el formato esperado");
            return false;
        }

        return true;
    }

    /**
     * Comprueba que el comando principal introducido coincida
     * con el tipo de proceso esperado (Job).
     * 
     * @return true si el comando es correcto, false en caso contrario
     */
    public boolean validateCommand() {
        if (!this.getCommand().toUpperCase().equals(getJobTypeAsString())) {
            System.out.println("[ERR] El comando es inválido");
            jobRepository.add("[ERR] El comando es inválido");
            return false;
        }
        return true;
    }

    public String getJobTypeAsString() {
        if (jobType == null) {
            return null;
        }
        return jobType.toString();
    }

   
}