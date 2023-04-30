# Changelog

## [Unreleased]

### Added

### Changed
- Library dependencies:
  - Upgrade Kotlin to `1.8.21`
- Test dependencies:
  - Upgrade JUnit to `5.9.3`
  - Upgrade `de.mannodermaus.gradle.plugins:android-junit5` to `1.9.3.0`

### Deprecated

### Removed

### Fixed

### Security

## [1.3.0] - 2023-04-18
### Changed
- Upgrade Gradle to `8.0.2`
- Use Java 17
- Change target SDK to `33`
- Library dependencies:
  - Upgrade Kotlin to `1.8.20`
  - Upgrade Room to `2.5.1`
  - Upgrade Android Gradle Plugin to `8.0.0`
  - Upgrade `sqlite-ktx` to `2.3.1`
- Test dependencies:
  - Upgrade MockK to `1.13.5`
  - Upgrade JUnit to `5.9.2`
  - Upgrade `de.mannodermaus.gradle.plugins:android-junit5` to `1.8.2.1`
- Build dependencies:
  - Upgrade `io.gitlab.arturbosch.detekt` to `1.22.0`
  - Upgrade `org.jetbrains.changelog` to `2.0.0`
  - Upgrade Dokka to `1.8.10`

## [1.2.0]
### Changed
- Upgrade Gradle to `7.4.2`
- Dependencies:
  - Upgrade Kotlin to `1.6.20`
  - Upgrade Android Gradle Plugin to `7.1.3`
  - Upgrade Room `2.4.2`
  - Upgrade `sqlite-ktx` to `2.2.0`
  - Upgrade `org.jetbrains.changelog` to `1.3.1`
  - Upgrade JUnit to `5.8.2`
  - Upgrade `android-junit5` to `1.8.2.0`
  - Upgrade `io.mockk:mockk` to `1.12.3`
  - Upgrade `io.gitlab.arturbosch.detekt` to `1.19.0`

### Removed
- `@GenerateEnumTypeConverter` annotation
- Enum `TypeConverter` generator

### Fixed
- Invalid assets used in `addMigrationsFromSqlAssets()` for assets inside a directory.

## [1.1.0]
### Changed
- Upgrade Gradle to `6.9`
- Dependencies:
  - Upgrade Kotlin to `1.5.21`
  - Upgrade Android Gradle Plugin to `4.2.2`
  - Upgrade Room `2.3.0`
  - Upgrade `org.jetbrains.changelog` to `1.2.0`
  - Upgrade `android-junit5` to `1.7.1.1`
  - Upgrade `io.mockk:mockk` to `1.12.0`
  - Upgrade `io.gitlab.arturbosch.detekt` to `1.17.1`

### Deprecated
- Enum `TypeConverter` generator

## [1.0.1]
### Changed
- Upgrade Gradle to `6.8`
- Dependencies:
  - Upgrade Kotlin to `1.4.30`
  - Upgrade Android Gradle Plugin to `4.1.2`
  - Upgrade `org.jetbrains.changelog` to `1.0.1`
  - Upgrade `io.mockk:mockk` to `1.10.5`

## [1.0.0]

## [1.0.0-RC1]
### Added
- Aggregating artifact: `room-extensions`

## [1.0.0-BETA1]

## [1.0.0-ALPHA3]
### Added
- Annotation processor generating `TypeConverter`s for enum classes

## [1.0.0-ALPHA2]
### Added
- `SQLScriptExecutor`
- `SQLPopulateRoomDatabaseCallback`
- `SQLScriptMigration`
- Extension functions for `RoomDatabase.Builder`:
  - `populateFromSql`
  - `populateFromSqlAsset`
  - `addMigrationFromSql`
  - `addMigrationFromSqlAsset`
  - `addMigrationsFromSqlAssets`

### Changed
- Dependencies:
  - Upgrade `io.gitlab.arturbosch.detekt` to `1.15.0`
  - Upgrade `io.mockk:mockk` to `1.10.3-jdk8`

### Removed
- Annotation processor generating `TypeConverter`s for enum classes
  (generated sources are not picked up by Room compiler)

## [1.0.0-ALPHA1]
### Added
- Extension functions creating `RoomDatabase.Builder`:
  - `Context.roomDatabaseBuilder()`
  - `Context.roomInMemoryDatabaseBuilder()`
- Annotation processor generating `TypeConverter`s for enum classes