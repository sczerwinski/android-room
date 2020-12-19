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

### Room `TypeConverter`s Generator

[![Maven Central](https://img.shields.io/maven-central/v/it.czerwinski.android.room/room-converters)][room-converters-release]
[![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/s/it.czerwinski.android.room/room-converters?server=https%3A%2F%2Foss.sonatype.org)][room-converters-snapshot]

<details>
  <summary>Kotlin</summary>

  ```kotlin
  dependencies {
      implementation("androidx.room:room-runtime:2.2.6")
      implementation("it.czerwinski.android.room:room-converters:[VERSION]")
      kapt("it.czerwinski.android.room:room-converters-processor:[VERSION]")
  }
  ```
</details>

<details>
  <summary>Groovy</summary>

  ```groovy
  dependencies {
      implementation 'androidx.room:room-runtime:2.2.6'
      implementation 'it.czerwinski.android.room:room-converters:[VERSION]'
      kapt 'it.czerwinski.android.room:room-converters-processor:[VERSION]'
  }
  ```
</details>

#### `GenerateEnumTypeConverter` Annotation

Marks an enum class to have automatically generated `@TypeConverter`s to and from the given type.

Enum can be stored in the database as name (`String` – default) or as ordinal (`Int`).

To store enum in a text field (as name):
```kotlin
GenerateEnumTypeConverter(type = EnumType.STRING)
enum class MyEnum {
    FOO,
    BAR
}
```

To store enum in an integer field (as ordinal):
```kotlin
GenerateEnumTypeConverter(type = EnumType.ORDINAL)
enum class MyEnum {
    FOO,
    BAR
}
```


[ci-build]: https://github.com/sczerwinski/android-room/actions?query=workflow%3ABuild
[room-database-release]: https://repo1.maven.org/maven2/it/czerwinski/android/room/room-database/
[room-database-snapshot]: https://oss.sonatype.org/content/repositories/snapshots/it/czerwinski/android/room/room-database/
[room-converters-release]: https://repo1.maven.org/maven2/it/czerwinski/android/room/room-converters/
[room-converters-snapshot]: https://oss.sonatype.org/content/repositories/snapshots/it/czerwinski/android/room/room-converters/
