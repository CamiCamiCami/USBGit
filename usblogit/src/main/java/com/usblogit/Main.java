package com.usblogit;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.util.SystemReader;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        try {
            SystemReader reader = SystemReader.getInstance();
            File userConfig = ((FileBasedConfig) reader.getUserConfig()).getFile();
            String path = userConfig.getPath();
            System.out.println(path);
            
        } catch(Exception e) {
            System.out.println("Error: " + e.get());
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


    private static final Path saveConfig = Path("../../../resources/trumpedConfig");
    private static void exchangeConfigFiles(Path config, Path tempConfig) throws IOException {
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
}