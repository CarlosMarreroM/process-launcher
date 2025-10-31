package com.dam.process_launcher.domain;

/**
 * Enum que representa los distintos procesos del sistema
 * que la aplicación puede ejecutar.
 * 
 * Cada valor corresponde a un comando Linux permitido:
 * <ul>
 * <li>TOP: Ejecuta "top"</li>
 * <li>LSOF: Ejecuta "lsof -i"</li>
 * <li>PS: Ejecuta "ps aux | head"</li>
 * </ul>
 */
public enum Job {
    TOP, LSOF, PS
}
