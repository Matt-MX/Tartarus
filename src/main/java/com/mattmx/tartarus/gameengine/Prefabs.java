package com.mattmx.tartarus.gameengine;

import com.mattmx.tartarus.components.*;
import com.mattmx.tartarus.physics2D.components.RigidBody2D;
import com.mattmx.tartarus.util.AssetPool;
import com.mattmx.tartarus.world.entities.PlayablePlayer;
import org.jbox2d.dynamics.BodyType;

public class Prefabs {
    public static GameObject generateSpriteObject(Sprite sprite, float sizeX, float sizeY) {
        GameObject block = Window.getScene().createGameObject("Gen");
        block.transform.scale.x = sizeX;
        block.transform.scale.y = sizeY;
        SpriteRenderer renderer = new SpriteRenderer();
        renderer.setSprite(sprite);
        block.addComponent(renderer);

        return block;
    }

    public static GameObject generateBackground(Sprite sprite) {
        GameObject bg = Window.getScene().createGameObject("background");
        bg.transform.scale.x = Window.getWidth();
        bg.transform.scale.y = Window.getHeight();
        bg.transform.position.x = 0f;
        bg.transform.position.y = 0f;
        SpriteRenderer renderer = new SpriteRenderer();
        renderer.setSprite(sprite);
        bg.addComponent(renderer);
        return bg;
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

        PillboxCollider pb = new PillboxCollider();
        pb.width = 0.34f;
        pb.height = 0.36f;
        mario.addComponent(pb);

        RigidBody2D rb = new RigidBody2D();
        rb.setBodyType(BodyType.DYNAMIC);
        rb.setContinuousCollision(false);
        rb.setFixedRotation(true);
        rb.setMass(25.0f);
        mario.addComponent(rb);

        return mario;
    }
}
