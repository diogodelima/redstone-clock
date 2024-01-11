package com.diogo.redstoneclock.database;

import com.diogo.redstoneclock.RedstoneClockPlugin;
import com.vertz.database.database.Database;
import com.vertz.database.database.DatabaseCredentials;
import com.vertz.database.database.DatabaseFactory;
import com.vertz.database.database.configuration.DatabaseConfiguration;
import com.vertz.database.database.configuration.provider.BukkitDatabaseConfiguration;
import lombok.AllArgsConstructor;
import org.bukkit.configuration.ConfigurationSection;

@AllArgsConstructor
public class DatabaseProvider {

    private final RedstoneClockPlugin plugin;

    public Database setup() {
        ConfigurationSection section = this.plugin.getConfig().getConfigurationSection("database");
        DatabaseConfiguration configuration = new BukkitDatabaseConfiguration(section);
        DatabaseCredentials credentials = configuration.build();
        Database database = DatabaseFactory.getInstance().build(credentials);

        database.getConnection();

        return database;
    }

}
