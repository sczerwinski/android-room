# Changelog

## [Unreleased]
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
