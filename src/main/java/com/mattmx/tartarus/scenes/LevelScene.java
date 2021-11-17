package com.mattmx.tartarus.scenes;

import com.mattmx.tartarus.gameengine.Window;
import com.mattmx.tartarus.scenes.Scene;

public class LevelScene extends Scene {
    public LevelScene(){
        System.out.println("Inside Level Scene");
        Window.get().r = 1;
        Window.get().g = 1;
        Window.get().b = 1;
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render() {

    }
}
