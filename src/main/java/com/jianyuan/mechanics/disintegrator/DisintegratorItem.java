package com.jianyuan.mechanics.disintegrator;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

/**
 * 粉碎机
 */
public class DisintegratorItem {
    private static ItemStack item;

    public DisintegratorItem() {
        item = new ItemStack(Material.PISTON_STICKY_BASE);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(Arrays.asList(new String[]{"可粉碎大多数方块", "§7请头朝下放置 否则无法工作", "§7放置完成后请在下方放置粉碎锤", "§7铁块 金块 钻石块三种锤头", "§7接通电源就可以工作了"}));
        meta.setDisplayName("§c粉碎机");
        meta.addEnchant(Enchantment.DIG_SPEED, 10, true);
        item.setItemMeta(meta);
    }

    public static ItemStack getItem() {
        if (item == null) {
            new DisintegratorItem();
        }
        return item;
    }

    public static ShapedRecipe getShapedRecipe() {
        if (item == null) {
            new DisintegratorItem();
        }
        return new ShapedRecipe(item).shape(new String[]{
                "CCC",
                "FBF",
                "DAE"})
                .setIngredient('A', Material.PISTON_BASE)
                .setIngredient('B', Material.IRON_BLOCK)
                .setIngredient('C', Material.SLIME_BALL)
                .setIngredient('D', Material.DIODE)
                .setIngredient('E', Material.REDSTONE_COMPARATOR)
                .setIngredient('F', Material.REDSTONE);
    }
}
