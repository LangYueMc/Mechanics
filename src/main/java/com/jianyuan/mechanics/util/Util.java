package com.jianyuan.mechanics.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class Util {
    private Util() {

    }

    public static String getLocationString(Location location) {
        if (location == null) {
            return null;
        }
        return String.format("%s(%d,%d,%d)", location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public static Location getLocationByString(String location) {
        if (location == null || location.trim().length() == 0) {
            return null;
        }
        final String[] parts = location.split("\\(");
        if (parts.length == 2) {
            final World w = Bukkit.getServer().getWorld(parts[0]);
            final String[] p = parts[1].split(",");
            final int x = Integer.parseInt(p[1]);
            final int y = Integer.parseInt(p[2]);
            final int z = Integer.parseInt(p[3].replace(")", ""));
            return new Location(w, x, y, z);
        }
        return null;
    }

    public static String getWorldNameByLocation(String location) {
        return location.split("\\(")[0];
    }
}
