package org.ame.civspell.commands;


import org.ame.civspell.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class GiveScrollCommand implements CommandExecutor {
    public GiveScrollCommand(Main mainPlugin) {
        this.mainPlugin = mainPlugin;
    }

    private Main mainPlugin;
    private String formatString;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("civ-spell-api.give.scroll")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
            return true;
        }
        if (args.length < 3) {
            return false;
        }
        String playerName = args[0];
        int number;
        try {
            number = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage("Invalid number: " + e.getMessage());
            return false;
        }
        ArrayList<String> argsList = new ArrayList<>(Arrays.asList(args));
        argsList.remove(0); // Lazy way to remove first 2 elements.
        argsList.remove(0);
        String spellName = argsList.stream().reduce((a, b) -> a + b).orElse("");
        Player player = mainPlugin.getServer().getPlayer(playerName);
        ItemStack scroll = new ItemStack(Material.SUGAR_CANE, number);
        ItemMeta meta = scroll.getItemMeta();
        meta.setDisplayName(mainPlugin.config.getSpellNameFormat().replaceFirst("\\{NAME}", spellName));
        scroll.setItemMeta(meta);
        player.getInventory().addItem(scroll);
        return true;
    }
}
