package dev.krynn.sql.query;

import com.google.common.reflect.TypeToken;
import dev.krynn.sql.KrynnSQL;
import dev.krynn.sql.compiler.CompiledTemplate;
import dev.krynn.sql.compiler.data.DataCompiler;
import dev.krynn.sql.compiler.field.CompiledField;

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

public class QueryBuilder {

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

    @SuppressWarnings("unchecked")
    private static <T, I> T tryCompile(DataCompiler<T, I> dataCompiler, Object o) {
        return dataCompiler.compile((I) o);
    }
}
