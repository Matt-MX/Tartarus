package com.mattmx.tartarus.components;

import com.mattmx.tartarus.gameengine.Camera;
import com.mattmx.tartarus.gameengine.KeyListener;
import com.mattmx.tartarus.gameengine.MouseListener;
import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.*;

public class EditorCamera extends Component {

    private float dragDebounce = 0.032f;

    private Camera levelEditorCamera;
    private Vector2f clickOrigin;

    private float dragSens = 30.0f;
    private float scrollSens = 0.1f;
    private boolean reset = false;
    private float lerpTime = 0.0f;

    public EditorCamera(Camera levelEditorCamera) {
        this.levelEditorCamera = levelEditorCamera;
        this.clickOrigin = new Vector2f();
    }
    
    
    @Override
    public void update(float dt) {
        if (MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_MIDDLE) && dragDebounce > 0) {
            this.clickOrigin = new Vector2f(MouseListener.getOrthoX(), MouseListener.getOrthoY());
            dragDebounce -= dt;
            return;
        } else if (MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_MIDDLE)) {
            Vector2f mousePos = new Vector2f(MouseListener.getOrthoX(), MouseListener.getOrthoY());
            Vector2f delta = new Vector2f(mousePos).sub(this.clickOrigin);
            levelEditorCamera.position.sub(delta.mul(dt).mul(dragSens));
            this.clickOrigin.lerp(mousePos, dt);
        }

        if (dragDebounce <= 0.0f && !MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_MIDDLE)) {
            dragDebounce = 0.1f;
        }

        if (MouseListener.getScrollY() != 0.0f) {
            float addValue = (float)Math.pow(Math.abs(MouseListener.getScrollY() * scrollSens),
                    1 / levelEditorCamera.getZoom());
            addValue *= Math.signum(MouseListener.getScrollY());
            levelEditorCamera.addZoom(-addValue);
        }

        if (KeyListener.isKeyPressed(GLFW_KEY_KP_DECIMAL)) {
            reset = true;
        }

        if (reset) {
            levelEditorCamera.position.lerp(new Vector2f(0, 0), lerpTime);
            levelEditorCamera.setZoom(this.levelEditorCamera.getZoom() +
                    ((1f - levelEditorCamera.getZoom()) * lerpTime));
            this.lerpTime += 0.1f * dt;
            if (Math.abs(levelEditorCamera.position.x) <= 5.0f &&
                    Math.abs(levelEditorCamera.position.y) <= 5.0f) {
                this.lerpTime = 0f;
                levelEditorCamera.position.set(0, 0);
                levelEditorCamera.setZoom(1f);
                reset = false;
            }
        }
    }
}
