package com.diogo.redstoneclock.model.redstoneclock;

import com.diogo.redstoneclock.util.ItemBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Objects;

@AllArgsConstructor
@Data
@Builder
public class RedstoneClock extends BukkitRunnable {

    public final static ItemStack display = new ItemBuilder(Material.REDSTONE_BLOCK)
            .setDisplayName("§4Relógio de Redstone")
            .setLore(Arrays.asList(
                    "§7Simplifique o relógio redstone do seu canhão",
                    "§7utilizando este item, condensando-o em um único",
                    "§7bloco e agilizando o processo de criação do canhão."
            )).build();

    private final Location location;
    private boolean active;
    private double delay;

    public void setDelay(double delay) throws Exception {

        BigDecimal bigDecimal = BigDecimal.valueOf(delay).setScale(3, RoundingMode.FLOOR);

        if (bigDecimal.compareTo(BigDecimal.valueOf(0.7)) < 0 || bigDecimal.compareTo(BigDecimal.valueOf(1.5)) > 0)
            throw new Exception("Invalid delay");

        this.delay = delay;
    }

    public void setActive(boolean active) throws Exception {

        BigDecimal bigDecimal = BigDecimal.valueOf(delay).setScale(3, RoundingMode.FLOOR);

        if (active && (bigDecimal.compareTo(BigDecimal.valueOf(0.7)) < 0 || bigDecimal.compareTo(BigDecimal.valueOf(1.5)) > 0))
            throw new Exception("Invalid delay");

        this.active = active;
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

    @Override
    public void run() {

        if (!this.active)
            return;

        this.location.getBlock().setType(Material.REDSTONE_BLOCK);
    }

}
