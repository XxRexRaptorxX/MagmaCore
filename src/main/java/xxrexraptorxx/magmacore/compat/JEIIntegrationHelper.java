package xxrexraptorxx.magmacore.compat;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class JEIIntegrationHelper {

    private record JeiAction(List<ItemStack> stacks, Component desc) {}

    private static final List<JeiAction> actions = new ArrayList<>();


    public static void enqueue(List<ItemStack> stacks, Component desc) {
        actions.add(new JeiAction(stacks, desc));
    }


    public static void apply(IRecipeRegistration registry) {
        for (JeiAction action : actions) {
            registry.addIngredientInfo(
                    action.stacks(),
                    VanillaTypes.ITEM_STACK,
                    action.desc()
            );
        }
        actions.clear();
    }
}