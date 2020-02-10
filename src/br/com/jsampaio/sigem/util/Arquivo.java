package br.com.jsampaio.sigem.util;

import static br.com.jsampaio.sigem.util.Directories.APP_DATA_REPORTS;
import static br.com.jsampaio.sigem.util.Directories.SEPARATOR;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Janilson
 *
 */
public final class Arquivo extends Directory {

    final static Map<String, Path> relatorios = new HashMap<>();

    /**
     *
     * @param source
     * @param target
     */
    public static void copiarArquivos(Path source, Path target) {
        if (Files.isDirectory(source)) {
            try (DirectoryStream<Path> paths = Files.newDirectoryStream(source)) {
                for (Path p : paths) {
                    if (Files.isRegularFile(p)) {
                        Path destino;
                        destino = Paths.get(target.toString()
                                .concat(SEPARATOR).concat(p.getFileName().toString()));
                        if (Files.notExists(destino)) {
                            Files.copy(p, destino, StandardCopyOption.REPLACE_EXISTING);
                        }
                    } else {
                        Path path2 = Paths.get(APP_DATA_REPORTS.toString().concat(SEPARATOR).concat(p.getFileName().toString()));
                        if (Files.notExists(path2)) {
                            Files.createDirectory(path2);
                            copiarArquivos(p, path2);
                        }
                    }
                }
            } catch (IOException e) {
                Logger.getLogger(Arquivo.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    /**
     *
     */
    public static void carregarRelatorios() {
        try (DirectoryStream<Path> paths = Files.newDirectoryStream(APP_DATA_REPORTS)) {
            for (Path path : paths) {
                final String key = path.getFileName().toString().replace('.', ',').split(",")[0];
                relatorios.put(key, path);
            }
        } catch (IOException | ArrayIndexOutOfBoundsException e) {
            Logger.getLogger(Arquivo.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public static String getRelatorio(String relatorio) {
        return relatorios.get(relatorio).toString();
    }

    public static Map<String, Path> getRelatorios() {
        return relatorios;
    }
}