package com.mattmx.tartarus.components;

import com.mattmx.tartarus.gameengine.Component;
import com.mattmx.tartarus.gameengine.Transform;
import com.mattmx.tartarus.gameengine.renderer.Texture;
import imgui.ImGui;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class SpriteRenderer extends Component {

    private Vector4f color = new Vector4f(1, 1, 1, 1);
    private Sprite sprite = new Sprite();

    private transient Transform lastTransform;
    private transient boolean isDirty = false;

//    public SpriteRenderer(Vector4f color){
//        this.color = color;
//        this.sprite = new Sprite(null);
//        this.isDirty = true;
//    }
//
//    public SpriteRenderer(Sprite sprite) {
//        this.sprite = sprite;
//        this.color = new Vector4f(1,1,1,1);
//        this.isDirty = true;
//    }

    @Override
    public void start(){
        this.lastTransform = gameObject.transform.copy();
    }

    @Override
    public void update(float dt) {
        if (!this.lastTransform.equals(this.gameObject.transform)) {
            this.gameObject.transform.copy(this.lastTransform);
            isDirty = true;
        }
    }

    @Override
    public void imgui(){
        float[] imColor = {color.x, color.y, color.z, color.w};
        if (ImGui.colorPicker4("Colour Picker: ", imColor)) {
            this.color.set(imColor[0],imColor[1],imColor[2],imColor[3]);
            this.isDirty = true;
        }
    }

    public Vector4f getColor() {
        return this.color;
    }

    public Texture getTexture() {
        return this.sprite.getTexture();
    }

    public Vector2f[] getTexCoords(){
        return sprite.getTexCoords();
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
        this.isDirty = true;
    }

    public void setColor(Vector4f col) {
        if (!this.color.equals(color)) {
            this.color = col;
            this.isDirty = true;
        }
    }

    public boolean isDirty() {
        return this.isDirty;
    }

    public void clean() {
        this.isDirty = false;
    }
}
