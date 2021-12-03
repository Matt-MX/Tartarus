package com.mattmx.tartarus.scenes.world;

import com.mattmx.tartarus.components.Spritesheet;
import com.mattmx.tartarus.gameengine.GameObject;
import com.mattmx.tartarus.gameengine.Window;
import com.mattmx.tartarus.gameengine.renderer.Texture;
import com.mattmx.tartarus.scenes.Scene;
import com.mattmx.tartarus.scenes.SceneInitializer;
import com.mattmx.tartarus.util.AssetPool;
import com.mattmx.tartarus.util.SettingsFile;
import com.mattmx.tartarus.world.GameWorld;
import imgui.ImGui;
import imgui.flag.ImGuiWindowFlags;

public class SingleplayerWorldScene extends SceneInitializer {
    private GameWorld activeGameWorld;

    public SingleplayerWorldScene(GameWorld world) {
        this.activeGameWorld = world;
    }

    @Override
    public void init(Scene scene) {
        scene.start();
    }

    @Override
    public void loadResources(Scene scene) {
        AssetPool.addSpritesheet("assets/images/spritesheet.png",
                new Spritesheet(AssetPool.getTexture("assets/images/spritesheet.png"),
                        16, 16, 26, 0));
        AssetPool.loadDefaultResources();
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
        for (GameObject o : Window.getScene().getGameObjects()) {
            o.imgui();
        }
    }
}
