package dev.krynn.sql;

import dev.krynn.sql.annotations.Column;
import dev.krynn.sql.annotations.PrimaryKey;
import dev.krynn.sql.annotations.Table;

@Table("idk")
public class SecondUser {

    @PrimaryKey
    @Column
    private Integer test;

    @Column(dataCompiler = KrynnTest.CustomCompiler.class)
    private String test2;

    public Integer getTest() {
        return test;
    }

    public String getTest2() {
        return test2;
    }
}
