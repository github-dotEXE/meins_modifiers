package de.ender.modifiers.modifiers;


import de.ender.core.itemtypes.ItemType;
import de.ender.core.modifiers.Modifier;
import de.ender.core.modifiers.ModifierManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.EnumSet;

public class MoreExpModifier implements Modifier {
    private final float multiplier = 1.5F;
    @Override
    public EnumSet<ItemType> forTypes() {
        return EnumSet.of(ItemType.ALL_WEAPONS,ItemType.PICKAXE);
    }

    @Override
    public String getName() {
        return "moXp";
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if(ModifierManager.hasItemModifier(item,this)){
            event.setExpToDrop((int) (event.getExpToDrop()*multiplier));
        }
    }

    @EventHandler
    public void onEntityDie(EntityDeathEvent event) {
        Player player = event.getEntity().getKiller();
        if(player == null)return;
        ItemStack item = player.getInventory().getItemInMainHand();
        if(ModifierManager.hasItemModifier(item,this)){
            event.setDroppedExp((int) (event.getDroppedExp()*multiplier));
        }
    }
}
