package com.mattmx.tartarus.world;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jbox2d.dynamics.World;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.stream.Stream;

public class WorldInfoFile {
    private static Gson gson = new GsonBuilder()
            .setPrettyPrinting().create();
    public static String ext = "/info.json";
    /*
     * File saved to recognise a valid world
     */
    public String version = "1.0";
    public String description = "Default world description";
    public String seed = "-1";
    public String modified = "";
    public int lastX, lastY;

    public boolean save(String filepath) {
        File f = new File(filepath + ext);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        try {
            String pattern = "yyyy-MM-dd";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            modified = simpleDateFormat.format(new Date());
            FileWriter writer = new FileWriter(f, false);
            writer.write(gson.toJson(this));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static WorldInfoFile from(String filepath) {
        File f = new File(filepath + ext);
        if (f.exists()) {
            String content = getFileContent(f.getAbsolutePath());
            WorldInfoFile worldInfoFile = gson.fromJson(content, WorldInfoFile.class);
            return worldInfoFile;
        }
        return null;
    }

    private static String getFileContent(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // System.out.println(contentBuilder.toString());
        return contentBuilder.toString();
    }

    public static Gson getGsonFormat() {
        return gson;
    }
}
