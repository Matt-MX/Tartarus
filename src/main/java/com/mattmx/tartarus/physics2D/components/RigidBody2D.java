package com.mattmx.tartarus.physics2D.components;

import com.mattmx.tartarus.components.Component;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.joml.Vector2f;

public class RigidBody2D extends Component {
    private Vector2f velocity = new Vector2f();
    private float angularDamping = 0.8f;
    private float linearDamping = 0.9f;
    private float mass = 0;
    private BodyType bodyType = BodyType.DYNAMIC;

    private boolean fixedRotation = false;
    private boolean continuousCollision = true;

    private transient Body rawBody = null;
    private transient boolean isPlaying = false;

    public RigidBody2D() {

    }

    @Override
    public void update(float dt) {
        Collider collider = gameObject.getComponent(Collider.class);
        if (rawBody != null && isPlaying && collider != null) {
            this.gameObject.transform.position.set(this.rawBody.getPosition().x - collider.getOffset().x, this.rawBody.getPosition().y - collider.getOffset().y);
            this.gameObject.transform.rotation = (float) Math.toDegrees(this.rawBody.getAngle());
        }
    }

    public Vector2f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2f velocity) {
        this.velocity = velocity;
    }

    public float getAngularDamping() {
        return angularDamping;
    }

    public void setAngularDamping(float angularDamping) {
        this.angularDamping = angularDamping;
    }

    public float getLinearDamping() {
        return linearDamping;
    }

    public void setLinearDamping(float linearDamping) {
        this.linearDamping = linearDamping;
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public BodyType getBodyType() {
        return bodyType;
    }

    public void setBodyType(BodyType bodyType) {
        this.bodyType = bodyType;
    }

    public boolean isFixedRotation() {
        return fixedRotation;
    }

    public void setFixedRotation(boolean fixedRotation) {
        this.fixedRotation = fixedRotation;
    }

    public boolean isContinuousCollision() {
        return continuousCollision;
    }

    public void setContinuousCollision(boolean continuousCollision) {
        this.continuousCollision = continuousCollision;
    }

    public Body getRawBody() {
        return rawBody;
    }

    public void setRawBody(Body rawBody) {
        this.rawBody = rawBody;
    }

    public void setIsPlaying(boolean val) {
        this.isPlaying = val;
    }
}