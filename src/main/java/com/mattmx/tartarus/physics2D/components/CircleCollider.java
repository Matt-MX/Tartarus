package com.mattmx.tartarus.physics2D.components;

import com.mattmx.tartarus.components.Component;
import com.mattmx.tartarus.gameengine.renderer.DebugDraw;
import org.joml.Vector2f;

public class CircleCollider extends Component {
    private Vector2f offset = new Vector2f();
    public Vector2f getOffset() {
        return this.offset;
    }
    private float radius = 1f;

    public void setOffset(Vector2f newOffset) {
        this.offset.set(newOffset);
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    @Override
    public void editorUpdate(float dt) {
        Vector2f center = new Vector2f(this.gameObject.transform.position).add(this.offset);
        DebugDraw.addCircle(center, this.radius);
    }
}
