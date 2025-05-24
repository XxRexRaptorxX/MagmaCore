[![CurseForge Downloads](https://cf.way2muchnoise.eu/1267885.svg?badge_style=for_the_badge)][cf_mod] [![CurseForge Game Versions](https://cf.way2muchnoise.eu/versions/1267885.svg?badge_style=for_the_badge)][cf_mod]

[![Modrinth Downloads](https://img.shields.io/modrinth/dt/rqi506vX?label=Modrinth&logo=modrinth&style=for-the-badge)][mr_mod] [![Modrinth Game Versions](https://img.shields.io/modrinth/game-versions/rqi506vX?label=Available%20for&logo=modrinth&style=for-the-badge)][mr_mod]

# Magma Core for Minecraft

![Logo](https://github.com/XxRexRaptorxX/MagmaCore/blob/main/src/main/resources/logo.png?raw=true)

## 📖 Description

This core mod contains basic code and features like update checker and much more that my other mods access.


### Features:

- chat console logger
- ingame config
- version checker
- debug mode
- helper methodes (for formatting, datagen, config, integration and much more!
- [stop-mod-reposts](https://vazkii.net/repost/) info message

-----

## 📄 How to depend on Magma Core


### Set up your Gradle build script

You can integrate and automatically download Magma Core for your mod project using Gradle.
Just add the following to your build script ```build.gradle```, we are using CurseMaven for that:

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


###  Set up your version property

Now set the version in a file named ```gradle.properties```, placed in the same directory as your ```build.gradle``` file.

```gradle
mc_version=1.21     //other versions

magma_core_version=6551413
```

Enter the CurseForge version id as the number. You can find it at the end of the url of the corresponding version:
> https://www.curseforge.com/minecraft/mc-mods/magma-core/files/6551413


### Add the dependency
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

-----

## 🗒️ License

All rights reserved.

[cf_mod]: https://www.curseforge.com/minecraft/mc-mods/magma-core
[mr_mod]: https://modrinth.com/mod/magma-core
