package com.dam.process_launcher.services;

import com.dam.process_launcher.domain.Job;
import org.springframework.stereotype.Component;

/**
 * Servicio encargado de ejecutar y validar el comando PS.
 * 
 * El comando "ps aux | head" muestra las primeras líneas de la lista
 * de procesos activos del sistema.
 * 
 * Solo se permite el comando exacto:
 * ps aux | head
 * 
 * Cualquier otro formato se considerará inválido.
 * 
 * Ejemplo válido:
 * ps aux | head
 * 
 */
@Component
public class PsHeadService extends CommandServiceAbstract {

    /**
     * Constructor de la clase PsHeadService.
     * 
     * Define el tipo de comando (PS) y la expresión regular que valida
     * el formato exacto del comando.
     */
    public PsHeadService() {
        this.setJobType(Job.PS);
        // Solo acepta exactamente "aux | head"
        this.setRegexExpression("^aux\\s*\\|\\s*head$");
    }
}
