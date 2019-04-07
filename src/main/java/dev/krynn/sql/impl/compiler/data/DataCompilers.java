package dev.krynn.sql.impl.compiler.data;

import dev.krynn.sql.compiler.data.DataCompiler;

public class DataCompilers {

    public static final DataCompiler<String, Object> OBJECT_COMPILER = new ObjectCompiler();
    public static final DataCompiler<String, String> STRING_COMPILER = new StringCompiler();
    public static final DataCompiler<Integer, Integer> INT_COMPILER = new IntCompiler();
    public static final DataCompiler<Long, Long> LONG_COMPILER = new LongCompiler();
    public static final DataCompiler<Integer, Boolean> BOOLEAN_COMPILER = new BooleanCompiler();

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

    public static class BooleanCompiler implements DataCompiler<Integer, Boolean> {

        @Override
        public Integer compile(Boolean original) {
            return original ? 1 : 0;
        }

        @Override
        public Boolean decompile(Integer toDecompile) {
            return toDecompile == 1;
        }
    }

}
