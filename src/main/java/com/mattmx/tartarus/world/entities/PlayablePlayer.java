package com.mattmx.tartarus.world.entities;

import com.mattmx.tartarus.components.Component;
import com.mattmx.tartarus.components.Ground;
import com.mattmx.tartarus.components.StateMachine;
import com.mattmx.tartarus.gameengine.GameObject;
import com.mattmx.tartarus.gameengine.KeyListener;
import com.mattmx.tartarus.gameengine.MouseListener;
import com.mattmx.tartarus.gameengine.Window;
import com.mattmx.tartarus.gameengine.renderer.DebugDraw;
import com.mattmx.tartarus.physics2D.RaycastInfo;
import com.mattmx.tartarus.physics2D.components.RigidBody2D;
import com.mattmx.tartarus.scenes.guis.MainMenuInitializer;
import com.mattmx.tartarus.util.AssetPool;
import com.mattmx.tartarus.util.SettingsFile;
import com.mattmx.tartarus.world.GameWorld;
import com.mattmx.tartarus.world.entities.components.Inventory;
import com.mattmx.tartarus.world.entities.components.Item;
import com.mattmx.tartarus.world.entities.components.ItemStack;
import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiWindowFlags;
import org.jbox2d.common.Vec3;
import org.jbox2d.dynamics.contacts.Contact;
import org.joml.Vector2f;

import java.util.Vector;

import static org.lwjgl.glfw.GLFW.*;

public class PlayablePlayer extends PlayerEntity {
    public PlayablePlayer(GameObject instance, GameWorld world) {
        super(instance, world);
        this.isWindow = true;
    }
    public boolean isOverridden = false;
    public float walkSpeed = 1.9f;
    public float jumpBoost = 1.0f;
    public float jumpImpulse = 3.0f;
    public float slowDownForce = 0.05f;
    public Vector2f terminalVelocity = new Vector2f(2.1f, 3.1f);

    public transient boolean onGround = false;
    private transient float groundDebounce = 0.0f;
    private transient float groundDebounceTime = 0.1f;
    private transient RigidBody2D rb;
    private transient StateMachine stateMachine;
    private transient float bigJumpBoostFactor = 1.05f;
    private float playerWidth = 0.25f;
    private transient int jumpTime = 0;
    private transient Vector2f acceleration = new Vector2f();
    private transient Vector2f velocity = new Vector2f();
    private transient int enemyBounce = 0;

    @Override
    public void start() {
        super.start();
        this.rb = gameObject.getComponent(RigidBody2D.class);
        this.stateMachine = gameObject.getComponent(StateMachine.class);
        this.rb.setGravityScale(0.0f);
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        if (isDead()) return;
        if (KeyListener.isKeyPressed(SettingsFile.get().KEY_RIGHT)) {
            this.gameObject.transform.scale.x = playerWidth;
            this.acceleration.x = walkSpeed;

            if (this.velocity.x < 0) {
                this.stateMachine.trigger("switchDirection");
                this.velocity.x += slowDownForce;
            } else {
                this.stateMachine.trigger("startRunning");
            }
        } else if (KeyListener.isKeyPressed(SettingsFile.get().KEY_LEFT)) {
            this.gameObject.transform.scale.x = -playerWidth;
            this.acceleration.x = -walkSpeed;

            if (this.velocity.x > 0) {
                this.stateMachine.trigger("switchDirection");
                this.velocity.x -= slowDownForce;
            } else {
                this.stateMachine.trigger("startRunning");
            }
        } else {
            this.acceleration.x = 0;
            if (this.velocity.x > 0) {
                this.velocity.x = Math.max(0, this.velocity.x - slowDownForce);
            } else if (this.velocity.x < 0) {
                this.velocity.x = Math.min(0, this.velocity.x + slowDownForce);
            }

            if (this.velocity.x == 0) {
                this.stateMachine.trigger("stopRunning");
            }
        }

        checkOnGround();
        if (KeyListener.isKeyPressed(SettingsFile.get().KEY_UP) && (jumpTime > 0 || onGround || groundDebounce > 0)) {
            if ((onGround || groundDebounce > 0) && jumpTime == 0) {
                AssetPool.getSound("assets/sounds/entity/player/woosh_light.ogg").play();
                jumpTime = 28;
                this.velocity.y = jumpImpulse;
            } else if (jumpTime > 0) {
                jumpTime--;
                this.velocity.y = ((jumpTime / 2.2f) * jumpBoost);
            } else {
                this.velocity.y = 0;
            }
            groundDebounce = 0;
        } else if (!onGround) {
            if (this.jumpTime > 0) {
                this.velocity.y *= 0.35f;
                this.jumpTime = 0;
            }
            groundDebounce -= dt;
            this.acceleration.y = Window.getPhysics().getGravity().y * 0.7f;
        } else {
            this.velocity.y = 0;
            this.acceleration.y = 0;
            groundDebounce = groundDebounceTime;
        }

        this.velocity.x += this.acceleration.x * dt;
        this.velocity.y += this.acceleration.y * dt;
        this.velocity.x = Math.max(Math.min(this.velocity.x, this.terminalVelocity.x), -this.terminalVelocity.x);
        this.velocity.y = Math.max(Math.min(this.velocity.y, this.terminalVelocity.y), -this.terminalVelocity.y);
        this.rb.setVelocity(this.velocity);
        this.rb.setAngularVelocity(0);

        if (!onGround) {
            stateMachine.trigger("jump");
        } else {
            stateMachine.trigger("stopJumping");
        }
        handleInventory();
    }

