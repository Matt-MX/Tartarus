package com.mattmx.tartarus.components;

import com.mattmx.tartarus.editor.PropertiesWindow;
import com.mattmx.tartarus.gameengine.GameObject;
import com.mattmx.tartarus.gameengine.KeyListener;
import com.mattmx.tartarus.gameengine.Window;
import com.mattmx.tartarus.util.Settings;

import java.util.*;

import static org.lwjgl.glfw.GLFW.*;

public class KeyControls extends Component {

    @Override
    public void editorUpdate(float dt) {
        PropertiesWindow propertiesWindow = Window.getImguiLayer().getPropertiesWindow();
        GameObject activeGameObject = propertiesWindow.getActiveGameObject();
        List<GameObject> activeGameObjects = propertiesWindow.getActiveGameObjects();

        if (KeyListener.isKeyPressed(GLFW_KEY_LEFT_CONTROL) &&
                KeyListener.keyBeginPress(GLFW_KEY_D) && activeGameObject != null) {
            GameObject newObj = activeGameObject.copy();
            Window.getScene().addGameObjectToScene(newObj);
            newObj.transform.position.add(Settings.GRID_WIDTH, 0);
            propertiesWindow.setActiveGameObject(newObj);
        } else if (KeyListener.isKeyPressed(GLFW_KEY_LEFT_CONTROL)
                && KeyListener.keyBeginPress(GLFW_KEY_D) && activeGameObjects.size() > 1) {
            List<GameObject> gameObjects = new ArrayList<>(activeGameObjects);
            propertiesWindow.clearSelected();
            for (GameObject go : gameObjects) {
                GameObject copy = go.copy();
                Window.getScene().addGameObjectToScene(copy);
                propertiesWindow.addActiveGameObject(copy);
            }
        }
        else if (KeyListener.keyBeginPress(GLFW_KEY_DELETE)) {
            for (GameObject go : activeGameObjects) {
                go.destroy();
            }
            propertiesWindow.clearSelected();
        }
    }
}
