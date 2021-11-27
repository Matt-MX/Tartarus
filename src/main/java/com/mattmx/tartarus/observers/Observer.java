package com.mattmx.tartarus.observers;

import com.mattmx.tartarus.gameengine.GameObject;
import com.mattmx.tartarus.observers.events.Event;

public interface Observer {
    void onNotify(GameObject object, Event event);
}