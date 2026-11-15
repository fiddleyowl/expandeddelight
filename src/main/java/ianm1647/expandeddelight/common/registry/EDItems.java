package ianm1647.expandeddelight.common.registry;

import com.google.common.collect.Sets;
import ianm1647.expandeddelight.ExpandedDelight;
import ianm1647.expandeddelight.common.item.JellyItem;
import ianm1647.expandeddelight.common.item.JuicerItem;
import ianm1647.expandeddelight.common.item.LilypadCropItem;
import java.util.LinkedHashSet;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.neoforged.neoforge.registries.DeferredRegister;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.item.DrinkableItem;
import vectorwing.farmersdelight.common.item.MilkBottleItem;
import vectorwing.farmersdelight.common.registry.ModEffects;

public class EDItems {

    public static final DeferredRegister.Items ITEMS;
    public static LinkedHashSet<Supplier<Item>> CREATIVE_TAB_ITEMS;

    public static Supplier<Item> CINNAMON_SAPLING;
    public static Supplier<Item> CINNAMON_LEAVES;
    public static Supplier<Item> CINNAMON_LOG;
    public static Supplier<Item> CINNAMON_WOOD;
    public static Supplier<Item> CINNAMON_STRIPPED_LOG;
    public static Supplier<Item> CINNAMON_STRIPPED_WOOD;
    public static Supplier<Item> CINNAMON_PLANKS;
    public static Supplier<Item> CINNAMON_STAIRS;
    public static Supplier<Item> CINNAMON_SLAB;
    public static Supplier<Item> CINNAMON_FENCE;
    public static Supplier<Item> CINNAMON_FENCE_GATE;
    public static Supplier<Item> CINNAMON_DOOR;
    public static Supplier<Item> CINNAMON_TRAPDOOR;
    public static Supplier<Item> CINNAMON_PRESSURE_PLATE;
    public static Supplier<Item> CINNAMON_BUTTON;
    public static Supplier<Item> CINNAMON_SIGN;
    public static Supplier<Item> CINNAMON_HANGING_SIGN;
    public static Supplier<Item> CINNAMON_CABINET;
    public static Supplier<Item> CINNAMON_BOAT;
    public static Supplier<Item> CINNAMON_CHEST_BOAT;

    public static Supplier<Item> JUICER;
    public static Supplier<Item> CASK;
    public static Supplier<Item> MILK_CASK;
    public static Supplier<Item> GOAT_MILK_CASK;
    public static Supplier<Item> CHEESE_CASK;
    public static Supplier<Item> GOAT_CHEESE_CASK;

    public static Supplier<Item> ASPARAGUS_CRATE;
    public static Supplier<Item> SWEET_POTATO_CRATE;
    public static Supplier<Item> CHILI_PEPPER_CRATE;
    public static Supplier<Item> CRANBERRY_BAG;

    public static Supplier<Item> WILD_ASPARAGUS;
    public static Supplier<Item> WILD_SWEET_POTATO;
    public static Supplier<Item> WILD_CHILI_PEPPER;
    public static Supplier<Item> WILD_PEANUTS;

    public static Supplier<Item> SALT_ORE;
    public static Supplier<Item> DEEPSLATE_SALT_ORE;

    public static Supplier<Item> CRUSHING_MALLET;
    public static Supplier<Item> GLASS_JAR;

    public static Supplier<Item> CINNAMON_STICK;
    public static Supplier<Item> CINNAMON;
    public static Supplier<Item> SALT_ROCK;
    public static Supplier<Item> SALT;

    public static Supplier<Item> GOAT_MILK_BUCKET;
    public static Supplier<Item> GOAT_MILK_BOTTLE;

    public static Supplier<Item> ASPARAGUS;
    public static Supplier<Item> ASPARAGUS_SEEDS;
    public static Supplier<Item> SWEET_POTATO;
    public static Supplier<Item> BAKED_SWEET_POTATO;
    public static Supplier<Item> CHILI_PEPPER;
    public static Supplier<Item> CHILI_PEPPER_SEEDS;
    public static Supplier<Item> PEANUT;

