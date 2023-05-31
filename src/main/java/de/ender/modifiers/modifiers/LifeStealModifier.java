package de.ender.modifiers.modifiers;

import de.ender.core.itemtypes.ItemType;
import de.ender.core.modifiers.Modifier;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.EnumSet;

public class LifeStealModifier implements Modifier {
    @Override
    public EnumSet<ItemType> forTypes() {
        return EnumSet.of(ItemType.ALL_WEAPONS);
    }

    @Override
    public String getName() {
        return "lifeSteal";
    }

    @EventHandler
    public void onEntityDie(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        Player player = entity.getKiller();
        AttributeInstance entityMaxHealth = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if(player == null || entityMaxHealth == null) return;
        AttributeInstance playerMaxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if(playerMaxHealth == null)return;
        double newHealth = player.getHealth()+0.05*entityMaxHealth.getValue();
        if(newHealth >= playerMaxHealth.getValue()) newHealth = playerMaxHealth.getValue();
        player.setHealth(newHealth);
    }
}
