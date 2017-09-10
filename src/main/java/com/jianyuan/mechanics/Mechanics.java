/**
 * 剑缘服务器专用空岛插件
 * 不论你通过任何渠道获得此插件
 * 请告知与我，并在服务器空岛出生点说明来源
 * 且不能在传播
 *
 * @author langyue
 * @website www.jianymc.com
 * @QQ 252526086
 */
package com.jianyuan.mechanics;

import com.jianyuan.mechanics.command.CommandManager;
import com.jianyuan.mechanics.config.Settings;
import com.jianyuan.mechanics.disintegrator.DisintegratorItem;
import com.jianyuan.mechanics.disintegrator.DisintegratorManager;
import com.jianyuan.mechanics.disintegrator.DisintegratorListener;
import com.jianyuan.mechanics.listener.MachineryPlaceOrBreakListener;
import com.jianyuan.mechanics.log.Logger;
import org.bukkit.World;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * 剑缘服务器专用空岛插件
 *
 * @author langyue
 */
public class Mechanics extends JavaPlugin {
    private Settings settings;
    private Logger logger;
    private DisintegratorManager disintegratorManager;

    private final String config_version = "1.0";

    @Override
    public void onEnable() {
        logger = Logger.getLogger(this);
        disintegratorManager = new DisintegratorManager();
        if (!getDataFolder().exists() || !config_version.equals(getConfig().getString("config-version"))) {
            getConfig().options().copyDefaults(true);
            getConfig().set("GeneratesBlockWorlds", getWorlds());
            getConfig().set("config-version", config_version);
            saveConfig();
        }
        settings = new Settings(this);
        settings.load();
        this.getServer().addRecipe(DisintegratorItem.getShapedRecipe());
        PluginManager pm = getServer().getPluginManager();
        DisintegratorListener disintegratorListener = new DisintegratorListener(this);
        MachineryPlaceOrBreakListener machineryPlaceOrBreakListener = new MachineryPlaceOrBreakListener(this);
        pm.registerEvents(disintegratorListener, this);
        pm.registerEvents(machineryPlaceOrBreakListener, this);
        getCommand("mechanics").setExecutor(new CommandManager(this));
        logger.info("Enabled");
    }

    @Override
    public void onDisable() {
        disintegratorManager.clear();
        logger.info("Disabled");
    }

    public Logger getMyLogger() {
        return logger;
    }

    private List<String> getWorlds() {
        List<String> worldList = new ArrayList<String>();
        for (World w : getServer().getWorlds()) {
            worldList.add(w.getName());
        }
        return worldList;
    }

    public Settings getSettings() {
        return settings;
    }

    public DisintegratorManager getDisintegratorManager() {
        return disintegratorManager;
    }
}
