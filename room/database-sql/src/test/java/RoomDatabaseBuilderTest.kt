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

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.mockkStatic
import io.mockk.slot
import io.mockk.unmockkStatic
import io.mockk.verifySequence
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
@DisplayName("Tests for RoomDatabaseBuilder extensions")
class RoomDatabaseBuilderTest {

    @MockK
    lateinit var context: Context

    @MockK
    lateinit var databaseBuilder: RoomDatabase.Builder<TestDatabase>

    @MockK
    lateinit var database: SupportSQLiteDatabase

    @MockK
    lateinit var roomDatabase: TestDatabase

    private lateinit var callback: RoomDatabase.Callback

    private lateinit var migration: Migration

    @BeforeEach
    fun initRoomMockk() {
        mockkStatic("androidx.room.Room")
        every { Room.databaseBuilder<TestDatabase>(any(), any(), any()) } returns databaseBuilder
        every { Room.inMemoryDatabaseBuilder<TestDatabase>(any(), any()) } returns databaseBuilder
    }

    @AfterEach
    fun cleanRoomMockk() {
        unmockkStatic("androidx.room.Room")
    }

    @BeforeEach
    fun setUpDatabaseBuilder() {
        val callbackSlot = slot<RoomDatabase.Callback>()
        val migrationSlot = slot<Migration>()
        every { databaseBuilder.addCallback(capture(callbackSlot)) } answers {
            callback = callbackSlot.captured
            databaseBuilder
        }
        every { databaseBuilder.addMigrations(capture(migrationSlot)) } answers {
            migration = migrationSlot.captured
            databaseBuilder
        }
        every { databaseBuilder.build() } answers {
            if (::callback.isInitialized) callback.onCreate(database)
            if (::migration.isInitialized) migration.migrate(database)
            roomDatabase
        }
    }

    @BeforeEach
    fun setUpDatabase() {
        every { database.beginTransaction() } just Runs
        every { database.execSQL(any()) } just Runs
        every { database.setTransactionSuccessful() } just Runs
        every { database.endTransaction() } just Runs
    }

    @Test
    @DisplayName(
        value = "GIVEN database will be created for the first time, " +
            "WHEN populateFromSql, " +
            "THEN execute all SQL statements on the database"
    )
    fun populateFromSql() {
        val sqlScript = """
            insert into users(id, username, password) values (1, 'root', 'qwerty');
            insert into user_roles(user_id, role_id) values (1, 1);
            update roles set name='Admin' where id is 1;
        """.trimIndent()

        Room.inMemoryDatabaseBuilder(context, TestDatabase::class.java)
            .populateFromSql {
                +sqlScript
            }
            .build()

        verifySequence {
            database.beginTransaction()
            database.execSQL("insert into users(id, username, password) values (1, 'root', 'qwerty')")
            database.execSQL("insert into user_roles(user_id, role_id) values (1, 1)")
            database.execSQL("update roles set name='Admin' where id is 1")
            database.setTransactionSuccessful()
            database.endTransaction()
        }
    }

    @Test
    @DisplayName(
        value = "GIVEN database will be created for the first time, " +
            "WHEN populateFromSqlAsset, " +
            "THEN execute all SQL statements on the database"
    )
    fun populateFromSqlAsset() {
        val sqlScript = """
            insert into users(id, username, password) values (1, 'root', 'qwerty');
            insert into user_roles(user_id, role_id) values (1, 1);
            update roles set name='Admin' where id is 1;
        """.trimIndent()
        every { context.assets.open("populate.sql") } returns sqlScript.byteInputStream()

        Room.inMemoryDatabaseBuilder(context, TestDatabase::class.java)
            .populateFromSqlAsset(context, "populate.sql")
            .build()

        verifySequence {
            database.beginTransaction()
            database.execSQL("insert into users(id, username, password) values (1, 'root', 'qwerty')")
            database.execSQL("insert into user_roles(user_id, role_id) values (1, 1)")
            database.execSQL("update roles set name='Admin' where id is 1")
            database.setTransactionSuccessful()
            database.endTransaction()
        }
    }

    @Test
    @DisplayName(
        value = "GIVEN database will be migrated from version 1 to 2, " +
            "WHEN addMigrationFromSql, " +
            "THEN execute all SQL statements on the database"
    )
    fun addMigrationFromSql() {
        val sqlScript = """
            insert into users(id, username, password) values (1, 'root', 'qwerty');
            insert into user_roles(user_id, role_id) values (1, 1);
            update roles set name='Admin' where id is 1;
        """.trimIndent()

        Room.inMemoryDatabaseBuilder(context, TestDatabase::class.java)
            .addMigrationFromSql(startVersion = 1, endVersion = 2) {
                +sqlScript
            }
            .build()

        verifySequence {
            database.beginTransaction()
            database.execSQL("insert into users(id, username, password) values (1, 'root', 'qwerty')")
            database.execSQL("insert into user_roles(user_id, role_id) values (1, 1)")
            database.execSQL("update roles set name='Admin' where id is 1")
            database.setTransactionSuccessful()
            database.endTransaction()
        }
    }

    abstract class TestDatabase : RoomDatabase()
}
