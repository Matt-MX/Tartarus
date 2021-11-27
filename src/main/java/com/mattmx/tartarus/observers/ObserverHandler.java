package com.mattmx.tartarus.observers;

import com.mattmx.tartarus.gameengine.GameObject;
import com.mattmx.tartarus.observers.events.Event;

import java.util.*;

public class ObserverHandler {
    private static List<Observer> observers = new ArrayList<>();

    public static void addObserver(Observer observer) {
        observers.add(observer);
    }

    public static void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public static void notify(GameObject obj, Event event) {
        for (Observer observer : observers) {
            observer.onNotify(obj, event);
        }
    }
}