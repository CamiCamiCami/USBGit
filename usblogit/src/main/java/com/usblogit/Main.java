package com.usblogit;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.util.SystemReader;

public class Main {
    private static final Path saveConfig = Path.of("trumpedConfig");
    private static final Path tempConfig = Path.of("tempConfig");
    public static void main(String[] args) {
        Path config = getConfigPath();
        try {
            exchangeConfigFiles(config);
            System.out.println("Press enter to log out:");
            System.in.read();
        } catch (IOException e) {
            System.err.println("I/O Error: " + e.toString());
            System.exit(1);
        }
        try {
            restoreConfigFile(config);
        } catch (IOException e) {
            System.err.println("I/O Error: " + e.getMessage());
            System.err.println("No pudo restaurar el archivo " + config.toString() + " con " + saveConfig.toString());
            System.err.println("Por favor hagalo manualmente");
        }
    }


    private static Path getConfigPath() {
        try {
            SystemReader reader = SystemReader.getInstance();
            File userConfig = ((FileBasedConfig) reader.getUserConfig()).getFile();
            String path = userConfig.getPath();
            return Path.of(path);
        } catch(IOException e) {
            System.out.println("IO Exception: " + e.getMessage());
            System.exit(1);
            return Path.of("");
        } catch (ConfigInvalidException e) {
            System.out.println("Config Exception: " + e.getMessage());
            System.exit(1);
            return Path.of("");
        }
    } 


    
    private static void exchangeConfigFiles(Path config) throws IOException {
        try {
            Files.copy(config, saveConfig, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            if (!Files.notExists(config, LinkOption.NOFOLLOW_LINKS)) {
                // Caso verdaderamente problematico
                throw e;
            }
        } finally {
            Files.copy(tempConfig, config, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private static void restoreConfigFile(Path config) throws IOException {
        try {
            Files.copy(saveConfig, config, StandardCopyOption.REPLACE_EXISTING);
            Files.delete(saveConfig);
        } catch (IOException e) {
            if (Files.notExists(saveConfig, LinkOption.NOFOLLOW_LINKS)) {
                // Nada que restaurar, simplemente borra
                Files.delete(config);
            } else {
                // Caso verdaderamente problematico
                throw e;
            }
        }
    }
}