    public static Supplier<Item> ANCIENT_FRUIT;
    public static Supplier<Item> ANCIENT_SEEDS;
    public static Supplier<Item> CRANBERRIES;
    public static Supplier<Item> CRANBERRY_PLANT;
    public static Supplier<Item> LEMON;
    public static Supplier<Item> LEMON_SEEDS;

    public static Supplier<Item> CHEESE_WHEEL;
    public static Supplier<Item> GOAT_CHEESE_WHEEL;
    public static Supplier<Item> CHEESE_SLICE;
    public static Supplier<Item> GOAT_CHEESE_SLICE;

    public static Supplier<Item> CHEESE_SANDWICH;
    public static Supplier<Item> GRILLED_CHEESE;
    public static Supplier<Item> CRANBERRY_GOAT_CHESE_TOAST;
    public static Supplier<Item> GOAT_CHEESE_SOUFFLE;

    public static Supplier<Item> PEANUT_BUTTER;
    public static Supplier<Item> PEANUT_BUTTER_SANDWICH;
    public static Supplier<Item> PEANUT_BUTTER_HONEY_SANDWICH;
    public static Supplier<Item> APPLE_JELLY_SANDWICH;
    public static Supplier<Item> MELON_JELLY_SANDWICH;
    public static Supplier<Item> SWEET_BERRY_JELLY_SANDWICH;
    public static Supplier<Item> GLOW_BERRY_JELLY_SANDWICH;
    public static Supplier<Item> CRANBERRY_JELLY_SANDWICH;

    public static Supplier<Item> CHOCOLATE_COOKIE;
    public static Supplier<Item> SUGAR_COOKIE;
    public static Supplier<Item> SNICKERDOODLE;

    public static Supplier<Item> CINNAMON_RICE;
    public static Supplier<Item> CINNAMON_APPLES;

    public static Supplier<Item> APPLE_JUICE;
    public static Supplier<Item> SWEET_BERRY_JUICE;
    public static Supplier<Item> GLOW_BERRY_JUICE;
    public static Supplier<Item> CRANBERRY_JUICE;
    public static Supplier<Item> LEMONADE;
    public static Supplier<Item> SWEET_BERRY_LEMONADE;
    public static Supplier<Item> GLOW_BERRY_LEMONADE;
    public static Supplier<Item> CRANBERRY_LEMONADE;

    public static Supplier<Item> APPLE_JELLY;
    public static Supplier<Item> MELON_JELLY;
    public static Supplier<Item> SWEET_BERRY_JELLY;
    public static Supplier<Item> GLOW_BERRY_JELLY;
    public static Supplier<Item> CRANBERRY_JELLY;

    public static Supplier<Item> SWEET_ROLL;
    public static Supplier<Item> BERRY_SWEET_ROLL;
    public static Supplier<Item> GLOW_BERRY_SWEET_ROLL;

    public static Supplier<Item> CRANBERRY_COBBLER;
    public static Supplier<Item> CRANBERRY_COBBLER_SLICE;
    public static Supplier<Item> HONEYED_GOAT_CHEESE_TART;
    public static Supplier<Item> HONEYED_GOAT_CHEESE_TART_SLICE;
    public static Supplier<Item> LEMON_MERINGUE_PIE;
    public static Supplier<Item> LEMON_MERINGUE_PIE_SLICE;

    public static Supplier<Item> PEANUT_SALAD;
    public static Supplier<Item> SWEET_POTATO_SALAD;
    public static Supplier<Item> GOAT_CHEESE_BEETROOT_SALAD;
    public static Supplier<Item> CHILI_CUCUMBER_SALAD;

