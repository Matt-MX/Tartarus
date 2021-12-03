package com.mattmx.tartarus.components;

import com.mattmx.tartarus.gameengine.Camera;
import com.mattmx.tartarus.gameengine.Window;
import com.mattmx.tartarus.gameengine.renderer.DebugDraw;
import com.mattmx.tartarus.util.Settings;
import com.mattmx.tartarus.world.GameWorld;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class GridLines extends Component {

    @Override
    public void editorUpdate(float dt) {
        Camera camera = Window.getScene().camera();
        Vector2f cameraPos = camera.position;
        Vector2f projectionSize = camera.getProjectionSize();

        float firstX = ((int)Math.floor(cameraPos.x / Settings.GRID_WIDTH)) * Settings.GRID_HEIGHT;
        float firstY = ((int)Math.floor(cameraPos.y / Settings.GRID_HEIGHT)) * Settings.GRID_HEIGHT;

        int numVtLines = (int)(projectionSize.x * camera.getZoom() / Settings.GRID_WIDTH) + 2;
        int numHzLines = (int)(projectionSize.y * camera.getZoom() / Settings.GRID_HEIGHT) + 2;

        float width = (int)(projectionSize.x * camera.getZoom()) + (5 * Settings.GRID_WIDTH);
        float height = (int)(projectionSize.y * camera.getZoom()) + (5 * Settings.GRID_HEIGHT);

        int maxLines = Math.max(numVtLines, numHzLines);
        Vector3f color = new Vector3f(0.2f, 0.2f, 0.2f);
        for (int i=0; i < maxLines; i++) {
            float x = firstX + (Settings.GRID_WIDTH * i);
            float y = firstY + (Settings.GRID_HEIGHT * i);

            DebugDraw.addLine2D(new Vector2f(firstX, GameWorld.VOID_LEVEL),
                    new Vector2f(firstX + width, GameWorld.VOID_LEVEL),
                    new Vector3f(1f, 0f, 0f));

            if (i < numVtLines) {
                DebugDraw.addLine2D(new Vector2f(x, firstY), new Vector2f(x, firstY + height), color);
            }

            if (i < numHzLines) {
                DebugDraw.addLine2D(new Vector2f(firstX, y), new Vector2f(firstX + width, y), color);
            }
        }
    }
}