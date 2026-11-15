package ianm1647.expandeddelight;

import ianm1647.expandeddelight.common.block.entity.JuicerBlockEntity;
import ianm1647.expandeddelight.common.registry.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.function.Consumer;

/** Development-only checks against a real game server and its loaded datapacks. */
@EventBusSubscriber(modid = ExpandedDelight.MODID)
public final class PortGameTests {
    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> {
            registry.register(ExpandedDelight.loc("resources"), PortGameTests::resources);
            registry.register(ExpandedDelight.loc("juicer"), PortGameTests::juicer);
            registry.register(ExpandedDelight.loc("boats"), PortGameTests::boats);
        });
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(ExpandedDelight.loc("port"));
        for (String name : new String[]{"resources", "juicer", "boats"}) {
            ResourceKey<Consumer<GameTestHelper>> function = ResourceKey.create(Registries.TEST_FUNCTION, ExpandedDelight.loc(name));
            event.registerTest(ExpandedDelight.loc(name), new FunctionGameTestInstance(function,
                    new TestData<>(environment, ExpandedDelight.loc("empty"), 100, 0, true)));
        }
    }

    private static void resources(GameTestHelper helper) {
        var level = helper.getLevel();
        helper.assertTrue(level.recipeAccess().byKey(ResourceKey.create(Registries.RECIPE,
                ExpandedDelight.loc("juicing/apple_juice"))).isPresent(), "Apple juice recipe must load");
        helper.assertTrue(level.registryAccess().lookupOrThrow(Registries.CONFIGURED_FEATURE)
                .get(ExpandedDelight.loc("cinnamon_tree")).isPresent(), "Cinnamon tree must load");
        helper.assertTrue(level.registryAccess().lookupOrThrow(Registries.VILLAGER_TRADE)
                .get(ExpandedDelight.loc("farmer/level_1/buy_asparagus")).isPresent(), "Farmer trade must load");
        helper.assertTrue(new ItemStack(EDItems.APPLE_JUICE.get()).has(net.minecraft.core.component.DataComponents.CONSUMABLE),
                "Juice must remain drinkable");
        helper.assertTrue(new ItemStack(EDItems.GOAT_MILK_BOTTLE.get()).has(net.minecraft.core.component.DataComponents.CONSUMABLE),
                "Goat milk must remain drinkable");
        if (Boolean.getBoolean("expandeddelight.testKubeJS")) {
            helper.assertTrue(level.recipeAccess().byKey(ResourceKey.create(Registries.RECIPE,
                    ExpandedDelight.loc("port_smoke_kubejs"))).isPresent(), "KubeJS juicing schema must create a recipe");
        }
        helper.succeed();
    }

    private static void juicer(GameTestHelper helper) {
        var level = helper.getLevel();
        BlockPos pos = helper.absolutePos(new BlockPos(1, 1, 1));
        var state = EDBlocks.JUICER.get().defaultBlockState();
        level.setBlock(pos, state, 3);
        var juicer = (JuicerBlockEntity) level.getBlockEntity(pos);
        juicer.getInventory().setStackInSlot(0, new ItemStack(Items.APPLE));
        juicer.getInventory().setStackInSlot(1, new ItemStack(Items.SUGAR));
        juicer.getInventory().setStackInSlot(3, new ItemStack(Items.GLASS_BOTTLE));
        for (int i = 0; i < 100; i++) JuicerBlockEntity.juicingTick(level, pos, state, juicer);
        var restored = (JuicerBlockEntity) BlockEntity.loadStatic(pos, state,
                juicer.saveWithFullMetadata(level.registryAccess()), level.registryAccess());
        helper.assertTrue(restored != null, "Juicer must deserialize");
        helper.assertValueEqual(restored.juiceTime, 100, "Saved juicing progress");
        helper.assertTrue(restored.getInventory().getStackInSlot(0).is(Items.APPLE), "Inputs must survive saving");
        restored.setLevel(level);
        for (int i = 0; i < 100; i++) JuicerBlockEntity.juicingTick(level, pos, state, restored);
        helper.assertTrue(restored.getInventory().getStackInSlot(4).is(EDItems.APPLE_JUICE.get()),
                "Juicing must finish after restoring progress");
        helper.assertTrue(restored.getInventory().getStackInSlot(3).isEmpty(), "Juicing must consume one bottle");
        helper.succeed();
    }

    private static void boats(GameTestHelper helper) {
        helper.assertTrue(EDEntityTypes.CINNAMON_BOAT.get().create(helper.getLevel(), EntitySpawnReason.COMMAND) != null,
                "Cinnamon boat must instantiate");
        helper.assertTrue(EDEntityTypes.CINNAMON_CHEST_BOAT.get().create(helper.getLevel(), EntitySpawnReason.COMMAND) != null,
                "Cinnamon chest boat must instantiate");
        helper.succeed();
    }
}
