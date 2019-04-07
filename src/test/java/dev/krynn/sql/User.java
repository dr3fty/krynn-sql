package dev.krynn.sql;

import dev.krynn.sql.annotations.Column;
import dev.krynn.sql.annotations.PrimaryKey;
import dev.krynn.sql.annotations.Table;

@Table("test")
public class User {

    @PrimaryKey
    @Column
    private String test;

    @Column
    private Integer test2;

    public String getTest() {
        return test;
    }

    public Integer getTest2() {
        return test2;
    }
}
