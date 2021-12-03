package com.mattmx.tartarus.util;

import com.mattmx.tartarus.components.Spritesheet;
import com.mattmx.tartarus.gameengine.Sound;
import com.mattmx.tartarus.gameengine.renderer.Shader;
import com.mattmx.tartarus.gameengine.renderer.Texture;

import java.util.*;
import java.io.File;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AssetPool {
    private static Map<String, Shader> shaders = new HashMap<>();
    private static Map<String, Texture> textures = new HashMap<>();
    private static Map<String, Spritesheet> spritesheets = new HashMap<>();
    private static Map<String, Sound> sounds = new HashMap<>();

    public static Shader getShader(String resourceName) {
        File file = new File(resourceName);
        if (AssetPool.shaders.containsKey(file.getAbsolutePath())) {
            return AssetPool.shaders.get(file.getAbsolutePath());
        } else {
            Shader shader = new Shader(resourceName);
            shader.compile();
            AssetPool.shaders.put(file.getAbsolutePath(), shader);
            return shader;
        }
    }

    public static Texture getTexture(String resourceName) {
        File file = new File(resourceName);
        if (AssetPool.textures.containsKey(file.getAbsolutePath())) {
            return AssetPool.textures.get(file.getAbsolutePath());
        } else {
            Texture texture = new Texture();
            texture.init(resourceName);
            AssetPool.textures.put(file.getAbsolutePath(), texture);
            return texture;
        }
    }

    public static void addSpritesheet(String resourceName, Spritesheet spritesheet) {
        File file = new File(resourceName);
        if (!AssetPool.spritesheets.containsKey(file.getAbsolutePath())) {
            AssetPool.spritesheets.put(file.getAbsolutePath(), spritesheet);
        }
    }

    public static Spritesheet getSpritesheet(String resourceName) {
        File file = new File(resourceName);
        if (!AssetPool.spritesheets.containsKey(file.getAbsolutePath())) {
            assert false : "Error: Tried to access spritesheet '" + resourceName + "' and it has not been added to asset pool.";
        }
        return AssetPool.spritesheets.getOrDefault(file.getAbsolutePath(), null);
    }

    public static Collection<Sound> getAllSounds() {
        return sounds.values();
    }

    public static Sound getSound(String soundFile) {
        File file = new File(soundFile);
        if (sounds.containsKey(file.getAbsolutePath())) {
            return sounds.get(file.getAbsolutePath());
        } else {
            assert false : "Sound file not added '" + soundFile + "'";
        }
        return null;
    }

    public static Sound addSound(String soundFile, boolean loops) {
        File file = new File(soundFile);
        if (sounds.containsKey(file.getAbsolutePath())) {
            return sounds.get(file.getAbsolutePath());
        } else {
            Sound sound = new Sound(file.getAbsolutePath(), loops);
            AssetPool.sounds.put(file.getAbsolutePath(), sound);
            return sound;
        }
    }

    public static void loadDefaultResources() {
        String assetsFolder = Paths.get("").toAbsolutePath() + "/assets/";
        String imagesFolder = assetsFolder + "images/";
        String soundsFolder = assetsFolder + "sounds/";
        for (String s : getFilesRecursively(new File(imagesFolder + "block"))) {
            if (s.endsWith(".png")) AssetPool.getTexture("assets/" + s.substring(assetsFolder.length()));
        }
        for (String s : getFilesRecursively(new File(soundsFolder))) {
            if (s.endsWith(".ogg")) AssetPool.addSound("assets/" + s.substring(assetsFolder.length()), false);
        }
    }

    private static List<String> getFilesRecursively(File dir){
        List<String> ls = new ArrayList<String>();
        if (dir.isDirectory())
            for (File fObj : dir.listFiles()) {
                if(fObj.isDirectory()) {
                    ls.add(String.valueOf(fObj));
                    ls.addAll(getFilesRecursively(fObj));
                } else {
                    ls.add(String.valueOf(fObj));
                }
            }
        else
            ls.add(String.valueOf(dir));

        return ls;
    }
}