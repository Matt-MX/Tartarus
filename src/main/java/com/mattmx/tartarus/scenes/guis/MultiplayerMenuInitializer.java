package com.mattmx.tartarus.scenes.guis;

import com.mattmx.tartarus.gameengine.Window;
import com.mattmx.tartarus.scenes.Scene;
import com.mattmx.tartarus.scenes.SceneInitializer;
import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;
import imgui.type.ImString;

public class MultiplayerMenuInitializer extends SceneInitializer {
    private ImString serverAddress = new ImString();

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
        ImGui.setNextWindowSize(300, 200);
        ImGui.setNextWindowPos(Window.getWidth() / 2 - 150, Window.getHeight() / 2 - 200);
        ImGui.begin("MULTIPLAYER", new ImBoolean(false), ImGuiWindowFlags.NoDocking |
                ImGuiWindowFlags.NoResize |
                ImGuiWindowFlags.NoMove |
                ImGuiWindowFlags.NoSavedSettings |
                ImGuiWindowFlags.NoCollapse
        );
        ImGui.pushStyleColor(ImGuiCol.Button, ImGui.getColorU32(0f, 0f, 0f, 0.3f));
        ImGui.pushStyleColor(ImGuiCol.ButtonHovered, ImGui.getColorU32(0f, 0f, 0f, 0.5f));
        ImGui.pushStyleColor(ImGuiCol.ButtonActive, ImGui.getColorU32(1f, 1f, 1f, 0.1f));
        ImGui.pushStyleColor(ImGuiCol.ChildBg, ImGui.getColorU32(0f, 0f, 0f, 0.5f));

        ImGui.spacing();

        ImGui.inputText("Server Address", serverAddress);
        if (ImGui.button("Cancel")) {
            Window.changeScene(new MainMenuInitializer());
        }
        ImGui.sameLine();
        if (ImGui.button("Connect")) {

        }

        ImGui.popStyleColor(7);
        ImGui.end();
    }
}
