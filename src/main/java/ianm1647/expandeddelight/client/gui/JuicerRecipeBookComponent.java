package ianm1647.expandeddelight.client.gui;

import ianm1647.expandeddelight.ExpandedDelight;
import ianm1647.expandeddelight.client.recipebook.RecipeCategories;
import ianm1647.expandeddelight.common.block.entity.container.JuicerMenu;
import ianm1647.expandeddelight.common.crafting.JuicerRecipe;
import ianm1647.expandeddelight.common.registry.EDItems;
import ianm1647.expandeddelight.common.registry.EDRecipeBookCategories;
import java.util.List;
import java.util.Optional;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.recipebook.GhostSlots;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeCollection;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.context.ContextMap;
import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.ShapelessCraftingRecipeDisplay;

public class JuicerRecipeBookComponent extends RecipeBookComponent<JuicerMenu>
{
	protected static final WidgetSprites RECIPE_BOOK_BUTTONS = new WidgetSprites(
			Identifier.fromNamespaceAndPath(ExpandedDelight.MODID, "recipe_book/juicer_enabled"),
			Identifier.fromNamespaceAndPath(ExpandedDelight.MODID, "recipe_book/juicer_disabled"),
			Identifier.fromNamespaceAndPath(ExpandedDelight.MODID, "recipe_book/juicer_enabled_highlighted"),
			Identifier.fromNamespaceAndPath(ExpandedDelight.MODID, "recipe_book/juicer_disabled_highlighted"));

	private static final List<TabInfo> TABS = List.of(
			new TabInfo(new ItemStack(Items.COMPASS), Optional.empty(), RecipeCategories.JUICING_SEARCH),
			new TabInfo(EDItems.APPLE_JUICE.get(), EDRecipeBookCategories.JUICING_DRINKS.get()),
			new TabInfo(EDItems.SWEET_BERRY_JELLY.get(), EDRecipeBookCategories.JUICING_MISC.get())
	);

	public JuicerRecipeBookComponent(JuicerMenu menu) {
		super(menu, TABS);
	}

	@Override
	protected WidgetSprites getFilterButtonTextures() {
		return RECIPE_BOOK_BUTTONS;
	}

	public void hide() {
		this.setVisible(false);
	}

	@Override
	protected Component getRecipeFilterName() {
		return Component.translatable("expandeddelight.container.recipe_book.juiceable");
	}

	@Override
	protected boolean isCraftingSlot(Slot slot) {
		return slot.index <= 4;
	}

	@Override
	protected void fillGhostRecipe(GhostSlots ghostSlots, RecipeDisplay recipe, ContextMap context) {
		ghostSlots.setResult(this.menu.slots.get(2), context, recipe.result());
		if (recipe instanceof ShapelessCraftingRecipeDisplay shapeless) {
			int slotCount = Math.min(shapeless.ingredients().size(), JuicerRecipe.INPUT_SLOTS);
			for (int i = 0; i < slotCount; i++) {
				ghostSlots.setInput(this.menu.slots.get(i), context, shapeless.ingredients().get(i));
			}
		}
	}

	@Override
	protected void selectMatchingRecipes(RecipeCollection collection, StackedItemContents stackedContents) {
		collection.selectRecipes(stackedContents, display ->
				display instanceof ShapelessCraftingRecipeDisplay shapeless
						&& shapeless.ingredients().size() <= JuicerRecipe.INPUT_SLOTS);
	}
}
