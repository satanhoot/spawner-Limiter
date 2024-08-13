# Spawner Limiter

**Spawner Limiter** is a Minecraft plugin that allows server administrators to limit the number of spawners a player can use. This plugin is perfect for balancing gameplay and preventing excessive spawner use on your server.

## Features
- Limit the number of spawners a player can use.
- Configurable max spawner limit via configuration file.
- Assign specific max spawner limits to players with permission nodes.
- Easy-to-use commands for managing spawner limits.
- Placeholders for integrating with other plugins.

## Commands
| Command | Description |
|---------|-------------|
| `/spawnerlimiter set <player> <used-spawner-count>` | Set the used spawner count for a specific player. |
| `/spawnerlimiter info <player>` | View the used and max spawner limits for a specific player. |

## Permissions
| Permission | Description |
|------------|-------------|
| `spawnerlimiter.command` | Allows use of Spawner Limiter commands. |

## Placeholders
| Placeholder | Description |
|-------------|-------------|
| `%spawnerlimiter_used_spawner%` | Displays the number of spawners a player has used. |
| `%spawnerlimiter_max_spawner%` | Displays the max number of spawners a player can use. |

## Configuration
You can set the global max spawner limit in the configuration file or assign specific limits to players using permission nodes like `spawner.max.{maxspawner}`. For example, `spawner.max.5` would allow a player to use up to 5 spawners.

## Installation
1. Download the [plugin jar](https://github.com/satanhoot/spawner-Limiter/releases) file and place it in your server's `plugins` folder.
2. Start the server to generate the configuration file.
3. Edit the configuration file to set your desired max spawner limits.
4. Restart the server for the changes to take effect.


