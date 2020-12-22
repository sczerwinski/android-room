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
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * A callback that populates a newly created [RoomDatabase], by executing SQL statements defined
 * in an [sqlScriptExecutor].
 */
class SQLPopulateRoomDatabaseCallback(
    private val sqlScriptExecutor: SQLScriptExecutor
) : RoomDatabase.Callback() {

    /**
     * Executes SQL statements on the given [db].
     */
    override fun onCreate(db: SupportSQLiteDatabase) {
        sqlScriptExecutor.executeOn(db)
    }
}
