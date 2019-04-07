package dev.krynn.sql.compiler;

import dev.krynn.sql.compiler.data.DataCompiler;

import java.lang.reflect.Type;
import java.sql.ResultSet;

public interface Compiler {

    void compile(Class<?> clazz);

    <C> C build(Class<C> clazz, ResultSet resultSet);

    <T, I> void registerDataCompiler(DataCompiler<T, I> dataCompiler);

    CompiledTemplate findTemplate(Type type);

    DataCompiler<?, ?> findDataCompiler(Type type);
}
