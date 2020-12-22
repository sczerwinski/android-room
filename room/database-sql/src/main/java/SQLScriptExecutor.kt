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

import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.transaction

/**
 * An executor for SQL scripts consisting of multiple [sqlStatements].
 */
class SQLScriptExecutor internal constructor(
    private val sqlStatements: List<String>
) {

    /**
     * Executes all SQL statements in order on the given [database].
     */
    fun executeOn(database: SupportSQLiteDatabase) {
        database.transaction {
            for (sqlStatement in sqlStatements) {
                database.execSQL(sqlStatement)
            }
        }
    }

    /**
     * A builder for [SQLScriptExecutor].
     */
    class Builder {

        private val sqlStatements = mutableListOf<String>()

        /**
         * Adds an [sqlScript] to the builder.
         *
         * All statements in the [sqlScript] must be separated with a semicolon: `';'`.
         */
        fun addScript(sqlScript: String): Builder {
            sqlScript
                .split(SQL_DELIMITER)
                .map { it.trim() }
                .filterTo(sqlStatements) { it.isNotBlank() }
            return this
        }

        /**
         * Adds an SQL script to the builder.
         *
         * All statements in the SQL script must be separated with a semicolon: `';'`.
         */
        operator fun String.unaryPlus() {
            addScript(sqlScript = this)
        }

        /**
         * Builds a new [SQLScriptExecutor] with all added SQL scripts.
         */
        fun build(): SQLScriptExecutor = SQLScriptExecutor(sqlStatements)
    }

    companion object {
        private const val SQL_DELIMITER = ";"
    }
}

/**
 * Builds an instance of [SQLScriptExecutor], using [init] block to add SQL scripts.
 *
 * **Example:**
 * ```
 * val sqlScriptExecutor = SQLScriptExecutor {
 *     +"""
 *         insert into users(id, username, password) values (1, 'root', 'qwerty')
 *         insert into user_roles(user_id, role_id) values (1, 1)
 *     """
 * }
 * ```
 */
@Suppress("FunctionName")
inline fun SQLScriptExecutor(init: SQLScriptExecutor.Builder.() -> Unit): SQLScriptExecutor =
    SQLScriptExecutor.Builder().apply(init).build()
