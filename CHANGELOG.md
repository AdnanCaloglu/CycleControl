# Changelog

All notable changes to CycleControl are documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/), and this project follows [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## Unreleased

### Added

- Repository documentation and publication metadata.
- A Git ignore policy for build outputs, local server files, IDE metadata, and common secrets.
- An All Rights Reserved proprietary license notice.
- Contribution guidelines covering development, testing, bug reports, and licensing terms.

## 1.1.0 - 2026-07-06

### Added

- `/cyclecontrol [minutes]` for displaying or changing the complete cycle duration.
- `/daycycle` as a command alias.
- Persistent `cycle-minutes` configuration.
- `cyclecontrol.admin` permission, granted to operators by default.
- Support for cycle durations from 1 to 1,440 minutes, including decimal values.

### Changed

- Renamed the plugin from LongDays to CycleControl.
- Replaced the fixed half-speed clock with a configurable tick accumulator.

## 1.0.0 - 2026-07-02

### Added

- Initial release as LongDays.
- Fixed 40-minute complete cycle: approximately 20 minutes of day and 20 minutes of night.
- Automatic handling of loaded and unloaded worlds.
- Restoration of previous `doDaylightCycle` values during normal shutdown.