    public void checkOnGround() {
        if (isDead()) return;
        Vector2f raycastBegin = new Vector2f(this.gameObject.transform.position);
        float innerPlayerWidth = this.playerWidth * 0.6f;
        raycastBegin.sub(innerPlayerWidth / 2.0f, 0.0f);
        float yVal = -0.24f;
        Vector2f raycastEnd = new Vector2f(raycastBegin).add(0.0f, yVal);

        RaycastInfo info = Window.getPhysics().raycast(gameObject, raycastBegin, raycastEnd);

        Vector2f raycast2Begin = new Vector2f(raycastBegin).add(innerPlayerWidth, 0.0f);
        Vector2f raycast2End = new Vector2f(raycastEnd).add(innerPlayerWidth, 0.0f);
        RaycastInfo info2 = Window.getPhysics().raycast(gameObject, raycast2Begin, raycast2End);

        onGround = (info.hit && info.hitObject != null && info.hitObject.getComponent(Ground.class) != null) ||
                (info2.hit && info2.hitObject != null && info2.hitObject.getComponent(Ground.class) != null);

        //DebugDraw.addLine2D(raycastBegin, raycastEnd, new Vector3f(1, 0, 0));
        //DebugDraw.addLine2D(raycast2Begin, raycast2End, new Vector3f(1, 0, 0));
    }

    @Override
    public void beginCollision(GameObject collidingObj, Contact contact, Vector2f normal) {
        if (isDead()) return;
        if (collidingObj.getComponent(Ground.class) != null) {
            if (Math.abs(normal.x) > 0.8f) {
                // Running into wall
                this.velocity.x = 0.0f;
            } else if (normal.y > 0.8f) {
                // Head hits bottom of block
                this.velocity.y = 0f;
                this.acceleration.y = 0;
                this.jumpTime = 0;
            }
        }
    }

    @Override
    public void imgui() {
        // Death Popup
        if (isDead()) {
            deathPopup();
        } else {
            drawInventory();
        }
    }

    @Override
    public void onDamage(double dmg) {
        AssetPool.getSound("assets/sounds/entity/player/hurt.ogg").play();
    }

    public void respawn() {
        me().transform.position = new Vector2f(0, 0);
        this.resetData();
        this.getWorld().spawnEntityInWorld(me());
    }

