package com.dam.process_launcher.services;

import com.dam.process_launcher.domain.Job;
import org.springframework.stereotype.Component;

/**
 * Servicio encargado de ejecutar y validar el comando TOP.
 *
 * Este comando muestra los procesos activos del sistema.
 *
 * Solo se permite el comando con los parámetros exactos:
 * top -bn1
 *
 */
@Component
public class TopService extends CommandServiceAbstract {

    /**
     * Constructor de la clase TopService.
     *
     * Define el tipo de comando (TOP) y la expresión regular que
     * valida únicamente el parámetro "-bn1".
     */
    public TopService() {
        this.setJobType(Job.TOP);
        this.setRegexExpression("^\\-bn1$");
    }
}
