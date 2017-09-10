package com.jianyuan.mechanics.listener;

import com.jianyuan.mechanics.Mechanics;
import com.jianyuan.mechanics.config.Settings;

public class BaseListener {
    protected Mechanics plugin;
    protected Settings settings;

    public BaseListener(Mechanics plugin) {
        this.plugin = plugin;
        this.settings = plugin.getSettings();
    }
}
