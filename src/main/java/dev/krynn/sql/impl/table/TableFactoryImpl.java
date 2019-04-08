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

package dev.krynn.sql.impl.table;

import dev.krynn.sql.KrynnSQL;
import dev.krynn.sql.compiler.CompiledTemplate;
import dev.krynn.sql.connection.DatabaseConnection;
import dev.krynn.sql.database.Database;
import dev.krynn.sql.table.Table;
import dev.krynn.sql.table.TableFactory;
import dev.krynn.sql.util.QueryUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TableFactoryImpl implements TableFactory {

    private DatabaseConnection databaseConnection;

    private List<String> tables = new ArrayList<>();

    public TableFactoryImpl(DatabaseConnection connection) {
        this.databaseConnection = connection;
    }

    @Override
    public <T> Table<T> get(Database database, Class<T> type) {
        return new TableImpl<>(database, type);
    }

    @Override
    public <T> Table<T> getOrCreate(Database database, Class<T> type) {
        CompiledTemplate template = KrynnSQL.getCompiler().findOrCreate(type);
        if(!tables.contains(database.name() + "." + template.table())) {
            create(database, type);
        }
        return get(database, type);
    }

    @Override
    public <T> void create(Database database, Class<T> type) {
        try(Connection con = databaseConnection.connection()) {
            PreparedStatement table = QueryUtil.table(database.name(), con, type);
            table.executeUpdate();
            tables.add(database + "." + KrynnSQL.getCompiler().findTemplate(type).table());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
