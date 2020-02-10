package br.com.jsampaio.sigem.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Janilson
 *
 */
public class Directory implements Directories {

    /**
     *
     */
    public static void criarDiretorios() {
        if (Files.notExists(Paths.get(APP_DATA_SIGEM))) {
            try {
                Files.createDirectory(Paths.get(APP_DATA_SIGEM));
            } catch (IOException e) {
                Logger.getLogger(Directory.class.getName()).log(Level.SEVERE, null, e);
            }
        }

        if (Files.notExists(APP_DATA_LOG)) {
            try {
                Files.createDirectory(APP_DATA_LOG);
            } catch (IOException e) {
                Logger.getLogger(Directory.class.getName()).log(Level.SEVERE, null, e);
            }
        }

        if (Files.notExists(APP_DATA_CONFIG)) {
            try {
                Files.createDirectory(APP_DATA_CONFIG);
            } catch (IOException e) {
                Logger.getLogger(Directory.class.getName()).log(Level.SEVERE, null, e);
            }
        }

        if (Files.notExists(APP_DATA_REPORTS)) {
            try {
                Files.createDirectory(APP_DATA_REPORTS);
            } catch (IOException e) {
                Logger.getLogger(Directory.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
}