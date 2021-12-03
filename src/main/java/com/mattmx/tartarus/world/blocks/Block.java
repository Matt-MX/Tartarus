package com.mattmx.tartarus.world.blocks;

import com.mattmx.tartarus.components.Component;
import com.mattmx.tartarus.gameengine.GameObject;
import com.mattmx.tartarus.gameengine.MouseListener;
import com.mattmx.tartarus.world.GameWorld;
import com.mattmx.tartarus.world.entities.components.Item;
import org.joml.Vector2f;

public class Block extends Component {
    private GameObject o;

    public Block(GameObject obj) {
        this.o = obj;
    }

    @Override
    public void update(float dt) {
        // TODO Way to translate from mouse coords to screen
        //System.out.println(MouseListener.getX() + " " + me().transform.position.x);
        if (isHovered()) {
            if (MouseListener.mouseButtonDown(0)) {
                onLeftClick();
            }
            if (MouseListener.mouseButtonDown(1)) {
                onRightClick();
            }
        }
    }

    public void onRightClick() {

    }

    public void onLeftClick() {

    }

    public boolean isHovered() {
        return me().transform.position.x <= MouseListener.getX() &&
                me().transform.position.x + me().transform.scale.x >= MouseListener.getX() &&
                me().transform.position.y <= MouseListener.getY() &&
                me().transform.position.y + me().transform.scale.y >= MouseListener.getY();
    }

    private Item item;

    public Item getType() {
        return item;
    }

    public Vector2f getPos() {
        return this.gameObject.transform.position;
    }

    public GameObject me() {
        return this.o;
    }
}
