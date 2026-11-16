package ianm1647.expandeddelight.common.block.entity;

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import ianm1647.expandeddelight.common.block.JuicerBlock;
import ianm1647.expandeddelight.common.block.entity.container.JuicerMenu;
import ianm1647.expandeddelight.common.block.entity.inventory.JuicerItemHandler;
import ianm1647.expandeddelight.common.crafting.JuicerRecipe;
import ianm1647.expandeddelight.common.registry.EDBlockEntityTypes;
import ianm1647.expandeddelight.common.registry.EDDataComponents;
import ianm1647.expandeddelight.common.registry.EDItems;
import ianm1647.expandeddelight.common.registry.EDRecipeTypes;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeCraftingHolder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import org.jspecify.annotations.Nullable;
import vectorwing.farmersdelight.common.block.entity.SyncedBlockEntity;
import vectorwing.farmersdelight.common.block.entity.inventory.LegacyItemHandlerResourceHandler;
import vectorwing.farmersdelight.common.item.component.ItemStackWrapper;
import vectorwing.farmersdelight.common.registry.ModDataComponents;
import vectorwing.farmersdelight.common.utility.ItemUtils;

@EventBusSubscriber(modid = "expandeddelight")
public class JuicerBlockEntity extends SyncedBlockEntity implements MenuProvider, Nameable, RecipeCraftingHolder {
   public static final int MEAL_DISPLAY_SLOT = 2;
   public static final int CONTAINER_SLOT = 3;
   public static final int OUTPUT_SLOT = 4;
   public static final int INVENTORY_SIZE = 5;
   private final ItemStackHandler inventory = this.createHandler();
   private final ResourceHandler<ItemResource> inputHandler;
   private final ResourceHandler<ItemResource> outputHandler;
   public int juiceTime;
   private int juiceTimeTotal;
   private ItemStack containerStack;
   private Component customName;
   protected final ContainerData juicerData;
   private final Object2IntOpenHashMap<ResourceKey<Recipe<?>>> usedRecipeTracker;
   private final RecipeManager.CachedCheck<RecipeWrapper, JuicerRecipe> quickCheck;

   public JuicerBlockEntity(BlockPos pos, BlockState state) {
      super(EDBlockEntityTypes.JUICER.get(), pos, state);
      this.inputHandler = new LegacyItemHandlerResourceHandler(new JuicerItemHandler(this.inventory, Direction.UP), this.inventory::setStackInSlot);
      this.outputHandler = new LegacyItemHandlerResourceHandler(new JuicerItemHandler(this.inventory, Direction.DOWN), this.inventory::setStackInSlot);
      this.containerStack = ItemStack.EMPTY;
      this.juicerData = this.createIntArray();
      this.usedRecipeTracker = new Object2IntOpenHashMap();
      this.quickCheck = RecipeManager.createCheck((RecipeType) EDRecipeTypes.JUICING.get());
   }

   @SubscribeEvent
   public static void registerCapabilities(RegisterCapabilitiesEvent event) {
      event.registerBlockEntity(Capabilities.Item.BLOCK, EDBlockEntityTypes.JUICER.get(), (be, context) -> context == Direction.UP ? be.inputHandler: be.outputHandler);
   }

   public static ItemStack getDrinkFromItem(ItemStack juicerStack) {
      return !juicerStack.is(EDItems.JUICER.get()) ? ItemStack.EMPTY : juicerStack.getOrDefault(EDDataComponents.DRINK, ItemStackWrapper.EMPTY).getStack();
   }

   public static void takeServingFromItem(ItemStack juicerStack) {
      if (juicerStack.is(EDItems.JUICER.get())) {
         ItemStack drinkStack = juicerStack.getOrDefault(EDDataComponents.DRINK, ItemStackWrapper.EMPTY).getStack();
         drinkStack.shrink(1);
         juicerStack.set(EDDataComponents.DRINK, new ItemStackWrapper(drinkStack));
      }
   }

