package com.mattmx.tartarus.scenes.guis;

import com.mattmx.tartarus.components.*;
import com.mattmx.tartarus.gameengine.*;
import com.mattmx.tartarus.gameengine.renderer.Texture;
import com.mattmx.tartarus.scenes.Scene;
import com.mattmx.tartarus.scenes.SceneInitializer;
import com.mattmx.tartarus.util.AssetPool;
import com.mattmx.tartarus.util.Authentication;
import com.mattmx.tartarus.util.SettingsFile;
import imgui.*;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;
import org.joml.Vector2f;

public class MainMenuInitializer extends SceneInitializer {

    @Override
    public void init(Scene scene) {
        Sprite bg = new Sprite();
        bg.setTexture(AssetPool.getTexture("assets/images/backgrounds/main_menu1.png"));
        GameObject o = Prefabs.generateSpriteObject(bg, 16,  9);
        o.transform.position = new Vector2f(0, 0);
        scene.camera().setZoom(2f);
        scene.camera().position = new Vector2f( -4, -3);
        scene.addGameObjectToScene(o);
        scene.start();
    }

    @Override
    public void loadResources(Scene scene) {
        AssetPool.getTexture("assets/images/backgrounds/main_menu1.png");
    }

    @Override
    public void imgui() {
        ImGui.setNextWindowPos(0, 0);
        ImGui.setNextWindowSize(SettingsFile.get().WINDOW_WIDTH, SettingsFile.get().WINDOW_HEIGHT);
        ImGui.begin("Background", ImGuiWindowFlags.NoScrollbar |
                ImGuiWindowFlags.NoScrollWithMouse |
                ImGuiWindowFlags.NoMove |
                ImGuiWindowFlags.NoResize |
                ImGuiWindowFlags.NoTitleBar |
                ImGuiWindowFlags.NoDocking |
                ImGuiWindowFlags.NoBackground |
                ImGuiWindowFlags.NoBringToFrontOnFocus
        );
        Texture tex = Window.getFramebuffer().getTexture();
        ImGui.image(tex.getId(), tex.getWidth(), tex.getHeight(), 0, 1, 1, 0);
        ImGui.end();

        ImGui.setNextWindowSize(200, 50);
        ImGui.setNextWindowPos(20, Window.getHeight() - 270);
        ImGui.begin("TITLE", ImGuiWindowFlags.NoScrollbar |
                ImGuiWindowFlags.NoScrollWithMouse |
                ImGuiWindowFlags.NoMove |
                ImGuiWindowFlags.NoResize |
                ImGuiWindowFlags.NoTitleBar |
                ImGuiWindowFlags.NoBackground
        );
        ImGui.pushFont(Window.getImguiLayer().title);
        ImGui.text("TARTARUS");
        ImGui.popFont();
        ImGui.end();

        ImGui.setNextWindowSize(200, 200);
        ImGui.setNextWindowPos(20, Window.getHeight() - 220);
        ImGui.begin("Tartarus", new ImBoolean(false), ImGuiWindowFlags.NoDocking |
                ImGuiWindowFlags.NoResize |
                ImGuiWindowFlags.NoScrollbar |
                ImGuiWindowFlags.NoScrollWithMouse |
                ImGuiWindowFlags.NoTitleBar |
                ImGuiWindowFlags.NoMove |
                ImGuiWindowFlags.NoBackground |
                ImGuiWindowFlags.NoSavedSettings
        );
        ImGui.pushStyleColor(ImGuiCol.Button, ImGui.getColorU32(0f, 0f, 0f, 0.3f));
        ImGui.pushStyleColor(ImGuiCol.ButtonHovered, ImGui.getColorU32(0f, 0f, 0f, 0.5f));
        ImGui.pushStyleColor(ImGuiCol.ButtonActive, ImGui.getColorU32(1f, 1f, 1f, 0.1f));
        if (ImGui.button("Multiplayer")) {
            if (Authentication.TOKEN.length() > 1) {
                // User has probably already logged in
                Window.changeScene(new MultiplayerMenuInitializer());
            } else {
                // User has not yet logged in
                Window.changeScene(new LoginMenuInitializer(this));
            }
        }
        if (ImGui.button("Singleplayer")) {
            // Go to world selection screen
            Window.changeScene(new SingleplayerMenuInitializer());
        }
        if (ImGui.button("Options")) {
            // Go to Options screen
            Window.changeScene(new OptionsMenuInitializer(this));
        }
        if (ImGui.button("Quit")) {
            Window.get().close();
        }
        ImGui.popStyleColor();
        ImGui.popStyleColor();
        ImGui.popStyleColor();
        ImGui.end();
    }
}
