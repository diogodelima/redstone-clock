package com.diogo.redstoneclock.model.redstoneclock.adapter;

import com.diogo.redstoneclock.model.redstoneclock.RedstoneClock;
import com.diogo.redstoneclock.util.Serializer;
import com.vertz.database.database.adapter.DatabaseAdapter;
import com.vertz.database.database.executor.DatabaseQuery;
import org.bukkit.Location;

public class RedstoneClockAdapter implements DatabaseAdapter<RedstoneClock> {

    @Override
    public RedstoneClock adapt(DatabaseQuery databaseQuery) {

        Location location = Serializer.deserializeLocation((String) databaseQuery.get("location"));
        double delay = (Double) databaseQuery.get("delay");

        return RedstoneClock.builder()
                .location(location)
                .delay(delay)
                .active(false)
                .build();
    }

}
