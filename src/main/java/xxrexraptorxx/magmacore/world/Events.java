package xxrexraptorxx.magmacore.world;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.VersionChecker;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import xxrexraptorxx.magmacore.config.Config;
import xxrexraptorxx.magmacore.content.items.RewardItems;
import xxrexraptorxx.magmacore.main.MagmaCore;
import xxrexraptorxx.magmacore.main.ModRegistry;
import xxrexraptorxx.magmacore.main.References;
import xxrexraptorxx.magmacore.utils.FormattingHelper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = References.MODID, bus = EventBusSubscriber.Bus.GAME)
public class Events {

    private static final Map<String, Boolean> shownMap = new HashMap<>();
    private static boolean hasShownUp = false;

    /**
     * Handles update checking for registered mods after world joining.
     */
    @SubscribeEvent
    public static void UpdateChecker(ClientTickEvent.Pre event) {
        if (Minecraft.getInstance().screen != null || !Config.getUpdateChecker() || hasShownUp) return;

        for (var entry : ModRegistry.getEntries()) {
            if (Config.getDebugMode()) MagmaCore.LOGGER.info("Update-Checker for " + entry.modName() + " is running!");

            if (shownMap.getOrDefault(entry.modId(), false)) continue;

            var player = Minecraft.getInstance().player;
            if (player == null) continue;

            var modContainer = ModList.get().getModContainerById(entry.modId()).orElse(null);
            if (modContainer == null) continue;

            var result = VersionChecker.getResult(modContainer.getModInfo());
            switch (result.status()) {
                case OUTDATED, BETA_OUTDATED -> {
                    MutableComponent msg = FormattingHelper.setCoreLangComponent("message.update_available", entry.modName()).withStyle(style -> style.withColor(ChatFormatting.BLUE));
                    MutableComponent link = FormattingHelper.setCoreLangComponent("message.update_link").withStyle(style -> style.withColor(ChatFormatting.GREEN)
                            .withClickEvent(new ClickEvent.OpenUrl(URI.create(entry.updateUrl()))).withHoverEvent(new HoverEvent.ShowText(FormattingHelper.setCoreLangComponent("message.official").withStyle(ChatFormatting.GOLD))));

                    player.displayClientMessage(msg, false);
                    player.displayClientMessage(link, false);
                    shownMap.put(entry.modId(), true);
                }

                case FAILED -> {
                    MagmaCore.LOGGER.error("Update-Checker for {} is failed!", entry.modName());
                    shownMap.put(entry.modId(), true);
                }
                default -> { /* up to date */ }
            }
        }

        if (Config.getDebugMode()) MagmaCore.LOGGER.info("All updates checked!");
        hasShownUp = true;
    }


    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();
    public static URI supporterList = URI.create("https://raw.githubusercontent.com/XxRexRaptorxX/Patreons/main/Supporter");
    public static URI premiumSupporterList = URI.create("https://raw.githubusercontent.com/XxRexRaptorxX/Patreons/main/Premium%20Supporter");
    public static URI eliteSupporterList = URI.create("https://raw.githubusercontent.com/XxRexRaptorxX/Patreons/main/Elite");
    public static Set<String> supporterSet = new HashSet<>();

    /**
     * Distributes supporter rewards on first login.
     */
    @SubscribeEvent
    public static void DistributeSupporterRewards(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        Level level = player.level();

        if (Config.getSupporterRewards()) {
            if (Config.getDebugMode()) MagmaCore.LOGGER.info("Supporter rewards will be distributed!");

            // Check if the player already has rewards
            if (!player.getInventory().contains(RewardItems.getCertificate())) {
                if (player instanceof ServerPlayer serverPlayer) { // Ensure the player is a ServerPlayer
                    // Check if the player is logging in for the first time
                    if (serverPlayer.getStats().getValue(Stats.CUSTOM, Stats.PLAY_TIME) < 5) {
                        boolean isDev = player.getName().getString().equals("Dev");

                        // Perform supporter checks asynchronously
                        CompletableFuture.runAsync(() -> {
                            if (SupporterCheck(supporterList, player) || Config.getDebugMode() && isDev) {
                                giveSupporterReward(player, level);
                            }
                            if (SupporterCheck(premiumSupporterList, player) || Config.getDebugMode() && isDev) {
                                givePremiumSupporterReward(player, level);
                            }
                            if (SupporterCheck(eliteSupporterList, player) || Config.getDebugMode() && isDev) {
                                giveEliteReward(player, level);
                            }
                        });
                    }
                }
            }
        }
    }


