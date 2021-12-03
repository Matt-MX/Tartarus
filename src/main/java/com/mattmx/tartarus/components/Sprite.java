package com.mattmx.tartarus.components;

import com.mattmx.tartarus.gameengine.renderer.Texture;
import com.mattmx.tartarus.util.AssetPool;
import org.joml.Vector2f;

public class Sprite {

    private float width, height;

    private Texture texture = null;
    private Vector2f[] texCoords = {
            new Vector2f(1, 1),
            new Vector2f(1, 0),
            new Vector2f(0, 0),
            new Vector2f(0, 1)
    };

    public Texture getTexture() {
        return this.texture;
    }

    public Vector2f[] getTexCoords() {
        return this.texCoords;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setTexture(Texture tex) {
        this.texture = tex;
    }

    public void setTexCoords(Vector2f[] texCoords) {
        this.texCoords = texCoords;
    }

    public int getTexId() {
        return texture == null ? -1 : texture.getId();
    }

    public static Sprite of(String filepath, float width, float height) {
        Sprite gen = new Sprite();
        gen.setTexture(AssetPool.getTexture(filepath));
        gen.setWidth(width);
        gen.setHeight(height);
        return gen;
    }
}