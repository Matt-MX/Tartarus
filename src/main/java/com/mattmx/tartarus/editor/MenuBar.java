package com.mattmx.tartarus.editor;

import com.mattmx.tartarus.observers.EventSystem;
import com.mattmx.tartarus.observers.events.Event;
import com.mattmx.tartarus.observers.events.EventType;
import imgui.ImGui;

public class MenuBar {

    public void imgui() {
        ImGui.beginMenuBar();

        if (ImGui.beginMenu("File")) {
            if (ImGui.menuItem("Save", "Ctrl+S")) {
                EventSystem.notify(null, new Event(EventType.SaveLevel));
            }

            if (ImGui.menuItem("Load", "Ctrl+O")) {
                EventSystem.notify(null, new Event(EventType.LoadLevel));
            }

            ImGui.endMenu();
        }

        ImGui.endMenuBar();
    }
}