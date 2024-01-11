package com.diogo.redstoneclock;

import com.diogo.redstoneclock.command.RedstoneClockCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class RedstoneClockPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("redstoneclock").setExecutor(new RedstoneClockCommand());
    }

    @Override
    public void onDisable() {

    }

}
