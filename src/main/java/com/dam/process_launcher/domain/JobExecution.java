package com.dam.process_launcher.domain;

import java.time.LocalDateTime;

/**
 * Representa una ejecución concreta de un proceso del sistema.
 * 
 * Contiene toda la información necesaria para registrar y mostrar
 * el resultado de un comando lanzado desde la aplicación.
 */
public class JobExecution {

    /**
     * Tipo de trabajo ejecutado (TOP, LSOF o PS).
     */
    private Job job;

    /**
     * Fecha y hora en la que se ejecutó el proceso.
     */
    private LocalDateTime timestamp;

    /**
     * Salida estándar del proceso (stdout).
     */
    private String output;

    /**
     * Salida de error del proceso (stderr).
     */
    private String error;

    /**
     * Indica si la ejecución fue correcta (true) o si hubo error (false).
     */
    private boolean success;

    /**
     * Constructor completo que inicializa todos los campos.
     *
     * @param job       tipo de proceso ejecutado
     * @param timestamp fecha y hora de ejecución
     * @param output    salida estándar
     * @param error     salida de error
     * @param success   estado de la ejecución
     */
    public JobExecution(Job job, LocalDateTime timestamp, String output, String error, boolean success) {
        this.job = job;
        this.timestamp = timestamp;
        this.output = output;
        this.error = error;
        this.success = success;
    }

    // ==== Getters ====

    public Job getJob() {
        return job;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getOutput() {
        return output;
    }

    public String getError() {
        return error;
    }

    public boolean isSuccess() {
        return success;
    }

    // ==== Setters ====

    public void setJob(Job job) {
        this.job = job;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Devuelve una representación textual de la ejecución.
     * Se usará para imprimir resultados por consola o guardar en ficheros.
     */
    @Override
    public String toString() {
        return String.format(
                "=== Job Execution ===%n" +
                        "Job: %s%n" +
                        "Fecha: %s%n" +
                        "Éxito: %s%n" +
                        "Salida:%n%s%n" +
                        "Error:%n%s%n",
                job, timestamp, success ? "Sí" : "No", output, error);
    }
}
