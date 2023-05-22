package fr.slophil.hammercraft.utils;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

public class ItemBuilder {

    private ItemStack stack;

    public ItemBuilder(Material mat) {
        stack = new ItemStack(mat);
    }

    public ItemMeta getItemMeta() {
        return stack.getItemMeta();
    }

    public ItemBuilder setGlow(boolean glow) {
        if (glow) {
            addEnchant(Enchantment.FROST_WALKER, 1);
            addItemFlag(ItemFlag.HIDE_ENCHANTS);
        } else {
            ItemMeta meta = getItemMeta();
            for (Enchantment enchantment : meta.getEnchants().keySet()) {
                meta.removeEnchant(enchantment);
            }
        }
        return this;
    }

    public ItemBuilder setItemMeta(ItemMeta meta) {
        stack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setDisplayName(String displayname) {
        ItemMeta meta = getItemMeta();
        meta.setDisplayName(displayname);
        setItemMeta(meta);
        return this;
    }

    public ItemBuilder setLore(ArrayList<String> lore) {
        ItemMeta meta = getItemMeta();
        meta.setLore(lore);
        setItemMeta(meta);
        return this;
    }

    public ItemBuilder setLore(String lore) {
        ItemMeta meta = getItemMeta();
        ArrayList<String> loreList = new ArrayList<>();
        loreList.add(lore);
        meta.setLore(loreList);
        setItemMeta(meta);
        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchantment, int level) {
        ItemMeta meta = getItemMeta();
        meta.addEnchant(enchantment, level, true);
        setItemMeta(meta);
        return this;
    }

    public ItemBuilder addItemFlag(ItemFlag flag) {
        ItemMeta meta = getItemMeta();
        meta.addItemFlags(flag);
        setItemMeta(meta);
        return this;
    }

    public <T> ItemBuilder setPersistentDataContainer(NamespacedKey namespacedKey, PersistentDataType<T, T> persistentDataType, T value) {

        ItemMeta meta = getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        if (!data.has(namespacedKey, persistentDataType)) {
            data.set(namespacedKey, persistentDataType, value);
        } else {
            return null;
        }
        setItemMeta(meta);
        return this;
    }


    public ItemStack build() {
        return stack;
    }

    }
