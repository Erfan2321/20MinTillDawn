# 20 Min Till Dawn

[![Build](https://github.com/Erfan2321/20MinTillDawn/actions/workflows/build.yml/badge.svg)](https://github.com/Erfan2321/20MinTillDawn/actions/workflows/build.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

A top-down survival roguelite shooter built with **Java 17** and **libGDX**, inspired by
*20 Minutes Till Dawn*. Pick a hero and a gun, survive the night, and spend each level-up
on one of three random upgrades.

> A university coursework project. See [NOTICE.md](NOTICE.md) — the MIT licence covers the
> code, not the art under `assets/`.

---

## Running it

You need a **JDK 17 or newer** and nothing else; the Gradle wrapper fetches the rest.

```bash
git clone https://github.com/Erfan2321/20MinTillDawn.git
cd 20MinTillDawn
./gradlew lwjgl3:run
```

On Windows use `gradlew.bat lwjgl3:run`.

### Building a runnable jar

```bash
./gradlew lwjgl3:jar
java -jar lwjgl3/build/libs/20MinTillDawn-1.0.0.jar
```

The jar is self-contained and bundles the assets and native libraries for Linux, macOS and
Windows. `jarLinux`, `jarMac` and `jarWin` build smaller single-platform jars, and
`./gradlew lwjgl3:packageLinuxX64` (or `packageMacM1`, `packageMacX64`, `packageWinX64`)
produces a native bundle with a JDK included.

---

## Controls

| Key | Action |
|---|---|
| `W` `A` `S` `D` | Move |
| Mouse | Aim |
| Left click | Shoot |
| `R` | Reload |
| `Esc` | Pause menu |
| `1` `2` `3` | Pick an upgrade on level-up |
| `E` | Quit to the game-over screen (while paused) |
| `S` | Save and quit (while paused) |

Auto-aim and auto-reload can be switched on under **Settings**.

### Cheat keys

Left in deliberately, and listed on the pause screen:

| Key | Effect |
|---|---|
| `5` | Skip 20 seconds |
| `6` | Gain a heart |
| `7` | Gain a level |
| `8` | Effectively infinite ammo |

---

## Gameplay

**Heroes** differ in health and speed:

| Hero | HP | Speed |
|---|---|---|
| Dasher | 2 | 4 |
| Scarlet | 3 | 3 |
| Shana | 4 | 2 |
| Lilith | 5 | 2 |
| Diamond | 7 | 2 |

**Weapons:**

| Weapon | Damage | Projectiles | Magazine |
|---|---|---|---|
| Revolver | 20 | 1 | 6 |
| Shotgun | 10 | 4 | 2 |
| Dual SMGs | 8 | 1 | 24 |

**Upgrades** offered three at a time on each level-up: `VITALITY` (+1 max HP),
`DAMAGER` (+25% damage for 10s), `PROCREASE` (+1 projectile), `AMOCREASE` (+5 max ammo),
`SPEEDY` (double movement speed for 10s).

Run length is chosen in **Pre-Game**: 2, 5, 10 or 20 minutes. Enemies are trees, tentacles,
eyebats and an elder boss that appears at the halfway mark.

---

## Project layout

Generated with [gdx-liftoff](https://github.com/libgdx/gdx-liftoff).

```
core/     shared game logic, in a Model / View / Controller split
lwjgl3/   desktop launcher (LWJGL3 backend)
assets/   sprites, sounds, shaders and the UI skin
```

Inside `core`:

- `Model/` — game state, `User`/`UserManager` accounts, `SaveManager`, asset and audio managers
- `View/` — one scene2d `Screen` per menu, plus `GameMenuView` for the run itself
- `Controller/` — input handling and menu transitions

### Where your data is kept

Accounts and saves are written to **`~/.20mintilldawn/`** (`users.json` and `savegame.json`),
so they are found no matter which directory you launch from. A file left in the working
directory by an older build is migrated there on first use.

Passwords are stored as salted SHA-256 hashes. This is a local, offline account system for
coursework — it is not meant to protect anything of value.

### Adding music

No music ships with the repo. Drop files into `assets/music/` and list them in
`assets/music/tracks.txt`:

```
Main Theme|music/main-theme.mp3
```

The first playable entry is what starts on launch, and the settings menu picks up the list
automatically. Tracks whose files are missing are skipped.

---

## Development notes

```bash
./gradlew build          # compile everything
./gradlew lwjgl3:run     # run the game
./gradlew clean          # wipe build output
```

There is no test suite; CI builds the project on every push.

---

## Licence

[MIT](LICENSE) for the code. Assets are third-party — read [NOTICE.md](NOTICE.md) before
reusing anything from `assets/`.
