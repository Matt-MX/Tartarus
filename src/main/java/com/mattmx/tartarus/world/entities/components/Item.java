package com.mattmx.tartarus.world.entities.components;

import com.mattmx.tartarus.components.Component;
import com.mattmx.tartarus.components.Sprite;
import com.mattmx.tartarus.gameengine.renderer.Texture;
import com.mattmx.tartarus.util.AssetPool;

public class Item {
    public String name;
    public Type type;
    public Sprite sprite;

    public Item(String name, Type t, String texture) {
        this.name = name;
        this.type = t;
        this.sprite = new Sprite();
        this.sprite.setTexture(AssetPool.getTexture(texture));
    }

    public enum Type {
        BLOCK,
        WEAPON,
        TOOL,
        OTHER,
        UNDEFINED,
    }
    static {
        Item DIRT = new Item("dirt", Type.BLOCK, "assets/images/block/dirt.png");
    }

    public enum Blocks {
        dirt,
        stone_bricks
    }

    public static Item block(String name) {
        return from(name, Type.BLOCK);
    }

    public static Item from(String name, Type type) {
        for (Blocks e : Blocks.values()) {
            if (name.equalsIgnoreCase(e.toString())) {
                String n = e.toString().toLowerCase();
                return new Item(n, type, "assets/images/" + type.toString().toLowerCase() + "/" + n + ".png");
            }
        }
        return null;
    }

    public void onUse(int amount) {

    }
}
