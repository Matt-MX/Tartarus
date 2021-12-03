package com.mattmx.tartarus.world.entities;

import com.mattmx.tartarus.components.Component;
import com.mattmx.tartarus.components.Sprite;
import com.mattmx.tartarus.components.SpriteRenderer;
import com.mattmx.tartarus.gameengine.GameObject;
import com.mattmx.tartarus.gameengine.Window;
import com.mattmx.tartarus.gameengine.renderer.Texture;
import com.mattmx.tartarus.scenes.guis.MainMenuInitializer;
import com.mattmx.tartarus.world.GameWorld;
import com.mattmx.tartarus.world.entities.components.Inventory;
import com.mattmx.tartarus.world.entities.components.ItemStack;
import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiWindowFlags;
import org.joml.Vector2f;
import org.joml.Vector4f;

public abstract class AbstractEntity extends Component {
    private GameObject instance;
    private String name;
    private double health;
    private String uuid;
    private Vector2f position;
    private boolean isDead;
    private long age = 0;
    private long deathTime = 0;
    private Inventory inventory;

    public AbstractEntity(GameObject instance) {
        this.instance = instance;
    }

    @Override
    public void start() {
        inventory = new Inventory(-1);
        if (age == 0) {
            onSpawn();
        }
    }

    private float dmgtimer = 0.0f;
    @Override
    public void update(float dt) {
        // Check Health
        isDead = checkDead();
        if (isDead) {
            // Entity is dead
            if (deathTime == 0) {
                // Entity has just died
                onDeath(dt);
            }
            deathTime++;
        }
        age++;
        SpriteRenderer spr = me().getComponent(SpriteRenderer.class);
        if (dmgtimer > 0) {
            if (spr != null) {
                spr.setColor(new Vector4f(1f, 0f, 0f, 1f));
            }
            dmgtimer--;
        } else {
            spr.setColor(new Vector4f(1f, 1f, 1f, 1f));
        }
    }

    public void onDamage(double dmg) {
        dmgtimer = 15f;
    }

    public void onDeath(float dt) {

    }

    public void onSpawn() {

    }

    public boolean checkDead() {
        if (health <= 0 || instance.transform.position.y < GameWorld.VOID_LEVEL) {
            return true;
        }
        return false;
    }

    public boolean isDead() {
        return this.isDead;
    }

    public long getAge() {
        return age;
    }

    public String getUuid() {
        return this.uuid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void kill() {
        this.health = 0;
    }

    public void damage(double dmg) {
        setHealth(getHealth() - dmg);
        onDamage(dmg);
    }

    public double getHealth() {
        return this.health;
    }

    public void setHealth(double newHealth) {
        this.health = newHealth;
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public float getDistanceFrom(Vector2f point) {
        return (float)Math.sqrt(Math.pow(this.position.x - point.x, 2) + Math.pow(this.position.y - point.y, 2));
    }

    public GameObject me() {
        return this.instance;
    }

    public void resetData() {
        this.start();
    }

    public Inventory getInventory() {
        return this.inventory;
    }
}