    public void drawInventory() {
        int width = getInventory().getUsedSlots() * 40 + 20 * getInventory().getUsedSlots() * 2;
        int height = 100;
        ImGui.setNextWindowSize(width, height);
        ImGui.setNextWindowPos(Window.getWidth() / 2 - width / 2, 20);
        ImGui.begin("inventory", ImGuiWindowFlags.NoScrollbar |
                ImGuiWindowFlags.NoScrollWithMouse |
                ImGuiWindowFlags.NoMove |
                ImGuiWindowFlags.NoCollapse |
                ImGuiWindowFlags.NoResize |
                ImGuiWindowFlags.NoTitleBar |
                ImGuiWindowFlags.NoBackground
        );
        ImGui.pushStyleColor(ImGuiCol.Button, ImGui.getColorU32(0f, 0f, 0f, 0.0f));
        ImGui.pushStyleColor(ImGuiCol.ButtonHovered, ImGui.getColorU32(0f, 0f, 0f, 0.1f));
        ImGui.pushStyleColor(ImGuiCol.ButtonActive, ImGui.getColorU32(0f, 0f, 0f, 0.5f));
        /*    +---+   +---+
         *    |   |   |   |
         *    +---+   +---+    ...
         *      5       3
         */
        for (ItemStack i : this.getInventory().getContents()) {
            if (!i.isEmpty()) {
                boolean isSelected = i == getInventory().getHeldItemStack();
                if (isSelected) {
                    ImGui.pushStyleColor(ImGuiCol.Button, ImGui.getColorU32(0f, 0f, 0f, 0.5f));
                }
                if (ImGui.imageButton(i.getItem().sprite.getTexId(), 40f, 40f)) {
                    getInventory().setHeld(getInventory().getSlot(i));
                }
                if (isSelected) {
                    ImGui.popStyleColor();
                }
                ImGui.sameLine();
                ImGui.text(Integer.toString(i.getAmount()));
                ImGui.sameLine();
            }
        }
        ImGui.popStyleColor(3);
        ImGui.end();
    }

    public void deathPopup() {
        ImGui.setNextWindowSize(200, 200);
        ImGui.setNextWindowPos(Window.getWidth() / 2 - 100, Window.getHeight() / 2 - 100);
        ImGui.begin("Death Gui", ImGuiWindowFlags.NoScrollbar |
                ImGuiWindowFlags.NoScrollWithMouse |
                ImGuiWindowFlags.NoMove |
                ImGuiWindowFlags.NoResize |
                ImGuiWindowFlags.NoTitleBar |
                ImGuiWindowFlags.NoBackground
        );
        ImGui.pushStyleColor(ImGuiCol.Button, ImGui.getColorU32(0f, 0f, 0f, 0.3f));
        ImGui.pushStyleColor(ImGuiCol.ButtonHovered, ImGui.getColorU32(0f, 0f, 0f, 0.5f));
        ImGui.pushStyleColor(ImGuiCol.ButtonActive, ImGui.getColorU32(1f, 1f, 1f, 0.1f));
        ImGui.pushFont(Window.getImguiLayer().title);
        ImGui.text("YOU DIED");
        if (ImGui.button("Respawn")) {
            respawn();
        }
        if (ImGui.button("Exit")) {
            // Save, quit to main menu
            this.getWorld().save();
            Window.changeScene(new MainMenuInitializer());
        }
        ImGui.popFont();
        ImGui.popStyleColor(3);
        ImGui.end();
    }

    public void handleInventory() {
        if (KeyListener.keyBeginPress(GLFW_KEY_Q)) {
            if (KeyListener.isKeyPressed(GLFW_KEY_LEFT_CONTROL)) {
                ItemStack drop = getInventory().dropItem(getInventory().getHeld(), ItemStack.MAX_STACK);
                if (drop != null) {
                    getWorld().spawnItemInWorld(drop, me().transform.position.x, me().transform.position.y + 3f, true);
                }
            } else {
                ItemStack drop = getInventory().dropItem(getInventory().getHeld(), 1);
                if (drop != null) {
                    getWorld().spawnItemInWorld(drop, me().transform.position.x, me().transform.position.y + 3f, true);
                }
            }
        }

        if (getInventory().getHeldItemStack().isEmpty()) {
            int index = 0;
            for (ItemStack i : getInventory().getContents()) {
                if (!i.isEmpty()) {
                    getInventory().setHeld(index);
                } else {
                    index++;
                }
            }
        }
    }
}