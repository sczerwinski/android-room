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
import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.verifySequence
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
@DisplayName("Tests for SQLScriptMigration")
class SQLScriptMigrationTest {

    @MockK
    lateinit var database: SupportSQLiteDatabase

    @BeforeEach
    fun setUpDatabase() {
        every { database.beginTransaction() } just Runs
        every { database.execSQL(any()) } just Runs
        every { database.setTransactionSuccessful() } just Runs
        every { database.endTransaction() } just Runs
    }

    @Test
    @DisplayName(
        value = "GIVEN start version, end version and SQLScriptExecutor, " +
            "WHEN create SQLScriptMigration, " +
            "THEN startVersion of the migration should be as specified"
    )
    fun startVersion() {
        val sqlScript = """
            insert into users(id, username, password) values (1, 'root', 'qwerty');
            insert into user_roles(user_id, role_id) values (1, 1);
            update roles set name='Admin' where id is 1;
        """.trimIndent()

        val sqlExecutor = SQLScriptExecutor.Builder()
            .addScript(sqlScript)
            .build()
        val migration = SQLScriptMigration(startVersion = 1, endVersion = 2, sqlExecutor)

        assertEquals(1, migration.startVersion)
    }

    @Test
    @DisplayName(
        value = "GIVEN start version, end version and SQLScriptExecutor, " +
            "WHEN create SQLScriptMigration, " +
            "THEN endVersion of the migration should be as specified"
    )
    fun endVersion() {
        val sqlScript = """
            insert into users(id, username, password) values (1, 'root', 'qwerty');
            insert into user_roles(user_id, role_id) values (1, 1);
            update roles set name='Admin' where id is 1;
        """.trimIndent()

        val sqlExecutor = SQLScriptExecutor.Builder()
            .addScript(sqlScript)
            .build()
        val migration = SQLScriptMigration(startVersion = 1, endVersion = 2, sqlExecutor)

        assertEquals(2, migration.endVersion)
    }

    @Test
    @DisplayName(
        value = "GIVEN SQLScriptMigration with SQLScriptExecutor, " +
            "WHEN migrate, " +
            "THEN execute all SQL statements on the database"
    )
    fun migrate() {
        val sqlScript = """
            insert into users(id, username, password) values (1, 'root', 'qwerty');
            insert into user_roles(user_id, role_id) values (1, 1);
            update roles set name='Admin' where id is 1;
        """.trimIndent()

        val sqlExecutor = SQLScriptExecutor.Builder()
            .addScript(sqlScript)
            .build()
        val migration = SQLScriptMigration(startVersion = 1, endVersion = 2, sqlExecutor)

        migration.migrate(database)

        verifySequence {
            database.beginTransaction()
            database.execSQL("insert into users(id, username, password) values (1, 'root', 'qwerty')")
            database.execSQL("insert into user_roles(user_id, role_id) values (1, 1)")
            database.execSQL("update roles set name='Admin' where id is 1")
            database.setTransactionSuccessful()
            database.endTransaction()
        }
    }
}
