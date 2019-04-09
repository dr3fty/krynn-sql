/*
 * Copyright (c) 2019 Oskarr1239
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.krynn.sql;

import com.zaxxer.hikari.HikariConfig;
import dev.krynn.sql.compiler.Compiler;
import dev.krynn.sql.connection.DatabaseConnection;
import dev.krynn.sql.database.Database;
import dev.krynn.sql.database.DatabaseFactory;
import dev.krynn.sql.impl.compiler.CompilerImpl;
import dev.krynn.sql.impl.connection.DatabaseConnectionImpl;
import dev.krynn.sql.impl.database.DatabaseFactoryImpl;
import dev.krynn.sql.impl.table.TableFactoryImpl;
import dev.krynn.sql.table.TableFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class KrynnSQL {

    private static Compiler compiler = new CompilerImpl();
    private static DatabaseConnection connection = new DatabaseConnectionImpl();
    private static DatabaseFactory databaseFactory;
    private static TableFactory tableFactory;

    public static void initialize(HikariConfig config) {
        connection.config(config);
        databaseFactory = new DatabaseFactoryImpl(connection);
        tableFactory = new TableFactoryImpl(connection);
    }

    public static Database getDatabase(String name) {
        return databaseFactory.getOrCreate(name);
    }

    public static Connection getConnection() throws SQLException {
        return connection.connection();
    }

    public static Compiler getCompiler() {
        return compiler;
    }

    public static TableFactory getTableFactory() {
        return tableFactory;
    }

    public static DatabaseFactory getDatabaseFactory() {
        return databaseFactory;
    }
}
