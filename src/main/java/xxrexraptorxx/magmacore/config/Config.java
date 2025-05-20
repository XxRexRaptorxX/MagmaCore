package xxrexraptorxx.magmacore.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {

    private static final ModConfigSpec.Builder SERVER_BUILDER = new ModConfigSpec.Builder();
    private static final ModConfigSpec.Builder CLIENT_BUILDER = new ModConfigSpec.Builder();
    private static final ModConfigSpec.Builder STARTUP_BUILDER = new ModConfigSpec.Builder();

    public static ModConfigSpec SERVER_CONFIG;
    public static ModConfigSpec CLIENT_CONFIG;
    public static ModConfigSpec STARTUP_CONFIG;

    private static final ModConfigSpec.BooleanValue UPDATE_CHECKER;
    private static final ModConfigSpec.BooleanValue MOD_REPOSTS_INFO;
    private static final ModConfigSpec.BooleanValue SUPPORTER_REWARDS;
    private static final ModConfigSpec.BooleanValue DEBUG_MODE;
    private static final ModConfigSpec.BooleanValue INGAME_LOGS;
    private static final ModConfigSpec.BooleanValue SHOW_ALL_LOGS;


    //CLIENT
    static {
        ConfigHelper.setCategory(CLIENT_BUILDER, "general");
        UPDATE_CHECKER =        CLIENT_BUILDER.comment("Activate whether the game should check at every world start whether your mod matches the latest version").define("update-checker", true);
        MOD_REPOSTS_INFO =      CLIENT_BUILDER.comment("Activate whether the game should show the mod reposts info the first time the game launches. To pack makers: Please support us!").define("mod_reposts_info", true);
        CLIENT_BUILDER.pop();

        ConfigHelper.setCategory(CLIENT_BUILDER, "logger");
        INGAME_LOGS =           CLIENT_BUILDER.comment("Shows you ingame in the chat, all logs of the console. Only useful for debugging").define("ingame_logs", false);
        SHOW_ALL_LOGS =         CLIENT_BUILDER.comment("Shows you all logs, if switched off only logs from RexRaptor mods are displayed").define("show_all_logs", true);
        CLIENT_BUILDER.pop();

        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }

    //SERVER
    static {
        ConfigHelper.setCategory(SERVER_BUILDER, "general");
        SUPPORTER_REWARDS =       SERVER_BUILDER.comment("Enables ingame rewards on first spawn for Patreons and supporters").define("supporter_rewards", true);
        SERVER_BUILDER.pop();

        SERVER_CONFIG = SERVER_BUILDER.build();
    }

    //STARTUP
    static {
        ConfigHelper.setCategory(STARTUP_BUILDER, "general");
        DEBUG_MODE =            STARTUP_BUILDER.comment("Activate the Debug Mode").define("debug_mode", false);
        STARTUP_BUILDER.pop();

        STARTUP_CONFIG = STARTUP_BUILDER.build();
    }


    public static boolean getUpdateChecker()            { return UPDATE_CHECKER.get();                          }
    public static boolean getModRepostsInfo()           { return MOD_REPOSTS_INFO.get();                        }
    public static boolean getSupporterRewards()         { return SUPPORTER_REWARDS.get();                       }
    public static boolean getIngameLogs()               { return INGAME_LOGS.get();                             }
    public static boolean getShowAllLogs()              { return SHOW_ALL_LOGS.get();                           }
    public static boolean getDebugMode()                { return DEBUG_MODE != null || DEBUG_MODE.get();        }
}