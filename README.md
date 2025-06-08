<p align="center">
  <a href="https://www.curseforge.com/minecraft/mc-mods/magma-core"><img src="http://cf.way2muchnoise.eu/short_1267885.svg?badge_style=flat" alt="Curseforge"></a> 
  <a href="https://modrinth.com/mod/magma-core"><img src="https://img.shields.io/modrinth/dt/runecraft?logo=modrinth&label=&suffix=%20&style=flat&color=242629&labelColor=5ca424&logoColor=1c1c1c" alt="Modrinth"></a>
  <a href="https://www.curseforge.com/minecraft/mc-mods/magma-core"><img src="https://cf.way2muchnoise.eu/versions/1267885(266fff).svg?badge_style=flat" alt="Supported Versions"></a>
  <a href="https://discord.gg/hmaD7Se"><img src="https://img.shields.io/discord/523112426913267713?color=282828&logo=discord&style=flat&label=&labelColor=B6BCEA" alt="Discord"></a>
</p>
<p align="center">
  <img src="https://github.com/XxRexRaptorxX/MagmaCore/blob/main/src/main/resources/logo.png?raw=true" alt="logo" width="400"/><br>
  <img src="https://github.com/XxRexRaptorxX/General/blob/main/page_decorations/line_curved.png?raw=true" alt="line" width="2000"/>
</p>


## 📖 Description

This core mod contains basic code and features like update checker and much more that my other mods access.


### Features:

