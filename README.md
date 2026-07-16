# CycleControl

CycleControl is a lightweight Paper/Spigot plugin for changing the duration of Minecraft's complete day-night cycle. Server administrators can make time pass faster or slower with one command, and the selected duration persists across restarts.

The default configuration uses a 40-minute cycle: approximately 20 minutes of day and 20 minutes of night.

## Features

- Changes the complete day-night cycle to any duration from 1 to 1,440 minutes.
- Accepts whole or decimal minute values.
- Applies the same duration to every loaded world.
- Saves command changes to `config.yml` automatically.
- Restores each world's previous `doDaylightCycle` value when the plugin is disabled normally.
- Provides the `/daycycle` alias for the primary command.
- Has no runtime dependencies.

## Requirements and compatibility

- Minecraft 1.21.x
- Paper, Spigot, Purpur, or a compatible Bukkit/Paper server implementation
- Java 21 or newer

CycleControl is compiled against the Paper 1.21.11 API and uses only Bukkit-compatible APIs. Paper/Spigot 1.21.11 is the primary supported target; other 1.21.x releases and compatible forks are expected to work but are not covered by automated server integration tests.

CycleControl is a backend server plugin. It does not run on BungeeCord, Waterfall, Velocity, Sponge, Fabric, Forge, or NeoForge. Folia is not currently supported.

## Installation

1. Download the latest `CycleControl-*.jar` release.
2. Stop the Minecraft server.
3. Place the JAR in the server's `plugins` directory.
4. Start the server.
5. Run `/cyclecontrol` to confirm the current duration.

When upgrading, remove the older CycleControl or LongDays JAR so only one version is present.

## Commands and permissions

| Command | Description | Permission |
| --- | --- | --- |
| `/cyclecontrol` | Displays the current complete cycle duration. | `cyclecontrol.admin` |
| `/cyclecontrol <minutes>` | Sets and saves the complete cycle duration. | `cyclecontrol.admin` |
| `/daycycle [minutes]` | Alias for `/cyclecontrol`. | `cyclecontrol.admin` |

The `cyclecontrol.admin` permission is granted to server operators by default. Commands can also be run from the server console.

Example:

```text
/cyclecontrol 60
```

This produces an approximately 60-minute complete cycle, split into roughly 30 minutes of day and 30 minutes of night.

## Configuration

CycleControl creates `plugins/CycleControl/config.yml` on first launch:

```yaml
# Length of one complete daylight and nighttime cycle, in real-world minutes.
cycle-minutes: 40.0
```

The value must be between `1.0` and `1440.0`. Changes made with `/cyclecontrol` are written to this file immediately.

## Building from source

Prerequisites:

- JDK 21
- Maven 3.9 or newer

Clone the repository and run:

```shell
mvn clean verify
```

The compiled plugin will be written to `target/CycleControl-1.1.0.jar`.

## Known limitations

- Durations are measured in server ticks. If the server runs below 20 TPS, cycles take longer in real time.
- The duration is global and applies to every loaded world; per-world durations and exclusions are not available.
- Sleeping, commands, or other plugins may jump the current world time. CycleControl continues at the configured rate after the jump.
- Other plugins that manage world time or `doDaylightCycle` may conflict with CycleControl.
- Folia's regionized scheduler is not supported.
- The previous `doDaylightCycle` value is restored only during a normal plugin disable or world unload; a crash cannot run cleanup code.

## Changelog

Release history is documented in [CHANGELOG.md](CHANGELOG.md).

## Contributing

Bug reports and focused contributions are welcome. Read [CONTRIBUTING.md](CONTRIBUTING.md) before preparing a pull request, particularly the contribution terms for this All Rights Reserved project.

## License

Copyright (c) 2026 FezIsOut. All rights reserved.

Personal use of unmodified official binary releases is permitted. Redistribution, modification, resale, commercial use, and derivative works require prior written permission. See [LICENSE](LICENSE) for the complete terms.
