/*
 * Copyright 2020 Slawomir Czerwinski
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

package it.czerwinski.android.room.database.sql

import androidx.room.RoomDatabase

/**
 * Configures Room to populate a newly created database with an SQL script.
 *
 * When an existing database is opened, the SQL script will not be executed.
 *
 * **Example:**
 * ```
 * val database = context.roomDatabaseBuilder<MyDatabase>()
 *     .populateFromSql {
 *         +"""
 *             insert into users(id, username, password) values (1, 'root', 'qwerty');
 *             insert into user_roles(user_id, role_id) values (1, 1);
 *         """
 *     }
 *     .build()
 * ```
 */
inline fun <T : RoomDatabase> RoomDatabase.Builder<T>.populateFromSql(
    sql: SQLScriptExecutor.Builder.() -> Unit
): RoomDatabase.Builder<T> =
    addCallback(SQLPopulateRoomDatabaseCallback(SQLScriptExecutor(sql)))
