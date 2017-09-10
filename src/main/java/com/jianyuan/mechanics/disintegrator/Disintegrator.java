package com.jianyuan.mechanics.disintegrator;

import com.jianyuan.mechanics.util.Util;
import org.bukkit.Location;

import java.util.UUID;

/**
 * 粉碎机
 */
public class Disintegrator {
    private String uuid;        // 编号
    private String location;    // 位置
    private String owner;       // 拥有者
    private String type;        // 粉碎头类型
    private double durability;  // 粉碎头耐久
    private String enchantments;  // 粉碎头附魔效果
    private boolean isPublic;  // 是否公共的

    public Disintegrator(String playerId, Location location) {
        this.uuid = UUID.randomUUID().toString();
        this.location = Util.getLocationString(location);
        this.owner = playerId;
        this.type = null;
        this.durability = 0;
        this.enchantments = null;
        this.isPublic = false;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getDurability() {
        return durability;
    }

    public void setDurability(double durability) {
        this.durability = durability;
    }

    public String getEnchantments() {
        return enchantments;
    }

    public void setEnchantments(String enchantments) {
        this.enchantments = enchantments;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    @Override
    public String toString() {
        return String.format("%s={location: %s, owner: %s, type: %s, enchantments: %s, durability: %f}", this.uuid, this.location, this.owner, this.type, this.enchantments, this.durability);
    }
}
