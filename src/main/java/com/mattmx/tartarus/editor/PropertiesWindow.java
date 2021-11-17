package com.mattmx.tartarus.editor;

import com.mattmx.tartarus.gameengine.GameObject;
import com.mattmx.tartarus.gameengine.MouseListener;
import com.mattmx.tartarus.gameengine.renderer.PickingTexture;
import com.mattmx.tartarus.scenes.Scene;
import imgui.ImGui;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

public class PropertiesWindow {
    private GameObject activeGameObject = null;
    private PickingTexture pickingTexture;

    public PropertiesWindow(PickingTexture pickingTexture) {
        this.pickingTexture = pickingTexture;
    }

    public void update(float dt, Scene currentScene) {
        if (MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)) {
            int x = (int)MouseListener.getScreenX();
            int y = (int)MouseListener.getScreenY();
            activeGameObject = currentScene.getGameObject(pickingTexture.readPixel(x, y));
        }
    }

    public void imGui() {
        if (activeGameObject != null) {
            ImGui.begin("Properties");
            activeGameObject.imgui();
            ImGui.end();
        }
    }
}
