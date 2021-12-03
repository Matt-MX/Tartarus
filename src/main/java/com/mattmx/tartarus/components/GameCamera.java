package com.mattmx.tartarus.components;

import com.mattmx.tartarus.gameengine.*;
import com.mattmx.tartarus.world.entities.PlayablePlayer;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class GameCamera extends Component {
    private transient GameObject player;
    private transient Camera gameCamera;

    private float scrollSensitivity = 0.25f;
    private float maxZoom = 3f;

    private Vector4f skyColor = new Vector4f(92.0f / 255.0f, 148.0f / 255.0f, 252.0f / 255.0f, 1.0f);
    private Vector4f undergroundColor = new Vector4f(0f, 0f, 0f, 1f);

    public GameCamera(Camera gameCamera) {
        this.gameCamera = gameCamera;
    }

    @Override
    public void start() {
        this.player = Window.getScene().getGameObjectWith(PlayablePlayer.class);
        this.gameCamera.clearColor.set(skyColor);
    }
    /*
        TODO improve camera movement functionality (Lerp camera to player's center)
     */

    @Override
    public void update(float dt) {
        if (player != null && !player.getComponent(PlayablePlayer.class).isOverridden) {
            Vector2f cameraPos = new Vector2f(player.transform.position)
                    .sub(new Vector2f(gameCamera.getProjectionSize()).mul(gameCamera.getZoom()).mul(0.5f, 0.5f));
            gameCamera.position = cameraPos;
        }
        if (MouseListener.getScrollY() != 0.0f) {
            float addValue = (float)Math.pow(Math.abs(MouseListener.getScrollY() * scrollSensitivity),
                    1 / gameCamera.getZoom());
            addValue *= -Math.signum(MouseListener.getScrollY());
            gameCamera.addZoom(addValue);
        }
        if (gameCamera.getZoom() < 1f) {
            gameCamera.setZoom(1f);
        }
        if (gameCamera.getZoom() > maxZoom) {
            gameCamera.setZoom(maxZoom);
        }
    }
}
