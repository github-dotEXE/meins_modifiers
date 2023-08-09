package de.ender.modifiers;

import de.ender.core.Log;
import de.ender.core.UpdateChecker;
import de.ender.core.modifiers.ModifierManager;
import de.ender.modifiers.modifiers.*;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    public static Main plugin;

    @Override
    public void onEnable() {
        Log.enable(this);
        plugin = this;
        new UpdateChecker(this,"master").check().downloadLatestMeins();

        ModifierManager.registerModifier(new TelekinesisModifier());
        ModifierManager.registerModifier(new AutoSmeltModifier());
        ModifierManager.registerModifier(new EfficiencyModifier());
        ModifierManager.registerModifier(new MoreExpModifier());
        ModifierManager.registerModifier(new LifeStealModifier());

        getCommand("modifier").setExecutor(new ModifierCommand());
        getCommand("modifier").setTabCompleter(new ModifierCommand());
    }

    @Override
    public void onDisable() {
        Log.disable(this);
    }
    public static Main getPlugin() {
        return plugin;
    }
}
