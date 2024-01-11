package com.diogo.redstoneclock;

import com.diogo.redstoneclock.command.RedstoneClockCommand;
import com.diogo.redstoneclock.database.DatabaseProvider;
import com.diogo.redstoneclock.inventory.RedstoneClockInventory;
import com.diogo.redstoneclock.listener.PlayerListener;
import com.diogo.redstoneclock.model.redstoneclock.service.RedClockFoundationService;
import com.diogo.redstoneclock.model.redstoneclock.service.RedClockService;
import com.vertz.database.database.Database;
import me.devnatan.inventoryframework.ViewFrame;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class RedstoneClockPlugin extends JavaPlugin {

    @Override
    public void onEnable() {

        Database database = new DatabaseProvider(this).setup();
        RedClockFoundationService redClockService = new RedClockService(database);

        ViewFrame viewFrame = ViewFrame.create(this)
                        .with(new RedstoneClockInventory())
                        .register();

        getCommand("redstoneclock").setExecutor(new RedstoneClockCommand());
        Bukkit.getPluginManager().registerEvents(new PlayerListener(redClockService, viewFrame), this);
    }

    @Override
    public void onDisable() {

    }

}
