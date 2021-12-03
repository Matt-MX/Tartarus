package com.mattmx.tartarus.world.entities;

import com.mattmx.tartarus.components.SpriteRenderer;
import com.mattmx.tartarus.gameengine.GameObject;
import com.mattmx.tartarus.physics2D.components.Box2DCollider;
import com.mattmx.tartarus.physics2D.components.RigidBody2D;
import com.mattmx.tartarus.util.AssetPool;
import com.mattmx.tartarus.world.entities.components.Item;
import com.mattmx.tartarus.world.entities.components.ItemStack;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.contacts.Contact;
import org.joml.Vector2f;

public class DroppedItem extends AbstractEntity {
    private ItemStack itemStack;
    private SpriteRenderer renderer;
    private float force;
    private float xPush;
    private float yPush;

    public DroppedItem(GameObject instance, ItemStack i) {
        this(instance, i, 0, 0, 0);
    }

    public DroppedItem(GameObject instance, ItemStack i, float f, float x, float y) {
        super(instance);
        this.force = f;
        this.xPush = x;
        this.yPush = y;
        this.itemStack = i;
        SpriteRenderer spr = me().getComponent(SpriteRenderer.class);
        if (spr == null) {
            renderer = new SpriteRenderer();
            renderer.setSprite(i.getItem().sprite);
            me().addComponent(renderer);
        } else {
            renderer = spr;
        }
        initPhysics();
    }

    private void initPhysics() {
        RigidBody2D rb = new RigidBody2D();
        rb.setBodyType(BodyType.DYNAMIC);
        rb.setFixedRotation(false);
        me().addComponent(rb);
        Box2DCollider b2d = new Box2DCollider();
        b2d.setHalfSize(new Vector2f(0.10f, 0.10f));
        me().addComponent(b2d);
    }

    @Override
    public void start() {
        super.start();
        this.setHealth(10f);
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        if (Math.max(force, 0) != 0) {
            force = force - 0.05f;
            me().getComponent(RigidBody2D.class).setVelocity(
                    new Vector2f(xPush * force, yPush * force));
        } else {
            force = 0;
        }
        if (doDespawnAnim) {
            me().transform.scale.lerp(new Vector2f(), -despawnProgress);
            despawnProgress -= dt;
            if (despawnProgress < -1) {
                me().destroy();
            }
        }
    }



    @Override
    public void onDeath(float dt) {
        me().destroy();
    }

    private boolean doDespawnAnim = false;
    private float despawnProgress = 0f;
    @Override
    public void preSolve(GameObject collidingObj, Contact contact, Vector2f hitNormal) {
        PlayablePlayer player = collidingObj.getComponent(PlayablePlayer.class);
        if (player != null) {
            int left = player.getInventory().addStack(this.itemStack);
            if (!doDespawnAnim) AssetPool.getSound("assets/sounds/sfx/pop.ogg").play();
            if (left > 0) {
                itemStack.setAmount(left);
            } else {
                doDespawnAnim = true;
            }
        }
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }
}
