package fr.slophil.hammercraft.listeners;

import fr.slophil.hammercraft.HammerCraft;
import fr.slophil.hammercraft.utils.HammersConstructor;
import fr.slophil.hammercraft.utils.PlayerDirection;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;
import org.bukkit.material.MaterialData;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class HammerBlockBreakEvent implements Listener {


    public HammerBlockBreakEvent(HammerCraft hammerCraft) {
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        // North/South : X/Y +-
        // East/West : Z/Y +-
        // Up/Down : X/Z +-
        PlayerDirection cardinalDirection = PlayerDirection.getPlayerDirection(player);

        int blockX = block.getLocation().getBlockX();
        int blockY = block.getLocation().getBlockY();
        int blockZ = block.getLocation().getBlockZ();
        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        if (!itemInHand.hasItemMeta()) {
            return;
        }

        ItemStack itemInHandClone = itemInHand.clone();

        if (itemInHandClone.getItemMeta() instanceof Damageable) {
            ((Damageable) itemInHandClone.getItemMeta()).setDamage(0);
        }

        // NORTH / SOUTH
        for (HammersConstructor hammer : HammersConstructor.values()) {
            if (isSimilarIgnoreDurability(itemInHand, hammer.getItemStack())) {
                if (cardinalDirection.equals(PlayerDirection.NORTH) || cardinalDirection.equals(PlayerDirection.SOUTH)) {
                    for (int x = blockX - 1; x <= blockX + 1; x++) {
                        for (int y = blockY - 1; y <= blockY + 1; y++) {
                            Block blockToBreak = block.getWorld().getBlockAt(x, y, blockZ);
                            if (blockToBreak.getLocation().equals(block.getLocation())) {
                                continue;
                            }
                            if (blockToBreak.getType() != Material.AIR) {
                                if (blockToBreak.isValidTool(itemInHand)) {
                                    blockToBreak.breakNaturally(itemInHand);
                                    ((Damageable) itemInHand.getItemMeta()).setDamage(((Damageable) itemInHand.getItemMeta()).getDamage() + 1);
                                    itemInHand.setItemMeta(itemInHand.getItemMeta());
                                }
                            }
                        }
                    }
                }
            }
        }

        // EAST / WEST
        for (HammersConstructor hammer : HammersConstructor.values()) {
            if (isSimilarIgnoreDurability(itemInHand, hammer.getItemStack())) {
                if (cardinalDirection.equals(PlayerDirection.EAST) || cardinalDirection.equals(PlayerDirection.WEST)) {
                    for (int z = blockZ - 1; z <= blockZ + 1; z++) {
                        for (int y = blockY - 1; y <= blockY + 1; y++) {
                            Block blockToBreak = block.getWorld().getBlockAt(blockX, y, z);
                            if (blockToBreak.getType() != Material.AIR) {
                                if (blockToBreak.isValidTool(itemInHand)) {
                                    blockToBreak.breakNaturally(itemInHand);
                                    ((Damageable) itemInHand.getItemMeta()).setDamage(((Damageable) itemInHand.getItemMeta()).getDamage() + 1);
                                    itemInHand.setItemMeta(itemInHand.getItemMeta());
                                }
                            }
                        }
                    }
                }
            }
        }

        // UP / DOWN
        for (HammersConstructor hammer : HammersConstructor.values()) {
            if (isSimilarIgnoreDurability(itemInHand, hammer.getItemStack())) {
                if (cardinalDirection.equals(PlayerDirection.UP) || cardinalDirection.equals(PlayerDirection.DOWN)) {
                    for (int x = blockX - 1; x <= blockX + 1; x++) {
                        for (int z = blockZ - 1; z <= blockZ + 1; z++) {
                            Block blockToBreak = block.getWorld().getBlockAt(x, blockY, z);
                            if (blockToBreak.getType() != Material.AIR) {
                                if (blockToBreak.isValidTool(itemInHand)) {
                                    blockToBreak.breakNaturally(itemInHand);
                                    ((Damageable) itemInHand.getItemMeta()).setDamage(((Damageable) itemInHand.getItemMeta()).getDamage() + 1);
                                    itemInHand.setItemMeta(itemInHand.getItemMeta());
                                }
                            }
                        }
                    }
                }
            }
        }


    }

    public static boolean isSimilarIgnoreDurability(ItemStack itemStack1, ItemStack itemStack2) {
        if (itemStack1 == null || itemStack2 == null || itemStack1.getType() != itemStack2.getType()) {
            return false;
        }
        ItemMeta itemMeta1 = itemStack1.getItemMeta();
        ItemMeta itemMeta2 = itemStack2.getItemMeta();
        if (itemMeta1 == null || itemMeta2 == null) {
            return itemMeta1 == itemMeta2;
        }
        if (itemMeta1.hasDisplayName() != itemMeta2.hasDisplayName()
                || (itemMeta1.hasDisplayName() && !itemMeta1.getDisplayName().equals(itemMeta2.getDisplayName()))) {
            return false;
        }
        if (itemMeta1.hasLore() != itemMeta2.hasLore()
                || (itemMeta1.hasLore() && !itemMeta1.getLore().equals(itemMeta2.getLore()))) {
            return false;
        }
        if (itemMeta1.hasEnchants() != itemMeta2.hasEnchants()
                || (itemMeta1.hasEnchants() && !itemMeta1.getEnchants().equals(itemMeta2.getEnchants()))) {
            return false;
        }
        Set<ItemFlag> itemFlags1 = itemMeta1.getItemFlags();
        Set<ItemFlag> itemFlags2 = itemMeta2.getItemFlags();
        if ((itemFlags1.contains(ItemFlag.HIDE_ENCHANTS) != itemFlags2.contains(ItemFlag.HIDE_ENCHANTS))
                || (itemFlags1.contains(ItemFlag.HIDE_ENCHANTS) && !itemFlags1.equals(itemFlags2))) {
            return false;
        }
        if (itemFlags1.contains(ItemFlag.HIDE_ATTRIBUTES) != itemFlags2.contains(ItemFlag.HIDE_ATTRIBUTES)
                || (itemFlags1.contains(ItemFlag.HIDE_ATTRIBUTES) && !itemFlags1.equals(itemFlags2))) {
            return false;
        }
        return true;
    }

}
