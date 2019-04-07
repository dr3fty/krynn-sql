package dev.krynn.sql.compiler.field;

import dev.krynn.sql.compiler.data.DataCompiler;

import java.lang.reflect.Field;

public interface CompiledField {

    Field field();

    String sqlType();

    int numericType();

    String name();

    DataCompiler<?, ?> dataCompiler();

    boolean isPrimary();
}
