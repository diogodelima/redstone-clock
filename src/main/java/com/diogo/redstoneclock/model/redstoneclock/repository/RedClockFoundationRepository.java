package com.diogo.redstoneclock.model.redstoneclock.repository;

import com.diogo.redstoneclock.model.redstoneclock.RedstoneClock;
import org.bukkit.Location;

public interface RedClockFoundationRepository {

    void setup();

    void insert(RedstoneClock redstoneClock);

    void update(RedstoneClock redstoneClock);

    void delete(RedstoneClock redstoneClock);

    RedstoneClock findOne(Location location);

}
