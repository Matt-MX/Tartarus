package com.mattmx.tartarus.components;

import com.mattmx.tartarus.editor.PropertiesWindow;
import com.mattmx.tartarus.gameengine.MouseListener;

public class ScaleGizmo extends Gizmo {
    public ScaleGizmo(Sprite scaleSprite, PropertiesWindow propertiesWindow) {
        super(scaleSprite, propertiesWindow);
    }

    @Override
    public void editorUpdate(float dt) {
        if (activeGameObject != null) {
            if (xAxisActive && !yAxisActive) {
                activeGameObject.transform.scale.x -= MouseListener.getWorldDx();
            } else if (yAxisActive) {
                activeGameObject.transform.scale.y -= MouseListener.getWorldDy();
            }
        }

        super.editorUpdate(dt);
    }
}