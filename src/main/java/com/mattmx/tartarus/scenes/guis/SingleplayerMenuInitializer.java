package com.mattmx.tartarus.scenes.guis;

import com.mattmx.tartarus.gameengine.Window;
import com.mattmx.tartarus.scenes.Scene;
import com.mattmx.tartarus.scenes.SceneInitializer;
import com.mattmx.tartarus.world.GameWorld;
import com.mattmx.tartarus.world.WorldInfoFile;
import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;
import org.apache.commons.io.FileUtils;
import org.jbox2d.dynamics.World;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

public class SingleplayerMenuInitializer extends SceneInitializer {

    private List<WorldSave> saves = new ArrayList<>();
    WorldSave selected = null;

    @Override
    public void init(Scene scene) {
        saves = getSaves();
    }

    @Override
    public void loadResources(Scene scene) {

    }

    @Override
    public void imgui() {
        ImGui.pushStyleColor(ImGuiCol.TitleBg, ImGui.getColorU32(0f, 0f, 0f, 0.5f));
        ImGui.pushStyleColor(ImGuiCol.TitleBgActive, ImGui.getColorU32(0f, 0f, 0f, 0.5f));
        ImGui.pushStyleColor(ImGuiCol.WindowBg, ImGui.getColorU32(0f, 0f, 0f, 0.5f));
        ImGui.setNextWindowSize(600, 700);
        ImGui.setNextWindowPos(Window.getWidth() / 2 - 300, Window.getHeight() / 2 - 350);
        ImGui.begin("SINGLEPLAYER", new ImBoolean(false), ImGuiWindowFlags.NoDocking |
                ImGuiWindowFlags.NoResize |
                ImGuiWindowFlags.NoMove |
                ImGuiWindowFlags.NoSavedSettings |
                ImGuiWindowFlags.NoCollapse
        );
        ImGui.pushStyleColor(ImGuiCol.Button, ImGui.getColorU32(0f, 0f, 0f, 0.3f));
        ImGui.pushStyleColor(ImGuiCol.ButtonHovered, ImGui.getColorU32(0f, 0f, 0f, 0.5f));
        ImGui.pushStyleColor(ImGuiCol.ButtonActive, ImGui.getColorU32(1f, 1f, 1f, 0.1f));

        ImGui.beginTable("WORLD SAVES", 3);
        if (saves.size() == 0) {
            ImGui.text("No saves detected!");
            ImGui.text("Create a new one!");
        }
        for (WorldSave save : saves) {
            ImGui.tableNextColumn();
            if (ImGui.button(save.name)) {
                selected = save;
            }
            ImGui.tableNextColumn();
            ImGui.text(save.modified);
            ImGui.tableNextRow();
        }
        ImGui.endTable();

        if (selected != null) ImGui.textColored(ImGui.getColorU32(1f, 0f, 0f, 1f), selected.name);

        if (ImGui.button("Load world")) {
            // Load selected
            if (selected != null) {
                GameWorld world = new GameWorld(selected.name, selected.seed);
                world.createScene();
                world.init();
            }
        }
        ImGui.sameLine();
        if (ImGui.button("Delete")) {
            // Delete selected
            if (selected != null) {
                boolean deleted = deleteWorld(selected);
                saves = getSaves();
                selected = null;
            }
        }
        ImGui.sameLine();
        if (ImGui.button("Create New")) {
            // Create a new
            Window.changeScene(new NewWorldInitializer(this));
        }
        ImGui.sameLine();
        if (ImGui.button("Cancel")) {
            Window.changeScene(new MainMenuInitializer());
        }

        ImGui.popStyleColor(6);
        ImGui.end();
    }

    private class WorldSave {
        public String name;
        public String filepath;
        public String modified;
        public String seed;
        public WorldSave(String name, String filepath, String modified, String seed) {
            this.name = name;
            this.filepath = filepath;
            this.modified = modified;
            this.seed = seed;
        }
    }

    public List<WorldSave> getSaves() {
        String s = Paths.get("").toAbsolutePath().toString();
        List<WorldSave> ret = new ArrayList<>();
        File dir = new File(s + "/saves/");
        dir.mkdirs();
        String[] paths = dir.list();
        for (String p : paths) {
            WorldInfoFile w =WorldInfoFile.from(dir.getAbsolutePath() + "/" + p);
            if (w != null) {
                File f = new File(p);
                ret.add(new WorldSave(f.getName(), f.getAbsolutePath(), w.modified, w.seed));
            }
        }
        return ret;
    }

    private boolean deleteWorld(WorldSave world) {
        try {
            FileUtils.deleteDirectory(new File(world.filepath));
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }
    }
}
