package ianm1647.expandeddelight.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

public class JuicerTooltip implements ClientTooltipComponent
{
	private static final int ITEM_SIZE = 16;
	private static final int MARGIN = 4;

	private final int textSpacing = Minecraft.getInstance().font.lineHeight + 1;
	private final ItemStack mealStack;

	public JuicerTooltip(JuicerTooltipComponent tooltip) {
		this.mealStack = tooltip.mealStack;
	}

	@Override
	public int getHeight(Font font) {
		return mealStack.isEmpty() ? textSpacing : textSpacing + ITEM_SIZE;
	}

	@Override
	public int getWidth(Font font) {
		if (!mealStack.isEmpty()) {
			MutableComponent textServingsOf = mealStack.getCount() == 1
					? Component.translatable("tooltip.farmersdelight.cooking_pot.single_serving")
					: Component.translatable("tooltip.farmersdelight.cooking_pot.many_servings", mealStack.getCount());
			return Math.max(font.width(textServingsOf), font.width(mealStack.getHoverName()) + 20);
		} else {
			return font.width(Component.translatable("tooltip.farmersdelight.cooking_pot.empty"));
		}
	}

	@Override
	public void extractImage(Font font, int mouseX, int mouseY, int width, int height, GuiGraphicsExtractor gui) {
		if (mealStack.isEmpty()) return;
		gui.item(mealStack, mouseX, mouseY + textSpacing, 0);
	}

	@Override
	public void extractText(GuiGraphicsExtractor gui, Font font, int x, int y) {
		int gray = 0xFFAAAAAA;
		if (!mealStack.isEmpty()) {
			MutableComponent textServingsOf = mealStack.getCount() == 1
					? Component.translatable("tooltip.farmersdelight.cooking_pot.single_serving")
					: Component.translatable("tooltip.farmersdelight.cooking_pot.many_servings", mealStack.getCount());

			gui.text(font, textServingsOf, x, y, gray, true);
			gui.text(font, mealStack.getHoverName(), x + ITEM_SIZE + MARGIN, y + textSpacing + MARGIN, -1, true);
		} else {
			MutableComponent textEmpty = Component.translatable("tooltip.farmersdelight.cooking_pot.empty");
			gui.text(font, textEmpty, x, y, gray, true);
		}
	}

	public record JuicerTooltipComponent(ItemStack mealStack) implements TooltipComponent
	{
	}
}
