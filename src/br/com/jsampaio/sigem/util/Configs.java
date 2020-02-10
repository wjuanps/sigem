package br.com.jsampaio.sigem.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Janilson
 */
public final class Configs {

    public static void loadConfigs() {
        Config.load();
        ConfigTela.load();
    }

    /**
     *
     */
    public static class Config {

        private static final Map<String, String> configs = new HashMap<>();

        /**
         *
         */
        public static void load() {
            final Path path = Paths.get(Arquivo.APP_DATA_CONFIG.toString()
                    .concat(Arquivo.SEPARATOR).concat("config.ini"));

            try (BufferedReader reader = Files.newBufferedReader(path,
                    StandardCharsets.UTF_8)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.startsWith("#") && !line.isEmpty()) {
                        try {
                            final String[] config = line.split(":");
                            configs.put(config[0], config[1]);
                        } catch (ArrayIndexOutOfBoundsException o) {
                        }
                    }
                }
            } catch (IOException ioe) {
                Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ioe);
            }
        }

        /**
         *
         * @param key
         * @return
         */
        public static String getConfig(String key) {
            return configs.get(key);
        }

        /**
         *
         * @return
         */
        public static Map<String, String> getConfigs() {
            return configs;
        }
    }

    /**
     *
     */
    public static class ConfigTela {

        private static final Map<String, String> configs = new HashMap<>();

        /**
         *
         */
        public static void load() {
            final Path path = Paths.get(Arquivo.APP_DATA_CONFIG.toString().concat(Arquivo.SEPARATOR)
                    .concat("config-telas-").concat(Config.getConfig("Idioma")).concat(".ini"));

            try (BufferedReader reader = Files.newBufferedReader(path,
                    StandardCharsets.UTF_8)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.startsWith("#") && !line.isEmpty()) {
                        try {
                            final String[] config = line.split(":");
                            configs.put(config[0], config[1]);
                        } catch (ArrayIndexOutOfBoundsException o) {
                        }
                    }
                }
            } catch (IOException ioe) {
                Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ioe);
            }
        }

        /**
         *
         * @param key
         * @return
         */
        public static String getConfig(String key) {
            return configs.get(key);
        }

        /**
         *
         * @return
         */
        public static Map<String, String> getConfigs() {
            return configs;
        }
    }
}
