package com.mattmx.tartarus.scenes.guis;

import com.mattmx.tartarus.gameengine.Window;
import com.mattmx.tartarus.scenes.Scene;
import com.mattmx.tartarus.scenes.SceneInitializer;
import com.mattmx.tartarus.world.GameWorld;
import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;
import imgui.type.ImLong;
import imgui.type.ImString;

public class NewWorldInitializer extends SceneInitializer {
    private SceneInitializer parent;
    private ImString worldName = new ImString();
    private ImString worldSeed = new ImString();

    public NewWorldInitializer(SceneInitializer parent) {
        this.parent = parent;
    }

    @Override
    public void init(Scene scene) {

    }

    @Override
    public void loadResources(Scene scene) {

    }

    @Override
    public void imgui() {
        ImGui.pushStyleColor(ImGuiCol.TitleBg, ImGui.getColorU32(0f, 0f, 0f, 0.5f));
        ImGui.pushStyleColor(ImGuiCol.TitleBgActive, ImGui.getColorU32(0f, 0f, 0f, 0.5f));
        ImGui.pushStyleColor(ImGuiCol.WindowBg, ImGui.getColorU32(0f, 0f, 0f, 0.5f));
        ImGui.setNextWindowSize(300, 400);
        ImGui.setNextWindowPos(Window.getWidth() / 2 - 150, Window.getHeight() / 2 - 200);
        ImGui.begin("NEW WORLD", new ImBoolean(false), ImGuiWindowFlags.NoDocking |
                ImGuiWindowFlags.NoResize |
                ImGuiWindowFlags.NoMove |
                ImGuiWindowFlags.NoSavedSettings |
                ImGuiWindowFlags.NoCollapse
        );
        ImGui.pushStyleColor(ImGuiCol.Button, ImGui.getColorU32(0f, 0f, 0f, 0.3f));
        ImGui.pushStyleColor(ImGuiCol.ButtonHovered, ImGui.getColorU32(0f, 0f, 0f, 0.5f));
        ImGui.pushStyleColor(ImGuiCol.ButtonActive, ImGui.getColorU32(1f, 1f, 1f, 0.1f));

        // Options
        ImGui.inputText("World Name", worldName);
        ImGui.inputTextWithHint("Seed", "Leave blank for generated.", worldSeed);

        if (ImGui.button("Cancel")) {
            Window.changeScene(parent);
        }
        ImGui.sameLine();
        if (ImGui.button("Create")) {
            // Create new world, and switch to world screen.
            GameWorld world = new GameWorld(worldName.get(), worldSeed.get());
            world.createScene();
            world.init();
        }

        ImGui.popStyleColor(6);
        ImGui.end();
    }
}
