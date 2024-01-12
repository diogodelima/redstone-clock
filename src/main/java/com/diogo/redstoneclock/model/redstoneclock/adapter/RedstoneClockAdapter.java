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

        return new RedstoneClock(location, false, delay, 0.0);
    }

}
