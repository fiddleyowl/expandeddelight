package ianm1647.expandeddelight.integration.kubejs;

import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchemaRegistry;
import ianm1647.expandeddelight.ExpandedDelight;
import ianm1647.expandeddelight.integration.kubejs.recipe.JuicingRecipeJS;
import net.minecraft.resources.Identifier;

public class ExpandedDelightKubeJS implements KubeJSPlugin {
    @Override
    public void registerRecipeSchemas(RecipeSchemaRegistry registry) {
        registry.register(Identifier.fromNamespaceAndPath(ExpandedDelight.MODID, "juicing"), JuicingRecipeJS.SCHEMA);
    }
}
