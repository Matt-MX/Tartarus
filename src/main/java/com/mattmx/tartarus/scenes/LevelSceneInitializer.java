package com.mattmx.tartarus.scenes;

import com.mattmx.tartarus.components.*;
import com.mattmx.tartarus.gameengine.*;
import com.mattmx.tartarus.physics2D.components.Box2DCollider;
import com.mattmx.tartarus.physics2D.components.RigidBody2D;
import com.mattmx.tartarus.util.AssetPool;
import imgui.ImGui;
import imgui.ImVec2;
import org.box2d.proto.Box2D;
import org.jbox2d.dynamics.BodyType;
import org.joml.Vector2f;

import java.io.File;
import java.util.Collection;

public class LevelSceneInitializer extends SceneInitializer {

    private GameObject cameraObject;

    public LevelSceneInitializer() {

    }

    @Override
    public void init(Scene scene) {
        Spritesheet sprites = AssetPool.getSpritesheet("assets/images/decorationsAndBlocks.png");
        cameraObject = scene.createGameObject("Game Camera");
        cameraObject.addComponent(new GameCamera(scene.camera()));
        scene.start();
        scene.addGameObjectToScene(cameraObject);
    }

    @Override
    public void loadResources(Scene scene) {
        AssetPool.getShader("assets/shaders/default.glsl");

        AssetPool.addSpritesheet("assets/images/decorationsAndBlocks.png",
                new Spritesheet(AssetPool.getTexture("assets/images/decorationsAndBlocks.png"),
                        16, 16, 81, 0));
        AssetPool.addSpritesheet("assets/images/spritesheet.png",
                new Spritesheet(AssetPool.getTexture("assets/images/spritesheet.png"),
                        16, 16, 26, 0));
        AssetPool.addSpritesheet("assets/images/gizmos.png",
                new Spritesheet(AssetPool.getTexture("assets/images/gizmos.png"),
                        24, 48, 3, 0));
        AssetPool.getTexture("assets/images/char_static.png");

        AssetPool.addSound("assets/sounds/entity/player/woosh_light.ogg", false);

        for (GameObject g : scene.getGameObjects()) {
            if (g.getComponent(SpriteRenderer.class) != null) {
                SpriteRenderer spr = g.getComponent(SpriteRenderer.class);
                if (spr.getTexture() != null) {
                    spr.setTexture(AssetPool.getTexture(spr.getTexture().getFilepath()));
                }
            }
            if (g.getComponent(StateMachine.class) != null) {
                StateMachine machine = g.getComponent(StateMachine.class);
                machine.refreshTextures();
            }
        }
    }

    @Override
    public void imgui() {

    }
}