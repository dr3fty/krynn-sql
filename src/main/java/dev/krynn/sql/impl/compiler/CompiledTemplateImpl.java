package dev.krynn.sql.impl.compiler;

import dev.krynn.sql.compiler.CompiledTemplate;
import dev.krynn.sql.compiler.field.CompiledField;

import java.util.HashMap;
import java.util.Map;

public class CompiledTemplateImpl implements CompiledTemplate {

    private Map<String, CompiledField> fieldMap = new HashMap<>();
    private String primaryKey;
    private String table;

    public CompiledTemplateImpl(Map<String, CompiledField> fieldMap, String primaryKey, String table) {
        this.fieldMap = fieldMap;
        this.primaryKey = primaryKey;
        this.table = table;
    }

    private CompiledTemplateImpl(Builder builder) {
        fieldMap = builder.fieldMap;
        primaryKey = builder.primaryKey;
        table = builder.table;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public Map<String, CompiledField> compiledFields() {
        return this.fieldMap;
    }

    @Override
    public String primaryKey() {
        return this.primaryKey;
    }

    @Override
    public String table() {
        return this.table;
    }

    public static final class Builder {
        private Map<String, CompiledField> fieldMap;
        private String primaryKey;
        private String table;

        private Builder() {
        }

        public Builder fieldMap(Map<String, CompiledField> val) {
            fieldMap = val;
            return this;
        }

        public Builder primaryKey(String val) {
            primaryKey = val;
            return this;
        }

        public Builder table(String val) {
            table = val;
            return this;
        }

        public CompiledTemplateImpl build() {
            return new CompiledTemplateImpl(this);
        }
    }
}
