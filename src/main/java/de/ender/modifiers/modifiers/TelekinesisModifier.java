package de.ender.modifiers.modifiers;

import de.ender.core.itemtypes.ItemType;
import de.ender.core.modifiers.Modifier;
import de.ender.core.modifiers.ModifierManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.PlayerInventory;

import java.util.EnumSet;

public class TelekinesisModifier implements Modifier {

    @Override
    public EnumSet<ItemType> forTypes() {
        return EnumSet.of(ItemType.ALL_TOOLS,ItemType.ALL_WEAPONS);
    }

    @Override
    public String getName() {
        return "telekinesis";
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockDropItem(BlockDropItemEvent event){
        Player player= event.getPlayer();
        PlayerInventory inv = player.getInventory();
        Location location = event.getBlock().getLocation();
        if(ModifierManager.hasItemModifier(inv.getItemInMainHand(),this)) {
            event.getItems().forEach(
                    (item) -> inv.addItem(item.getItemStack()).values().forEach(
                            (lItem) -> location.getWorld().dropItemNaturally(location, lItem))
            );
            event.getItems().clear();
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityDie(EntityDeathEvent event) {
        Player player= event.getEntity().getKiller();
        if(player == null)return;
        PlayerInventory inv = player.getInventory();
        Location location = event.getEntity().getLocation();
        if(ModifierManager.hasItemModifier(inv.getItemInMainHand(),this)) {
            event.getDrops().forEach(
                    (item) -> inv.addItem(item).values().forEach(
                            (lItem) -> location.getWorld().dropItemNaturally(location, lItem))
            );
            event.getDrops().clear();
        }
    }
}
