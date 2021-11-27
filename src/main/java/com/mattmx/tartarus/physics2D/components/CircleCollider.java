package com.mattmx.tartarus.physics2D.components;

import com.mattmx.tartarus.components.Component;

public class CircleCollider extends Component {
    private float radius = 1f;

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

}
