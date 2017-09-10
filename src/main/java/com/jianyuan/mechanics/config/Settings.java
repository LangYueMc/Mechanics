package com.jianyuan.mechanics.config;

import com.jianyuan.mechanics.Mechanics;
import org.bukkit.configuration.file.FileConfiguration;

public class Settings {
    private Mechanics plugin;
    private FileConfiguration config;

    public Settings(Mechanics plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    private boolean loadSettings() {
        long time = System.currentTimeMillis();
        boolean result = true;
        try {
            plugin.getMyLogger().setDebug(plugin.getConfig().getBoolean("debug"));
        } catch (Exception e) {
            result = false;
            plugin.getMyLogger().warning("配置文件加载失败，请检查配置！");
            plugin.getMyLogger().debug("", e);
        } finally {
            plugin.getMyLogger().info("加载配置文件{}, 共耗时 {} 毫秒", result ? "成功" : "失败", System.currentTimeMillis() - time);
        }
        return result;
    }

    public void load() {
        plugin.getMyLogger().info("开始加载配置文件");
        loadSettings();
    }

    public void reload() {
        plugin.reloadConfig();
        plugin.getMyLogger().info("开始重新加载配置文件");
        loadSettings();
    }
}
