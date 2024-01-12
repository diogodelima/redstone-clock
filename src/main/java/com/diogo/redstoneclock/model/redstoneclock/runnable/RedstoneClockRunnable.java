package com.diogo.redstoneclock.model.redstoneclock.runnable;

import com.diogo.redstoneclock.RedstoneClockPlugin;
import com.diogo.redstoneclock.model.redstoneclock.RedstoneClock;
import com.diogo.redstoneclock.model.redstoneclock.service.RedClockFoundationService;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

@AllArgsConstructor
public class RedstoneClockRunnable extends BukkitRunnable {

    private final RedClockFoundationService redClockService;
    private final RedstoneClockPlugin plugin;

    @Override
    public void run() {

        for (RedstoneClock redstoneClock : redClockService.getAll()){

            if (!redstoneClock.isActive())
                continue;

            double timeInTick = redstoneClock.getDelay() * 20;
            Block block = redstoneClock.getLocation().getBlock();

            if (redstoneClock.getTimePassed() >= timeInTick){

                redstoneClock.getLocation().getBlock().setType(Material.REDSTONE_BLOCK);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        block.setType(Material.STAINED_GLASS);
                        block.setData((byte) 14);
                    }
                }.runTaskLater(plugin, 5);

                redstoneClock.setTimePassed(0.0);
                return;
            }

            redstoneClock.setTimePassed(redstoneClock.getTimePassed() + 1);
        }

    }

}
