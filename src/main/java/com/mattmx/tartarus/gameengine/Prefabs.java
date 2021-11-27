package com.mattmx.tartarus.gameengine;

import com.mattmx.tartarus.components.*;
import com.mattmx.tartarus.util.AssetPool;
import org.joml.Vector2f;

public class Prefabs {
    public static GameObject generateSpriteObject(Sprite sprite, float sizeX, float sizeY) {
        GameObject block = Window.getScene().createGameObject("Sprite_Object_Gen");
        block.transform.scale.x = sizeX;
        block.transform.scale.y = sizeY;
        SpriteRenderer renderer = new SpriteRenderer();
        renderer.setSprite(sprite);
        block.addComponent(renderer);

        return block;
    }

    public static GameObject generateMario() {
        Spritesheet playerSprites = AssetPool.getSpritesheet("assets/images/spritesheet.png");
        GameObject mario = generateSpriteObject(playerSprites.getSprite(0), 0.25f, 0.25f);

        AnimationState run = new AnimationState();
        run.title = "RUN";
        float defFrameTime = 0.23f;
        run.addFrame(playerSprites.getSprite(0), defFrameTime);
        run.addFrame(playerSprites.getSprite(2), defFrameTime);
        run.addFrame(playerSprites.getSprite(3), defFrameTime);
        run.addFrame(playerSprites.getSprite(2), defFrameTime);
        run.setLoop(true);

        StateMachine stateMachine = new StateMachine();
        stateMachine.addState(run);
        stateMachine.setDefaultState(run.title);
        mario.addComponent(stateMachine);
        return mario;
    }
}
