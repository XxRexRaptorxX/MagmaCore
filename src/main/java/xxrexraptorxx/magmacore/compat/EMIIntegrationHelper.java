package xxrexraptorxx.magmacore.compat;

import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiInfoRecipe;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class EMIIntegrationHelper {

    private static final List<EmiAction> actions = new ArrayList<>();

    private static record EmiAction(List<EmiIngredient> ingredients, List<Component> description, String idPath) {}

    public static void enqueue(List<ItemStack> stacks, Component desc, ResourceLocation recipeId) {
        List<EmiStack> emiStacks = stacks.stream().map(EmiStack::of).toList();
        EmiIngredient ingredient = EmiIngredient.of(emiStacks);
        actions.add(new EmiAction(List.of(ingredient), List.of(desc), recipeId.getPath()));
    }

    public static void apply(EmiRegistry registry) {
        for (EmiAction action : actions) {
            registry.addRecipe(new EmiInfoRecipe(action.ingredients(), action.description(), ResourceLocation.parse(action.idPath)));
        }
    }
}