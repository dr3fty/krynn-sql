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

package dev.krynn.sql.impl.compiler;

import com.google.common.reflect.Reflection;
import com.google.common.reflect.TypeToken;
import dev.krynn.sql.annotations.Column;
import dev.krynn.sql.annotations.PrimaryKey;
import dev.krynn.sql.annotations.Table;
import dev.krynn.sql.compiler.CompiledTemplate;
import dev.krynn.sql.compiler.Compiler;
import dev.krynn.sql.compiler.data.DataCompiler;
import dev.krynn.sql.compiler.field.CompiledField;
import dev.krynn.sql.impl.compiler.data.DataType;
import dev.krynn.sql.impl.compiler.exception.CompilerException;
import dev.krynn.sql.impl.compiler.field.CompiledFieldImpl;
import org.reflections.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Stream;

import static dev.krynn.sql.impl.compiler.data.DataCompilers.*;

public class CompilerImpl implements Compiler {

    private Map<Type, CompiledTemplate> templateMap = new HashMap<>();

    private Map<Type, DataCompiler<?, ?>> dataCompilerMap = new HashMap<>();

    {
        registerDataCompiler(OBJECT_COMPILER);
        registerDataCompiler(INT_COMPILER);
        registerDataCompiler(STRING_COMPILER);
        registerDataCompiler(LONG_COMPILER);
        registerDataCompiler(BOOLEAN_COMPILER);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void compile(Class<?> clazz) {
        if(!clazz.isAnnotationPresent(Table.class)) {
            throw new CompilerException();
        }

        Map<String, CompiledField> compiledFieldMap = new HashMap<>();

        ReflectionUtils.getFields(clazz, input -> Objects.requireNonNull(input).isAnnotationPresent(Column.class)).forEach(field -> {
            Column annotation = field.getAnnotation(Column.class);
            String name = annotation.name();
            DataType dataType = DataType.getType(field.getDeclaringClass());
            String sqlType = dataType.getSqlType();
            Integer numericType = dataType.getNumericType();
            DataCompiler<?, ?> dataCompiler = findDataCompiler(field.getGenericType());
            boolean primaryKey = false;

            if(annotation.name().isEmpty()) {
                name = field.getName();
            }
            if(!annotation.sqlType().isEmpty()) {
                sqlType = annotation.sqlType();
            }
            if(annotation.numericType() != Integer.MAX_VALUE) {
                numericType = annotation.numericType();
            }
            if(!annotation.dataCompiler().equals(OBJECT_COMPILER.getClass())) {
                try {
                    dataCompiler = annotation.dataCompiler().getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    throw new CompilerException(e);
                }
            }

            if(field.isAnnotationPresent(PrimaryKey.class)) {
                primaryKey = true;
            }
            compiledFieldMap.put(name, CompiledFieldImpl.newBuilder().name(name).sqlType(sqlType).numericType(numericType).dataCompiler(dataCompiler).field(field).primaryKey(primaryKey).build());
        });

        String primaryKey = compiledFieldMap.entrySet().stream().filter(ftr -> ftr.getValue().isPrimary()).findFirst().get().getKey();

        CompiledTemplate template = CompiledTemplateImpl.newBuilder()
                .fieldMap(compiledFieldMap)
                .table(clazz.getAnnotation(Table.class).value())
                .primaryKey(primaryKey).build();
        this.templateMap.put(clazz, template);

    }

    @Override
    public <C> C build(Class<C> clazz, ResultSet resultSet) {
        if(findTemplate(TypeToken.of(clazz).getType()) == null) {
            compile(clazz);
        }

        CompiledTemplate template = findTemplate(TypeToken.of(clazz).getType());

        try {
            C t = clazz.getDeclaredConstructor().newInstance();

            template.compiledFields().forEach((s, compiledField) -> {
                try {
                    Field field = compiledField.field();
                    field.setAccessible(true);
                    field.set(t, tryDecompile(compiledField.dataCompiler(), resultSet.getObject(compiledField.name(), compiledField.field().getType())));
                } catch (IllegalAccessException | SQLException e) {
                    e.printStackTrace();
                }
            });
            return t;
        } catch (Exception e) {
            throw new CompilerException(e);
        }
    }

    @Override
    public <T, I> void registerDataCompiler(DataCompiler<T, I> dataCompiler) {
        this.dataCompilerMap.put(dataCompiler.type(), dataCompiler);
    }

    @Override
    public <T> CompiledTemplate findOrCreate(Class<T> clazz) {
        if(findTemplate(clazz) == null) {
            compile(clazz);
        }
        return findTemplate(clazz);
    }

    @Override
    public CompiledTemplate findTemplate(Type type) {
        return this.templateMap.get(type);
    }

    @Override
    public DataCompiler<?, ?> findDataCompiler(Type type) {
        return dataCompilerMap.get(type);
    }

    private <T, I> I tryDecompile(DataCompiler<T, I> dataCompiler, Object o) {
        //idc
        return dataCompiler.decompile((T) o);
    }
}
