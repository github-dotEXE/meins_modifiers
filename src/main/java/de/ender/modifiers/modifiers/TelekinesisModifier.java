package de.ender.modifiers.modifiers;

import de.ender.core.cenchants.ItemType;
import de.ender.core.cenchants.Modifier;
import org.bukkit.entity.Player;

public class TelekinesisModifier implements Modifier {

    @Override
    public ItemType[] forTypes() {
        return new ItemType[]{ItemType.ALL_TOOLS};
    }

    @Override
    public void onUse(Player player) {

    }

    @Override
    public void onStopUse(Player player) {

    }

    @Override
    public void onMainHand(Player player) {

    }

    @Override
    public void onStopMainHand(Player player) {

    }

    @Override
    public void onOffHand(Player player) {

    }

    @Override
    public void onStopOffHand(Player player) {

    }
}
