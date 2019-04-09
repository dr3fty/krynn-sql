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

import dev.krynn.sql.compiler.data.DataCompiler;

import java.util.UUID;

public class DataCompilers {

    public static final DataCompiler<String, Object> OBJECT_COMPILER = new ObjectCompiler();
    public static final DataCompiler<String, String> STRING_COMPILER = new StringCompiler();
    public static final DataCompiler<Integer, Integer> INT_COMPILER = new IntCompiler();
    public static final DataCompiler<Long, Long> LONG_COMPILER = new LongCompiler();
    public static final DataCompiler<Boolean, Boolean> BOOLEAN_COMPILER = new BooleanCompiler();
    public static final DataCompiler<String, UUID> UUID_COMPILER = new UUIDCompiler();
    public static final DataCompiler<Short, Short> SHORT_COMPILER = new ShortCompiler();
    public static final DataCompiler<Double, Double> DOUBLE_COMPILER = new DoubleCompiler();
    public static final DataCompiler<Float, Float> FLOAT_COMPILER = new FloatCompiler();

    public static class ObjectCompiler implements DataCompiler<String, Object> {
        @Override
        public String compile(Object original) {
            return original.toString();
        }

        @Override
        public Object decompile(String toDecompile) {
            return toDecompile;
        }
    }

    public static class StringCompiler implements DataCompiler<String, String> {

        @Override
        public String compile(String original) {
            return original;
        }

        @Override
        public String decompile(String toDecompile) {
            return toDecompile;
        }
    }

    public static class IntCompiler implements DataCompiler<Integer, Integer> {

        @Override
        public Integer compile(Integer original) {
            return original;
        }

        @Override
        public Integer decompile(Integer toDecompile) {
            return toDecompile;
        }
    }

    public static class LongCompiler implements DataCompiler<Long, Long> {

        @Override
        public Long compile(Long original) {
            return original;
        }

        @Override
        public Long decompile(Long toDecompile) {
            return toDecompile;
        }
    }

    public static class BooleanCompiler implements DataCompiler<Boolean, Boolean> {
        //Dont ask

        @Override
        public Boolean compile(Boolean original) {
            return original;
        }

        @Override
        public Boolean decompile(Boolean toDecompile) {
            return toDecompile;
        }
    }

    public static class UUIDCompiler implements DataCompiler<String, UUID> {

        @Override
        public String compile(UUID original) {
            return original.toString();
        }

        @Override
        public UUID decompile(String toDecompile) {
            return UUID.fromString(toDecompile);
        }
    }

    public static class ShortCompiler implements DataCompiler<Short, Short> {

        @Override
        public Short compile(Short original) {
            return original;
        }

        @Override
        public Short decompile(Short toDecompile) {
            return toDecompile;
        }
    }

    public static class DoubleCompiler implements DataCompiler<Double, Double> {

        @Override
        public Double compile(Double original) {
            return original;
        }

        @Override
        public Double decompile(Double toDecompile) {
            return toDecompile;
        }
    }

    public static class FloatCompiler implements DataCompiler<Float, Float> {

        @Override
        public Float compile(Float original) {
            return original;
        }

        @Override
        public Float decompile(Float toDecompile) {
            return toDecompile;
        }
    }

}
