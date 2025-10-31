package com.dam.process_launcher.services.interfaces;

/**
 * Define el contrato común para todos los servicios de comandos del sistema.
 * 
 * Cada implementación concreta (TopService, LsofService, PsHeadService)
 * debe validar, ejecutar y procesar la salida del comando correspondiente.
 */
public interface CommandService {
    /**
     * Valida si el comando recibido es correcto y permitido.
     * 
     * @param arrayCommand array con los elementos del comando
     * @return true si el comando es válido, false en caso contrario
     */
    boolean validate(String[] arrayCommand);

    /**
     * Ejecuta el proceso asociado al comando usando ProcessBuilder.
     * 
     * @param process proceso a ejecutar
     */
    boolean executeProcess(Process process);

    /**
     * Procesa y muestra la salida (stdout y stderr) del proceso en tiempo real.
     * 
     * @param line línea de comando a procesar
     * @return true si el procesamiento fue exitoso, false en caso contrario
     */
    boolean processLine(String line);
}
