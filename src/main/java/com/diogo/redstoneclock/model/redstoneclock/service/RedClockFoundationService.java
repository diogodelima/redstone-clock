package com.diogo.redstoneclock.model.redstoneclock.service;

import com.diogo.redstoneclock.model.redstoneclock.RedstoneClock;
import org.bukkit.Location;

import java.util.List;
import java.util.Optional;

public interface RedClockFoundationService {

    void put(RedstoneClock redstoneClock);

    void update(RedstoneClock redstoneClock);

    void remove(RedstoneClock redstoneClock, boolean db);

    Optional<RedstoneClock> get(Location location);

    List<RedstoneClock> getAll();

}
