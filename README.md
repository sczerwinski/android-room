[![Build](https://github.com/sczerwinski/android-room/workflows/Build/badge.svg)][ci-build]

# Extensions for Jetpack Room

## Room Database Extensions

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


[ci-build]: https://github.com/sczerwinski/android-room/actions?query=workflow%3ABuild
