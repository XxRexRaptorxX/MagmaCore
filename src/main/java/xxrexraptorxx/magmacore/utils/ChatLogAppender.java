package xxrexraptorxx.magmacore.utils;

import com.mojang.logging.LogUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import xxrexraptorxx.magmacore.config.Config;

public class ChatLogAppender extends AbstractAppender {

    protected ChatLogAppender(String name, Filter filter, Layout<?> layout) {
        super(name, filter, layout, false);
    }


    @Override
    public void append(LogEvent event) {
        if (Config.getIngameLogs()) {
            String loggerName = event.getLoggerName();
            String msg = event.getMessage().getFormattedMessage();
            Level level = event.getLevel();

            if (msg != null && !msg.isEmpty()) {
                if (Config.getShowAllLogs() || loggerName.contains("xxrexraptorxx")) {
                    Minecraft mc = Minecraft.getInstance();

                    if (mc.player != null) {
                        mc.player.displayClientMessage(Component.literal("[Log] ").withStyle(ChatFormatting.BLUE)
                                .append(Component.literal("[" + loggerName + "] ").withStyle(ChatFormatting.GOLD))
                                .append(Component.literal(msg).withStyle(FormattingHelper.getDebugColor(level))), false);
                    }
                }
            }
        }
    }


    /**
     * Registers this appender on the root logger, listening to all levels.
     */
    public static void register() {
        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        Configuration config = ctx.getConfiguration();

        // Create and start the appender
        ChatLogAppender appender = new ChatLogAppender("ChatAppender", null, null);
        appender.start();
        config.addAppender(appender);

        // Attach to root logger
        LoggerConfig rootLoggerConfig = config.getLoggerConfig(LogUtils.getLogger().getName());
        rootLoggerConfig.addAppender(appender, Level.ALL, null);

        // Apply changes
        ctx.updateLoggers();
    }
}