package ianm1647.expandeddelight.client.recipebook;

import ianm1647.expandeddelight.common.registry.EDRecipeBookCategories;
import net.minecraft.world.item.crafting.ExtendedRecipeBookCategory;
import net.neoforged.neoforge.client.event.RegisterRecipeBookSearchCategoriesEvent;

public class RecipeCategories
{
	public static final ExtendedRecipeBookCategory JUICING_SEARCH = new ExtendedRecipeBookCategory() {
		@Override
		public String toString() {
			return "expandeddelight:juicing_search";
		}
	};

	public static void init(RegisterRecipeBookSearchCategoriesEvent event) {
		event.register(JUICING_SEARCH,
				EDRecipeBookCategories.JUICING_DRINKS.get(),
				EDRecipeBookCategories.JUICING_MISC.get());
	}
}
