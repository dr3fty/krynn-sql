package dev.krynn.sql.table;

import java.sql.PreparedStatement;
import java.util.List;

public interface Table<T> {

    List<T> query(T type);

    List<T> query(String query);

    void update(String query);

    void update(T type);

    boolean check();

}
