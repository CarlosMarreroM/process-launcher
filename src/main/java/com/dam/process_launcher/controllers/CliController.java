package com.dam.process_launcher.controllers;

import java.util.Scanner;

import com.dam.process_launcher.services.LsofService;
import com.dam.process_launcher.services.PsHeadService;
import com.dam.process_launcher.services.TopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

/**
 * Controlador principal de la aplicación CLI.
 * 
 * Muestra el menú de comandos disponibles, recibe la entrada del usuario
 * y delega la ejecución al servicio correspondiente.
 * 
 * Para salir de la aplicación, se debe presionar Ctrl+C.
 * 
 * Ejemplo de uso:
 * - lsof -i
 * - top -bn1
 * - ps aux | head
 * 
 */
@Service
public class CliController implements CommandLineRunner {

    @Autowired
    private LsofService lsofService;

    @Autowired
    private TopService topService;

    @Autowired
    private PsHeadService psHeadService;

    /**
     * Método principal que se ejecuta al iniciar la aplicación Spring Boot.
     * 
     * Muestra el menú, espera la entrada del usuario y ejecuta el comando
     * correspondiente.
     *
     * @param args argumentos de línea de comandos (no se usan aquí)
     */
    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Lanzador de Procesos (CLI) Linux ===");
        System.out.println("Comandos disponibles:");
        System.out.println("  lsof -i");
        System.out.println("  top -bn1");
        System.out.println("  ps aux | head");
        System.out.println("========================================");
        System.out.println("Escribe un comando o presiona Ctrl+C para salir:");
        System.out.println();

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("[WARN] No se ha introducido ningún comando.");
                continue;
            }

            // Detectar qué comando ejecutar
            if (input.startsWith("lsof")) {
                lsofService.processLine(input);
            } else if (input.startsWith("top")) {
                topService.processLine(input);
            } else if (input.startsWith("ps")) {
                psHeadService.processLine(input);
            } else {
                System.out.println("[ERROR] Comando no reconocido: " + input);
            }

            System.out.println();
        }
    }
}
