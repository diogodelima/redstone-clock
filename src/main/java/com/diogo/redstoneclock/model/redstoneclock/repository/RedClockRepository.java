package com.diogo.redstoneclock.model.redstoneclock.repository;

import com.diogo.redstoneclock.model.redstoneclock.RedstoneClock;
import com.diogo.redstoneclock.model.redstoneclock.adapter.RedstoneClockAdapter;
import com.diogo.redstoneclock.util.Serializer;
import com.vertz.database.database.Database;
import lombok.AllArgsConstructor;
import org.bukkit.Location;

@AllArgsConstructor
public class RedClockRepository implements RedClockFoundationRepository {

    private final RedstoneClockAdapter adapter = new RedstoneClockAdapter();
    private final Database database;

    @Override
    public void setup() {
        database
                .execute("CREATE TABLE IF NOT EXISTS redstone_clock (location TEXT PRIMARY KEY NOT NULL, delay DOUBLE NOT NULL)")
                .write();
    }

    @Override
    public void insert(RedstoneClock redstoneClock) {
        database
                .execute("INSERT INTO redstone_clock VALUES(?, ?)")
                .writeOne(statement -> {
                    statement.set(1, Serializer.serializeLocation(redstoneClock.getLocation()));
                    statement.set(2, redstoneClock.getDelay());
                });
    }

    @Override
    public void update(RedstoneClock redstoneClock) {
        database
                .execute("UPDATE redstone_clock SET delay = ? WHERE location = ?")
                .writeOne(statement -> {
                    statement.set(1, redstoneClock.getDelay());
                    statement.set(2, Serializer.serializeLocation(redstoneClock.getLocation()));
                });
    }

    @Override
    public void delete(RedstoneClock redstoneClock) {
        database
                .execute("DELETE FROM redstone_clock WHERE location = ?")
                .writeOne(statement -> statement.set(1, Serializer.serializeLocation(redstoneClock.getLocation())));
    }

    @Override
    public RedstoneClock findOne(Location location) {
        return database
                .execute("SELECT * FROM redstone_clock WHERE location = ?")
                .readOneWithAdapter(statement -> statement.set(1, Serializer.serializeLocation(location)), this.adapter)
                .join();
    }

}
