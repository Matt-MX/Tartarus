package com.mattmx.tartarus.world.guis;

import com.mattmx.tartarus.components.Component;
import com.mattmx.tartarus.gameengine.ImGuiLayer;
import com.mattmx.tartarus.world.entities.PlayablePlayer;
import com.mattmx.tartarus.gameengine.GameObject;
import com.mattmx.tartarus.gameengine.Window;
import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;

import java.util.*;

public class DebugSystem extends Component {
    private boolean isEnabled = false;
    private ImBoolean hitboxes = new ImBoolean(false);

    public DebugSystem() {
        this.isWindow = true;
    }

    @Override
    public void start() {

    }

    @Override
    public void update(float dt) {
        if (hitboxes.get()) {
            List<GameObject> blockObject = Window.getScene().getGameObjects();
            for (GameObject o : blockObject) {
                o.editorUpdate(dt);
            }
        }
    }

    @Override
    public void imgui() {
        GameObject playerObject = Window.getScene().getGameObjectWith(PlayablePlayer.class);
        if (playerObject != null) {
            ImGui.setNextWindowSize(200, 200);
            ImGui.setNextWindowPos(20, 20);
            ImGui.begin("Debug", ImGuiWindowFlags.NoScrollbar |
                    ImGuiWindowFlags.NoScrollWithMouse |
                    ImGuiWindowFlags.NoMove |
                    ImGuiWindowFlags.NoResize |
                    ImGuiWindowFlags.NoTitleBar |
                    ImGuiWindowFlags.NoBackground
            );
            ImGui.pushFont(Window.getImguiLayer().title);
            ImGui.text("Debug");
            ImGui.popFont();
            ImGui.text("X: " + playerObject.transform.position.x);
            ImGui.text("Y: " + playerObject.transform.position.y);
            ImGui.checkbox("Hitboxes", hitboxes);
            ImGui.end();
        }
        isEnabled = true;
    }

    public void setEnabled(boolean state) {
        this.isEnabled = state;
    }

    public boolean isEnabled() {
        return this.isEnabled;
    }
}
