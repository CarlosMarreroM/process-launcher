package com.dam.process_launcher.repositories.file;

import com.dam.process_launcher.repositories.interfaces.JobRepository;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Implementación del repositorio que guarda las ejecuciones en un fichero .txt
 * 
 * Cada ejecución se añade al final del fichero sin borrar el contenido
 * anterior.
 * Si el fichero no existe, se crea automáticamente.
 */
@Repository
public class FileJobRepository implements JobRepository {

    /** Nombre del archivo donde se guardan las ejecuciones. */
    String fileName;

    /** Ruta del archivo en el sistema de ficheros. */
    static Path path;

    /**
     * Asigna un nuevo nombre de fichero para guardar las ejecuciones.
     * 
     * @param fileName nombre del fichero
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Constructor por defecto.
     * Si no se indica nombre, se usa "mis_procesos.txt".
     * Si el fichero no existe, se crea automáticamente.
     */
    public FileJobRepository() {
        this.fileName = "mis_procesos.txt";
        this.path = Paths.get(System.getProperty("user.dir"), fileName);

        // Si el archivo no existe, lo creamos vacío
        try {
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
        } catch (IOException e) {
            System.err.println("[ERROR] No se pudo crear el archivo: " + e.getMessage());
        }
    }

    /**
     * Añade una nueva ejecución de proceso al fichero.
     *
     * @param jobExecution ejecución a almacenar
     * @return true si la operación fue correcta, false si hubo error
     */
    @Override
    public boolean add(String text) {
        try {
            Files.write(path, text.getBytes(), StandardOpenOption.APPEND);
            return true;
        } catch (IOException e) {
            System.err.println("[ERROR] No se pudo escribir en el archivo: " + e.getMessage());
        }

        return false;
    }
}
