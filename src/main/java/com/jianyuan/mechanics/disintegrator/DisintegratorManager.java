package com.jianyuan.mechanics.disintegrator;

import com.jianyuan.mechanics.util.Util;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

import java.util.HashMap;
import java.util.Map;

public class DisintegratorManager {
    private Map<String, String> worldDisintegrators = new HashMap<>();
    private Map<String, Disintegrator> disintegrators = new HashMap<>();

    /**
     * 创建粉碎机
     *
     * @param player   玩家
     * @param location 地点
     */
    public void creat(Player player, Location location) {
        Block block = location.getBlock();
        if (block == null || !block.getType().equals(Material.PISTON_STICKY_BASE)) {
            return;
        }
        Disintegrator disintegrator = new Disintegrator(player.getUniqueId().toString(), location);
        String uuid = disintegrator.getUuid();
        disintegrators.put(uuid, disintegrator);
        String locationStr = Util.getLocationString(location);
        worldDisintegrators.put(locationStr, uuid);
    }

    /**
     * 根据UUID获取粉碎机
     *
     * @param uuid
     * @return
     */
    public Disintegrator getByUUid(String uuid) {
        return disintegrators.get(uuid);
    }

    /**
     * 判断坐标是否为刷石机
     *
     * @param location
     * @return
     */
    public boolean isDisintegrator(Location location) {
        String locationStr = Util.getLocationString(location);
        String uuid = worldDisintegrators.get(locationStr);
        return uuid != null && uuid.trim().length() > 0;
    }

    /**
     * 获取坐标点上的刷石机
     *
     * @param location
     * @return
     */
    public Disintegrator getByLocation(Location location) {
        String locationStr = Util.getLocationString(location);
        String uuid = worldDisintegrators.get(locationStr);
        return disintegrators.get(uuid);
    }

    public Disintegrator viewDetails(Player player, String uuid) {
        Disintegrator disintegrator = disintegrators.get(uuid);
        //player.openInventory(InventoryView);
        return disintegrator;
    }

    public void remove(Location location) {
        String locationStr = Util.getLocationString(location);
        String uuid = worldDisintegrators.get(locationStr);
        disintegrators.remove(uuid);
        worldDisintegrators.remove(locationStr);
    }

    public void clear() {
        worldDisintegrators.clear();
        disintegrators.clear();
    }
}
