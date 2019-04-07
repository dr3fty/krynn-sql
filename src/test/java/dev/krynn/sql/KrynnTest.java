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

package dev.krynn.sql;

import com.zaxxer.hikari.HikariConfig;
import dev.krynn.sql.compiler.Compiler;
import dev.krynn.sql.compiler.data.DataCompiler;
import dev.krynn.sql.database.Database;
import dev.krynn.sql.impl.compiler.CompilerImpl;
import dev.krynn.sql.table.Table;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KrynnTest {

    @Test
    void compilerTest() {
        Compiler compiler = new CompilerImpl();
        compiler.compile(User.class);

        User build = compiler.build(User.class, new AbstractResultSet());

        assertEquals("test", build.getTest());
        assertEquals(2115, build.getTest2());
    }

    @Test
    void customDataCompilerTest() {
        Compiler compiler = new CompilerImpl();

        SecondUser build = compiler.build(SecondUser.class, new AbstractResultSet());

        assertEquals("testxd", build.getTest2());
    }

    public static class CustomCompiler implements DataCompiler<String, String> {

        @Override
        public String compile(String original) {
            return original + "xd";
        }

        @Override
        public String decompile(String toDecompile) {
            return toDecompile + "xd";
        }
    }
}
