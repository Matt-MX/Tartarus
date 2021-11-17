package com.mattmx.tartarus.scenes;

import com.mattmx.tartarus.components.*;
import com.mattmx.tartarus.gameengine.Camera;
import com.mattmx.tartarus.gameengine.GameObject;
import com.mattmx.tartarus.gameengine.Prefabs;
import com.mattmx.tartarus.gameengine.Transform;
import com.mattmx.tartarus.gameengine.renderer.DebugDraw;
import com.mattmx.tartarus.scenes.Scene;
import com.mattmx.tartarus.util.AssetPool;
import imgui.ImGui;
import imgui.ImVec2;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class LevelEditorScene extends Scene {

    private GameObject obj1;
    private Spritesheet sprites;

    GameObject levelEditorStuff = new GameObject("LevelEditor", new Transform(new Vector2f()), 0);

    public LevelEditorScene() {

    }

    @Override
    public void init() {
        this.camera = new Camera(new Vector2f(-250, 0));
        levelEditorStuff.addComponent(new MouseControls());
        levelEditorStuff.addComponent(new GridLines());
        levelEditorStuff.addComponent(new EditorCamera(this.camera));
        loadResources();
        sprites = AssetPool.getSpriteSheet("assets/images/decorationsAndBlocks.png");

//        obj1 = new GameObject("Object 1",
//                new Transform(new Vector2f(100, 100), new Vector2f(256, 256)), -2);
//        SpriteRenderer obj1Sprite = new SpriteRenderer();
//        obj1Sprite.setColor(new Vector4f(1, 0, 0, 1));
//        obj1.addComponent(obj1Sprite);
//        obj1.addComponent(new RigidBody());
//        this.addGameObjectToScene(obj1);
//        this.activeGameObject = obj1;

//        GameObject obj2 = new GameObject("Object 2",
//                new Transform(new Vector2f(400, 100), new Vector2f(256, 256)), 3);
//        SpriteRenderer obj2SpriteRenderer = new SpriteRenderer();
//        Sprite obj2Sprite = new Sprite();
//        obj2Sprite.setTexture(AssetPool.getTexture("assets/images/char_static.png"));
//        obj2SpriteRenderer.setSprite(obj2Sprite);
//        obj2.addComponent(obj2SpriteRenderer);
//        this.addGameObjectToScene(obj2);

    }

    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");
        AssetPool.addSpriteSheet("assets/images/decorationsAndBlocks.png",
                new Spritesheet(AssetPool.getTexture("assets/images/decorationsAndBlocks.png"),
                        16, 16, 81, 0));
        AssetPool.getTexture("assets/images/char_static.png");

        for (GameObject g : gameObjects) {
            if (g.getComponent(SpriteRenderer.class) != null) {
                SpriteRenderer spr = g.getComponent(SpriteRenderer.class);
                if (spr.getTexture() != null) {
                    spr.setTexture(AssetPool.getTexture(spr.getTexture().getFilepath()));
                }
            }
        }
    }

    @Override
    public void update(float dt) {
        levelEditorStuff.update(dt);
        this.camera.adjustProjection();
        DebugDraw.addCircle(new Vector2f(0, 64), 64, new Vector3f(0, 1, 0), 1);

        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }
        this.renderer.render();
    }

    @Override
    public void render() {
        this.renderer.render();
    }

    @Override
    public void imgui() {
        ImGui.begin("Test window");
        ImVec2 windowPos = new ImVec2();
        ImGui.getWindowPos(windowPos);
        ImVec2 windowSize = new ImVec2();
        ImGui.getWindowSize(windowSize);
        ImVec2 itemSpacing = new ImVec2();
        ImGui.getStyle().getItemSpacing(itemSpacing);

        float windowX2 = windowPos.x + windowSize.x;
        for (int i=0; i < sprites.size(); i++) {
            Sprite sprite = sprites.getSprite(i);
            float spriteWidth = sprite.getWidth() * 2;
            float spriteHeight = sprite.getHeight() * 2;
            int id = sprite.getTexId();
            Vector2f[] texCoords = sprite.getTexCoords();

            ImGui.pushID(i);
            if (ImGui.imageButton(id, spriteWidth, spriteHeight, texCoords[2].x, texCoords[0].y, texCoords[0].x, texCoords[2].y)) {
                GameObject object = Prefabs.generateSpriteObject(sprite, 32, 32);
                levelEditorStuff.getComponent(MouseControls.class).pickupObject(object);
            }
            ImGui.popID();

            ImVec2 lastButtonPos = new ImVec2();
            ImGui.getItemRectMax(lastButtonPos);
            float lastButtonX2 = lastButtonPos.x;
            float nextButtonX2 = lastButtonX2 + itemSpacing.x + spriteWidth;
            if (i + 1 < sprites.size() && nextButtonX2 < windowX2) {
                ImGui.sameLine();
            }
        }

        ImGui.end();
    }
}
