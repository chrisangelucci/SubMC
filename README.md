# SubMC
A Spigot plugin written for 1.16 that would emulate a game of Subnautica. 
Includes 
* Custom Items
* Fabrication
* World generation with populators for plants and resources
* And more

# Goals
* Play Subnautica with friends (The Subnautica multiplayer was very buggy when I wrote this plugin)
* Emulate as much of the game as I could
* Use a single plugin that could handle everything necessary to host a public server with this gamemode

# Custom Items
24 items from Subnautica are implemented, each of which can be harvested from the world or crafted using a Fabricator
See [ItemUtils.java](src/submc/utils/ItemUtils.java)

# Fabrication
Custom crafting system based on the Fabricator. Uses a inventory gui for each category present in the game. Custom recipe system - see [FabricatorRecipe.java](src/submc/fabricator/FabricatorRecipe.java)

# World Generation
My first attempt at world generation using Spigot. Generates a basic ocean world with [Creepvine Plants](src/submc/worldgen/CreepvinePopulator.java) and [Resource Deposits](src/submc/worldgen/ResourcePopulator.java). All resource deposits from Subnautica are implemented and when harvested, they produce a random drop from their pool of items.

# Beacons
A useful tool in Subnautica is the beacon. It is used to mark a location so you can find your way around the Ocean. Using the HolographicDisplays API, I was able to implement [beacons](src/submc/beacons) fairly close to how they work in the game. I'm very proud of how this ended up looking.

# Custom Command and Permission System
Since one of my goals was to run a single plugin and still be able to manage a server as an administrator, I implemented a custom permissions system. Implementing that led me to also work on a more efficient way to write commands, since it is one of the most reptitive tasks in plugin development.
See [Command System](src/submc/commands) and [Permissions System](src/submc/permissions)
