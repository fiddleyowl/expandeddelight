package ianm1647.expandeddelight.integration.jei.category;

import com.google.common.collect.ImmutableList;
import ianm1647.expandeddelight.common.registry.EDBlocks;
import ianm1647.expandeddelight.common.registry.EDItems;
import ianm1647.expandeddelight.common.tag.EDTags;
import ianm1647.expandeddelight.integration.jei.JEIRecipeTypes;
import ianm1647.expandeddelight.integration.jei.resource.CheeseFermentingDummy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import vectorwing.farmersdelight.common.utility.ClientRenderUtils;

public class CheeseFermentingRecipeCategory implements IRecipeCategory<CheeseFermentingDummy> {
    public static final Identifier UID = Identifier.fromNamespaceAndPath("expandeddelight", "cheese_fermenting");
    private static final int slotSize = 22;
    private final Component title = Component.translatable("expandeddelight.jei.cheese_fermenting", new Object[0]);
    private final IDrawable background;
    private final IDrawable slotIcon;
    private final IDrawable icon;
    private final ItemStack milkCask;
    private final ItemStack goatMilkCask;
    private final ItemStack cheeseCask;
    private final ItemStack goatCheeseCask;

    public CheeseFermentingRecipeCategory(IGuiHelper helper) {
        Identifier backgroundImage = Identifier.fromNamespaceAndPath("expandeddelight", "textures/gui/jei/fermenting.png");
        this.background = helper.createDrawable(backgroundImage, 0, 0, 118, 80);
        this.milkCask = new ItemStack(EDBlocks.MILK_CASK.get());
        this.cheeseCask = new ItemStack(EDItems.CHEESE_WHEEL.get(), 2);
        this.goatMilkCask = new ItemStack(EDBlocks.GOAT_MILK_CASK.get());
        this.goatCheeseCask = new ItemStack(EDItems.GOAT_CHEESE_WHEEL.get(), 2);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(EDItems.CASK.get()));
        this.slotIcon = helper.createDrawable(backgroundImage, 119, 0, 22, 22);
    }

    public RecipeType<CheeseFermentingDummy> getRecipeType() {
        return JEIRecipeTypes.CHEESE_FERMENTING;
    }

    public Component getTitle() {
        return this.title;
    }

     public int getWidth() { return background.getWidth(); }
    public int getHeight() { return background.getHeight(); }

   public IDrawable getBackground() {
        return this.background;
    }

    public IDrawable getIcon() {
        return this.icon;
    }

    public void setRecipe(IRecipeLayoutBuilder builder, CheeseFermentingDummy recipe, IFocusGroup focusGroup) {
        List<ItemStack> accelerators = new ArrayList();
        BuiltInRegistries.BLOCK.get(EDTags.MILK_ACTIVATORS).ifPresent((s) -> {
            s.forEach((f) -> {
                accelerators.add(new ItemStack(f.value()));
            });
        });

        builder.addSlot(RecipeIngredientRole.INPUT, 9, 26).addItemStack(this.milkCask).addItemStack(this.goatMilkCask);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 93, 26).addItemStack(this.cheeseCask).addItemStack(this.goatCheeseCask);
        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 64, 54).addItemStacks(accelerators);
    }

    public void draw(CheeseFermentingDummy recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        this.background.draw(guiGraphics, 0, 0);
        this.slotIcon.draw(guiGraphics, 63, 53);
    }

    public List<Component> getTooltipStrings(CheeseFermentingDummy recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        if (ClientRenderUtils.isCursorInsideBounds(40, 38, 11, 11, mouseX, mouseY)) {
            return ImmutableList.of(Component.translatable("expandeddelight.jei.cheese_fermenting.light"));
        } else if (ClientRenderUtils.isCursorInsideBounds(53, 38, 11, 11, mouseX, mouseY)) {
            return ImmutableList.of(Component.translatable("expandeddelight.jei.cheese_fermenting.fluid"));
        } else {
            return (ClientRenderUtils.isCursorInsideBounds(67, 38, 11, 11, mouseX, mouseY) ? ImmutableList.of(Component.translatable("expandeddelight.jei.cheese_fermenting.accelerators")) : Collections.emptyList());
        }
    }

    private static MutableComponent translateKey(String suffix) {
        return Component.translatable("expandeddelight.jei.cheese_fermenting" + suffix);
    }
}
