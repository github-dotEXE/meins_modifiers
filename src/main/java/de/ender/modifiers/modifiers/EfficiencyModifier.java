package de.ender.modifiers.modifiers;


import de.ender.core.itemtypes.ItemType;
import de.ender.core.modifiers.Modifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.EnumSet;
import java.util.Map;

public class EfficiencyModifier implements Modifier {
    @Override
    public EnumSet<ItemType> forTypes() {
        return EnumSet.of(ItemType.ALL_TOOLS);
    }

    @Override
    public boolean canApply(ItemStack item) {
        for (Map.Entry<Enchantment,Integer> entry: item.getEnchantments().entrySet()) {
            if (entry.getKey().equals(Enchantment.DIG_SPEED) && entry.getValue() == 5) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getName() {
        return "hasty";
    }

    @Override
    public void onApply(ItemStack itemStack) {
        itemStack.addUnsafeEnchantment(Enchantment.DIG_SPEED,6);
    }

    @Override
    public void onRemove(ItemStack itemStack) {
        itemStack.addUnsafeEnchantment(Enchantment.DIG_SPEED,5);
    }

}
