package dev.krynn.sql.impl.database;

import dev.krynn.sql.database.Database;
import dev.krynn.sql.impl.table.TableImpl;
import dev.krynn.sql.table.Table;

import java.util.HashMap;
import java.util.Map;

public class DatabaseImpl implements Database {

    private String name;

    public DatabaseImpl(String name) {
        this.name = name;
    }

    @Override
    public <T> Table<T> table(Class<T> type) {
        return new TableImpl<>(this, type);
    }

    @Override
    public String name() {
        return this.name;
    }
}
