# Contributing to CycleControl

Thank you for taking the time to improve CycleControl. Bug reports, documentation corrections, and focused code contributions are welcome.

## Before you begin

- Search the existing issues and pull requests to avoid duplicates.
- Open an issue before starting a large change or a change to gameplay behavior.
- Keep each pull request focused on one problem or feature.
- Do not include server files, build output, credentials, logs containing private data, or third-party code you do not have permission to contribute.

## Development requirements

- JDK 21
- Maven 3.9 or newer
- Git

Clone your fork and verify the project before making changes:

```shell
mvn clean verify
```

The compiled plugin is written to `target/CycleControl-1.1.0.jar`.

## Making a contribution

1. Fork the repository on GitHub.
2. Create a descriptive branch from `main`, such as `fix/world-unload` or `docs/configuration`.
3. Make the smallest change that completely addresses the issue.
4. Preserve existing gameplay behavior unless the pull request intentionally proposes a behavior change.
5. Update the README or changelog when the user-facing behavior, commands, permissions, configuration, or compatibility changes.
6. Run `mvn clean verify` and resolve all failures.
7. Commit with a concise, imperative message.
8. Open a pull request describing the change, its motivation, and how it was tested.

## Code guidelines

- Follow the style of the existing Java source.
- Use clear names and keep methods focused.
- Use Bukkit/Paper APIs instead of version-specific internal server classes where practical.
- Keep server API dependencies in Maven's `provided` scope.
- Do not add runtime dependencies unless they provide a clear benefit and are discussed first.
- Avoid blocking work or asynchronous Bukkit world access on the main server thread.

## Bug reports

A useful bug report includes:

- CycleControl version
- Server software and exact Minecraft version
- Java version
- Relevant configuration
- Steps to reproduce the problem
- Expected and actual behavior
- Relevant console output with tokens, addresses, usernames, and other private information removed

## License and contribution terms

CycleControl is an All Rights Reserved project, not an open-source project. The copyright holder grants you limited permission to fork and modify the repository solely to prepare and submit a contribution to CycleControl. This permission does not allow publishing, redistributing, selling, or using modified builds outside contribution development and testing.

By submitting a contribution, you confirm that you have the right to submit it and grant the CycleControl copyright holder a perpetual, worldwide, irrevocable, royalty-free license to use, reproduce, modify, distribute, sublicense, and relicense your contribution as part of CycleControl. Your contribution may be accepted, changed, or declined at the maintainer's discretion.

For all other uses, the terms in [LICENSE](LICENSE) apply.
