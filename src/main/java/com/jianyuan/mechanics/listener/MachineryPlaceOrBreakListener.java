package com.jianyuan.mechanics.listener;

import com.jianyuan.mechanics.Mechanics;
import com.jianyuan.mechanics.disintegrator.Disintegrator;
import com.jianyuan.mechanics.disintegrator.DisintegratorItem;
import com.jianyuan.mechanics.util.Util;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class MachineryPlaceOrBreakListener extends BaseListener implements Listener {

    public MachineryPlaceOrBreakListener(Mechanics plugin) {
        super(plugin);
    }

    /**
     * 放置机器
     *
     * @param e
     */
    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (e.isCancelled()) {
            return;
        }
        // 放置主体
        ItemStack itemStack = e.getItemInHand();
        Location location = e.getBlock().getLocation();
        Player player = e.getPlayer();
        if (itemStack.getItemMeta().equals(DisintegratorItem.getItem().getItemMeta())) {
            // 放置粉碎机
            if (!player.hasPermission("Mechanics.Disintegrator.place")) {
                // 无权放置
                player.sendRawMessage("你无权安装粉碎机");
                e.setCancelled(true);
                return;
            }
            plugin.getMyLogger().debug("放置粉碎机 位置:{}", Util.getLocationString(location));
            plugin.getDisintegratorManager().creat(player, location);
            player.sendRawMessage("你成功安装了粉碎机");
        }
    }

    /**
     * 互动
     * @param e
     */
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.isCancelled()) {
            return;
        }
        Player user = e.getPlayer();
        Block block = e.getClickedBlock();
        if (plugin.getDisintegratorManager().isDisintegrator(block.getLocation())) {
            // 如果是粉碎机
            Disintegrator disintegrator = plugin.getDisintegratorManager().getByLocation(block.getLocation());
            if (!disintegrator.isPublic() && !user.getUniqueId().toString().equals(disintegrator.getOwner()) && !user.hasPermission("Mechanics.Disintegrator.other")) {
                // 非公共的且用户没有权限使用他人机器
                user.sendRawMessage("这个粉碎机不是你的，你无权使用！");
                e.setCancelled(true);
                return;
            }
            plugin.getMyLogger().debug("用户{}正在使用{}的粉碎机", user.getDisplayName(), disintegrator.getLocation());
            plugin.getDisintegratorManager().viewDetails(user, disintegrator.getUuid());
        }
    }

    /**
     * 卸下机器
     *
     * @param e
     */
    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (e.isCancelled()) {
            return;
        }
        Player player = e.getPlayer();
        if (player != null && player.getGameMode().equals(GameMode.CREATIVE)) {
            return;
        }
        Block block = e.getBlock();
        Location location = block.getLocation();
        List<ItemStack> drops = new ArrayList<>();
        if (plugin.getDisintegratorManager().isDisintegrator(block.getLocation())) {
            Disintegrator disintegrator = plugin.getDisintegratorManager().getByLocation(location);
            if (!disintegrator.isPublic() && !player.getUniqueId().toString().equals(disintegrator.getOwner()) && !player.hasPermission("Mechanics.Disintegrator.other")) {
                // 非公共的且用户没有权限使用他人机器
                player.sendRawMessage("这个粉碎机不是你的，你无权拆下！");
                e.setCancelled(true);
                return;
            }
            // 粉碎机 掉落物
            plugin.getMyLogger().debug("拆下 {} 的粉碎机", disintegrator.getLocation());
            ItemStack item = DisintegratorItem.getItem();
            drops.add(item);
            block.setType(Material.AIR);
        }
        BukkitRunnable dropItemRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                for (ItemStack itemStack : drops) {
                    block.getWorld().dropItem(location.add(0.5, 0.5, 0.5), itemStack);
                }
            }
        };
        dropItemRunnable.runTaskLater(plugin, 1);
    }

    /**
     * 爆炸粉碎机器
     *
     * @param e
     */
    @EventHandler
    public void onExplod(EntityExplodeEvent e) {
        if (e.isCancelled()) {
            return;
        }
        for (Block block : e.blockList()) {
            Location location = block.getLocation();
            if (plugin.getDisintegratorManager().isDisintegrator(location)) {
                plugin.getMyLogger().debug("炸毁 {} 的粉碎机", Util.getLocationString(location));
                // 移除粉碎机
                plugin.getDisintegratorManager().remove(location);
            }
        }
    }
}