   public static ItemStack getContainerFromItem(ItemStack juicerStack) {
      return !juicerStack.is((Item) EDItems.JUICER.get()) ? ItemStack.EMPTY : (juicerStack.getOrDefault(ModDataComponents.CONTAINER.get(), ItemStackWrapper.EMPTY)).getStack();
   }

	@Override
	protected void loadAdditional(ValueInput input) {
		super.loadAdditional(input);
		input.readChild("Inventory", inventory);
		juiceTime = input.getIntOr("JuiceTime", 0);
		juiceTimeTotal = input.getIntOr("JuiceTimeTotal", 0);
		containerStack = input.read("Container", ItemStack.OPTIONAL_CODEC).orElse(ItemStack.EMPTY);
		customName = input.read("CustomName", CUSTOM_NAME_CODEC).orElse(null);
		usedRecipeTracker.clear();
		ValueInput recipes = input.childOrEmpty("RecipesUsed");
		for (String key : recipes.keySet()) {
			usedRecipeTracker.put(ResourceKey.create(Registries.RECIPE, Identifier.parse(key)), recipes.getIntOr(key, 0));
		}
	}

   // Keep the original JSON-string format, and also accept early port saves with direct components.
   private static final Codec<Component> CUSTOM_NAME_CODEC = new Codec<>() {
      @Override
      public <T> DataResult<Pair<Component, T>> decode(DynamicOps<T> ops, T input) {
         return Codec.either(Codec.STRING, ComponentSerialization.CODEC).decode(ops, input)
               .map(pair -> pair.mapFirst(name -> name.map(text -> {
                  try {
                     return ComponentSerialization.CODEC.parse(jsonOps(ops), JsonParser.parseString(text))
                           .result().orElseGet(() -> Component.literal(text));
                  } catch (JsonParseException ignored) {
                     return Component.literal(text);
                  }
               }, component -> component)));
      }

      @Override
      public <T> DataResult<T> encode(Component value, DynamicOps<T> ops, T prefix) {
         return ComponentSerialization.CODEC.encodeStart(jsonOps(ops), value)
               .flatMap(json -> Codec.STRING.encode(json.toString(), ops, prefix));
      }

      private static <T> DynamicOps<JsonElement> jsonOps(DynamicOps<T> ops) {
         return ops instanceof net.minecraft.resources.RegistryOps<T> registries
               ? registries.withParent(JsonOps.INSTANCE) : JsonOps.INSTANCE;
      }
   };

	@Override
	protected void saveAdditional(ValueOutput output) {
		super.saveAdditional(output);
		output.putInt("JuiceTime", juiceTime);
		output.putInt("JuiceTimeTotal", juiceTimeTotal);
		output.store("Container", ItemStack.OPTIONAL_CODEC, containerStack);
		output.storeNullable("CustomName", CUSTOM_NAME_CODEC, customName);
		output.putChild("Inventory", inventory);
		ValueOutput recipes = output.child("RecipesUsed");
		usedRecipeTracker.forEach((recipeId, craftedAmount) -> recipes.putInt(recipeId.identifier().toString(), craftedAmount));
	}

   public ItemStack getAsItem() {
      ItemStack stack = new ItemStack(EDItems.JUICER.get());
      stack.applyComponents(this.collectComponents());
      return stack;
   }

   public static void juicingTick(Level level, BlockPos pos, BlockState state, JuicerBlockEntity juicer) {
      boolean didInventoryChange = false;
      if (juicer.hasInput()) {
         Optional<RecipeHolder<JuicerRecipe>> recipe = juicer.getMatchingRecipe(new RecipeWrapper(juicer.inventory));
         if (recipe.isPresent() && juicer.canJuice((recipe.get()).value())) {
            didInventoryChange = juicer.processJuicing(recipe.get(), juicer);
         } else {
            juicer.juiceTime = 0;
         }
      } else if (juicer.juiceTime > 0) {
         juicer.juiceTime = Mth.clamp(juicer.juiceTime - 2, 0, juicer.juiceTimeTotal);
      }

      ItemStack drinkStack = juicer.getDrink();
      if (!drinkStack.isEmpty()) {
         if (!juicer.doesDrinkHaveContainer(drinkStack)) {
            juicer.moveDrinkToOutput();
            didInventoryChange = true;
         } else if (!juicer.inventory.getStackInSlot(3).isEmpty()) {
            juicer.useStoredContainersOnDrink();
            didInventoryChange = true;
         }
      }

      if (didInventoryChange) {
         juicer.inventoryChanged();
      }

   }