    public static Supplier<Item> ASPARAGUS_SOUP;
    public static Supplier<Item> ASPARAGUS_SOUP_CREAMY;
    public static Supplier<Item> PEANUT_HONEY_SOUP;
    public static Supplier<Item> CHICKEN_GOAT_CHEESE_SOUP;

    public static Supplier<Item> MAC_AND_CHEESE;
    public static Supplier<Item> ASPARAGUS_BACON_MEAL;
    public static Supplier<Item> ASPARAGUS_MUSHROOM_PASTA;
    public static Supplier<Item> ASPARAGUS_FRITTATA;
    public static Supplier<Item> PEPERONATA;
    public static Supplier<Item> CHILI_PEPPER_SALMON;
    public static Supplier<Item> SWEET_POTATO_CASSEROLE;
    public static Supplier<Item> CRANBERRY_CHICKEN;
    public static Supplier<Item> GOAT_CHEESE_RATATOUILLE;


    static {
        ITEMS = DeferredRegister.createItems(ExpandedDelight.MODID);
        CREATIVE_TAB_ITEMS = Sets.newLinkedHashSet();

        CINNAMON_SAPLING = registerWithTab("cinnamon_sapling", properties -> new BlockItem(EDBlocks.CINNAMON_SAPLING.get(), properties.useBlockDescriptionPrefix()));
        CINNAMON_LEAVES = registerWithTab("cinnamon_leaves", properties -> new BlockItem(EDBlocks.CINNAMON_LEAVES.get(), properties.useBlockDescriptionPrefix()));
        CINNAMON_LOG = registerWithTab("cinnamon_log", properties -> new BlockItem(EDBlocks.CINNAMON_LOG.get(), properties.useBlockDescriptionPrefix()));
        CINNAMON_WOOD = registerWithTab("cinnamon_wood", properties -> new BlockItem(EDBlocks.CINNAMON_WOOD.get(), properties.useBlockDescriptionPrefix()));
        CINNAMON_STRIPPED_LOG = registerWithTab("stripped_cinnamon_log", properties -> new BlockItem(EDBlocks.CINNAMON_STRIPPED_LOG.get(), properties.useBlockDescriptionPrefix()));
        CINNAMON_STRIPPED_WOOD = registerWithTab("stripped_cinnamon_wood", properties -> new BlockItem(EDBlocks.CINNAMON_STRIPPED_WOOD.get(), properties.useBlockDescriptionPrefix()));
        CINNAMON_PLANKS = registerWithTab("cinnamon_planks", properties -> new BlockItem(EDBlocks.CINNAMON_PLANKS.get(), properties.useBlockDescriptionPrefix()));
        CINNAMON_STAIRS = registerWithTab("cinnamon_stairs", properties -> new BlockItem(EDBlocks.CINNAMON_STAIRS.get(), properties.useBlockDescriptionPrefix()));
        CINNAMON_SLAB = registerWithTab("cinnamon_slab", properties -> new BlockItem(EDBlocks.CINNAMON_SLAB.get(), properties.useBlockDescriptionPrefix()));
        CINNAMON_FENCE = registerWithTab("cinnamon_fence", properties -> new BlockItem(EDBlocks.CINNAMON_FENCE.get(), properties.useBlockDescriptionPrefix()));
        CINNAMON_FENCE_GATE = registerWithTab("cinnamon_fence_gate", properties -> new BlockItem(EDBlocks.CINNAMON_FENCE_GATE.get(), properties.useBlockDescriptionPrefix()));
        CINNAMON_DOOR = registerWithTab("cinnamon_door", properties -> new BlockItem(EDBlocks.CINNAMON_DOOR.get(), properties.useBlockDescriptionPrefix()));
        CINNAMON_TRAPDOOR = registerWithTab("cinnamon_trapdoor", properties -> new BlockItem(EDBlocks.CINNAMON_TRAPDOOR.get(), properties.useBlockDescriptionPrefix()));
        CINNAMON_PRESSURE_PLATE = registerWithTab("cinnamon_pressure_plate", properties -> new BlockItem(EDBlocks.CINNAMON_PRESSURE_PLATE.get(), properties.useBlockDescriptionPrefix()));
        CINNAMON_BUTTON = registerWithTab("cinnamon_button", properties -> new BlockItem(EDBlocks.CINNAMON_BUTTON.get(), properties.useBlockDescriptionPrefix()));
        CINNAMON_SIGN = registerWithTab("cinnamon_sign", properties -> new SignItem(EDBlocks.CINNAMON_SIGN.get(), EDBlocks.CINNAMON_WALL_SIGN.get(), properties.useBlockDescriptionPrefix()));
        CINNAMON_HANGING_SIGN = registerWithTab("cinnamon_hanging_sign", properties -> new HangingSignItem(EDBlocks.CINNAMON_CEILING_HANGING_SIGN.get(), EDBlocks.CINNAMON_WALL_HANGING_SIGN.get(), properties.useBlockDescriptionPrefix()));
        CINNAMON_CABINET = registerWithTab("cinnamon_cabinet", properties -> new BlockItem(EDBlocks.CINNAMON_CABINET.get(), properties.useBlockDescriptionPrefix()));
        CINNAMON_BOAT = registerWithTab("cinnamon_boat", properties -> new BoatItem(EDEntityTypes.CINNAMON_BOAT.get(), properties.stacksTo(1)));
        CINNAMON_CHEST_BOAT = registerWithTab("cinnamon_chest_boat", properties -> new BoatItem(EDEntityTypes.CINNAMON_CHEST_BOAT.get(), properties.stacksTo(1)));

        JUICER = registerWithTab("juicer", properties -> new JuicerItem(EDBlocks.JUICER.get(), properties.useBlockDescriptionPrefix()));
        CASK = registerWithTab("cask", properties -> new BlockItem(EDBlocks.CASK.get(), properties.useBlockDescriptionPrefix()));
        MILK_CASK = ITEMS.registerItem("milk_cask", properties -> new BlockItem(EDBlocks.MILK_CASK.get(), properties.useBlockDescriptionPrefix()));
        CHEESE_CASK = ITEMS.registerItem("cheese_cask", properties -> new BlockItem(EDBlocks.CHEESE_CASK.get(), properties.useBlockDescriptionPrefix()));
        GOAT_MILK_CASK = ITEMS.registerItem("goat_milk_cask", properties -> new BlockItem(EDBlocks.GOAT_MILK_CASK.get(), properties.useBlockDescriptionPrefix()));
        GOAT_CHEESE_CASK = ITEMS.registerItem("goat_cheese_cask", properties -> new BlockItem(EDBlocks.GOAT_CHEESE_CASK.get(), properties.useBlockDescriptionPrefix()));

        ASPARAGUS_CRATE = registerWithTab("asparagus_crate", properties -> new BlockItem(EDBlocks.ASPARAGUS_CRATE.get(), properties.useBlockDescriptionPrefix()));
        SWEET_POTATO_CRATE = registerWithTab("sweet_potato_crate", properties -> new BlockItem(EDBlocks.SWEET_POTATO_CRATE.get(), properties.useBlockDescriptionPrefix()));
        CHILI_PEPPER_CRATE = registerWithTab("chili_pepper_crate", properties -> new BlockItem(EDBlocks.CHILI_PEPPER_CRATE.get(), properties.useBlockDescriptionPrefix()));
        CRANBERRY_BAG = registerWithTab("cranberry_bag", properties -> new BlockItem(EDBlocks.CRANBERRY_BAG.get(), properties.useBlockDescriptionPrefix()));

        WILD_ASPARAGUS = registerWithTab("wild_asparagus", properties -> new BlockItem(EDBlocks.WILD_ASPARAGUS.get(), properties.useBlockDescriptionPrefix()));
        WILD_SWEET_POTATO = registerWithTab("wild_sweet_potato", properties -> new BlockItem(EDBlocks.WILD_SWEET_POTATO.get(), properties.useBlockDescriptionPrefix()));
        WILD_CHILI_PEPPER = registerWithTab("wild_chili_pepper", properties -> new BlockItem(EDBlocks.WILD_CHILI_PEPPER.get(), properties.useBlockDescriptionPrefix()));
        WILD_PEANUTS = registerWithTab("wild_peanuts", properties -> new BlockItem(EDBlocks.WILD_PEANUTS.get(), properties.useBlockDescriptionPrefix()));
        CRANBERRY_PLANT = ITEMS.registerItem("cranberry_plant", properties -> new BlockItem(EDBlocks.CRANBERRY_PLANT.get(), properties.useBlockDescriptionPrefix()));

        SALT_ORE = registerWithTab("salt_ore", properties -> new BlockItem(EDBlocks.SALT_ORE.get(), properties.useBlockDescriptionPrefix()));
        DEEPSLATE_SALT_ORE = registerWithTab("deepslate_salt_ore", properties -> new BlockItem(EDBlocks.DEEPSLATE_SALT_ORE.get(), properties.useBlockDescriptionPrefix()));

        CRUSHING_MALLET = registerWithTab("crushing_mallet", properties -> new Item(properties.durability(64).stacksTo(1)));
        GLASS_JAR = registerWithTab("glass_jar", properties -> new Item(properties));

        CINNAMON_STICK = registerWithTab("cinnamon_stick", properties -> new Item(properties));
        CINNAMON = registerWithTab("cinnamon", properties -> new Item(properties));
        SALT_ROCK = registerWithTab("salt_rock", properties -> new Item(properties));
        SALT = registerWithTab("salt", properties -> new Item(properties));

        GOAT_MILK_BUCKET = registerWithTab("goat_milk_bucket", properties -> new Item(properties.craftRemainder(Items.BUCKET).usingConvertsTo(Items.BUCKET).stacksTo(1).component(DataComponents.CONSUMABLE, Consumables.MILK_BUCKET)));
        GOAT_MILK_BOTTLE = registerWithTab("goat_milk_bottle", properties -> new MilkBottleItem(properties.component(DataComponents.CONSUMABLE, Consumables.DEFAULT_DRINK).craftRemainder(Items.GLASS_BOTTLE).usingConvertsTo(Items.GLASS_BOTTLE).stacksTo(16)));

        ASPARAGUS_SEEDS = registerWithTab("asparagus_seeds", properties -> new BlockItem(EDBlocks.ASPARAGUS_CROP.get(), properties.useBlockDescriptionPrefix()));
        ASPARAGUS = registerWithTab("asparagus", properties -> new Item(foodItem(properties, 2, 0.2f)));
        SWEET_POTATO = registerWithTab("sweet_potato", properties -> new BlockItem(EDBlocks.SWEET_POTATO_CROP.get(), foodItem(properties, 2, 0.2f)));
        CHILI_PEPPER_SEEDS = registerWithTab("chili_pepper_seeds", properties -> new BlockItem(EDBlocks.CHILI_PEPPER_CROP.get(), properties.useBlockDescriptionPrefix()));
        CHILI_PEPPER = registerWithTab("chili_pepper", properties -> new Item(foodItem(properties, 2, 0.1f)));
        PEANUT = registerWithTab("peanut", properties -> new BlockItem(EDBlocks.PEANUT_CROP.get(), foodItem(properties, 1, 0f)));
        CRANBERRIES = registerWithTab("cranberries", properties -> new LilypadCropItem(EDBlocks.CRANBERRY_PLANT.get(), foodItem(properties, 2, 0.5f)));

        BAKED_SWEET_POTATO = registerWithTab("baked_sweet_potato", properties -> new Item(foodItem(properties, 6, 0.4f)));

        CHEESE_WHEEL = registerWithTab("cheese_wheel", properties -> new BlockItem(EDBlocks.CHEESE_WHEEL.get(), properties.useBlockDescriptionPrefix()));
        CHEESE_SLICE = registerWithTab("cheese_slice", properties -> new Item(foodItem(properties, 2, 0.2f)));
        GOAT_CHEESE_WHEEL = registerWithTab("goat_cheese_wheel", properties -> new BlockItem(EDBlocks.GOAT_CHEESE_WHEEL.get(), properties.useBlockDescriptionPrefix()));
        GOAT_CHEESE_SLICE = registerWithTab("goat_cheese_slice", properties -> new Item(foodItem(properties, 2, 0.2f)));

        CHEESE_SANDWICH = registerWithTab("cheese_sandwich", properties -> new Item(foodItem(properties, 5, 0.3f)));
        GRILLED_CHEESE = registerWithTab("grilled_cheese", properties -> new Item(foodItem(properties, 8, 0.5f)));
        CRANBERRY_GOAT_CHESE_TOAST = registerWithTab("cranberry_goat_cheese_toast", properties -> new Item(foodItem(properties, 4, 0.6f)));

        PEANUT_BUTTER = registerWithTab("peanut_butter", properties -> new Item(foodItem(properties, 2, 0.3f).craftRemainder(Items.BOWL).usingConvertsTo(Items.BOWL)));
        PEANUT_BUTTER_SANDWICH = registerWithTab("peanut_butter_sandwich", properties -> new Item(foodItem(properties, 6, 0.4f)));
        PEANUT_BUTTER_HONEY_SANDWICH = registerWithTab("peanut_butter_honey_sandwich", properties -> new Item(foodItem(properties, 7, 0.5f)));
        SWEET_BERRY_JELLY_SANDWICH = registerWithTab("sweet_berry_jelly_sandwich", properties -> new Item(foodItem(properties, 7, 0.5f)));
        GLOW_BERRY_JELLY_SANDWICH = registerWithTab("glow_berry_jelly_sandwich", properties -> new Item(foodItem(properties, 7, 0.5f)));
        CRANBERRY_JELLY_SANDWICH = registerWithTab("cranberry_jelly_sandwich", properties -> new Item(foodItem(properties, 7, 0.6f)));

        SWEET_ROLL = registerWithTab("sweet_roll", properties -> new Item(foodItem(properties, 5, 0.3f)));
        BERRY_SWEET_ROLL = registerWithTab("berry_sweet_roll", properties -> new Item(foodItem(properties, 6, 0.5f)));
        GLOW_BERRY_SWEET_ROLL = registerWithTab("glow_berry_sweet_roll", properties -> new Item(foodItem(properties, 6, 0.5f)));

        CRANBERRY_COBBLER = registerWithTab("cranberry_cobbler", properties -> new BlockItem(EDBlocks.CRANBERRY_COBBLER.get(), properties.useBlockDescriptionPrefix()));
        CRANBERRY_COBBLER_SLICE = registerWithTab("cranberry_cobbler_slice", properties -> new Item(pieSliceItem(properties, 3, 0.3f)));
        HONEYED_GOAT_CHEESE_TART = registerWithTab("honeyed_goat_cheese_tart", properties -> new BlockItem(EDBlocks.HONEYED_GOAT_CHEESE_TART.get(), properties.useBlockDescriptionPrefix()));
        HONEYED_GOAT_CHEESE_TART_SLICE = registerWithTab("honeyed_goat_cheese_tart_slice", properties -> new Item(pieSliceItem(properties, 4, 0.5f)));

        CHOCOLATE_COOKIE = registerWithTab("chocolate_cookie", properties -> new Item(foodItem(properties, 2, 0.3f)));
        SUGAR_COOKIE = registerWithTab("sugar_cookie", properties -> new Item(foodItem(properties, 2, 0.3f)));
        SNICKERDOODLE = registerWithTab("snickerdoodle", properties -> new Item(foodItem(properties, 2, 0.4f)));

        CINNAMON_RICE = registerWithTab("cinnamon_rice", properties -> new Item(foodItem(properties, 7, 0.5f).craftRemainder(Items.BOWL).usingConvertsTo(Items.BOWL)));
        CINNAMON_APPLES = registerWithTab("cinnamon_apples", properties -> new Item(foodItem(properties, 7, 0.5f).craftRemainder(Items.BOWL).usingConvertsTo(Items.BOWL)));

        APPLE_JUICE = registerWithTab("apple_juice", properties -> new DrinkableItem(drinkItem(properties, 1, 1.2f, MobEffects.SPEED), true));
        SWEET_BERRY_JUICE = registerWithTab("sweet_berry_juice", properties -> new DrinkableItem(drinkItem(properties, 1, 1.2f, MobEffects.HEALTH_BOOST), true));
        GLOW_BERRY_JUICE = registerWithTab("glow_berry_juice", properties -> new DrinkableItem(drinkItem(properties, 1, 1.2f, MobEffects.NIGHT_VISION), true));
        CRANBERRY_JUICE = registerWithTab("cranberry_juice", properties -> new DrinkableItem(drinkItem(properties, 1, 1.2f, MobEffects.ABSORPTION), true));

        SWEET_BERRY_JELLY = registerWithTab("sweet_berry_jelly", properties -> new JellyItem(jellyItem(properties, 3, 0.6f, MobEffects.HEALTH_BOOST), true));
        GLOW_BERRY_JELLY = registerWithTab("glow_berry_jelly", properties -> new JellyItem(jellyItem(properties, 3, 0.6f, MobEffects.NIGHT_VISION), true));
        CRANBERRY_JELLY = registerWithTab("cranberry_jelly", properties -> new JellyItem(jellyItem(properties, 3, 0.6f, MobEffects.ABSORPTION), true));

        PEANUT_SALAD = registerWithTab("peanut_salad", properties -> new ConsumableItem(saladItem(properties, 8, 0.6f)));
        SWEET_POTATO_SALAD = registerWithTab("sweet_potato_salad", properties -> new ConsumableItem(saladItem(properties, 8, 0.4f)));
        GOAT_CHEESE_BEETROOT_SALAD = registerWithTab("goat_cheese_beetroot_salad", properties -> new ConsumableItem(saladItem(properties, 7, 0.3f)));

        ASPARAGUS_SOUP = registerWithTab("asparagus_soup", properties -> new ConsumableItem(stewItem(properties, 10, 0.8f)));
        ASPARAGUS_SOUP_CREAMY = registerWithTab("asparagus_soup_creamy", properties -> new ConsumableItem(stewItem(properties, 11, 0.9f)));
        PEANUT_HONEY_SOUP = registerWithTab("peanut_honey_soup", properties -> new ConsumableItem(stewItem(properties, 10, 0.8f)));
        MAC_AND_CHEESE = registerWithTab("mac_and_cheese", properties -> new ConsumableItem(stewItem(properties, 12, 0.8f)));

        ASPARAGUS_BACON_MEAL = registerWithTab("asparagus_and_bacon_cheesy", properties -> new ConsumableItem(mealItem(properties, 10, 0.8f)));
        ASPARAGUS_MUSHROOM_PASTA = registerWithTab("asparagus_mushroom_pasta", properties -> new ConsumableItem(mealItem(properties, 12, 0.9f)));
        ASPARAGUS_FRITTATA = registerWithTab("asparagus_frittata", properties -> new ConsumableItem(mealItem(properties, 11, 0.8f)));
        PEPERONATA = registerWithTab("peperonata", properties -> new ConsumableItem(mealItem(properties, 12, 0.9f)));
        CHILI_PEPPER_SALMON = registerWithTab("chili_pepper_salmon", properties -> new ConsumableItem(mealItem(properties, 13, 1.0f)));
        CRANBERRY_CHICKEN = registerWithTab("cranberry_chicken", properties -> new ConsumableItem(mealItem(properties, 10, 0.8f)));
        SWEET_POTATO_CASSEROLE = registerWithTab("sweet_potato_casserole", properties -> new ConsumableItem(mealItem(properties, 11, 0.8f)));

    }

