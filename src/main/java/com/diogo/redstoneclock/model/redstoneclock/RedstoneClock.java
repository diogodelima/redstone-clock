package com.diogo.redstoneclock.model.redstoneclock;

import com.diogo.redstoneclock.util.ItemBuilder;
import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@Data
public class RedstoneClock {

    public final static ItemStack display = new ItemBuilder(Material.REDSTONE_BLOCK)
            .setDisplayName("§4Relógio de Redstone")
            .setLore(Arrays.asList(
                    "§7Simplifique o relógio redstone do seu canhão",
                    "§7utilizando este item, condensando-o em um único",
                    "§7bloco e agilizando o processo de criação do canhão."
            )).build();

    private final Hologram hologram;
    private final Location location;
    private boolean active;
    private double delay;
    private double timePassed;

    public RedstoneClock(Location location, boolean active, double delay, double timePassed){
        this(DHAPI.createHologram(UUID.randomUUID().toString(), location.clone().add(0.5, 1.25, 0.5)), location, active, delay, timePassed);
    }

    public void setDelay(double delay) throws Exception {

        BigDecimal bigDecimal = BigDecimal.valueOf(delay).setScale(3, RoundingMode.FLOOR);

        if (bigDecimal.compareTo(BigDecimal.valueOf(0.7)) < 0 || bigDecimal.compareTo(BigDecimal.valueOf(1.5)) > 0)
            throw new Exception("Invalid delay");

        this.delay = delay;
        updateHologram();
    }

    public void setActive(boolean active) throws Exception {

        BigDecimal bigDecimal = BigDecimal.valueOf(delay).setScale(3, RoundingMode.FLOOR);

        if (active && (bigDecimal.compareTo(BigDecimal.valueOf(0.7)) < 0 || bigDecimal.compareTo(BigDecimal.valueOf(1.5)) > 0))
            throw new Exception("Invalid delay");

        this.location.getBlock().setType(Material.STAINED_GLASS);

        if (active)
            this.location.getBlock().setData((byte) 14);
        else reset();

        this.active = active;
        updateHologram();
    }

    public void updateHologram(){

        String line = (this.active ? "§c" : "§7") + String.format("%.1f", this.delay) + "s";

        DHAPI.setHologramLines(this.hologram, List.of(line));
        Bukkit.getOnlinePlayers().forEach(this.hologram::setShowPlayer);
    }

    public void reset(){
        this.timePassed = 0;
        this.location.getBlock().setType(Material.STAINED_GLASS);
        this.location.getBlock().setData((byte) 7);
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.location);
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == this)
            return true;

        if (!(obj instanceof RedstoneClock))
            return false;

        RedstoneClock redstoneClock = (RedstoneClock) obj;

        return redstoneClock.getLocation().equals(this.location);
    }

}
