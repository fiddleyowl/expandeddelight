package ianm1647.expandeddelight.integration.jei;

import ianm1647.expandeddelight.ExpandedDelight;
import ianm1647.expandeddelight.common.crafting.JuicerRecipe;
import ianm1647.expandeddelight.common.registry.EDRecipeTypes;
import java.util.List;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeMap;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.RecipesReceivedEvent;

@EventBusSubscriber(modid = ExpandedDelight.MODID, value = Dist.CLIENT)
public class JEIRecipes {
    private static RecipeMap recipes = RecipeMap.EMPTY;

    @SubscribeEvent
    public static void receiveRecipes(RecipesReceivedEvent event) { recipes = event.getRecipeMap(); }

    @SubscribeEvent
    public static void disconnect(ClientPlayerNetworkEvent.LoggingOut event) { recipes = RecipeMap.EMPTY; }

    public List<JuicerRecipe> getJuicerRecipes() {
        return recipes.byType(EDRecipeTypes.JUICING.get()).stream().map(RecipeHolder::value).toList();
    }
}
