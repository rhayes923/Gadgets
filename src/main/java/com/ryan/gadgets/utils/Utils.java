package com.ryan.gadgets.utils;

import com.ryan.gadgets.items.Gadget;
import org.reflections.Reflections;

import java.util.HashSet;
import java.util.Set;

public class Utils {

    static final String packageName = "com.ryan.gadgets.items";

    public static Gadget getGadget(String gadget) {
        for (Class<?> clazz : new Reflections(packageName).getSubTypesOf(Gadget.class)) {
            if (gadget.equals(clazz.getSimpleName())) {
                try {
                    return (Gadget) clazz.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static Set<Class<?>> getAllGadgets() {
        return new HashSet<>(new Reflections(packageName).getSubTypesOf(Gadget.class));
    }
}