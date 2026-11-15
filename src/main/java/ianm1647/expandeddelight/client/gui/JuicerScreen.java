package ianm1647.expandeddelight.client.gui;

import ianm1647.expandeddelight.EDConfig;
import ianm1647.expandeddelight.ExpandedDelight;
import ianm1647.expandeddelight.common.block.entity.container.JuicerMenu;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.navigation.ScreenPosition;
import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class JuicerScreen extends AbstractRecipeBookScreen<JuicerMenu>
{
	private static final Identifier BACKGROUND_TEXTURE = Identifier.fromNamespaceAndPath(ExpandedDelight.MODID, "textures/gui/juicer.png");
	private static final Rectangle HEAT_ICON = new Rectangle(47, 55, 17, 15);
	private static final Rectangle PROGRESS_ARROW = new Rectangle(71, 25, 0, 17);

	private final JuicerRecipeBookComponent recipeBookComponent;

	public JuicerScreen(JuicerMenu menu, Inventory inventory, Component title) {
		this(menu, new JuicerRecipeBookComponent(menu), inventory, title);
	}

	private JuicerScreen(JuicerMenu menu, JuicerRecipeBookComponent recipeBookComponent, Inventory inventory, Component title) {
		super(menu, recipeBookComponent, inventory, title);
		this.recipeBookComponent = recipeBookComponent;
	}

	@Override
	public void init() {
		super.init();
		this.titleLabelX = 28;
		if (!EDConfig.ENABLE_RECIPE_BOOK_JUICER.get()) {
			this.recipeBookComponent.hide();
			this.leftPos = this.recipeBookComponent.updateScreenPosition(this.width, this.imageWidth);
		}
	}

	@Override
	protected ScreenPosition getRecipeBookButtonPosition() {
		return new ScreenPosition(this.leftPos + 5, this.height / 2 - 49);
	}

	@Override
	protected void onRecipeBookButtonClick() {
		if (!EDConfig.ENABLE_RECIPE_BOOK_JUICER.get()) {
			this.recipeBookComponent.hide();
			this.leftPos = this.recipeBookComponent.updateScreenPosition(this.width, this.imageWidth);
		}
	}

	@Override
	public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks) {
		super.extractBackground(graphics, mouseX, mouseY, partialTicks);
		graphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND_TEXTURE, this.leftPos, this.topPos, 0, 0,
				this.imageWidth, this.imageHeight, 256, 256);

		int progress = this.menu.getJuiceProgressionScaled();
		graphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND_TEXTURE,
				this.leftPos + PROGRESS_ARROW.x, this.topPos + PROGRESS_ARROW.y, 176, 0,
				progress + 1, PROGRESS_ARROW.height, 256, 256);
	}

	@Override
	protected void extractLabels(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
		super.extractLabels(graphics, mouseX, mouseY);
		graphics.text(this.font, this.playerInventoryTitle, 8, this.imageHeight - 94, 4210752, false);
	}

	@Override
	protected void extractTooltip(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {

		if (this.minecraft != null && this.minecraft.player != null && this.menu.getCarried().isEmpty()
				&& this.hoveredSlot != null && this.hoveredSlot.hasItem() && this.hoveredSlot.index == 2) {
			List<Component> tooltip = new ArrayList<>();
			ItemStack mealStack = this.hoveredSlot.getItem();
			tooltip.add(Component.empty().append(mealStack.getHoverName())
					.withStyle(mealStack.getRarity().getStyleModifier()));

			ItemStack containerStack = this.menu.tileEntity.getContainer();
			if (!containerStack.isEmpty()) {
				tooltip.add(Component.translatable("farmersdelight.container.cooking_pot.served_on", containerStack.getHoverName().getString())
						.withStyle(ChatFormatting.GRAY));
			}
			graphics.setComponentTooltipForNextFrame(this.font, tooltip, mouseX, mouseY, mealStack);
		} else {
			super.extractTooltip(graphics, mouseX, mouseY);
		}
	}

	@Override
	protected boolean isBiggerResultSlot() {
		return false;
	}
}
