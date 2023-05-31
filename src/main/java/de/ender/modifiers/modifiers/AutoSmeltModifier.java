package de.ender.modifiers.modifiers;

import de.ender.core.itemtypes.ItemType;
import de.ender.core.modifiers.Modifier;
import de.ender.core.modifiers.ModifierManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.Recipe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Iterator;

public class AutoSmeltModifier implements Modifier {

    @Override
    public EnumSet<ItemType> forTypes() {
        return EnumSet.of(ItemType.PICKAXE,ItemType.AXE,ItemType.SHOVEL,ItemType.ALL_WEAPONS,ItemType.HOE);
    }

    @Override
    public String getName() {
        return "autosmelt";
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onBlockDropItem(BlockDropItemEvent event) {
        Player player= event.getPlayer();
        PlayerInventory inv = player.getInventory();
        if(ModifierManager.hasItemModifier(inv.getItemInMainHand(),this)) {
            Collection<Item> items = new ArrayList<>();
            for (Item item:
                 event.getItems()) {
                item.setItemStack(getAfterFurnace(item.getItemStack()));
                items.add(item);
            }
            event.getItems().clear();
            event.getItems().addAll(items);
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onEntityDie(EntityDeathEvent event) {
        Player player= event.getEntity().getKiller();
        if(player == null) return;

        PlayerInventory inv = player.getInventory();
        if(ModifierManager.hasItemModifier(inv.getItemInMainHand(),this)) {
            Collection<ItemStack> items = new ArrayList<>();
            for (ItemStack item:
                    event.getDrops()) {
                items.add(getAfterFurnace(item));
            }
            event.getDrops().clear();
            event.getDrops().addAll(items);
        }
    }

    private static ItemStack getAfterFurnace(ItemStack item){
        ItemStack result = item;
        Iterator<Recipe> iter = Bukkit.recipeIterator();
        while (iter.hasNext()) {
            Recipe recipe = iter.next();
            if (!(recipe instanceof FurnaceRecipe)) continue;
            if (((FurnaceRecipe) recipe).getInput().getType() != item.getType()) continue;
            result = recipe.getResult();
            break;
        }
        result.setAmount(item.getAmount());
        return result;
    }
}