    public static Supplier<Item> registerWithTab(String name, Function<Item.Properties, Item> factory) {
        Supplier<Item> block = ITEMS.registerItem(name, factory);
        CREATIVE_TAB_ITEMS.add(block);
        return block;
    }

    public static Item.Properties foodItem(Item.Properties properties, int nutrition, float saturation) {
        return properties.food(new FoodProperties.Builder().nutrition(nutrition).saturationModifier(saturation).build());
    }

    public static Item.Properties drinkItem(Item.Properties properties, int nutrition, float saturation, Holder<MobEffect> effect) {
        return properties.craftRemainder(Items.GLASS_BOTTLE).usingConvertsTo(Items.GLASS_BOTTLE).stacksTo(16)
                .food(new FoodProperties.Builder().nutrition(nutrition).saturationModifier(saturation)
                .build())
                .component(DataComponents.CONSUMABLE, Consumables.defaultDrink()
                        .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(effect, 200, 0), 1.0f))
                        .build());
    }

    public static Item.Properties jellyItem(Item.Properties properties, int nutrition, float saturation, Holder<MobEffect> effect) {
        return properties.craftRemainder(GLASS_JAR.get()).usingConvertsTo(GLASS_JAR.get()).stacksTo(16)
                .food(new FoodProperties.Builder().nutrition(nutrition).saturationModifier(saturation)
                        .build())
                .component(DataComponents.CONSUMABLE, Consumables.defaultFood()
                        .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(effect, 400, 0), 1.0f))
                        .build());
    }

    public static Item.Properties pieSliceItem(Item.Properties properties, int nutrition, float saturation) {
        return properties.food(new FoodProperties.Builder().nutrition(nutrition).saturationModifier(saturation)
                        .build())
                .component(DataComponents.CONSUMABLE, Consumables.defaultFood().consumeSeconds(0.8F)
                        .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.SPEED, 600, 0), 1.0f))
                        .build());
    }

    public static Item.Properties stewItem(Item.Properties properties, int nutrition, float saturation) {
        return properties.craftRemainder(Items.BOWL).usingConvertsTo(Items.BOWL).stacksTo(16)
                .food(new FoodProperties.Builder().nutrition(nutrition).saturationModifier(saturation)
                        .build())
                .component(DataComponents.CONSUMABLE, Consumables.defaultFood()
                        .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(ModEffects.COMFORT, 2400, 0), 1.0f))
                        .build());
    }

    public static Item.Properties saladItem(Item.Properties properties, int nutrition, float saturation) {
        return properties.craftRemainder(Items.BOWL).usingConvertsTo(Items.BOWL).stacksTo(16)
                .food(new FoodProperties.Builder().nutrition(nutrition).saturationModifier(saturation)
                .build())
                .component(DataComponents.CONSUMABLE, Consumables.defaultFood()
                        .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 0), 1.0f))
                        .build());
    }

    public static Item.Properties mealItem(Item.Properties properties, int nutrition, float saturation) {
        return properties.craftRemainder(Items.BOWL).usingConvertsTo(Items.BOWL).stacksTo(16)
                .food(new FoodProperties.Builder().nutrition(nutrition).saturationModifier(saturation)
                        .build())
                .component(DataComponents.CONSUMABLE, Consumables.defaultFood()
                        .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(ModEffects.NOURISHMENT, 3600, 0), 1.0f))
                        .build());
    }

}
