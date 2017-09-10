package com.jianyuan.mechanics.disintegrator;

import com.jianyuan.mechanics.Mechanics;
import com.jianyuan.mechanics.listener.BaseListener;
import com.jianyuan.mechanics.util.Util;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;

public class DisintegratorListener extends BaseListener implements Listener {

    public DisintegratorListener(Mechanics plugin) {
        super(plugin);
    }

    /**
     * 粉碎机工作核心
     *
     * @param e
     */
    @EventHandler
    public void onWork(BlockPistonExtendEvent e) {
        if (e.isCancelled()) {
            return;
        }
        for (Block block : e.getBlocks()) {
            if (plugin.getDisintegratorManager().isDisintegrator(block.getLocation())) {
                // 若有活塞试图推动粉碎机 则取消 粉碎机无法推动
                e.setCancelled(true);
                return;
            }
        }
        Location location = e.getBlock().getLocation();
        plugin.getMyLogger().debug("{} {}粉碎机", Util.getLocationString(location), plugin.getDisintegratorManager().isDisintegrator(location) ? "是" : "不是");
        if (!plugin.getDisintegratorManager().isDisintegrator(location)) {
            // 若不是粉碎机
            return;
        }
        if (e.isSticky()) {
            if (!e.getDirection().name().equals(BlockFace.DOWN.name())) {
                //探头不向下
                plugin.getMyLogger().debug("方向: {}", e.getDirection().name());
                e.setCancelled(true);
                return;
            }
            // 粉碎机使用粘性活塞
            if (e.getBlocks().size() != 1) {
                // 未装粉碎头或者粉碎头被卡住了
                // TODO 粉碎头卡主消耗大量耐久
                plugin.getMyLogger().debug("卡住了: {}", e.getBlocks().size());
                e.setCancelled(true);
                return;
            } else {
                Block b = e.getBlocks().get(0);
                // TODO 粉碎头类型
                switch (b.getType()) {
                    case IRON_BLOCK:
                    case GOLD_BLOCK:
                    case DIAMOND_BLOCK:
                        disintegrating(b.getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN));
                        break;
                    default:
                        // 装错粉碎头
                        plugin.getMyLogger().debug("类型: {}", b.getType());
                        e.setCancelled(true);
                        return;
                }
            }
        }
    }

    /**
     * 粉碎方块
     */
    private void disintegrating(Block b) {
        final Collection<ItemStack> drops = b.getDrops(new ItemStack(Material.DIAMOND_PICKAXE));
        plugin.getMyLogger().debug("要粉碎的块为: {}", b.getType());
        b.setType(Material.AIR);
        BukkitRunnable dropItemRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                // b.getWorld().playSound(b.getLocation().add(0.5, 0.5, 0.5), Sound.);
                for (ItemStack itemStack : drops) {
                    b.getWorld().dropItem(b.getLocation().add(0.5, 0.5, 0.5), itemStack);
                }
            }
        };
        dropItemRunnable.runTaskLater(plugin, 1);
    }
}
