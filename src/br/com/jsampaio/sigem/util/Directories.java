package br.com.jsampaio.sigem.util;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Janilson
 *
 */
public interface Directories {

    /**
     *
     */
    String SEPARATOR = File.separator;

    /**
     *
     */
    String SYSTEM_ROOT = System.getProperty("user.dir");

    /**
     *
     */
    String SYSTEM_SIGEM = SYSTEM_ROOT.concat(SEPARATOR).concat("src").concat(SEPARATOR).concat("sigem");

    /**
     *
     */
    String SYSTEM_REPORTS = SYSTEM_SIGEM.concat(SEPARATOR).concat("reports");
    
    /**
     * 
     */
    String SYSTEM_CONFIG = SYSTEM_SIGEM.concat(SEPARATOR).concat("config");

    /**
     *
     */
    String APP_DATA_ROOT = System.getProperty("user.home");

    /**
     *
     */
    String APP_DATA_SIGEM = APP_DATA_ROOT.concat(SEPARATOR).concat("AppData").concat(SEPARATOR).concat("Roaming").concat(SEPARATOR).concat("SIGEM");

    /**
     *
     */
    Path APP_DATA_LOG = Paths.get(APP_DATA_SIGEM.concat(SEPARATOR).concat("log"));

    /**
     *
     */
    Path APP_DATA_CONFIG = Paths.get(APP_DATA_SIGEM.concat(SEPARATOR).concat("config"));

    /**
     *
     */
    Path APP_DATA_REPORTS = Paths.get(APP_DATA_SIGEM.concat(SEPARATOR).concat("reports"));
}
