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
import dev.krynn.sql.compiler.data.DataCompiler;
import dev.krynn.sql.connection.DatabaseConnection;
import dev.krynn.sql.database.Database;
import dev.krynn.sql.database.DatabaseFactory;
import dev.krynn.sql.impl.compiler.CompilerImpl;
import dev.krynn.sql.impl.connection.DatabaseConnectionImpl;
import dev.krynn.sql.impl.database.DatabaseFactoryImpl;
import dev.krynn.sql.impl.table.TableFactoryImpl;
import dev.krynn.sql.table.TableFactory;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.SQLException;

public class KrynnSQL {

    private static final Compiler COMPILER = new CompilerImpl();
    private DatabaseConnection connection = new DatabaseConnectionImpl();
    private DatabaseFactory databaseFactory;
    private TableFactory tableFactory;

    public KrynnSQL(HikariConfig config) {
        connection.config(config);
        databaseFactory = new DatabaseFactoryImpl(this, connection);
        tableFactory = new TableFactoryImpl(this, connection);
    }

    public Database getDatabase(String name) {
        return databaseFactory.getOrCreate(name);
    }

    public <T, I> void registerDataCompiler(DataCompiler<T, I> dataCompiler, Type... primitives) {
        COMPILER.registerDataCompiler(dataCompiler, primitives);
    }

    public Connection getConnection() throws SQLException {
        return connection.connection();
    }

    public static Compiler getCompiler() {
        return COMPILER;
    }

    public TableFactory getTableFactory() {
        return tableFactory;
    }

    public DatabaseFactory getDatabaseFactory() {
        return databaseFactory;
    }
}
