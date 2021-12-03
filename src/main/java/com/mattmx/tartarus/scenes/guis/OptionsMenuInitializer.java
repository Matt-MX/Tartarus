package com.mattmx.tartarus.scenes.guis;

import com.mattmx.tartarus.components.Sprite;
import com.mattmx.tartarus.gameengine.GameObject;
import com.mattmx.tartarus.gameengine.Prefabs;
import com.mattmx.tartarus.gameengine.Window;
import com.mattmx.tartarus.gameengine.renderer.Texture;
import com.mattmx.tartarus.scenes.Scene;
import com.mattmx.tartarus.scenes.SceneInitializer;
import com.mattmx.tartarus.util.AssetPool;
import com.mattmx.tartarus.util.SettingsFile;
import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;
import org.joml.Vector2f;

public class OptionsMenuInitializer extends SceneInitializer {
    SceneInitializer parent;

    public OptionsMenuInitializer(SceneInitializer parent) {
        this.parent = parent;
    }

    @Override
    public void init(Scene scene) {
        Sprite bg = new Sprite();
        bg.setTexture(AssetPool.getTexture("assets/images/backgrounds/main_menu.png"));
        GameObject o = Prefabs.generateSpriteObject(bg, 16,  9);
        o.transform.position = new Vector2f(0, 0);
        scene.camera().setZoom(2f);
        scene.camera().position = new Vector2f( -4, -3);
        scene.addGameObjectToScene(o);
        scene.start();
    }

    @Override
    public void loadResources(Scene scene) {
        AssetPool.getTexture("assets/images/backgrounds/main_menu.png");
    }

    @Override
    public void imgui() {
        if (parent instanceof MainMenuInitializer) {
            ImGui.setNextWindowPos(0, 0);
            ImGui.setNextWindowSize(SettingsFile.get().WINDOW_WIDTH, SettingsFile.get().WINDOW_HEIGHT);
            ImGui.begin("Background", ImGuiWindowFlags.NoScrollbar |
                    ImGuiWindowFlags.NoScrollWithMouse |
                    ImGuiWindowFlags.NoMove |
                    ImGuiWindowFlags.NoResize |
                    ImGuiWindowFlags.NoTitleBar |
                    ImGuiWindowFlags.NoDocking |
                    ImGuiWindowFlags.NoBackground |
                    ImGuiWindowFlags.NoBringToFrontOnFocus);
            Texture tex = Window.getFramebuffer().getTexture();
            ImGui.image(tex.getId(), tex.getWidth(), tex.getHeight(), 0, 1, 1, 0);
            ImGui.end();
        }

        ImGui.pushStyleColor(ImGuiCol.TitleBg, ImGui.getColorU32(0f, 0f, 0f, 0.5f));
        ImGui.pushStyleColor(ImGuiCol.TitleBgActive, ImGui.getColorU32(0f, 0f, 0f, 0.5f));
        ImGui.pushStyleColor(ImGuiCol.WindowBg, ImGui.getColorU32(0f, 0f, 0f, 0.5f));
        ImGui.setNextWindowSize(600, 700);
        ImGui.setNextWindowPos(Window.getWidth() / 2 - 300, Window.getHeight() / 2 - 350);
        ImGui.begin("OPTIONS", new ImBoolean(false), ImGuiWindowFlags.NoDocking |
                ImGuiWindowFlags.NoResize |
                ImGuiWindowFlags.NoMove |
                ImGuiWindowFlags.NoSavedSettings |
                ImGuiWindowFlags.NoCollapse
        );
        ImGui.pushStyleColor(ImGuiCol.Button, ImGui.getColorU32(0f, 0f, 0f, 0.3f));
        ImGui.pushStyleColor(ImGuiCol.ButtonHovered, ImGui.getColorU32(0f, 0f, 0f, 0.5f));
        ImGui.pushStyleColor(ImGuiCol.ButtonActive, ImGui.getColorU32(1f, 1f, 1f, 0.1f));

        // Options


        if (ImGui.button("Cancel")) {
            Window.changeScene(parent);
        }

        ImGui.popStyleColor(6);
        ImGui.end();
    }
}
