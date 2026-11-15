package ianm1647.expandeddelight.common.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import ianm1647.expandeddelight.client.recipebook.JuicerRecipeBookTab;
import ianm1647.expandeddelight.common.registry.EDItems;
import ianm1647.expandeddelight.common.registry.EDRecipeBookCategories;
import ianm1647.expandeddelight.common.registry.EDRecipeSerializers;
import ianm1647.expandeddelight.common.registry.EDRecipeTypes;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.ShapelessCraftingRecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.RecipeMatcher;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;
import org.jspecify.annotations.Nullable;

public class JuicerRecipe implements Recipe<RecipeWrapper>
{
	public static final int INPUT_SLOTS = 2;

	private final String group;
	private final JuicerRecipeBookTab tab;
	private final NonNullList<Ingredient> inputItems;
	private final ItemStackTemplate output;
	@Nullable
	private final ItemStackTemplate containerOverride;
	private final float experience;
	private final int juiceTime;

	public JuicerRecipe(String group, @Nullable JuicerRecipeBookTab tab, NonNullList<Ingredient> inputItems, ItemStack output, ItemStack container, float experience, int juiceTime) {
		this(group, tab, inputItems, ItemStackTemplate.fromNonEmptyStack(output),
				container.isEmpty() ? Optional.empty() : Optional.of(ItemStackTemplate.fromNonEmptyStack(container)), experience, juiceTime);
	}

	private JuicerRecipe(String group, @Nullable JuicerRecipeBookTab tab, NonNullList<Ingredient> inputItems,
			ItemStackTemplate output, Optional<ItemStackTemplate> containerOverride, float experience, int juiceTime) {
		this.group = group;
		this.tab = tab;
		this.inputItems = inputItems;
		this.output = output;
		this.containerOverride = containerOverride.orElse(null);
		this.experience = experience;
		this.juiceTime = juiceTime;
	}

	public String getGroup() {
		return this.group;
	}

	@Override
	public String group() {
		return this.group;
	}

	@Override
	public boolean showNotification() {
		return true;
	}

	@Nullable
	public JuicerRecipeBookTab getRecipeBookTab() {
		return this.tab;
	}

	public NonNullList<Ingredient> getIngredients() {
		return this.inputItems;
	}

	public ItemStack getResultItem() {
		return this.output.create();
	}

	public ItemStack getOutputContainer() {
		if (this.containerOverride != null) {
			return this.containerOverride.create();
		}
		ItemStack result = this.output.create();
		ItemStackTemplate remainder = result.getItem().getCraftingRemainder(result);
		return remainder != null ? remainder.create() : ItemStack.EMPTY;
	}

	public ItemStack getContainerOverride() {
		return this.containerOverride != null ? this.containerOverride.create() : ItemStack.EMPTY;
	}

	@Override
	public ItemStack assemble(RecipeWrapper inv) {
		return this.output.create();
	}

	public float getExperience() {
		return this.experience;
	}

	public int getJuiceTime() {
		return this.juiceTime;
	}

	@Override
	public boolean matches(RecipeWrapper inv, Level level) {
		java.util.List<ItemStack> inputs = new java.util.ArrayList<>();
		int i = 0;

		for (int j = 0; j < INPUT_SLOTS; ++j) {
			ItemStack itemstack = inv.getItem(j);
			if (!itemstack.isEmpty()) {
				++i;
				inputs.add(itemstack);
			}
		}
		return i == this.inputItems.size() && RecipeMatcher.findMatches(inputs, this.inputItems) != null;
	}

	public boolean canCraftInDimensions(int width, int height) {
		return width * height >= this.inputItems.size();
	}

	@Override
	public RecipeSerializer<JuicerRecipe> getSerializer() {
		return EDRecipeSerializers.JUICING.get();
	}

	@Override
	public RecipeType<JuicerRecipe> getType() {
		return EDRecipeTypes.JUICING.get();
	}

	public ItemStack getToastSymbol() {
		return new ItemStack(EDItems.JUICER.get());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		JuicerRecipe that = (JuicerRecipe) o;

		if (Float.compare(that.getExperience(), getExperience()) != 0) return false;
		if (getJuiceTime() != that.getJuiceTime()) return false;
		if (!getGroup().equals(that.getGroup())) return false;
		if (tab != that.tab) return false;
		if (!inputItems.equals(that.inputItems)) return false;
		if (!output.equals(that.output)) return false;
		return java.util.Objects.equals(containerOverride, that.containerOverride);
	}

