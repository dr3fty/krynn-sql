package dev.krynn.sql.database;

import dev.krynn.sql.table.Table;

public interface Database {

    <T> Table<T> table(Class<T> type);

    String name();

}