    /**
     * Checks if the player is in the supporter list from the given URI.
     *
     * @param uri URI to a file containing supporter names
     * @param player The in-game player
     * @return true if the player is a supporter, otherwise false
     */
    private static boolean SupporterCheck(URI uri, Player player) {
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            // Parse supporter list
            List<String> supporterList = List.of(response.body().split("\\R")); // Split lines
            return supporterList.contains(player.getName().getString());

        } catch (Exception e) {
            MagmaCore.LOGGER.error("Failed to fetch or process supporter list from URI: {}", uri, e);
            return false;
        }
    }


    private static void giveSupporterReward(Player player, Level level) {
        if (Config.getDebugMode()) MagmaCore.LOGGER.info("Supporter found! " + player.getDisplayName());

        level.playSound(null, player.blockPosition(), SoundEvents.PLAYER_LEVELUP, SoundSource.PLAYERS, 0.5F, level.random.nextFloat() * 0.15F + 0.8F);
        player.getInventory().add(RewardItems.getPlayerHead(player));
        player.getInventory().add(RewardItems.getCertificate());
    }


    private static void givePremiumSupporterReward(Player player, Level level) {
        if (Config.getDebugMode()) MagmaCore.LOGGER.info("Premium Supporter found! " + player.getDisplayName());

        player.getInventory().add(RewardItems.getLeggings(level, player));
    }


    private static void giveEliteReward(Player player, Level level) {
        if (Config.getDebugMode()) MagmaCore.LOGGER.info("Elite Supporter found! " + player.getDisplayName());

        player.getInventory().add(RewardItems.getChestplate(level, player));
    }


    @SubscribeEvent
    public static void onServerStart(ServerStartedEvent event) {
        CompletableFuture.runAsync(() -> {
            supporterSet = fetchList(supporterList);

            if (Config.getDebugMode()) MagmaCore.LOGGER.info("Loaded supporter: {}", supporterSet.size());
        });
    }


    private static Set<String> fetchList(URI uri) {
        try {
            HttpResponse<String> resp = HTTP_CLIENT.send(HttpRequest.newBuilder(uri).GET().build(), HttpResponse.BodyHandlers.ofString());
            return new HashSet<>(List.of(resp.body().split("\\R")));

        } catch (Exception ex) {
            MagmaCore.LOGGER.error("Error while loading: {}", ex.getMessage());

            return Set.of();
        }
    }


    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (Config.getSupporterHighlights()) {
            Entity player = event.getEntity();
            Level level = player.level();
            boolean isDev = player.getName().getString().equals("Dev");

            if (!level.isClientSide() && supporterSet.contains(event.getEntity().getUUID().toString()) || Config.getDebugMode() && isDev) {
                Vec3 pos = player.position();
                double d0 = pos.x + (level.random.nextFloat() - 0.5F);
                double d1 = pos.y + (level.random.nextFloat() * 1.5F - 0.75F);
                double d2 = pos.z + (level.random.nextFloat() - 0.5F);

                level.addParticle(ParticleTypes.GLOW, d0, d1, d2, 0.0D, 0.0D, 0.0D);
            }
        }
    }


    /**
     * @author XxRexRaptorxX (RexRaptor)
     *
     * When entering a new MC installation for the first time, a message appears informing about the risks of mod reposts.
     * Then generates a marker file in the configs folder with more details. Supports implementation in multiple mods.
     *
     * You are welcome to implement this method in your own mods!
     */
    @SubscribeEvent
    public static void showStopModRepostsMessage(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        Path configDir = FMLPaths.CONFIGDIR.get();
        Path marker   = configDir.resolve("#STOP_MOD_REPOSTS.txt");

        try {
            if (Files.notExists(marker)) {
                String fileContent = """
                        Sites like 9minecraft.net, mc-mod.net, and many others, are known for reuploading mod files without permission from the authors. These sites will also contain a bunch of ads, to try to make money from mods they did not create.
                        
                        These sites will use various methods to appear higher in Google when you search for the mod name, so a lot of people will download mods from them instead of the proper place. If you were linked to this site, you're one of these people.
                        
                        FOR YOU, AS A PLAYER, THIS CAN MEAN ANY OF THE FOLLOWING:
                        > Getting versions of the mods advertised for the wrong Minecraft versions, which will 100% crash when you load them.
                        > Getting old, and broken, versions of the mods, possibly causing problems in your game.
                        > Getting modified versions of the mods, which may contain malware and viruses.
                        > Having your information stolen from malicious ads in the sites.
                        > Taking money and views away from the official authors, which may cause them to stop making new mods.

                        WHAT DO I DO NOW?
                        The most important thing to do now is to make sure you stop visiting these sites, and get the mods from official sources. We also recommend you do the following:
                        
                        > Delete all the mods you've downloaded from these sites.
                        > Install the StopModReposts plugin, which makes sure you never visit them again.
                        > Run a virus/malware scan. We recommend MalwareBytes.
                        > Check out the #StopModReposts campaign, that tries to put an end to these sites. (https://stopmodreposts.org/)
                        > Spread the word. If you have any friends that use these sites, inform them to keep them safe.
                        
                        WHERE DO I GET MODS NOW?
                        Here's a bunch of links to places where you can download official versions of mods, hosted by their real authors:
                        
                        > CurseForge, where most modders host their mods. If it exists, it's probably there.
                        > Modrinth, a new hosting platform for mods that's also legit and more popular by the day.
                        > OptiFine.net, the official OptiFine site.
                        > Neoforged.net, which you need for any other Neoforge mods.
                        > FabricMC.net, which you need for any other Fabric mods.
                        > MinecraftForge Files, which you need for any other Forge mods.
                        
                        This doesn't mean other sites aren't legit. In general, the first place to look for a mod is CurseForge and Modrinth, so look there first.
                        
                        FAQ
                        Q: What if I've never had problems before?
                        > Just because you've never had problems with these sites before doesn't mean they're good. You should still avoid them for all the reasons listed above.
                        
                        Q: Is there a list of these sites I can check out?
                        > Yes, however, due to these showing up all the time, it's possible to be incomplete. (https://github.com/StopModReposts/Illegal-Mod-Sites/blob/master/SITES.md)
                        
                        Q: Why can't you just take these sites down?
                        > Unfortunately, these sites are often hosted in countries like Russia or Vietnam, where doing so isn't as feasible.
                        
                        Q: What if it says "Official Download" on the sites?
                        > Sometimes they'll do that to trick you. If you're uncertain, you should verify with the StopModReposts list linked above.
                        
                        
                        Credits: XxRexRaptorxX, Vazkii, StopModReposts campaign
                    """;

                Files.writeString(marker, fileContent, StandardCharsets.UTF_8);
                String launcher = FMLLoader.getLauncherInfo().toLowerCase();

                if (!(launcher.contains("curseforge") || launcher.contains("modrinth") || launcher.contains("prism")) && Config.getModRepostsInfo()) {
                    MagmaCore.LOGGER.info("Stop-mod-reposts info message is generated. Don't worry, this message should only appear the very first time after installation!");
                    player.sendSystemMessage(Component.literal("<-------------------------------------------------->").withStyle(ChatFormatting.RED));

                    player.sendSystemMessage(FormattingHelper.setCoreLangComponent("message.reposts_header").withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.DARK_RED));
                    player.sendSystemMessage(FormattingHelper.setCoreLangComponent("message.reposts_warning").withStyle(ChatFormatting.RED));
                    player.sendSystemMessage(FormattingHelper.setCoreLangComponent("message.reposts_note_intro").withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.DARK_RED));
                    player.sendSystemMessage(FormattingHelper.setCoreLangComponent("message.reposts_malware").withStyle(ChatFormatting.RED));
                    player.sendSystemMessage(FormattingHelper.setCoreLangComponent("message.reposts_steal").withStyle(ChatFormatting.RED));
                    player.sendSystemMessage(FormattingHelper.setCoreLangComponent("message.reposts_broken").withStyle(ChatFormatting.RED));
                    player.sendSystemMessage(FormattingHelper.setCoreLangComponent("message.reposts_authors").withStyle(ChatFormatting.RED));
                    player.sendSystemMessage(Component.empty());

                    MutableComponent url = FormattingHelper.setCoreLangComponent("message.reposts_more_info")
                            .withStyle(style -> style.withClickEvent(new ClickEvent.OpenUrl(URI.create("https://vazkii.net/repost/")))
                            .withColor(ChatFormatting.GOLD).withHoverEvent(new HoverEvent.ShowText(Component.literal("?").withStyle(ChatFormatting.GRAY))));
                    player.sendSystemMessage(url);

                    player.sendSystemMessage(Component.literal("<-------------------------------------------------->").withStyle(ChatFormatting.RED));
                }
            }
        } catch (IOException e) {
            MagmaCore.LOGGER.error(e);
        }
    }
}
