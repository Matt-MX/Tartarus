package com.mattmx.tartarus.world.blocks;

import com.mattmx.tartarus.components.Ground;
import com.mattmx.tartarus.gameengine.GameObject;
import com.mattmx.tartarus.physics2D.components.Box2DCollider;
import com.mattmx.tartarus.physics2D.components.RigidBody2D;
import com.mattmx.tartarus.world.GameWorld;
import com.mattmx.tartarus.world.entities.components.Item;
import com.mattmx.tartarus.world.entities.components.ItemStack;
import org.jbox2d.dynamics.BodyType;
import org.joml.Vector2f;

public class SolidBlock extends Block {
    private GameWorld world;

    public SolidBlock(GameObject o, GameWorld world) {
        super(o);
        this.world = world;
        // Add colliders for this block
        RigidBody2D rb = new RigidBody2D();
        rb.setBodyType(BodyType.STATIC);
        me().addComponent(rb);
        Box2DCollider b2d = new Box2DCollider();
        b2d.setHalfSize(new Vector2f(0.25f, 0.25f));
        me().addComponent(b2d);
        me().addComponent(new Ground());
    }

    @Override
    public void onLeftClick() {
        breakBlock();
    }

    public void breakBlock() {
        // Destroy this game object
        // Spawn this item in the world space
        this.world.spawnItemInWorld(me());
        me().destroy();
    }

    public GameWorld getWorld() {
        return this.getWorld();
    }
}
