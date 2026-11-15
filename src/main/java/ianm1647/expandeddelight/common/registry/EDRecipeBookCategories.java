package ianm1647.expandeddelight.common.registry;

import ianm1647.expandeddelight.ExpandedDelight;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class EDRecipeBookCategories
{
	public static final DeferredRegister<RecipeBookCategory> RECIPE_BOOK_CATEGORIES =
			DeferredRegister.create(Registries.RECIPE_BOOK_CATEGORY, ExpandedDelight.MODID);

	public static final Supplier<RecipeBookCategory> JUICING_DRINKS = register("juicing_drinks");
	public static final Supplier<RecipeBookCategory> JUICING_MISC = register("juicing_misc");

	private EDRecipeBookCategories() {
	}

	private static Supplier<RecipeBookCategory> register(String name) {
		return RECIPE_BOOK_CATEGORIES.register(name, RecipeBookCategory::new);
	}
}
