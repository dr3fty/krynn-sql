package dev.krynn.sql.impl.table;

import com.google.common.reflect.TypeToken;
import dev.krynn.sql.KrynnSQL;
import dev.krynn.sql.database.Database;
import dev.krynn.sql.query.QueryBuilder;
import dev.krynn.sql.table.Table;

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

    public TableImpl(Database database, Class<T> clazz) {
        this.database = database;
        this.clazz = clazz;
    }

    public List<T> query(String statement) {
        try(Connection connection = KrynnSQL.getConnection()) {
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
        try(Connection connection = KrynnSQL.getConnection()) {
            connection.prepareStatement(query).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(T type) {
        Type objectType = TypeToken.of(clazz).getType();
        try(Connection connection = KrynnSQL.getConnection()) {
            if(KrynnSQL.getCompiler().findTemplate(objectType) == null) {
                KrynnSQL.getCompiler().compile(type.getClass());
            }
            QueryBuilder.update(this.database.name(), connection, type, objectType).executeUpdate();
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public boolean check() {
        return true;
    }
}
