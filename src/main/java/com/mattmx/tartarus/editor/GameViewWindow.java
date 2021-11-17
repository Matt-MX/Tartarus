package com.mattmx.tartarus.editor;

import com.mattmx.tartarus.gameengine.MouseListener;
import com.mattmx.tartarus.gameengine.Window;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiWindowFlags;
import org.joml.Vector2f;

public class GameViewWindow {

    private float leftX, rightX, topY, bottomY;

    public void imgui() {
        ImGui.begin("Game Viewport", ImGuiWindowFlags.NoScrollbar | ImGuiWindowFlags.NoScrollWithMouse);
        ImVec2 windowsize = getLargestSizeForViewport();
        ImVec2 windowpos = getCenteredPosForViewport(windowsize);

        ImGui.setCursorPos(windowpos.x, windowpos.y);

        ImVec2 topLeft = new ImVec2();
        ImGui.getCursorScreenPos(topLeft);
        topLeft.x -= ImGui.getScrollX();
        topLeft.y -= ImGui.getScrollY();
        leftX = topLeft.x;
        bottomY = topLeft.y;
        rightX = topLeft.x + windowsize.x;
        topY = topLeft.y + windowsize.y;

        int textureId = Window.getFramebuffer().getTextureId();
        ImGui.image(textureId, windowsize.x, windowsize.y, 0, 1, 1, 0);

        MouseListener.get().setGameViewPos(new Vector2f(topLeft.x, topLeft.y));
        MouseListener.get().setGameViewSize(new Vector2f(windowsize.x, windowsize.y));

        ImGui.end();
    }

    private ImVec2 getLargestSizeForViewport() {
        ImVec2 windowSize = new ImVec2();
        ImGui.getContentRegionAvail(windowSize);
        windowSize.x -= ImGui.getScrollX();
        windowSize.y -= ImGui.getScrollY();

        float aspectWidth = windowSize.x;
        float aspectHeight = windowSize.x / Window.getTargetAspectRatio();
        if (aspectHeight > windowSize.x) {
            aspectHeight = windowSize.y;
            aspectWidth = aspectHeight * Window.getTargetAspectRatio();
        }

        return new ImVec2(aspectWidth, aspectHeight);
    }

    private ImVec2 getCenteredPosForViewport(ImVec2 aspectSize) {
        ImVec2 windowSize = new ImVec2();
        ImGui.getContentRegionAvail(windowSize);
        windowSize.x -= ImGui.getScrollX();
        windowSize.y -= ImGui.getScrollY();

        float viewportX = (windowSize.x / 2.0f) - (aspectSize.x / 2.0f);
        float viewportY = (windowSize.y / 2.0f) - (aspectSize.y / 2.0f);

        return new ImVec2(viewportX + ImGui.getCursorPosX(),
                viewportY + ImGui.getCursorPosY());
    }

    public boolean getWantCaptureMouse() {
        return MouseListener.getX() >= leftX &&
                MouseListener.getX() <= rightX &&
                MouseListener.getY() >= bottomY &&
                MouseListener.getY() <= topY;
    }
}
