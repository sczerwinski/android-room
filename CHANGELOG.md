# Changelog

## [Unreleased]
### Added
- `SQLScriptExecutor`
- `SQLPopulateRoomDatabaseCallback`
- `SQLScriptMigration`
- Extension functions for `RoomDatabase.Builder`:
  - `populateFromSql`
  - `populateFromSqlAsset`
  - `addMigrationFromSql`
  - `addMigrationFromSqlAsset`

### Removed
- Annotation processor generating `TypeConverter`s for enum classes
  (generated sources are not picked up by Room compiler)

## [1.0.0-ALPHA1]
### Added
- Extension functions creating `RoomDatabase.Builder`:
  - `Context.roomDatabaseBuilder()`
  - `Context.roomInMemoryDatabaseBuilder()`
- Annotation processor generating `TypeConverter`s for enum classes
