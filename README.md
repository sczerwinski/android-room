[![Build](https://github.com/sczerwinski/android-room/workflows/Build/badge.svg)][ci-build]

# Extensions for Jetpack Room

## Room Database Extensions

[![Maven Central](https://img.shields.io/maven-central/v/it.czerwinski.android.room/room-database)][room-database-release]
[![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/s/it.czerwinski.android.room/room-database?server=https%3A%2F%2Foss.sonatype.org)][room-database-snapshot]

<details>
  <summary>Kotlin</summary>

  ```kotlin
  dependencies {
      implementation("androidx.room:room-runtime:2.2.6")
      implementation("it.czerwinski.android.room:room-database:[VERSION]")
  }
  ```
</details>

<details>
  <summary>Groovy</summary>

  ```groovy
  dependencies {
      implementation 'androidx.room:room-runtime:2.2.6'
      implementation 'it.czerwinski.android.room:room-database:[VERSION]'
  }
  ```
</details>

### Room Database Builders

#### `roomDatabaseBuilder`
Creates a `RoomDatabase.Builder` for a persistent database with type `T` and the given `name`.

```kotlin
val database = context.roomDatabaseBuilder<MyDatabase>().build()
```
or:
```kotlin
val database = context.roomDatabaseBuilder<MyDatabase>(name = "database").build()
```

#### `roomInMemoryDatabaseBuilder`
Creates a `RoomDatabase.Builder` for an in memory database with type `T`.

```kotlin
val database = context.roomInMemoryDatabaseBuilder<MyDatabase>().build()
```

## Room Database SQL Extensions

[![Maven Central](https://img.shields.io/maven-central/v/it.czerwinski.android.room/room-database-sql)][room-database-sql-release]
[![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/s/it.czerwinski.android.room/room-database-sql?server=https%3A%2F%2Foss.sonatype.org)][room-database-sql-snapshot]

<details>
  <summary>Kotlin</summary>

  ```kotlin
  dependencies {
      implementation("androidx.room:room-runtime:2.2.6")
      implementation("it.czerwinski.android.room:room-database-sql:[VERSION]")
  }
  ```
</details>

<details>
  <summary>Groovy</summary>

  ```groovy
  dependencies {
      implementation 'androidx.room:room-runtime:2.2.6'
      implementation 'it.czerwinski.android.room:room-database-sql:[VERSION]'
  }
  ```
</details>

### SQL Script Executor

```kotlin
val database: SupportSQLiteDatabase
val sqlScriptExecutor = SQLScriptExecutor {
    +"""
        insert into users(id, username, password) values (1, 'root', 'qwerty')
        insert into user_roles(user_id, role_id) values (1, 1)
    """
}
sqlScriptExecutor.executeOn(database)
```

### Room Database Builder Extensions

#### Populate From SQL
Configures Room to populate a newly created database with an SQL script.

```kotlin
val database = context.roomDatabaseBuilder<MyDatabase>()
    .populateFromSql {
        +"""
            insert into users(id, username, password) values (1, 'root', 'qwerty')
            insert into user_roles(user_id, role_id) values (1, 1)
        """
    }
    .build()
```


[ci-build]: https://github.com/sczerwinski/android-room/actions?query=workflow%3ABuild
[room-database-release]: https://repo1.maven.org/maven2/it/czerwinski/android/room/room-database/
[room-database-snapshot]: https://oss.sonatype.org/content/repositories/snapshots/it/czerwinski/android/room/room-database/
[room-database-sql-release]: https://repo1.maven.org/maven2/it/czerwinski/android/room/room-database-sql/
[room-database-sql-snapshot]: https://oss.sonatype.org/content/repositories/snapshots/it/czerwinski/android/room/room-database-sql/
