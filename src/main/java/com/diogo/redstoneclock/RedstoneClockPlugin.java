package com.diogo.redstoneclock;

import com.diogo.redstoneclock.command.RedstoneClockCommand;
import com.diogo.redstoneclock.database.DatabaseProvider;
import com.diogo.redstoneclock.inventory.RedstoneClockInventory;
import com.diogo.redstoneclock.listener.PlayerListener;
import com.diogo.redstoneclock.model.redstoneclock.RedstoneClock;
import com.diogo.redstoneclock.model.redstoneclock.runnable.RedstoneClockRunnable;
import com.diogo.redstoneclock.model.redstoneclock.service.RedClockFoundationService;
import com.diogo.redstoneclock.model.redstoneclock.service.RedClockService;
import com.vertz.database.database.Database;
import me.devnatan.inventoryframework.ViewFrame;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class RedstoneClockPlugin extends JavaPlugin {

    private Database database;
    private RedClockFoundationService redClockService;

    @Override
    public void onEnable() {

        database = new DatabaseProvider(this).setup();
        redClockService = new RedClockService(database);
        new RedstoneClockRunnable(redClockService, this).runTaskTimer(this, 0, 1);

        ViewFrame viewFrame = ViewFrame.create(this)
                        .with(new RedstoneClockInventory(redClockService))
                        .register();

        getCommand("redstoneclock").setExecutor(new RedstoneClockCommand());
        Bukkit.getPluginManager().registerEvents(new PlayerListener(redClockService, viewFrame), this);
    }

    @Override
    public void onDisable() {
        redClockService.getAll().forEach(RedstoneClock::reset);
        database.close();
    }

}
