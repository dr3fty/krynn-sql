/*
 * Copyright (c) 2019 Oskarr1239
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.krynn.sql.impl.compiler.field;

import dev.krynn.sql.compiler.data.DataCompiler;
import dev.krynn.sql.compiler.field.CompiledField;

import java.lang.reflect.Field;

public class CompiledFieldImpl implements CompiledField {

    private Field field;
    private String sqlType;
    private int numericType;
    private String name;
    private DataCompiler<?, ?> dataCompiler;
    private boolean primaryKey;

    public CompiledFieldImpl(Field field, String sqlType, String name, DataCompiler<?, ?> dataCompiler) {
        this.field = field;
        this.sqlType = sqlType;
        this.name = name;
        this.dataCompiler = dataCompiler;
    }

    public CompiledFieldImpl(Field field, String sqlType, String name, DataCompiler<?, ?> dataCompiler, boolean primaryKey) {
        this.field = field;
        this.sqlType = sqlType;
        this.name = name;
        this.dataCompiler = dataCompiler;
        this.primaryKey = primaryKey;
    }

    public CompiledFieldImpl(Field field, String sqlType, int numericType, String name, DataCompiler<?, ?> dataCompiler, boolean primaryKey) {
        this.field = field;
        this.sqlType = sqlType;
        this.numericType = numericType;
        this.name = name;
        this.dataCompiler = dataCompiler;
        this.primaryKey = primaryKey;
    }

    private CompiledFieldImpl(Builder builder) {
        field = builder.field;
        sqlType = builder.sqlType;
        numericType = builder.numericType;
        name = builder.name;
        dataCompiler = builder.dataCompiler;
        primaryKey = builder.primaryKey;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public Field field() {
        return this.field;
    }

    @Override
    public String sqlType() {
        return this.sqlType;
    }

    @Override
    public int numericType() {
        return this.numericType;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public DataCompiler<?, ?> dataCompiler() {
        return this.dataCompiler;
    }

    @Override
    public boolean isPrimary() {
        return this.primaryKey;
    }


    public static final class Builder {
        private Field field;
        private String sqlType;
        private int numericType;
        private String name;
        private DataCompiler<?, ?> dataCompiler;
        private boolean primaryKey;

        private Builder() {
        }

        public Builder field(Field val) {
            field = val;
            return this;
        }

        public Builder sqlType(String val) {
            sqlType = val;
            return this;
        }

        public Builder numericType(int val) {
            numericType = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder dataCompiler(DataCompiler<?, ?> val) {
            dataCompiler = val;
            return this;
        }

        public Builder primaryKey(boolean val) {
            primaryKey = val;
            return this;
        }

        public CompiledFieldImpl build() {
            return new CompiledFieldImpl(this);
        }
    }
}
