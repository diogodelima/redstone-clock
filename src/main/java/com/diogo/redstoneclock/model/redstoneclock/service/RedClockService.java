package com.diogo.redstoneclock.model.redstoneclock.service;

import com.diogo.redstoneclock.model.redstoneclock.RedstoneClock;
import com.diogo.redstoneclock.model.redstoneclock.repository.RedClockFoundationRepository;
import com.diogo.redstoneclock.model.redstoneclock.repository.RedClockRepository;
import com.vertz.database.database.Database;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class RedClockService implements RedClockFoundationService {

    private final Map<Location, RedstoneClock> cache = new HashMap<>();
    private final RedClockFoundationRepository redClockRepository;

    public RedClockService(Database database){
        this.redClockRepository = new RedClockRepository(database);
        this.redClockRepository.setup();
    }

    @Override
    public void put(RedstoneClock redstoneClock) {
        this.cache.put(redstoneClock.getLocation(), redstoneClock);
        this.redClockRepository.insert(redstoneClock);
    }

    @Override
    public void update(RedstoneClock redstoneClock) {
        this.redClockRepository.update(redstoneClock);
    }

    @Override
    public void remove(RedstoneClock redstoneClock, boolean db) {
        this.cache.remove(redstoneClock.getLocation());
        if (db)
            this.redClockRepository.delete(redstoneClock);
    }

    @Override
    public Optional<RedstoneClock> get(Location location) {

        RedstoneClock redstoneClock = this.cache.get(location);

        if (redstoneClock != null)
            return Optional.of(redstoneClock);

        redstoneClock = this.redClockRepository.findOne(location);

        if (redstoneClock != null)
            this.cache.put(redstoneClock.getLocation(), redstoneClock);

        return Optional.ofNullable(redstoneClock);
    }

    @Override
    public List<RedstoneClock> getAll() {
        return this.cache
                .keySet()
                .stream()
                .map(this::get)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}
