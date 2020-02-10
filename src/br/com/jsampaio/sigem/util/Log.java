/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.util;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;

/**
 *
 * @author Janilson
 */
public final class Log implements Directories {
    
    /**
     * 
     * @param exception 
     * @param clazz 
     */
    public static void saveLog(Exception exception, Class clazz) {
        try {
            final String date = String.format(clazz.getSimpleName() + "-%1$td%1$tm%1$tY-%1$tH%1$tM%1$tS", Calendar.getInstance());
            final Path file = Paths.get(APP_DATA_LOG.toString().concat(SEPARATOR).concat(date).concat(".txt"));
            exception.printStackTrace(new PrintStream(Files.newOutputStream(file), true));
        } catch (IOException ex) {}
    }
    
    /**
     * 
     * @param error 
     * @param clazz 
     */
    public static void saveLog(Error error, Class clazz) {
        try {
            final String date = String.format(clazz.getSimpleName() + "-%1$td%1$tm%1$tY-%1$tH%1$tM%1$tS", Calendar.getInstance());
            final Path file = Paths.get(APP_DATA_LOG.toString().concat(SEPARATOR).concat(date).concat(".txt"));
            error.printStackTrace(new PrintStream(Files.newOutputStream(file), true));
        } catch (IOException ex) {}
    }
}
