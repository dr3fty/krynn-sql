package dev.krynn.sql.compiler.data;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public interface DataCompiler<T, I> {

    T compile(I original);

    I decompile(T toDecompile);

    default Type type() {
        return ((ParameterizedType) getClass().getGenericInterfaces()[0]).getActualTypeArguments()[1];
    }
}
