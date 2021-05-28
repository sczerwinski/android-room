# Changelog

## [Unreleased]
### Changed
- Upgrade Gradle to `6.9`
- Dependencies:
  - Upgrade Kotlin to `1.5.10`
  - Upgrade Android Gradle Plugin to `4.2.1`
  - Upgrade Room `2.3.0`
  - Upgrade `android-junit5` to `1.7.1.1`
  - Upgrade `io.mockk:mockk` to `1.11.0`
  - Upgrade `io.gitlab.arturbosch.detekt` to `1.17.1`

## [1.0.1]
### Changed
- Upgrade Gradle to `6.8`
- Dependencies:
  - Upgrade Kotlin to `1.4.30`
  - Upgrade Android Gradle Plugin to `4.1.2`
  - Upgrade `org.jetbrains.changelog` to `1.0.1`
  - Upgrade `io.mockk:mockk` to `1.10.5`

## [1.0.0]
No changes since 1.0.0-RC1

## [1.0.0-RC1]
### Added
- Aggregating artifact: `room-extensions`

## [1.0.0-BETA1]
No changes since 1.0.0-ALPHA3

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
