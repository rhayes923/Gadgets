package com.ryan.gadgets.utils;

import com.ryan.gadgets.Gadgets;
import com.ryan.gadgets.items.DiscoBall;
import com.ryan.gadgets.items.Gadget;
import com.ryan.gadgets.items.GrapplingHook;
import com.ryan.gadgets.items.TeleportStick;
import org.bukkit.NamespacedKey;

public class Utils {

    static final Gadget[] gadgets = {new TeleportStick(), new DiscoBall(), new GrapplingHook()};

    public static NamespacedKey getKey(String gadget) {
        switch (gadget) {
            case "TeleportStick":
                return gadgets[0].getKey();
            case "DiscoBall":
                return gadgets[1].getKey();
            case "GrapplingHook":
                return gadgets[2].getKey();
        }
        return new NamespacedKey(Gadgets.getInstance(), "None");
    }

    public static Gadget[] getGadgets() {
        return gadgets;
    }
}
