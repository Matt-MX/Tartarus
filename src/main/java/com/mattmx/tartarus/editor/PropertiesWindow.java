package com.mattmx.tartarus.editor;

import com.mattmx.tartarus.components.NonPickable;
import com.mattmx.tartarus.gameengine.GameObject;
import com.mattmx.tartarus.gameengine.MouseListener;
import com.mattmx.tartarus.gameengine.renderer.PickingTexture;
import com.mattmx.tartarus.physics2D.components.Box2DCollider;
import com.mattmx.tartarus.physics2D.components.CircleCollider;
import com.mattmx.tartarus.physics2D.components.RigidBody2D;
import com.mattmx.tartarus.scenes.Scene;
import imgui.ImGui;

import java.util.*;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;


public class PropertiesWindow {
    private List<GameObject> activeGameObjects;
    private GameObject activeGameObject = null;
    private PickingTexture pickingTexture;

    public PropertiesWindow(PickingTexture pickingTexture) {
        this.pickingTexture = pickingTexture;
        this.activeGameObjects = new ArrayList<>();
    }

    public void imgui() {
        if (activeGameObjects.size() == 1 && activeGameObjects.get(0) != null) {
            activeGameObject = activeGameObjects.get(0);
            ImGui.begin("Properties");

            if (ImGui.beginPopupContextWindow("ComponentAdder")) {
                if (ImGui.menuItem("Add Rigidbody")) {
                    if (activeGameObject.getComponent(RigidBody2D.class) == null) {
                        activeGameObject.addComponent(new RigidBody2D());
                    }
                }

                if (ImGui.menuItem("Add Box Collider")) {
                    if (activeGameObject.getComponent(Box2DCollider.class) == null &&
                            activeGameObject.getComponent(CircleCollider.class) == null) {
                        activeGameObject.addComponent(new Box2DCollider());
                    }
                }

                if (ImGui.menuItem("Add Circle Collider")) {
                    if (activeGameObject.getComponent(CircleCollider.class) == null &&
                            activeGameObject.getComponent(Box2DCollider.class) == null) {
                        activeGameObject.addComponent(new CircleCollider());
                    }
                }

                ImGui.endPopup();
            }

            activeGameObject.imgui();
            ImGui.end();
        }
    }

    public GameObject getActiveGameObject() {
        return activeGameObjects.size() == 1 ? this.activeGameObjects.get(0) : null;
    }

    public List<GameObject> getActiveGameObjects() {
        return this.activeGameObjects;
    }

    public void clearSelected() {
        this.activeGameObjects.clear();
    }

    public void setActiveGameObject(GameObject go) {
        if (go != null) {
            clearSelected();
            this.activeGameObjects.add(go);
        }
    }

    public void addActiveGameObject(GameObject go) {
        this.activeGameObjects.add(go);
    }

    public PickingTexture getPickingTexture() {
        return this.pickingTexture;
    }
}