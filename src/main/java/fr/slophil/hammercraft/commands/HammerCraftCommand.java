package fr.slophil.hammercraft.commands;

import fr.slophil.hammercraft.HammerCraft;
import fr.slophil.hammercraft.utils.HammersConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Stream;

public class HammerCraftCommand implements TabExecutor {

    private static HammerCraft main;

    public HammerCraftCommand(HammerCraft hammerCraft) {
        main = hammerCraft;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage(main.getConfig().getString("HammerCraftCommandUsageMessage"));
            return false;
        }

//        if (!(sender instanceof Player)) {
//            sender.sendMessage("§cYou must be a player to use this command.");
//            return false;
//        }

        if (args.length < 2) {
            sender.sendMessage(main.getConfig().getString("HammerCraftCommandUsageMessage"));
            return false;
        }

        Player itemReceiver = main.getServer().getPlayer(args[0]);


        Stream.of(HammersConstructor.values())
                .filter(recipe -> recipe.name().equalsIgnoreCase(args[1]))
                .findFirst()
                .map(HammersConstructor::getItemStack)
                .ifPresentOrElse(
                        item -> itemReceiver.getInventory().addItem(item),
                        () -> {
                            if (!(sender instanceof Player)) {
                                main.getLogger().info(main.getConfig().getString("HammerCraftCommandUsageMessage"));
                            } else {
                                sender.sendMessage(main.getConfig().getString("HammerCraftCommandUsageMessage"));
                            }
                        }
                );


        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return null;
    }
}
