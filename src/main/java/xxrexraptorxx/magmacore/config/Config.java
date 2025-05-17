package xxrexraptorxx.magmacore.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {

    private static final ModConfigSpec.Builder SERVER_BUILDER = new ModConfigSpec.Builder();
    private static final ModConfigSpec.Builder CLIENT_BUILDER = new ModConfigSpec.Builder();

    public static ModConfigSpec SERVER_CONFIG;
    public static ModConfigSpec CLIENT_CONFIG;

    private static final ModConfigSpec.BooleanValue UPDATE_CHECKER;
    private static final ModConfigSpec.BooleanValue MOD_REPOSTS_INFO;
    private static final ModConfigSpec.BooleanValue SUPPORTER_REWARDS;
    private static final ModConfigSpec.BooleanValue DEBUG_MODE;


    //CLIENT
    static {
        ConfigHelper.setCategory(CLIENT_BUILDER, "general");
        UPDATE_CHECKER =        CLIENT_BUILDER.comment("Activate whether the game should check at every world start whether your mod matches the latest version").define("update-checker", true);
        MOD_REPOSTS_INFO =      CLIENT_BUILDER.comment("Activate whether the game should show the mod reposts info the first time the game launches. To pack makers: Please support us!").define("mod_reposts_info", true);
        DEBUG_MODE =            CLIENT_BUILDER.comment("Activate the Debug Mode").define("debug_mode", false);
        CLIENT_BUILDER.pop();

        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }

    //SERVER
    static {
        ConfigHelper.setCategory(SERVER_BUILDER, "general");
        SUPPORTER_REWARDS =       SERVER_BUILDER.comment("Enables ingame rewards on first spawn for Patreons").define("patreon_rewards", true);
        SERVER_BUILDER.pop();

        SERVER_CONFIG = SERVER_BUILDER.build();
    }


    public static boolean getUpdateChecker()            { return UPDATE_CHECKER.get();                          }
    public static boolean getModRepostsInfo()           { return MOD_REPOSTS_INFO.get();                        }
    public static boolean getSupporterRewards()         { return SUPPORTER_REWARDS.get();                       }
    public static boolean getDebugMode()                { return DEBUG_MODE.get();                              }
}