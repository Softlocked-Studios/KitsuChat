package com.softlocked.kitsuchat.core;

import com.softlocked.kitsuchat.api.event.Event;
import com.softlocked.kitsuchat.api.event.util.Listener;

import java.lang.reflect.Method;
import java.util.*;

public class EventHandler {
    private final static HashMap<Class<? extends Event>, HashMap<Method, Listener.Priority>> listeners = new HashMap<>();
    public static void registerListener(Object listener) {
        HashMap<Method, Listener.Priority> eventListeners = new HashMap<>();
        for (Method method : listener.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Listener.class)) {

                Listener annotation = method.getAnnotation(Listener.class);
                Listener.Priority priority = annotation.priority();
                if (method.getParameterCount() != 1) {
                    throw new IllegalArgumentException("Listener method must have exactly one parameter");
                }
                Class<?> parameter = method.getParameterTypes()[0];
                if (!Event.class.isAssignableFrom(parameter)) {
                    throw new IllegalArgumentException("Listener method parameter must be an event");
                }
                Class<? extends Event> event = (Class<? extends Event>) parameter;

                if (!listeners.containsKey(event)) {
                    listeners.put(event, new HashMap<>());
                }

                listeners.get(event).put(method, priority);
            }
        }

        // Sort the listeners by priority
        for (Map.Entry<Class<? extends Event>, HashMap<Method, Listener.Priority>> entry : listeners.entrySet()) {
            HashMap<Method, Listener.Priority> value = entry.getValue();
            value = sortListeners(value);
            listeners.put(entry.getKey(), value);
        }
    }

    private static HashMap<Method, Listener.Priority> sortListeners(HashMap<Method, Listener.Priority> eventListeners) {
        HashMap<Method, Listener.Priority> sortedListeners = new HashMap<>();
        List<Map.Entry<Method, Listener.Priority>> list = new LinkedList<>(eventListeners.entrySet());
        list.sort(Comparator.comparing(Map.Entry::getValue));

        for (Map.Entry<Method, Listener.Priority> entry : list) {
            sortedListeners.put(entry.getKey(), entry.getValue());
        }

        return sortedListeners;
    }

    public static void callEvent(Event event) {
        HashMap<Method, Listener.Priority> eventListeners = listeners.get(event.getClass());
        if (eventListeners == null || eventListeners.isEmpty()) {
            return;
        }
        List<Map.Entry<Method, Listener.Priority>> sortedListeners = new ArrayList<>(eventListeners.entrySet());
        sortedListeners.sort(Comparator.comparingInt(e -> -e.getValue().ordinal()));
        for (Map.Entry<Method, Listener.Priority> entry : sortedListeners) {
            try {
                Method method = entry.getKey();
                method.invoke(method.getDeclaringClass().newInstance(), event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
