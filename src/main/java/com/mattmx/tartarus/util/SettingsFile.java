package com.mattmx.tartarus.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.lwjgl.glfw.GLFW.*;

public class SettingsFile {
    static transient Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();
    static transient SettingsFile instance;
    static transient String fp = "options.txt";

    // Window settings
    public int WINDOW_WIDTH = 1920;
    public int WINDOW_HEIGHT = 1080;
    public int WINDOW_POSX = 0;
    public int WINDOW_POSY = 23;

    // Keybinds
    public int KEY_LEFT = GLFW_KEY_A;
    public int KEY_RIGHT = GLFW_KEY_D;
    public int KEY_UP = GLFW_KEY_SPACE;
    public int MENU = GLFW_KEY_ESCAPE;
    public int INVENTORY = GLFW_KEY_E;

    // Audio

    public static void save() {
        File file = new File(fp);
        try {
            file.createNewFile();
            FileWriter f = new FileWriter(file, false);
            f.write(gson.toJson(instance));
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SettingsFile get() {
        if (instance == null) {
            instance = new SettingsFile();
            File file = new File(fp);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                    FileWriter f = new FileWriter(file);
                    f.write(gson.toJson(instance));
                    f.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                instance = gson.fromJson(getFileContent(fp), SettingsFile.class);
            }
        }
        return instance;
    }

    private static String getFileContent(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }
}
