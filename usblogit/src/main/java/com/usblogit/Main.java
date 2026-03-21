package com.usblogit;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.SystemReader;

public class Main {
    private static final Path localConfigBackup = Path.of("trumpedConfig");
    private static final Path savedConfig = Path.of("tempConfig");
    private static final Path localTokenBackup = Path.of("trumpedToken");
    private static final Path savedToken = Path.of("tempToken");
    private static final String tokenFilename = ".git-credentials";
    public static void main(String[] args) {
        if(Files.exists(savedToken, LinkOption.NOFOLLOW_LINKS) && Files.exists(savedConfig, LinkOption.NOFOLLOW_LINKS)) {
            int action = 0;
            System.out.println("1) Log in");
            System.out.println("2) Reregister");
            Scanner scan = new Scanner(System.in);
            while (action != 1 && action != 2) {
                System.out.print("Select an action: ");
                action = scan.nextInt();
            }
            if (action == 1) logIn();
            else register();
        } else {
            register();
        }
    }


    private static void register() {
        try {
            FileBasedConfig userConfig = new FileBasedConfig(savedConfig.toAbsolutePath().toFile(), FS.detect());
            userConfig.load();
            Scanner scan = new Scanner(System.in);
            System.out.println("Input your login info:");
            System.out.print("Name: ");
            String name = scan.nextLine();
            System.out.print("Email: ");
            String email = scan.nextLine();
            System.out.print("Token: ");
            String token = scan.nextLine();

            userConfig.setString("user", null, "name", name);
            userConfig.setString("user", null, "email", email);
            userConfig.setString("credential", null, "helper", "store");
            userConfig.save();
            Files.writeString(savedToken, token);
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private static void logIn() {
        Path localConfig = getConfigPath();
        Path localToken = localConfig.getParent().resolve(tokenFilename);
        try {
            exchangeFiles(localConfig, savedConfig, localConfigBackup);
            exchangeFiles(localToken, savedToken, localTokenBackup);
            System.out.println("Press enter to log out:");
            System.in.read();
            restoreFile(localConfig, localConfigBackup);
            restoreFile(localToken, localTokenBackup);
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private static Path getConfigPath() {
        try {
            SystemReader reader = SystemReader.getInstance();
            File userConfig = ((FileBasedConfig) reader.getUserConfig()).getFile();
            String path = userConfig.getPath();
            return Path.of(path);
        } catch(IOException e) {
            System.out.println("IO error: " + e.toString());
            System.exit(1);
            return Path.of("");
        } catch (ConfigInvalidException e) {
            System.out.println("Config exception: " + e.toString());
            System.exit(1);
            return Path.of("");
        }
    }


    private static void exchangeFiles(Path file, Path newFile, Path backup) throws IOException {
        try {
            Files.copy(file, backup, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            if (!Files.notExists(file, LinkOption.NOFOLLOW_LINKS)) {
                // Caso verdaderamente problematico
                throw e;
            }
        } finally {
            Files.copy(newFile, file, StandardCopyOption.REPLACE_EXISTING);
        }
    }


    private static void restoreFile(Path file, Path backup) throws IOException {
        try {
            Files.copy(backup, file, StandardCopyOption.REPLACE_EXISTING);
            Files.delete(backup);
        } catch (IOException e) {
            if (Files.notExists(backup, LinkOption.NOFOLLOW_LINKS)) {
                // Nada que restaurar, simplemente borra
                Files.delete(file);
            } else {
                System.err.println("I/O error: " + e.toString());
                System.err.println("Couldn't restore local file " + file.toString() + " with the backup at " + backup.toString());
                System.err.println("Please do so manually");
            }
        }
    }

    public static String getTokenFilename() {
        return tokenFilename;
    }
}