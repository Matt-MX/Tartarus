package com.mattmx.tartarus.world.guis;

import com.mattmx.tartarus.components.Component;
import com.mattmx.tartarus.components.GameCamera;
import com.mattmx.tartarus.gameengine.GameObject;
import com.mattmx.tartarus.gameengine.Window;
import com.mattmx.tartarus.util.Authentication;
import com.mattmx.tartarus.world.GameWorld;
import com.mattmx.tartarus.world.entities.PlayablePlayer;
import com.mattmx.tartarus.world.guis.text.ColoredString;
import com.mattmx.tartarus.world.guis.text.FormattedString;
import imgui.ImDrawList;
import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiInputTextFlags;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;
import imgui.type.ImString;

import java.util.*;

public class ChatSystem extends Component {
    private transient static final String FORMAT = "<$username> $message";
    private transient List<FormattedString> chat;
    private ImString input = new ImString();
    private GameWorld world;

    public ChatSystem(GameWorld world) {
        this.world = world;
    }

    @Override
    public void start() {
        this.isWindow = true;
        this.chat = new ArrayList<>();
        addMessage("&4Initialized chat");
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void imgui() {
        // Chat format
        int width = 400;
        int height = 300;
        ImGui.pushStyleColor(ImGuiCol.TitleBg, ImGui.getColorU32(0f, 0f, 0f, 0.5f));
        ImGui.pushStyleColor(ImGuiCol.TitleBgActive, ImGui.getColorU32(0f, 0f, 0f, 0.5f));
        ImGui.pushStyleColor(ImGuiCol.WindowBg, ImGui.getColorU32(0f, 0f, 0f, 0.5f));
        ImGui.setNextWindowSize(width, height);
        ImGui.setNextWindowSizeConstraints(width, height, width, height);
        ImGui.setNextWindowPos(20, Window.getHeight() - height - 20 - ImGui.getFrameHeight());
        ImGui.begin("Chat", new ImBoolean(false), ImGuiWindowFlags.NoDocking |
                ImGuiWindowFlags.NoMove |
                ImGuiWindowFlags.NoSavedSettings |
                ImGuiWindowFlags.NoCollapse |
                ImGuiWindowFlags.NoResize |
                ImGuiWindowFlags.NoBackground |
                ImGuiWindowFlags.NoTitleBar
        );
        ImGui.pushStyleColor(ImGuiCol.Button, ImGui.getColorU32(0f, 0f, 0f, 0.3f));
        ImGui.pushStyleColor(ImGuiCol.ButtonHovered, ImGui.getColorU32(0f, 0f, 0f, 0.5f));
        ImGui.pushStyleColor(ImGuiCol.ButtonActive, ImGui.getColorU32(1f, 1f, 1f, 0.1f));

        // Display chat content
        List<FormattedString> msgs = new ArrayList<>(chat);
        for (FormattedString msg : msgs) {
            if (msg.isFormatted()) {
                for (ColoredString section : msg.get()) {
                    if (section.getColor() != null) {
                        ImGui.pushStyleColor(ImGuiCol.Text, ImGui.getColorU32(section.getColor().x,
                                section.getColor().y, section.getColor().z, section.getColor().w));
                    }
                    ImGui.text(section.getString());
                    if (section.getColor() != null) {
                        ImGui.popStyleColor();
                    }
                }
            } else {
                ImGui.text(msg.toString());
            }
        }
        ImGui.popStyleColor(6);
        ImGui.end();

        drawInputBox(width, height);
    }

    public void addMessage(String content) {
        chat.add(new FormattedString(content));
        if (chat.size() > 100) {
            chat.remove(99);
        }
    }

    public void command(String[] args) {
        if (args.length > 0) {
            args[0] = args[0].substring(1);
            if (args[0].startsWith("temp")) {
                GameObject cam = Window.getScene().getGameObjectWith(GameCamera.class);
                if (cam != null) {
                    GameCamera g = cam.getComponent(GameCamera.class);
                }
            } else if (args[0].startsWith("damage")) {
                PlayablePlayer p = world.getPlayer().getComponent(PlayablePlayer.class);
                if (p != null) {
                    p.damage(1);
                    System.out.println("damaged");
                }
            }
        }
    }

    public void drawInputBox(int width, int height) {
        ImGui.pushStyleColor(ImGuiCol.TitleBg, ImGui.getColorU32(0f, 0f, 0f, 0.5f));
        ImGui.pushStyleColor(ImGuiCol.TitleBgActive, ImGui.getColorU32(0f, 0f, 0f, 0.5f));
        ImGui.pushStyleColor(ImGuiCol.WindowBg, ImGui.getColorU32(0f, 0f, 0f, 0.5f));
        ImGui.setNextWindowSize(width, height);
        ImGui.setNextWindowSizeConstraints(width, height, width, height);
        ImGui.setNextWindowPos(20, Window.getHeight() - ImGui.getFrameHeight() - 20);
        ImGui.begin("input Chat", new ImBoolean(false), ImGuiWindowFlags.NoDocking |
                ImGuiWindowFlags.NoMove |
                ImGuiWindowFlags.NoSavedSettings |
                ImGuiWindowFlags.NoCollapse |
                ImGuiWindowFlags.NoResize |
                ImGuiWindowFlags.NoBackground |
                ImGuiWindowFlags.NoTitleBar
        );
        ImGui.pushStyleColor(ImGuiCol.Button, ImGui.getColorU32(0f, 0f, 0f, 0.3f));
        ImGui.pushStyleColor(ImGuiCol.ButtonHovered, ImGui.getColorU32(0f, 0f, 0f, 0.5f));
        ImGui.pushStyleColor(ImGuiCol.ButtonActive, ImGui.getColorU32(1f, 1f, 1f, 0.1f));
        if (ImGui.isWindowHovered()) ImGui.setKeyboardFocusHere();
        if (ImGui.inputTextWithHint("", "Chat", input, ImGuiInputTextFlags.EnterReturnsTrue)) {
            if (input.get().startsWith("/")) {
                command(input.get().split(" "));
            } else {
                addMessage(
                        FORMAT.replace("$username", Authentication.USERNAME.equals("") ? "Player" : Authentication.USERNAME)
                                .replace("$message", input.get())
                );
            }
            input.set("");
        }
        ImGui.popStyleColor(6);
        ImGui.end();
    }
}
