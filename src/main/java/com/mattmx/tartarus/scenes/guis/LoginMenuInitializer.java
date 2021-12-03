package com.mattmx.tartarus.scenes.guis;

import com.mattmx.tartarus.gameengine.Window;
import com.mattmx.tartarus.scenes.Scene;
import com.mattmx.tartarus.scenes.SceneInitializer;
import com.mattmx.tartarus.util.Authentication;
import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;
import imgui.type.ImString;

public class LoginMenuInitializer extends SceneInitializer {
    SceneInitializer parent;
    private String textDisplay = "";
    ImString username = new ImString();
    ImString password = new ImString();

    public LoginMenuInitializer(SceneInitializer parent) {
        this.parent = parent;
    }
    @Override
    public void init(Scene scene) {

    }

    @Override
    public void loadResources(Scene scene) {

    }

    @Override
    public void imgui() {
        ImGui.pushStyleColor(ImGuiCol.TitleBg, ImGui.getColorU32(0f, 0f, 0f, 0.5f));
        ImGui.pushStyleColor(ImGuiCol.TitleBgActive, ImGui.getColorU32(0f, 0f, 0f, 0.5f));
        ImGui.pushStyleColor(ImGuiCol.WindowBg, ImGui.getColorU32(0f, 0f, 0f, 0.5f));
        ImGui.setNextWindowSize(300, 200);
        ImGui.setNextWindowPos(Window.getWidth() / 2 - 150, Window.getHeight() / 2 - 200);
        ImGui.begin("LOGIN", new ImBoolean(false), ImGuiWindowFlags.NoDocking |
                ImGuiWindowFlags.NoResize |
                ImGuiWindowFlags.NoMove |
                ImGuiWindowFlags.NoSavedSettings |
                ImGuiWindowFlags.NoCollapse
        );
        ImGui.pushStyleColor(ImGuiCol.Button, ImGui.getColorU32(0f, 0f, 0f, 0.3f));
        ImGui.pushStyleColor(ImGuiCol.ButtonHovered, ImGui.getColorU32(0f, 0f, 0f, 0.5f));
        ImGui.pushStyleColor(ImGuiCol.ButtonActive, ImGui.getColorU32(1f, 1f, 1f, 0.1f));
        ImGui.pushStyleColor(ImGuiCol.ChildBg, ImGui.getColorU32(0f, 0f, 0f, 0.5f));

        ImGui.spacing();
        ImGui.pushStyleColor(ImGuiCol.Text, ImGui.getColorU32(0.75f, 0f, 0f, 1f));
        ImGui.text(textDisplay);
        ImGui.popStyleColor();
        ImGui.spacing();

        ImGui.inputText("Username", username);
        ImGui.inputText("Password", password);
        if (ImGui.button("Cancel")) {
            Window.changeScene(parent);
        }
        ImGui.sameLine();
        if (ImGui.button("Login")) {
            // Log in pressed
            if (username.get().matches("^[(A-Z)(a-z)(0-9)_]{4,16}$") &&
            password.get().matches("^[^'.]{4,16}$")) {
                Authentication.State s = Authentication.attemptLogin(username.get(), password.get());
                if (s == Authentication.State.LOGIN_SUCCESSFUL) {
                    textDisplay = "Successfully logged in!";
                    Window.changeScene(new MultiplayerMenuInitializer());
                } else if (s == Authentication.State.LOGIN_FAILURE) {
                    textDisplay = "Username or password incorrect.";
                } else {
                    textDisplay = "Authentication servers may be down!";
                }
            } else {
                textDisplay = "Username or password incorrect.";
            }
        }

        ImGui.popStyleColor(7);
        ImGui.end();
    }
}
