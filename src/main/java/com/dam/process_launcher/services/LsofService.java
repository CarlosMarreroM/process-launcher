package com.dam.process_launcher.services;

import com.dam.process_launcher.domain.Job;
import org.springframework.stereotype.Component;

/**
 * Servicio encargado de ejecutar y validar el comando LSOF.
 * 
 * El comando "lsof -i" muestra las conexiones de red abiertas en el sistema.
 * 
 * Solo se permite el comando exacto:
 * lsof -i
 * 
 * Cualquier otro parámetro hará que el comando se considere inválido.
 * 
 * Ejemplo válido:
 * lsof -i
 * 
 */
@Component
public class LsofService extends CommandServiceAbstract {

    /**
     * Constructor de la clase LsofService.
     * 
     * Define el tipo de comando (LSOF) y la expresión regular que valida únicamente
     * el parámetro "-i".
     */
    public LsofService() {
        this.setJobType(Job.LSOF);
        this.setRegexExpression("^-i$");
    }
}
