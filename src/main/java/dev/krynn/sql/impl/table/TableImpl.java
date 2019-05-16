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

import com.google.common.reflect.TypeToken;
import dev.krynn.sql.KrynnSQL;
import dev.krynn.sql.compiler.CompiledTemplate;
import dev.krynn.sql.database.Database;
import dev.krynn.sql.table.Table;
import dev.krynn.sql.util.QueryUtil;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TableImpl<T> implements Table<T> {

    private Class<T> clazz;

    private Database database;

    private KrynnSQL krynnSQL;

    public TableImpl(KrynnSQL krynnSQL, Database database, Class<T> clazz) {
        this.database = database;
        this.clazz = clazz;
        this.krynnSQL = krynnSQL;
    }

    public List<T> query(String statement) {
        try(Connection connection = krynnSQL.getConnection()) {
            if(KrynnSQL.getCompiler().findTemplate(clazz) == null) {
                KrynnSQL.getCompiler().compile(clazz);
            }
            ResultSet resultSet = connection.prepareStatement(statement.replace("{table}", database.name() + "." + KrynnSQL.getCompiler().findTemplate(clazz).table())).executeQuery();
            List<T> list = new ArrayList<>();
            while(resultSet.next()) {
                list.add(KrynnSQL.getCompiler().build(clazz, resultSet));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<T> query(T type) {
        throw new UnsupportedOperationException();
    }

    public void update(String query) {
        try(Connection connection = krynnSQL.getConnection()) {
            connection.prepareStatement(query).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(T type) {
        Type objectType = TypeToken.of(clazz).getType();
        try(Connection connection = krynnSQL.getConnection()) {
            if(KrynnSQL.getCompiler().findTemplate(objectType) == null) {
                KrynnSQL.getCompiler().compile(type.getClass());
            }
            QueryUtil.update(this.database.name(), connection, type, objectType).executeUpdate();
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(T type) {
        try(Connection connection = krynnSQL.getConnection()) {
            QueryUtil.delete(this.database.name(), connection, type, clazz).executeUpdate();
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String name() {
        return KrynnSQL.getCompiler().findOrCreate(this.clazz).table();
    }
}