   public static void animationTick(Level level, BlockPos pos, BlockState state, JuicerBlockEntity cookingPot) {
   }

   private Optional<RecipeHolder<JuicerRecipe>> getMatchingRecipe(RecipeWrapper inventoryWrapper) {
      if (this.level == null) {
         return Optional.empty();
      } else {
         return this.hasInput() ? this.quickCheck.getRecipeFor(inventoryWrapper, (ServerLevel) this.level) : Optional.empty();
      }
   }

   public ItemStack getContainer() {
      ItemStack mealStack = this.getDrink();
      return !mealStack.isEmpty() && !this.containerStack.isEmpty() ? this.containerStack : remainder(mealStack);
   }

   private boolean hasInput() {
      for(int i = 0; i < 2; ++i) {
         if (!this.inventory.getStackInSlot(i).isEmpty()) {
            return true;
         }
      }

      return false;
   }

   protected boolean canJuice(JuicerRecipe recipe) {
      if (this.hasInput()) {
         ItemStack resultStack = recipe.assemble(new RecipeWrapper(this.inventory));
         if (resultStack.isEmpty()) {
            return false;
         } else {
            ItemStack storedMealStack = this.inventory.getStackInSlot(2);
            if (storedMealStack.isEmpty()) {
               return true;
            } else if (!ItemStack.isSameItem(storedMealStack, resultStack)) {
               return false;
            } else if (storedMealStack.getCount() + resultStack.getCount() <= this.inventory.getSlotLimit(2)) {
               return true;
            } else {
               return storedMealStack.getCount() + resultStack.getCount() <= resultStack.getMaxStackSize();
            }
         }
      } else {
         return false;
      }
   }

   private boolean processJuicing(RecipeHolder<JuicerRecipe> recipe, JuicerBlockEntity juicer) {
      if (this.level == null) {
         return false;
      } else {
         ++this.juiceTime;
         this.juiceTimeTotal = (recipe.value()).getJuiceTime();
         if (this.juiceTime < this.juiceTimeTotal) {
            return false;
         } else {
            this.juiceTime = 0;
            this.containerStack = (recipe.value()).getOutputContainer();
            ItemStack resultStack = (recipe.value()).assemble(new RecipeWrapper(this.inventory));
            ItemStack storedDrinkStack = this.inventory.getStackInSlot(2);
            if (storedDrinkStack.isEmpty()) {
               this.inventory.setStackInSlot(2, resultStack.copy());
            } else if (ItemStack.isSameItem(storedDrinkStack, resultStack)) {
               storedDrinkStack.grow(resultStack.getCount());
            }

            juicer.setRecipeUsed(recipe);

            for(int i = 0; i < 2; ++i) {
               ItemStack slotStack = this.inventory.getStackInSlot(i);
               if (!remainder(slotStack).isEmpty()) {
                  this.ejectIngredientRemainder(remainder(slotStack));
               }
               if (!slotStack.isEmpty()) {
                  slotStack.shrink(1);
               }
            }

            return true;
         }
      }
   }

   protected void ejectIngredientRemainder(ItemStack remainderStack) {
      Direction direction = this.getBlockState().getValue(JuicerBlock.FACING).getCounterClockWise();
      double x = (double)this.worldPosition.getX() + 0.5 + (double)direction.getStepX() * 0.25;
      double y = (double)this.worldPosition.getY() + 0.7;
      double z = (double)this.worldPosition.getZ() + 0.5 + (double)direction.getStepZ() * 0.25;
      ItemUtils.spawnItemEntity(this.level, remainderStack, x, y, z, (double)((float)direction.getStepX() * 0.08F), 0.25, (double)((float)direction.getStepZ() * 0.08F));
   }

