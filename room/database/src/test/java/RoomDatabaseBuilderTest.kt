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

package it.czerwinski.android.room.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(
    MockKExtension::class)
class RoomDatabaseBuilderTest {

    @MockK
    lateinit var context: Context

    @MockK
    lateinit var databaseBuilder: RoomDatabase.Builder<TestDatabase>

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

    @Test
    @DisplayName(
        value = "GIVEN context, " +
            "WHEN roomDatabaseBuilder, " +
            "THEN call Room.databaseBuilder"
    )
    fun roomDatabaseBuilderWithoutName() {
        val result = context.roomDatabaseBuilder<TestDatabase>()

        assertSame(databaseBuilder, result)

        verify(exactly = 1) {
            Room.databaseBuilder(
                context,
                TestDatabase::class.java,
                "it.czerwinski.android.room.database.RoomDatabaseBuilderTest\$TestDatabase"
            )
        }
    }

    @Test
    @DisplayName(
        value = "GIVEN context, " +
            "WHEN roomDatabaseBuilder with name, " +
            "THEN call Room.databaseBuilder with the given name"
    )
    fun roomDatabaseBuilderWithName() {
        val result = context.roomDatabaseBuilder<TestDatabase>(name = "TestDatabase")

        assertSame(databaseBuilder, result)

        verify(exactly = 1) {
            Room.databaseBuilder(
                context,
                TestDatabase::class.java,
                "TestDatabase"
            )
        }
    }

    @Test
    @DisplayName(
        value = "GIVEN context, " +
            "WHEN roomInMemoryDatabaseBuilder, " +
            "THEN call Room.inMemoryDatabaseBuilder"
    )
    fun roomInMemoryDatabaseBuilder() {
        val result = context.roomInMemoryDatabaseBuilder<TestDatabase>()

        assertSame(databaseBuilder, result)

        verify(exactly = 1) {
            Room.inMemoryDatabaseBuilder(context, TestDatabase::class.java)
        }
    }

    abstract class TestDatabase : RoomDatabase()
}