	@Override
	public int hashCode() {
		int result = getGroup().hashCode();
		result = 31 * result + (getRecipeBookTab() != null ? getRecipeBookTab().hashCode() : 0);
		result = 31 * result + inputItems.hashCode();
		result = 31 * result + output.hashCode();
		result = 31 * result + (containerOverride != null ? containerOverride.hashCode() : 0);
		result = 31 * result + (getExperience() != 0.0f ? Float.floatToIntBits(getExperience()) : 0);
		result = 31 * result + getJuiceTime();
		return result;
	}

	@Override
	public PlacementInfo placementInfo() {
		return PlacementInfo.create(this.inputItems);
	}

	@Override
	public RecipeBookCategory recipeBookCategory() {
		return switch (this.tab != null ? this.tab : JuicerRecipeBookTab.MISC) {

			case DRINKS -> EDRecipeBookCategories.JUICING_DRINKS.get();
			case MISC -> EDRecipeBookCategories.JUICING_MISC.get();
		};
	}

	@Override
	public List<RecipeDisplay> display() {
		return List.of(new ShapelessCraftingRecipeDisplay(
				this.inputItems.stream().map(Ingredient::display).toList(),
				new SlotDisplay.ItemStackSlotDisplay(this.output),
				new SlotDisplay.ItemSlotDisplay(EDItems.JUICER.get())
		));
	}

	public static final class Serializer
	{
		public static final MapCodec<JuicerRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
				Codec.STRING.optionalFieldOf("group", "").forGetter(JuicerRecipe::getGroup),
				JuicerRecipeBookTab.CODEC.optionalFieldOf("recipe_book_tab", JuicerRecipeBookTab.MISC).forGetter(JuicerRecipe::getRecipeBookTab),
				Ingredient.CODEC.listOf().fieldOf("ingredients").xmap(ingredients -> {
					NonNullList<Ingredient> nonNullList = NonNullList.create();
					nonNullList.addAll(ingredients);
					return nonNullList;
				}, ingredients -> ingredients).forGetter(JuicerRecipe::getIngredients),
				ItemStackTemplate.CODEC.fieldOf("result").forGetter(r -> r.output),
				ItemStackTemplate.CODEC.optionalFieldOf("container").forGetter(r -> Optional.ofNullable(r.containerOverride)),
				Codec.FLOAT.optionalFieldOf("experience", 0.0F).forGetter(JuicerRecipe::getExperience),
				Codec.INT.optionalFieldOf("juicingtime", 200).forGetter(JuicerRecipe::getJuiceTime)
		).apply(inst, JuicerRecipe::new));

		public static final StreamCodec<RegistryFriendlyByteBuf, JuicerRecipe> STREAM_CODEC = StreamCodec.of(JuicerRecipe.Serializer::toNetwork, JuicerRecipe.Serializer::fromNetwork);
		public static final RecipeSerializer<JuicerRecipe> INSTANCE = new RecipeSerializer<>(CODEC, STREAM_CODEC);

		private Serializer() {
		}

		private static JuicerRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
			String group = buffer.readUtf();
			JuicerRecipeBookTab tab = JuicerRecipeBookTab.findByName(buffer.readUtf());
			int i = buffer.readVarInt();
			NonNullList<Ingredient> inputItems = NonNullList.createWithCapacity(i);
			for (int ingredientIndex = 0; ingredientIndex < i; ingredientIndex++) {
				inputItems.add(Ingredient.CONTENTS_STREAM_CODEC.decode(buffer));
			}

			ItemStackTemplate output = ItemStackTemplate.STREAM_CODEC.decode(buffer);
			Optional<ItemStackTemplate> container = buffer.readBoolean()
					? Optional.of(ItemStackTemplate.STREAM_CODEC.decode(buffer))
					: Optional.empty();
			float experience = buffer.readFloat();
			int juiceTime = buffer.readVarInt();
			return new JuicerRecipe(group, tab, inputItems, output, container, experience, juiceTime);
		}

		private static void toNetwork(RegistryFriendlyByteBuf buffer, JuicerRecipe recipe) {
			buffer.writeUtf(recipe.group);
			buffer.writeUtf(recipe.tab != null ? recipe.tab.toString() : "");
			buffer.writeVarInt(recipe.inputItems.size());

			for (Ingredient ingredient : recipe.inputItems) {
				Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, ingredient);
			}

			ItemStackTemplate.STREAM_CODEC.encode(buffer, recipe.output);
			buffer.writeBoolean(recipe.containerOverride != null);
			if (recipe.containerOverride != null) {
				ItemStackTemplate.STREAM_CODEC.encode(buffer, recipe.containerOverride);
			}
			buffer.writeFloat(recipe.experience);
			buffer.writeVarInt(recipe.juiceTime);
		}
	}
}
