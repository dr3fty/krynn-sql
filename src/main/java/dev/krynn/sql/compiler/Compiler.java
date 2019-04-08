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

package dev.krynn.sql.compiler;

import dev.krynn.sql.compiler.data.DataCompiler;

import java.lang.reflect.Type;
import java.sql.ResultSet;

public interface Compiler {

    void compile(Class<?> clazz);

    <C> C build(Class<C> clazz, ResultSet resultSet);

    <T, I> void registerDataCompiler(DataCompiler<T, I> dataCompiler);

    <T> CompiledTemplate findOrCreate(Class<T> clazz);

    CompiledTemplate findTemplate(Type type);

    DataCompiler<?, ?> findDataCompiler(Type type);
}
