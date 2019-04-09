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

import dev.krynn.sql.annotations.Column;
import dev.krynn.sql.annotations.PrimaryKey;
import dev.krynn.sql.annotations.Table;

@Table("testo3")
public class ThirdUser {

    @PrimaryKey
    @Column
    private int primitive;

    @Column
    private boolean too;

    @Column
    private String string;

    public ThirdUser(int primitive, boolean too, String string) {
        this.primitive = primitive;
        this.too = too;
        this.string = string;
    }

    public ThirdUser() {
    }

    public int getPrimitive() {
        return primitive;
    }

    public boolean isToo() {
        return too;
    }

    public String getString() {
        return string;
    }
}
