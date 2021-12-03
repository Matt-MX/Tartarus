package com.mattmx.tartarus;

import com.mattmx.tartarus.gameengine.Window;
import com.mattmx.tartarus.util.SettingsFile;

public class Main {

    public static void main(String[] args) {
        SettingsFile.get();
        Window window = Window.get();
        window.run();
    }

}
