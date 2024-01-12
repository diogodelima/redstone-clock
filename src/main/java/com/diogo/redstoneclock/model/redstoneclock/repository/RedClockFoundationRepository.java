package com.diogo.redstoneclock.model.redstoneclock.repository;

import com.diogo.redstoneclock.model.redstoneclock.RedstoneClock;

import java.util.Set;

public interface RedClockFoundationRepository {

    void setup();

    void insert(RedstoneClock redstoneClock);

    void update(RedstoneClock redstoneClock);

    void delete(RedstoneClock redstoneClock);

    Set<RedstoneClock> findAll();

}