   public void setRecipeUsed(@Nullable RecipeHolder<?> recipe) {
      if (recipe != null) {
         ResourceKey<Recipe<?>> recipeID = recipe.id();
         this.usedRecipeTracker.addTo(recipeID, 1);
      }

   }

   @Nullable
   public RecipeHolder<?> getRecipeUsed() {
      return null;
   }

   public void awardUsedRecipes(Player player, List<ItemStack> items) {
      List<RecipeHolder<?>> usedRecipes = this.getUsedRecipesAndPopExperience(player.level(), player.position());
      if (player instanceof ServerPlayer serverPlayer) serverPlayer.awardRecipes(usedRecipes);
      this.usedRecipeTracker.clear();
   }

   public List<RecipeHolder<?>> getUsedRecipesAndPopExperience(Level level, Vec3 pos) {
      List<RecipeHolder<?>> list = Lists.newArrayList();
      ObjectIterator var4 = this.usedRecipeTracker.object2IntEntrySet().iterator();

      while(var4.hasNext()) {
         Object2IntMap.Entry<ResourceKey<Recipe<?>>> entry = (Object2IntMap.Entry)var4.next();
         ((ServerLevel) level).recipeAccess().byKey(entry.getKey()).ifPresent((recipe) -> {
            list.add(recipe);
            splitAndSpawnExperience((ServerLevel)level, pos, entry.getIntValue(), ((JuicerRecipe)recipe.value()).getExperience());
         });
      }

      return list;
   }

   private static void splitAndSpawnExperience(ServerLevel level, Vec3 pos, int craftedAmount, float experience) {
      int expTotal = Mth.floor((float)craftedAmount * experience);
      float expFraction = Mth.frac((float)craftedAmount * experience);
      if (expFraction != 0.0F && Math.random() < (double)expFraction) {
         ++expTotal;
      }

      ExperienceOrb.award(level, pos, expTotal);
   }

   public ItemStackHandler getInventory() {
      return this.inventory;
   }

   public ItemStack getDrink() {
      return this.inventory.getStackInSlot(2);
   }

   public NonNullList<ItemStack> getDroppableInventory() {
      NonNullList<ItemStack> drops = NonNullList.create();

      for(int i = 0; i < 5; ++i) {
         if (i != 2) {
            drops.add(this.inventory.getStackInSlot(i));
         }
      }

      return drops;
   }

   private void moveDrinkToOutput() {
      ItemStack drinkStack = this.inventory.getStackInSlot(2);
      ItemStack outputStack = this.inventory.getStackInSlot(4);
      int drinkCount = Math.min(drinkStack.getCount(), drinkStack.getMaxStackSize() - outputStack.getCount());
      if (outputStack.isEmpty()) {
         this.inventory.setStackInSlot(4, drinkStack.split(drinkCount));
      } else if (outputStack.getItem() == drinkStack.getItem()) {
         drinkStack.shrink(drinkCount);
         outputStack.grow(drinkCount);
      }

   }

   private void useStoredContainersOnDrink() {
      ItemStack drinkStack = this.inventory.getStackInSlot(2);
      ItemStack containerInputStack = this.inventory.getStackInSlot(3);
      ItemStack outputStack = this.inventory.getStackInSlot(4);
      if (this.isContainerValid(containerInputStack) && outputStack.getCount() < outputStack.getMaxStackSize()) {
         int smallerStackCount = Math.min(drinkStack.getCount(), containerInputStack.getCount());
         int drinkCount = Math.min(smallerStackCount, drinkStack.getMaxStackSize() - outputStack.getCount());
         if (outputStack.isEmpty()) {
            containerInputStack.shrink(drinkCount);
            this.inventory.setStackInSlot(4, drinkStack.split(drinkCount));
         } else if (outputStack.getItem() == drinkStack.getItem()) {
            drinkStack.shrink(drinkCount);
            containerInputStack.shrink(drinkCount);
            outputStack.grow(drinkCount);
         }
      }

   }

