package xxrexraptorxx.magmacore.compat;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JEIIntegrationHelper {

    private static final List<BiConsumer<IRecipeRegistration, List<ItemStack>>> actions = new ArrayList<>();

    public static void enqueue(List<ItemStack> stacks, Component desc) {
        actions.add((registry, list) -> {
            registry.addIngredientInfo(list, VanillaTypes.ITEM_STACK, desc);
        });
    }

    public static void apply(IRecipeRegistration registry) {
        for (var action : actions) {
            // JEI expects a single list parameter, so pass all stacks
            action.accept(registry, actions instanceof List ? ((List<?>) action).stream().flatMap(o -> Stream.<ItemStack>of()).collect(Collectors.toList()) : Collections.emptyList());
        }
    }
}