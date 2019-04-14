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

package dev.krynn.sql.util;

import dev.krynn.sql.KrynnSQL;
import dev.krynn.sql.compiler.CompiledTemplate;
import dev.krynn.sql.compiler.data.DataCompiler;
import dev.krynn.sql.compiler.field.CompiledField;
import dev.krynn.sql.query.Query;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class QueryUtil {

    public static <T> PreparedStatement update(String database, Connection connection, T type, Type objectType) throws IllegalAccessException, SQLException {
        CompiledTemplate template = KrynnSQL.getCompiler().findTemplate(objectType);

        Set<Map.Entry<String, CompiledField>> entries = template.compiledFields().entrySet();
        Iterator<Map.Entry<String, CompiledField>> iterator = entries.iterator();

        String values = String.join(", ", Collections.nCopies(entries.size(), "?"));
        String table = database + "." + template.table();
        String fields = entries.stream().map(Map.Entry::getKey).collect(Collectors.joining(", "));
        String onDuplicate = entries.stream().map(map -> map.getKey() + " = ?").collect(Collectors.joining(", "));

        PreparedStatement preparedStatement = connection.prepareStatement(String.format(Query.CREATE_OR_UPDATE, table, fields, values, onDuplicate));

        int index = 1;
        while(iterator.hasNext()) {
            Map.Entry<String, CompiledField> next = iterator.next();
            CompiledField value = next.getValue();

            Field field = value.field();
            field.setAccessible(true);
            Object val = tryCompile(value.dataCompiler(), field.get(type));

            preparedStatement.setObject(index, val, value.numericType());
            preparedStatement.setObject(index + entries.size(), val, value.numericType());

            index++;
        }

        return preparedStatement;
    }

    public static <T> PreparedStatement table(String database, Connection connection, Class<T> clazz) throws SQLException {
        if(KrynnSQL.getCompiler().findTemplate(clazz) == null) {
            KrynnSQL.getCompiler().compile(clazz);
        }
        CompiledTemplate template = KrynnSQL.getCompiler().findTemplate(clazz);

        String name = database + "." + template.table();
        String types = template.compiledFields()
                .entrySet()
                .stream()
                .map(compiledField -> compiledField.getKey() + " " + compiledField.getValue().sqlType())
                .collect(Collectors.joining(", "));

        types += String.format(", PRIMARY KEY(%s)", template.primaryKey().name());

        return connection.prepareStatement(String.format(Query.CREATE_TABLE, name, types));
    }

    @SuppressWarnings("unchecked")
    private static <T, I> T tryCompile(DataCompiler<T, I> dataCompiler, Object o) {
        return dataCompiler.compile((I) o);
    }
}
