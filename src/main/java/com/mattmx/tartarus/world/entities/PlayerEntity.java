package com.mattmx.tartarus.world.entities;

import com.mattmx.tartarus.components.Component;
import com.mattmx.tartarus.components.PillboxCollider;
import com.mattmx.tartarus.components.SpriteRenderer;
import com.mattmx.tartarus.gameengine.GameObject;
import com.mattmx.tartarus.gameengine.Window;
import com.mattmx.tartarus.physics2D.components.RigidBody2D;
import com.mattmx.tartarus.scenes.guis.MainMenuInitializer;
import com.mattmx.tartarus.world.GameWorld;
import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiWindowFlags;
import org.joml.Vector2f;

public class PlayerEntity extends AbstractEntity {
    private GameWorld world;

    public PlayerEntity(GameObject instance, GameWorld world) {
        super(instance);
        this.isWindow = true;
        this.world = world;
    }

    @Override
    public void start() {
        super.start();
        this.setHealth(20);
    }

    @Override
    public void onDeath(float dt) {
        // Player death event
        // Drop items in game world as "ItemEntity"

        // Clear this player instance's inventory

        // Destroy Physics of this object.
        this.gameObject.removeComponent(RigidBody2D.class);
        this.gameObject.removeComponent(PillboxCollider.class);
        this.gameObject.removeComponent(SpriteRenderer.class);
    }

    public GameWorld getWorld() {
        return this.world;
    }
}
