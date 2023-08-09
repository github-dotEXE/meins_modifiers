package de.ender.modifiers;

import de.ender.core.itemtypes.ItemTypes;
import de.ender.core.modifiers.ModifierManager;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModifierCommand implements CommandExecutor, TabCompleter {
    private static final MiniMessage miniMessage = MiniMessage.miniMessage();
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player){
            Player player = ((Player) sender);
            ItemStack item = player.getInventory().getItemInMainHand();
            boolean worked;
            switch (args[0]){
                case "apply":
                    worked = ModifierManager.applyModifier(item,ModifierManager.getModifierByName(args[1]));
                    if(!worked) player.sendMessage(miniMessage.deserialize("<gold>Failed to put on modifier <dark_purple>"+ args[1] +"<gold>!"));
                    else player.sendMessage(miniMessage.deserialize("<green>Successfully applied modifier <dark_purple>"+ args[1] +"<green>!"));
                    break;
                case "remove":
                    worked = ModifierManager.removeModifier(item,ModifierManager.getModifierByName(args[1]));
                    if(!worked) player.sendMessage(miniMessage.deserialize("<gold>Failed to remove modifier <dark_purple>"+ args[1] +"<gold>!"));
                    else player.sendMessage(miniMessage.deserialize("<green>Successfully removed modifier <dark_purple>"+ args[1] +"<green>!"));
                    break;
                case "info":
                    player.sendMessage(miniMessage.deserialize("<dark_gray>ModifierInfo:"));
                    player.sendMessage(miniMessage.deserialize("<dark_aqua>Registered Modifiers: "));
                    ModifierManager.getAll().forEach((modifier) ->
                            player.sendMessage(miniMessage.deserialize("<aqua>"+modifier.getName()+": <light_purple>"+
                                    modifier.forTypes() +"; <dark_purle>"+ItemTypes.getExact(modifier.forTypes()))));
                    break;
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> commands = new ArrayList<>();
        List<String> completes = new ArrayList<>();

        if(args.length == 1) {
            commands.add("apply");
            commands.add("remove");
            commands.add("info");
        } else if (args.length == 2) {
            ModifierManager.getAll().forEach((modifier)-> commands.add( modifier.getName() ));
        }
        StringUtil.copyPartialMatches(args[args.length-1], commands,completes); //copy matches of first argument
        Collections.sort(commands);//sort the list
        return commands;
    }
}
