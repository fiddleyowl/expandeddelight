# Development and validation

Build, compatibility, and testing details for the [Expanded Delight NeoForge 26.1.2 port](README.md).

## Source baseline

The `26.1.2-neoforge` branch starts from upstream's Minecraft 1.21.1 version 0.1.4, commit [`ea82640`](https://github.com/ianm1647/expandeddelight/commit/ea826400654702587e86c9e94544bfd74f065083).

The migrated JSON in `src/main/resources/` and `src/generated/resources/` is maintained directly. Original 1.21.1 Java data generators are preserved in `src/legacy/java/` for reference and excluded from compilation.

## Dependencies and tooling

- Java **25** and the included Gradle **9.2.1** wrapper. Set `JAVA_HOME` to a Java 25 JDK if needed.
- Minecraft **26.1.2** and NeoForge **26.1.2.126**.
- [Farmer's Delight 26 Neo Ver **1.3.3-fix8** by BuaichiZY](https://www.curseforge.com/minecraft/mc-mods/farmers-delight-26-neo-ver/files/9236195). The build pins CurseForge project `1699768`, file `9236195`; players install this dependency separately.
- Optional: JEI **29.6.2.31+** and KubeJS **26.1.2-8.0.8+** for recipe viewing and scripting. Install KubeJS with its runtime dependencies, including Rhino.

JEI is included in development runs. Add `-PwithKubeJS=true` to include the pinned KubeJS, Rhino, and Better Advanced Tooltips builds.

## Build and run

```sh
bash gradlew build
bash gradlew runClient
bash gradlew runServer
```

Build output is `build/libs/expandeddelight-0.1.4+mc26.1.2.jar`; the sources jar is in the same directory.

## Port details

The port updates block and item registration, food effects and container returns, block interactions, juicer recipes and persistence, recipe book categories, client recipe synchronization, cinnamon boats and renderers, villager trades, world generation data, loot modifiers, item model definitions, and JEI/KubeJS integration.

Compatibility checks compare registered IDs, item translation keys, block-state properties, recipe IDs, resource paths, and English translations against the original 1.21.1 source. The baseline is captured in `src/portTest/resources/compatibility/original-1.21.1.json`.

The original bottle-slot texture and pie model names remain available for resource packs. Juicer custom names accept the original JSON-string format and the early port's component format.

## Validation

The build, server GameTests, KubeJS recipe scripting, and a client check of the juicer and recipe book pass.

```sh
# Original identifiers/resources, legacy names, juicer save/resume, and boats.
bash gradlew runGameTestServer

# Also create and load a juicing recipe through the KubeJS schema.
bash gradlew runGameTestServer -PwithKubeJS=true

# Open the juicer and recipe book in a disposable test world,
# save an in-game screenshot, and exit the client.
bash gradlew runClient -PportSmoke
```

GameTest reports are written to `build/port-gametest.xml` and `build/port-gametest-kubejs.xml`. The client screenshot is `run/screenshots/expanded-delight-port-smoke.png`.

Tests use dedicated worlds in `run-gametest/`, `run-gametest-kubejs/`, and `run/saves/expanded-delight-port-smoke/`; each test command refreshes its own world. Test code in `src/portTest/` is excluded from release jars.

## License packaging

The runtime and sources jars include `LICENSE`, `NOTICE`, `THIRD_PARTY_NOTICES.txt`, and `TEMPLATE_LICENSE.txt` under `META-INF/`, along with the Gradle license under `META-INF/licenses/`.

NeoForged supplied the original project template; its license is retained in [TEMPLATE_LICENSE.txt](TEMPLATE_LICENSE.txt). The Gradle wrapper retains its Apache License 2.0 notices; the full text is in [licenses/Gradle-Apache-2.0.txt](licenses/Gradle-Apache-2.0.txt).
