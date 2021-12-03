package com.mattmx.tartarus.world.guis.text;

import org.joml.Vector4f;

public class ColoredString {
    private String string;
    private Vector4f color;

    public ColoredString(String str) {
        this(str, new Vector4f(0f, 0f, 0f, 1f));
    }

    public ColoredString(String str, Vector4f color) {
        this.string = str;
        this.color = color;
    }

    public void setColor(Vector4f color) {
        this.color = color;
    }

    public Vector4f getColor() {
        return this.color;
    }

    public String getString() {
        return toString();
    }

    @Override
    public String toString() {
        return this.string;
    }

    public static Vector4f get(String c) {
        switch (c.toLowerCase()) {
            case "0": return new Vector4f(0f, 0f, 0f, 1f);
            case "1": return new Vector4f(0f, 0f, 170f / 255f, 1f);
            case "2": return new Vector4f(0f, 170f / 255f, 0f, 1f);
            case "3": return new Vector4f(0f, 170f / 255f, 170f / 255f, 1f);
            case "4": return new Vector4f(170f / 255f, 0f, 0f, 1f);
            case "5": return new Vector4f(170f / 255f, 0f, 170f / 255f, 1f);
            case "6": return new Vector4f(170f / 255f, 170f / 255f, 0f, 1f);
            case "7": return new Vector4f(170f / 255f, 170f / 255f, 170f / 255f, 1f);
            case "8": return new Vector4f(85f / 255f, 85f / 255f, 85f / 255f, 1f);
            case "9": return new Vector4f(85f / 255f, 85f / 255f, 1, 1f);
            case "a": return new Vector4f(85f / 255f, 1.0f, 85f / 255f, 1f);
            case "b": return new Vector4f(85f / 255f, 1, 1, 1f);
            case "c": return new Vector4f(1, 85f / 255f, 85f / 255f, 1f);
            case "d": return new Vector4f(1, 85f / 255f, 1, 1f);
            case "e": return new Vector4f(1, 1, 85f / 255f, 1f);
            case "f": case "r":
                return new Vector4f(1, 1, 1, 1f);
        }
        return new Vector4f(1, 1, 1, 1f);
    }
}
