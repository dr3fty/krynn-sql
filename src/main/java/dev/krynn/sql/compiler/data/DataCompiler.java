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
