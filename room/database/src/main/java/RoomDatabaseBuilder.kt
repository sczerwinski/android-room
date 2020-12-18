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

/**
 * Creates a [RoomDatabase.Builder] for a persistent database with type [T] and the given [name].
 * Once a database is built, you should keep a reference to it and re-use it.
 *
 * This method is analogous to [Room.databaseBuilder].
 */
inline fun <reified T : RoomDatabase> Context.roomDatabaseBuilder(
    name: String = T::class.java.name
): RoomDatabase.Builder<T> =
    Room.databaseBuilder(this, T::class.java, name)

/**
 * Creates a [RoomDatabase.Builder] for an in memory database with type [T].
 * Information stored in an in memory database disappears when the process is killed.
 * Once a database is built, you should keep a reference to it and re-use it.
 *
 * This method is analogous to [Room.inMemoryDatabaseBuilder].
 */
inline fun <reified T : RoomDatabase> Context.roomInMemoryDatabaseBuilder(): RoomDatabase.Builder<T> =
    Room.inMemoryDatabaseBuilder(this, T::class.java)