   public ItemStack useHeldItemOnDrink(ItemStack container) {
      if (this.isContainerValid(container) && !this.getDrink().isEmpty()) {
         container.shrink(1);
         return this.getDrink().split(1);
      } else {
         return ItemStack.EMPTY;
      }
   }

   private boolean doesDrinkHaveContainer(ItemStack stack) {
      return !this.containerStack.isEmpty() || !remainder(stack).isEmpty();
   }

   public boolean isContainerValid(ItemStack containerItem) {
      if (containerItem.isEmpty()) {
         return false;
      } else {
         return !this.containerStack.isEmpty() ? ItemStack.isSameItem(this.containerStack, containerItem) : ItemStack.isSameItem(this.getDrink(), containerItem);
      }
   }

   public Component getName() {
      return this.customName != null ? this.customName : Component.translatable("expandeddelight.container.juicer", new Object[0]);
   }

   public Component getDisplayName() {
      return this.getName();
   }

   @Nullable
   public Component getCustomName() {
      return this.customName;
   }

   public AbstractContainerMenu createMenu(int id, Inventory player, Player entity) {
      return new JuicerMenu(id, player, this, this.juicerData);
   }

   @Override
   public void preRemoveSideEffects(BlockPos pos, BlockState state) {
      if (this.level instanceof ServerLevel serverLevel) {
         net.minecraft.world.Containers.dropContents(serverLevel, pos, getDroppableInventory());
         getUsedRecipesAndPopExperience(serverLevel, Vec3.atCenterOf(pos));
         serverLevel.updateNeighbourForOutputSignal(pos, state.getBlock());
      }
      super.preRemoveSideEffects(pos, state);
   }

   public void setRemoved() {
      super.setRemoved();
   }

   protected void applyImplicitComponents(net.minecraft.core.component.DataComponentGetter componentInput) {
      super.applyImplicitComponents(componentInput);
      this.customName = componentInput.get(DataComponents.CUSTOM_NAME);
      this.getInventory().setStackInSlot(2, componentInput.getOrDefault(EDDataComponents.DRINK, ItemStackWrapper.EMPTY).getStack());
      this.containerStack = componentInput.getOrDefault(ModDataComponents.CONTAINER, ItemStackWrapper.EMPTY).getStack();
   }

   protected void collectImplicitComponents(DataComponentMap.Builder components) {
      super.collectImplicitComponents(components);
      components.set(DataComponents.CUSTOM_NAME, this.customName);
      components.set(EDDataComponents.DRINK, new ItemStackWrapper(this.getDrink()));
      components.set(ModDataComponents.CONTAINER, new ItemStackWrapper(this.getContainer()));
   }

   public void removeComponentsFromTag(ValueOutput tag) {
      tag.discard("CustomName");
      tag.discard("drink");
      tag.discard("container");
   }

   private static ItemStack remainder(ItemStack stack) {
      if (stack.isEmpty()) return ItemStack.EMPTY;
      var remainder = stack.getItem().getCraftingRemainder(stack);
      return remainder == null ? ItemStack.EMPTY : remainder.create();
   }

   private ItemStackHandler createHandler() {
      return new ItemStackHandler(INVENTORY_SIZE) {
         protected void onContentsChanged(int slot) {
            JuicerBlockEntity.this.inventoryChanged();
         }
      };
   }

   private ContainerData createIntArray() {
      return new ContainerData() {
         public int get(int index) {
            int var10000;
            switch (index) {
               case 0 -> var10000 = JuicerBlockEntity.this.juiceTime;
               case 1 -> var10000 = JuicerBlockEntity.this.juiceTimeTotal;
               default -> var10000 = 0;
            }

            return var10000;
         }

         public void set(int index, int value) {
            switch (index) {
               case 0 -> JuicerBlockEntity.this.juiceTime = value;
               case 1 -> JuicerBlockEntity.this.juiceTimeTotal = value;
            }

         }

         public int getCount() {
            return 2;
         }
      };
   }
}