- chat console logger
- ingame config
- version checker
- debug mode
- helper methodes (for formatting, datagen, config, integration and much more!
- [stop-mod-reposts](https://vazkii.net/repost/) info message

<p align="center">
  <img src="https://github.com/XxRexRaptorxX/General/blob/main/page_decorations/line.png?raw=true" alt="line" width="2000"/>
</p>

## 📷 Presentation

![update checker](https://cdn.modrinth.com/data/rqi506vX/images/3fb9c8e313cdfb01c5f34a658d0f5eabb69a0d0f.png)
![ingame logger](https://cdn.modrinth.com/data/rqi506vX/images/81c26259a113fedaf3e100b57a9cf7f2c84d1b5e.png)
![ingame config](https://cdn.modrinth.com/data/rqi506vX/images/cb4f2d9f13c6be5c880bcce3792dd8fed43368cf.png)

<p align="center">
  <img src="https://github.com/XxRexRaptorxX/General/blob/main/page_decorations/line.png?raw=true" alt="line" width="2000"/>
</p>

## 📄 How to depend on Magma Core

-----


### 1. Set up your Gradle build script

-----

You can integrate and automatically download [Magma Core][cf_mod] for your mod project using Gradle.
Just add the following to your build script ```build.gradle```, we are using [CurseMaven](https://cursemaven.com/) for that:

````gradle
repositories {
    maven { // Curseforge
        url "https://cursemaven.com"
        content {
            includeGroup "curse.maven"
        }
    }
}
````
````gradle
dependencies {
  /* other minecraft dependencies are here */
  
   //MagmaCore
    implementation "curse.maven:MagmaCore-1267885:${magma_core_version}"
}
````


###  2. Set up your version property

-----

Now set the version in a file named ```gradle.properties```, placed in the same directory as your ```build.gradle``` file.

```gradle
mc_version=1.21     //other versions

magma_core_version=6551413
```

Enter the CurseForge version id as the number. You can find it at the end of the url of the corresponding version:
> https://www.curseforge.com/minecraft/mc-mods/magma-core/files/6551413


### 3. Add the dependency

-----
Now you just have to add MagmaCore as a dependency below the other dependencies in your ```neoforge.mods.toml```, it should look something like this:

```properties
[[dependencies.${mod_id}]]
    modId="magmacore"
    type="required"
    versionRange="[1.2.0,)"
    ordering="AFTER"
    side="BOTH"
    referralUrl="https://www.curseforge.com/minecraft/mc-mods/magma-core"
    reason="Provides basic code and features such as update-checker"
```
<p align="center">
  <img src="https://github.com/XxRexRaptorxX/General/blob/main/page_decorations/line.png?raw=true" alt="line" width="2000"/>
</p>

## 🗃️ Depending Mods
 
- [Curseforge List](https://www.curseforge.com/minecraft/mc-mods/magma-core/relations/dependents?filter-related-dependents=3)

<p align="center">
  <img src="https://github.com/XxRexRaptorxX/General/blob/main/page_decorations/line.png?raw=true" alt="line" width="2000"/>
</p>

## 🗒️ License

All rights reserved.

<p align="center">
  <img src="https://github.com/XxRexRaptorxX/General/blob/main/page_decorations/line.png?raw=true" alt="line" width="2000"/>
</p>


## 🎮 My other mods!

<p align="center">
  <a href="https://www.curseforge.com/minecraft/mc-mods/additional-structures"><img src="https://github.com/XxRexRaptorxX/Additional-Structures/blob/master/src/main/resources/logo.png?raw=true" alt="Additional Structures" width="85"/></a>
  <a href="https://www.curseforge.com/minecraft/mc-mods/bedrock-miner"><img src="https://github.com/XxRexRaptorxX/Bedrock-Miner/blob/main/src/main/resources/logo.png?raw=true" alt="Bedrock Miner" width="85"/></a>
  <a href="https://www.curseforge.com/minecraft/mc-mods/tool-upgrades"><img src="https://github.com/XxRexRaptorxX/ToolUpgrades/blob/main/src/main/resources/logo.png?raw=true" alt="Tool Upgrades" width="85"/></a>
  <a href="https://www.curseforge.com/minecraft/mc-mods/all-the-compatibility"><img src="https://github.com/XxRexRaptorxX/AllTheCompatibility/blob/main/src/main/resources/logo.png?raw=true" alt="All The Compatibility" width="85"/></a>
  <a href="https://www.curseforge.com/minecraft/mc-mods/customized-dungeon-loot"><img src="https://camo.githubusercontent.com/2291f5f13114f09294ce5e079f8a61b8a2617a8692e68ca453674e93f1982b62/687474703a2f2f692e696d6775722e636f6d2f427065525748442e706e67" alt="Customized Dungeon Loot" width="85"/></a>
  <a href="https://www.curseforge.com/minecraft/mc-mods/advanced-sticks"><img src="https://github.com/XxRexRaptorxX/AdvancedSticks/blob/master/src/main/resources/logo.png?raw=true" alt="Advanced Tools" width="85"/></a>
  <a href="https://www.curseforge.com/minecraft/mc-mods/age-of-weapons"><img src="https://camo.githubusercontent.com/e8e1315b08a3bd1fb144ccd47488b1df4033418ad3aee01e61cc9d6e83fa55a5/68747470733a2f2f692e696d6775722e636f6d2f586d324332576a2e706e67" alt="Age of Weapons" width="85"/></a>
  <a href="https://www.curseforge.com/minecraft/mc-mods/rune-craft"><img src="https://github.com/XxRexRaptorxX/RuneCraft/blob/main/src/main/resources/logo.png?raw=true" alt="Rune Craft" width="85"/></a>
  <a href="https://www.curseforge.com/minecraft/mc-mods/collectibles"><img src="https://github.com/XxRexRaptorxX/Collectibles/blob/main/src/main/resources/logo.png?raw=true" alt="Collectibles" width="85"/></a> <br> 
  <a href="https://www.curseforge.com/minecraft/mc-mods/extra-gems"><img src="https://github.com/XxRexRaptorxX/ExtraGems/blob/main/src/main/resources/logo.png?raw=true" alt="Extra Gems" width="85"/></a>
  <a href="https://www.curseforge.com/minecraft/mc-mods/minetraps"><img src="https://github.com/XxRexRaptorxX/MineTraps/blob/main/src/main/resources/logo.png?raw=true" alt="Mine Traps" width="85"/></a>
  <a href="https://www.curseforge.com/minecraft/mc-mods/exo-craft"><img src="https://github.com/XxRexRaptorxX/ExoCraft/blob/main/src/main/resources/logo.png?raw=true" alt="ExoCraft" width="85"/></a>
  <a href="https://www.curseforge.com/minecraft/mc-mods/city-craft"><img src="https://github.com/XxRexRaptorxX/CityCraft/blob/master/src/main/resources/logo.png?raw=true" alt="City Craft" width="85"/></a>
  <a href="https://www.curseforge.com/minecraft/mc-mods/upcycle"><img src="https://github.com/XxRexRaptorxX/Upcycle/blob/main/src/main/resources/logo.png?raw=true" alt="Upcycle" width="85"/></a>
  <a href="https://www.curseforge.com/minecraft/mc-mods/lexicon"><img src="https://github.com/XxRexRaptorxX/Lexicon/blob/main/src/main/resources/logo.png?raw=true" alt="Lexicon" width="85"/></a>
  <a href="https://www.curseforge.com/minecraft/mc-mods/nexus-battle-mode"><img src="https://github.com/XxRexRaptorxX/Nexus/blob/master/src/main/resources/logo.png?raw=true" alt="Nexus" width="85"/></a>
  <a href="https://www.curseforge.com/minecraft/mc-mods/block-detective"><img src="https://github.com/XxRexRaptorxX/BlockDetective/blob/main/src/main/resources/logo.png?raw=true" alt="Block Detective" width="85"/></a>
  <a href="https://www.curseforge.com/minecraft/mc-mods/suspicious-pots"><img src="https://github.com/XxRexRaptorxX/Suspicious-Pots/blob/main/src/main/resources/logo.png?raw=true" alt="Suspicious Pots" width="85"/></a>
</p>

<p align="center">
  <img src="https://github.com/XxRexRaptorxX/General/blob/main/page_decorations/line.png?raw=true" alt="line" width="2000"/>
</p>

<p align="center">
<br>
  <a href="https://discord.com/invite/ycRJJk8EXV"><img src="https://github.com/XxRexRaptorxX/General/blob/main/page_decorations/message.png?raw=true" alt="message"/></a>
</p>


<p align="center">
  <a href="https://github.com/XxRexRaptorxX"><img src="https://github.com/XxRexRaptorxX/General/blob/main/icons/github_icon_bg.png?raw=true" alt="Github" width="70"/></a>
  <a href="https://ko-fi.com/xxrexraptorxx"><img src="https://github.com/XxRexRaptorxX/General/blob/main/icons/kofi_icon_bg.png?raw=true" alt="Ko-Fi" width="70"/></a>
  <a href="https://www.patreon.com/bePatron?u=75131568"><img src="https://github.com/XxRexRaptorxX/General/blob/main/icons/patreon_icon_bg.png?raw=true" alt="Patreon" width="70"/></a>
  <a href="https://discord.com/invite/ycRJJk8EXV"><img src="https://github.com/XxRexRaptorxX/General/blob/main/icons/discord_icon_bg.png?raw=true" alt="Discord" width="70"/></a>
  <a href="https://x.com/XxRexRaptorxX"><img src="https://github.com/XxRexRaptorxX/General/blob/main/icons/xtwitter_icon_bg.png?raw=true" alt="X" width="70"/></a>
  <a href="https://github.com/XxRexRaptorxX/General/blob/main/FAQ.md"><img src="https://github.com/XxRexRaptorxX/General/blob/main/icons/wikipedia_icon_bg.png?raw=true" alt="FAQ" width="70"/></a>
<br> <br>
  <a href="https://bisecthosting.com/REXRAPTOR"><img src="https://github.com/XxRexRaptorxX/General/blob/main/BH_REX_Bisect.png?raw=true" alt="Bisect Hosting - 25% off with code: REXRAPTOR"/></a>
</p>

[cf_mod]: https://www.curseforge.com/minecraft/mc-mods/magma-core
[mr_mod]: https://modrinth.com/mod/magma-core
