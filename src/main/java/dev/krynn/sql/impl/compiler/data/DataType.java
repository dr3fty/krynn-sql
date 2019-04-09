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

package dev.krynn.sql.impl.compiler.data;

import java.sql.Types;

public enum DataType {

    STRING("LONGTEXT", Types.LONGVARCHAR, String.class),
    INT("INT", Types.INTEGER, Integer.class, int.class),
    LONG("BIGINT", Types.BIGINT, Long.class, long.class),
    BOOLEAN("TYNYINT(1)", Types.TINYINT, Boolean.class, boolean.class),
    UUID("VARCHAR(36)", Types.VARCHAR, java.util.UUID.class),
    SHORT("SMALLINT", Types.SMALLINT, Short.class, short.class),
    DOUBLE("DOUBLE", Types.DOUBLE, Double.class, double.class),
    FLOAT("FLOAT", Types.FLOAT, Float.class, float.class),
    //Always last :XD:
    OBJECT("LONGTEXT", Types.LONGVARCHAR, Object.class);

    private Class<?>[] clazz;
    private String sqlType;
    private int numericType;

    DataType(String sqlType, int numericType, Class<?>... clazz) {
        this.clazz = clazz;
        this.sqlType = sqlType;
        this.numericType = numericType;
    }

    public static DataType getType(Class<?> clazz) {
        for (DataType value : values()) {
            for(Class<?> valueClass : value.getClazz()) {
                if(clazz.equals(valueClass)) return value;
            }
        }
        return OBJECT;
    }

    public Class<?>[] getClazz() {
        return clazz;
    }

    public String getSqlType() {
        return sqlType;
    }

    public int getNumericType() {
        return numericType;
    }
}
