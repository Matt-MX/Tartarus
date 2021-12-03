package com.mattmx.tartarus.world;

import com.mattmx.tartarus.components.*;
import com.mattmx.tartarus.gameengine.GameObject;
import com.mattmx.tartarus.gameengine.MouseListener;
import com.mattmx.tartarus.gameengine.Prefabs;
import com.mattmx.tartarus.gameengine.Window;
import com.mattmx.tartarus.physics2D.components.Box2DCollider;
import com.mattmx.tartarus.physics2D.components.RigidBody2D;
import com.mattmx.tartarus.scenes.Scene;
import com.mattmx.tartarus.scenes.world.SingleplayerWorldScene;
import com.mattmx.tartarus.util.JMath;
import com.mattmx.tartarus.util.Settings;
import com.mattmx.tartarus.world.blocks.SolidBlock;
import com.mattmx.tartarus.world.entities.DroppedItem;
import com.mattmx.tartarus.world.entities.PlayablePlayer;
import com.mattmx.tartarus.world.entities.PlayerEntity;
import com.mattmx.tartarus.world.entities.components.Item;
import com.mattmx.tartarus.world.entities.components.ItemStack;
import com.mattmx.tartarus.world.guis.ChatSystem;
import com.mattmx.tartarus.world.entities.AbstractEntity;
import com.mattmx.tartarus.world.guis.DebugSystem;
import org.joml.Vector2f;

import java.io.File;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class GameWorld {
    public static final float VOID_LEVEL = -30f;
    private Scene scene;
    private List<GameObject> loadedBlocks;
    private List<AbstractEntity> loadedEntities;
    private Component activePlayer;
    private ChatSystem chat;

    private String name;
    private String filepath;
    private String seed;
    private WorldInfoFile worldInfo;

    public GameWorld(String name) {
        this.name = name;
        String s = Paths.get("").toAbsolutePath().toString();
        File dir = new File(s + "/saves/" + name);
        this.filepath = dir.getAbsolutePath();
        WorldInfoFile info = WorldInfoFile.from(filepath);
        this.seed = info.seed;
        new GameWorld(name, seed);
    }

    public GameWorld(String name, String seed) {
        this.name = name;
        this.seed = seed;
        String s = Paths.get("").toAbsolutePath().toString();
        File dir = new File(s + "/saves/" + name);
        this.filepath = dir.getAbsolutePath();
        if (dir.mkdirs()) {
            // First time creation
            WorldInfoFile info = new WorldInfoFile();
            if (Objects.equals(seed, "")) {
                // Generate a seed, none specified
                this.seed = Integer.toString(ThreadLocalRandom.current().nextInt());
                info.seed = this.seed;
            } else {
                // Use seed provided
                this.seed = seed;
                info.seed = this.seed;
                info.lastX = 0;
                info.lastY = 0;
            }
            info.description = name;
            info.version = "1.0"; // Temp
            info.save(filepath);
        }
        // Load world information
        this.worldInfo = WorldInfoFile.from(dir.getAbsolutePath());
    }

    public void createScene() {
        Window.changeScene(new SingleplayerWorldScene(this));
    }

    public void init() {
        this.scene = Window.getScene();
        this.chat = new ChatSystem(this);
        GameObject chatObject = new GameObject("Chat");
        chatObject.addComponent(chat);
        scene.addGameObjectToScene(chatObject);

        GameObject tempPlayer = Prefabs.generateMario();
        tempPlayer.addComponent(new PlayablePlayer(tempPlayer, this));
        tempPlayer.transform.position = new Vector2f(0, 1);
        scene.addGameObjectToScene(tempPlayer);

        spawnItemInWorld(new ItemStack(Item.block("dirt"), 5), 0, 1.3f, true);
        spawnItemInWorld(new ItemStack(Item.block("stone_bricks"), 20), 0, 3f, true);

        GameObject blockTest = Prefabs.generateSpriteObject(Sprite.of("assets/images/block/dirt.png", 16, 16), 0.25f, 0.25f);
        blockTest.transform.position = getBlockCoords(new Vector2f());
        blockTest.addComponent(new ItemStack(Item.block("dirt")));
        blockTest.addComponent(new SolidBlock(blockTest, this));
        scene.addGameObjectToScene(blockTest);

        GameObject t = Prefabs.generateSpriteObject(Sprite.of("assets/images/block/dirt.png", 16, 16), 0.25f, 0.25f);
        t.transform.position = getBlockCoords(new Vector2f(-0.25f, 0));
        t.addComponent(new ItemStack(Item.block("dirt")));
        t.addComponent(new SolidBlock(t, this));
        scene.addGameObjectToScene(t);

        GameObject cam = new GameObject("Game Camera");
        cam.addComponent(new GameCamera(Window.getScene().camera()));
        GameObject debug = new GameObject("debug");
        debug.addComponent(new GridLines());
        debug.addComponent(new DebugSystem());
        scene.addGameObjectToScene(cam);
        scene.addGameObjectToScene(debug);
    }

    public void save() {

    }

    public void updateLoaded(int pos) {
        // [pos] - the position of blocks around which we want to update.

        // Remove any blocks deemed "unloaded"

        // Add any blocks deemed "loaded"
    }

    public void loadWorld(int pos) {
        // [pos] - the position of which we want to load blocks around.
        // the blocks will either be generated or loaded if this region can be found

        // Check if the region already exists.

        // Generate the height data for this region

        // Add game objects for this region
    }

    public ChatSystem chat() {
        return this.chat;
    }

    public GameObject getPlayer() {
        for (GameObject o : Window.getScene().getGameObjects()) {
            if (o.getComponent(PlayablePlayer.class) != null) {
                return o;
            }
        }
        return null;
    }

    public void spawnEntityInWorld(GameObject i) {
        scene.addGameObjectToScene(i);
    }

    public void spawnItemInWorld(GameObject i) {
        ItemStack stack = i.getComponent(ItemStack.class);
        if (stack != null) {
            spawnItemInWorld(stack, i, true);
        }
    }

    public void spawnItemInWorld(ItemStack i, GameObject o, boolean random) {
        spawnItemInWorld(i, o.transform.position.x, o.transform.position.y, random);
    }

    public void spawnItemInWorld(ItemStack i, float x, float y, boolean random) {
        spawnItemInWorld(i, x, y, 0, 0, random);
    }

    public GameObject spawnItemInWorld(ItemStack i, float x, float y, float xF, float yF, boolean random) {
        GameObject item = Prefabs.generateSpriteObject(i.getItem().sprite, 0.1f, 0.1f);
        item.addComponent(i);
        item.transform.position = new Vector2f(x, y);
        if (random) {
            item.addComponent(new DroppedItem(item, i, 1f, JMath.randomFloat(), JMath.randomFloat()));
        } else {
            item.addComponent(new DroppedItem(item, i, 1f, xF, yF));
        }
        scene.addGameObjectToScene(item);
        item.start();
        return item;
    }

    public Vector2f getBlockCoords(Vector2f point) {
        float x = point.x;
        float y = point.y;
        float blockX = ((int)Math.floor(x / Settings.GRID_WIDTH) * Settings.GRID_WIDTH) + Settings.GRID_WIDTH / 2.0f;
        float blockY = ((int)Math.floor(y / Settings.GRID_HEIGHT) * Settings.GRID_HEIGHT) + Settings.GRID_HEIGHT / 2.0f;
        return new Vector2f(blockX, blockY);
    }
}
