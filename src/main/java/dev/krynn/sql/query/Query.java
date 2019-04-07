package dev.krynn.sql.query;

public class Query {

    public static final String CREATE_DATABASE = "CREATE DATABASE `?`";

    public static final String CREATE_OR_UPDATE = "INSERT INTO %s (%s) VALUES(%s) ON DUPLICATE KEY UPDATE %s;";
}
