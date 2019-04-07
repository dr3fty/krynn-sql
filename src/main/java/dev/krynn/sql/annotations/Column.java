package dev.krynn.sql.annotations;

import dev.krynn.sql.compiler.data.DataCompiler;
import dev.krynn.sql.impl.compiler.data.DataCompilers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {

    String sqlType() default "";

    /**
     * Numeric type of #sqlType
     * @see java.sql.Types
     */
    int numericType() default Integer.MAX_VALUE;

    Class<? extends DataCompiler> dataCompiler() default DataCompilers.ObjectCompiler.class;

    String name() default "";
}
