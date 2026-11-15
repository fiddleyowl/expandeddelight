package ianm1647.expandeddelight.common.registry;

import ianm1647.expandeddelight.ExpandedDelight;
import ianm1647.expandeddelight.common.crafting.JuicerRecipe;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredRegister;

public class EDRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS;
    public static final Supplier<RecipeSerializer<JuicerRecipe>> JUICING;

    public EDRecipeSerializers() {
    }

    static {
        RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, ExpandedDelight.MODID);
        JUICING = RECIPE_SERIALIZERS.register("juicing", () -> JuicerRecipe.Serializer.INSTANCE);
    }
}

