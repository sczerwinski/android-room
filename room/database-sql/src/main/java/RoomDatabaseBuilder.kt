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
import androidx.room.RoomDatabase
import java.io.File

private const val SQL_MIGRATE_ASSET_START_GROUP = 1
private const val SQL_MIGRATE_ASSET_END_GROUP = 2

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

/**
 * Configures Room to populate a newly created database with an SQL script located in the application `assets/` folder.
 *
 * When an existing database is opened, the SQL script will not be executed.
 *
 * **Example:**
 * ```
 * val database = context.roomDatabaseBuilder<MyDatabase>()
 *     .populateFromSqlAsset(context, "sql/populate.sql")
 *     .build()
 * ```
 */
fun <T : RoomDatabase> RoomDatabase.Builder<T>.populateFromSqlAsset(
    context: Context,
    sqlFilePath: String
): RoomDatabase.Builder<T> {
    val sqlScript = context.assets.open(sqlFilePath).bufferedReader().use { it.readText() }
    return addCallback(SQLPopulateRoomDatabaseCallback(SQLScriptExecutor { +sqlScript }))
}

/**
 * Adds an SQL script migration to the builder.
 *
 * **Example:**
 * ```
 * val database = context.roomDatabaseBuilder<MyDatabase>()
 *     .addMigrationFromSql(startVersion = 1, endVersion = 2) {
 *         +"""
 *             create table if not exists users (
 *                 id integer primary key asc,
 *                 username text not null,
 *                 password text not null
 *             );
 *         """
 *     }
 *     .build()
 * ```
 */
inline fun <T : RoomDatabase> RoomDatabase.Builder<T>.addMigrationFromSql(
    startVersion: Int,
    endVersion: Int,
    sql: SQLScriptExecutor.Builder.() -> Unit
): RoomDatabase.Builder<T> =
    addMigrations(SQLScriptMigration(startVersion, endVersion, SQLScriptExecutor(sql)))

/**
 * Adds a migration to the builder, executing an SQL script located in the application `assets/` folder.
 *
 * **Example:**
 * ```
 * val database = context.roomDatabaseBuilder<MyDatabase>()
 *     .addMigrationFromSqlAsset(startVersion = 1, endVersion = 2, context, "sql/migrate_1_2.sql")
 *     .build()
 * ```
 */
fun <T : RoomDatabase> RoomDatabase.Builder<T>.addMigrationFromSqlAsset(
    startVersion: Int,
    endVersion: Int,
    context: Context,
    sqlFilePath: String
): RoomDatabase.Builder<T> {
    val sqlScript = context.assets.open(sqlFilePath).bufferedReader().use { it.readText() }
    return addMigrations(SQLScriptMigration(startVersion, endVersion, SQLScriptExecutor { +sqlScript }))
}

/**
 * Adds migrations to the builder, executing an SQL scripts located in the application `assets/` folder,
 * matching the given format.
 *
 * **Example:**
 * ```
 * val database = context.roomDatabaseBuilder<MyDatabase>()
 *     .addMigrationsFromSqlAssets(context, sqlFilePathFormat = "sql/migrate_{}_{}.sql")
 *     .build()
 * ```
 */
fun <T : RoomDatabase> RoomDatabase.Builder<T>.addMigrationsFromSqlAssets(
    context: Context,
    sqlFilePathFormat: String
): RoomDatabase.Builder<T> {
    val dir = File(sqlFilePathFormat).parent.orEmpty()
    val assetFiles = context.assets.list(dir).orEmpty()
    val sqlFileFormat = sqlFilePathFormat
        .replace(oldValue = "{}", newValue = "%d")
    val sqlFileRegex = sqlFilePathFormat
        .replace(oldValue = "{}", newValue = "(\\d+)")
        .replace(oldValue = ".", newValue = "\\.")
        .toRegex()

    val migrations = assetFiles
        .map { fileName -> File(dir, fileName).path }
        .mapNotNull { filePath ->
            sqlFileRegex.find(filePath)?.groups?.let { groups ->
                val startVersion = groups[SQL_MIGRATE_ASSET_START_GROUP]?.value?.toIntOrNull()
                val endVersion = groups[SQL_MIGRATE_ASSET_END_GROUP]?.value?.toIntOrNull()
                if (startVersion != null && endVersion != null) startVersion to endVersion
                else null
            }
        }
        .map { (startVersion, endVersion) ->
            val fileName = sqlFileFormat.format(startVersion, endVersion)
            val sqlScript = context.assets.open(fileName).bufferedReader().use { it.readText() }
            SQLScriptMigration(startVersion, endVersion, SQLScriptExecutor { +sqlScript })
        }

    @Suppress("SpreadOperator")
    return addMigrations(*migrations.toTypedArray())
}
