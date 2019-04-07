package dev.krynn.sql.compiler;

import dev.krynn.sql.compiler.field.CompiledField;

import java.util.Map;

public interface CompiledTemplate {

    Map<String, CompiledField> compiledFields();

    String primaryKey();

    String table();
}
