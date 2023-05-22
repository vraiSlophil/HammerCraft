package fr.slophil.hammercraft.utils;

import fr.slophil.hammercraft.HammerCraft;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.util.function.UnaryOperator;

public enum HammersConstructor {

    WOODEN_HAMMER(
            new ItemBuilder(Material.WOODEN_PICKAXE)
                    .setDisplayName("§6§lWooden hammer")
                    .setGlow(true)
                    .setLore("§7This hammer mine in 3x3.")
                    .build(),
            (recipe) -> recipe
                    .shape("OOO", " S ", " S ")
                    .setIngredient('O', Material.OAK_LOG)
                    .setIngredient('S', Material.STICK)
    ),
    STONE_HAMMER(
            new ItemBuilder(Material.STONE_PICKAXE)
                    .setDisplayName("§6§lStone hammer")
                    .setGlow(true)
                    .setLore("§7This hammer mine in 3x3")
                    .build(),
            (recipe) -> recipe
                    .shape("BBB", " S ", " S ")
                    .setIngredient('B', Material.STONE_BRICKS)
                    .setIngredient('S', Material.STICK)
    ),
    IRON_HAMMER(
            new ItemBuilder(Material.IRON_PICKAXE)
                    .setDisplayName("§6§lIron hammer")
                    .setGlow(true)
                    .setLore("§7This hammer mine in 3x3")
                    .build(),
            (recipe) -> recipe
                    .shape("III", " S ", " S ")
                    .setIngredient('I', Material.IRON_BLOCK)
                    .setIngredient('S', Material.STICK)
    ),
    GOLDEN_HAMMER(
            new ItemBuilder(Material.GOLDEN_PICKAXE)
                    .setDisplayName("§6§lGolden hammer")
                    .setGlow(true)
                    .setLore("§7This hammer mine in 3x3")
                    .build(),
            (recipe) -> recipe
                    .shape("GGG", " S ", " S ")
                    .setIngredient('G', Material.GOLD_BLOCK)
                    .setIngredient('S', Material.STICK)
    ),
    DIAMOND_HAMMER(
            new ItemBuilder(Material.DIAMOND_PICKAXE)
                    .setDisplayName("§6§lDiamond hammer")
                    .setGlow(true)
                    .setLore("§7This hammer mine in 3x3")
                    .build(),
            (recipe) -> recipe
                    .shape("DDD", " S ", " S ")
                    .setIngredient('D', Material.DIAMOND_BLOCK)
                    .setIngredient('S', Material.STICK)
    ),
    NETHERITE_HAMMER(
            new ItemBuilder(Material.NETHERITE_PICKAXE)
                    .setDisplayName("§6§lNetherite hammer")
                    .setGlow(true)
                    .setLore("§7This hammer mine in 3x3")
                    .build(),
            (recipe) -> recipe
                    .shape("NNN", " S ", " S ")
                    .setIngredient('N', Material.NETHERITE_INGOT)
                    .setIngredient('S', Material.STICK)
    );

    private final ShapedRecipe recipe;
    private final ItemStack itemStack;
    private final NamespacedKey key;


    HammersConstructor(ItemStack itemStack, UnaryOperator<ShapedRecipe> recipeProvider) {
        this.itemStack = itemStack;
        this.key = new NamespacedKey(HammerCraft.getInstance(), name().toLowerCase());
        this.recipe = recipeProvider.apply(new ShapedRecipe(key, itemStack));

    }

    public static void registerRecipes() {
        for (HammersConstructor recipe : values()) {
            Bukkit.addRecipe(recipe.recipe);
        }
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public ShapedRecipe getRecipe() {
        return recipe;
    }

}
