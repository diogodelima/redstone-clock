package com.diogo.redstoneclock.listener;

import com.diogo.redstoneclock.inventory.RedstoneClockInventory;
import com.diogo.redstoneclock.model.redstoneclock.RedstoneClock;
import com.diogo.redstoneclock.model.redstoneclock.service.RedClockFoundationService;
import lombok.AllArgsConstructor;
import me.devnatan.inventoryframework.ViewFrame;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

@AllArgsConstructor
public class PlayerListener implements Listener {

    private final RedClockFoundationService redClockService;
    private final ViewFrame viewFrame;

    @EventHandler
    void onBlockPlace(BlockPlaceEvent event){

        Player player = event.getPlayer();
        ItemStack item = player.getItemInHand();

        if (event.isCancelled() || !item.isSimilar(RedstoneClock.display))
            return;

        redClockService.put(new RedstoneClock(event.getBlock().getLocation(), false, 0.7, 0.0));
        event.getBlock().setType(Material.STAINED_GLASS);
        event.getBlock().setData((byte) 7);
    }

    @EventHandler
    void onPlayerInteract(PlayerInteractEvent event){

        Player player = event.getPlayer();

        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getClickedBlock() == null)
            return;

        Optional<RedstoneClock> redstoneClockOptional = redClockService.get(event.getClickedBlock().getLocation());

        if (redstoneClockOptional.isEmpty())
            return;

        RedstoneClock redstoneClock = redstoneClockOptional.get();
        viewFrame.open(RedstoneClockInventory.class, player, redstoneClock);
    }

    @EventHandler
    void onBlockBreak(BlockBreakEvent event){

        Player player = event.getPlayer();

        Optional<RedstoneClock> redstoneClockOptional = redClockService.get(event.getBlock().getLocation());

        if (redstoneClockOptional.isEmpty())
            return;

        event.setCancelled(true);
        event.getBlock().setType(Material.AIR);
        RedstoneClock redstoneClock = redstoneClockOptional.get();
        redstoneClock.getHologram().delete();
        redClockService.remove(redstoneClock, true);
        ItemStack item = RedstoneClock.display.clone();
        player.getInventory().addItem(item);
    }

    @EventHandler
    void onEntityExplode(EntityExplodeEvent event){

        for (Block block : event.blockList()){

            Optional<RedstoneClock> redstoneClockOptional = redClockService.get(block.getLocation());

            if (redstoneClockOptional.isEmpty())
                continue;

            RedstoneClock redstoneClock = redstoneClockOptional.get();
            block.setType(Material.AIR);
            redstoneClock.getHologram().delete();
            redClockService.remove(redstoneClock, true);
        }

    }

}
