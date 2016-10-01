# SurvivalGames [![Build Status](https://travis-ci.org/m0pt0pmatt/SurvivalGames.svg)](https://travis-ci.org/m0pt0pmatt/SpongeSurvivalGames)

SurvivalGames is a Sponge Plugin which lets you play the classic Minecraft Survival Games game mode on your Sponge Server! You can run multiple survival games at once, and each game is customizable. Config files for survival games can be saved and loaded from file.

This plugin was created for the Sponge Summer Plugin Competition 2016.

# Demo
Installing the plugin on your server gives you instant access to survival games. just run "/ssg demo" to have the demo worlds and config files automatically downloaded. BE WARNED: running this command might ruin your server (still working out bugs), so please run it on a fresh server.
Run these commands to get the server running:

# Commands
Everything which can be set via a config can also be adjusted through commands. The root command is "/ssg". Other commands include:
- create
- delete
- load
- save
- set
- unset
- add
- remove

# Permissions
Each command has it's own permission, based on the structure of the command in the command tree. For example, to run "/ssg add player", you need the permission "ssg.add.player".

# Config
Each survival game config has the following options:
- World name
- A vector for the center of the map
- Spawn locations
- Boundarys for the game (World borders are created from this)
- An exit world and vector, for teleporting players who have perished in the games.
- Loot for spawning items in chests
- Mob spawners
- And more!

# Mob Spawners
You can configure a certain region to spawn mobs when a survival game is running. This is done via the "/ssg add mob-spawn-area <game> <x> <y> <z> <x> <y> <z> <EntityType> <EntitysPerMinute>

# Events
SurvivalGames has it's own events which are fired when game-related things happen. Not only can other players use these events, but SurvivalGames also can fire command blocks with the command "/ssg event [EventName]". This allows for map makers to do some really custom stuff!

For example, If a command block is set with the command "/ssg event MobSpawnedEvent", then whenever a MobSpawnedEvent occurs, the command block is executed. It is then turned into a redstone torch for 5 seconds, so surronding blocks can be powered. (This is a temporary workaround)
