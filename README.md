# Expanded Delight — NeoForge 26.1.2

A Minecraft 26.1.2 port of [Expanded Delight by ianm1647](https://github.com/ianm1647/expandeddelight), a Farmer's Delight addon with new crops, cinnamon wood, food, cheese fermentation, and a juicer.

Maintained by **fiddleyowl** in [fiddleyowl/expandeddelight](https://github.com/fiddleyowl/expandeddelight), on branch `26.1.2-neoforge`. This branch starts from upstream's Minecraft 1.21.1 version 0.1.4, commit [`ea82640`](https://github.com/ianm1647/expandeddelight/commit/ea826400654702587e86c9e94544bfd74f065083).

## Requirements

- Minecraft **26.1.2** and NeoForge **26.1.2.126** or newer within the 26.1.2 series.
- Java **25**.
- [Farmer's Delight 26 Neo Ver **1.3.3-fix8** by BuaichiZY](https://www.curseforge.com/minecraft/mc-mods/farmers-delight-26-neo-ver/files/9236195). The build pins CurseForge project `1699768`, file `9236195`; install this dependency separately when playing.
- Optional: JEI **29.6.2.31+** and KubeJS **26.1.2-8.0.8+** for recipe viewing and scripting. Install KubeJS with its runtime dependencies, including Rhino.

## Build and development

```sh
bash gradlew build
bash gradlew runClient
bash gradlew runServer
```

The Gradle **9.2.1** wrapper is included. Set `JAVA_HOME` to a Java 25 JDK if needed. Build output is `build/libs/expandeddelight-0.1.4+mc26.1.2.jar`; the sources jar is in the same directory.

JEI is included in development runs. Add `-PwithKubeJS=true` to include the pinned KubeJS, Rhino, and Better Advanced Tooltips builds.

## Port changes

The port updates block and item registration, food effects and container returns, block interactions, juicer recipes and persistence, recipe book categories, client recipe synchronization, cinnamon boats and renderers, villager trades, world generation data, loot modifiers, item model definitions, and JEI/KubeJS integration. Original content IDs, textures, translations, and recipe content are retained.

The migrated JSON in `src/main/resources/` and `src/generated/resources/` is maintained directly. Original 1.21.1 Java data generators are preserved in `src/legacy/java/` for reference and excluded from compilation.

## Validation

The build, server GameTests, KubeJS recipe scripting, and a client check of the juicer and recipe book pass.

```sh
# Resource loading, juicer save/resume, and cinnamon boat creation.
bash gradlew runGameTestServer

# Also create and load a juicing recipe through the KubeJS schema.
bash gradlew runGameTestServer -PwithKubeJS=true

# Open the juicer and recipe book in a disposable test world,
# save an in-game screenshot, and exit the client.
bash gradlew runClient -PportSmoke
```

GameTest reports are written to `build/port-gametest.xml` and `build/port-gametest-kubejs.xml`. The client screenshot is `run/screenshots/expanded-delight-port-smoke.png`. Tests use dedicated worlds in `run-gametest/`, `run-gametest-kubejs/`, and `run/saves/expanded-delight-port-smoke/`; each test command refreshes its own world. Test code in `src/portTest/` is excluded from release jars.

## License and attribution

- **ianm1647** created Expanded Delight and its original code and assets. Original project: [GitHub](https://github.com/ianm1647/expandeddelight) · [CurseForge](https://www.curseforge.com/minecraft/mc-mods/expanded-delight).
- **fiddleyowl** maintains this community port to Minecraft 26.1.2 / NeoForge.
- **vectorwing** created Farmer's Delight; **BuaichiZY** maintains the [26.1.2 port](https://github.com/BuaichiZY/FarmersDelight_26_Neo.ver/tree/26.1.2). The updated recipe, recipe book, screen, and tooltip implementations adapt their MIT-licensed code.
- **NeoForged** supplied the original project template. Its license is retained in [TEMPLATE_LICENSE.txt](TEMPLATE_LICENSE.txt).

Expanded Delight and the port changes are distributed under the [MIT License](LICENSE). See [NOTICE](NOTICE) for provenance and [THIRD_PARTY_NOTICES.txt](THIRD_PARTY_NOTICES.txt) for the full Farmer's Delight copyright and license. The Gradle wrapper retains its Apache License 2.0 notices; the full text is in [licenses/Gradle-Apache-2.0.txt](licenses/Gradle-Apache-2.0.txt).

The runtime and sources jars include these license and attribution files under `META-INF/`. Mod dependencies are installed separately.
