package dev.krynn.sql.database;

public interface DatabaseFactory {

    Database get(String name);

    Database getOrCreate(String name);

    void create(String name);
}
