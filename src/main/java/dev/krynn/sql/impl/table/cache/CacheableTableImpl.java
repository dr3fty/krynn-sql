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

package dev.krynn.sql.impl.table.cache;

import dev.krynn.sql.KrynnSQL;
import dev.krynn.sql.compiler.field.CompiledField;
import dev.krynn.sql.database.Database;
import dev.krynn.sql.impl.table.TableImpl;
import dev.krynn.sql.query.Query;
import dev.krynn.sql.table.Table;
import dev.krynn.sql.table.cache.CacheableTable;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CacheableTableImpl<T> implements CacheableTable<T> {

    private Table<T> table;
    private Map<Object, T> cacheMap = new HashMap<>();
    private CompiledField primaryKey;
    private Class<T> clazz;
    private KrynnSQL krynnSQL;

    public CacheableTableImpl(KrynnSQL krynnSQL, Database database, Class<T> clazz) {
        this.table = new TableImpl<>(krynnSQL, database, clazz);
        this.krynnSQL = krynnSQL;
        this.clazz = clazz;
        this.primaryKey = KrynnSQL.getCompiler().findOrCreate(clazz).primaryKey();
    }

    @Override
    public void cache(T object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        this.cacheMap.clear();
    }

    @Override
    public void invalidate(T object) {
        try {
            this.cacheMap.remove(getKey(object));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public T cachedQuery(Object key) {
        if(this.cacheMap.containsKey(key)) return this.cacheMap.get(key);

        try(Connection connection = krynnSQL.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(String.format(Query.SELECT_OBJECT, name(), primaryKey.name()));
            statement.setObject(1, key);
            //Maybe stupid...
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            T build = KrynnSQL.getCompiler().build(clazz, resultSet);
            this.cacheMap.put(key, build);

            return build;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<T> query(T type) {
        return this.table.query(type);
    }

    @Override
    public List<T> query(String query) {
        return this.table.query(query);
    }

    @Override
    public void update(String query) {
        this.table.update(query);
    }

    @Override
    public void update(T type) {
        this.table.update(type);
    }

    @Override
    public String name() {
        return this.table.name();
    }

    private Object getKey(T object) throws IllegalAccessException {
        Field field = primaryKey.field();
        field.setAccessible(true);
        return field.get(object);
    }
}
