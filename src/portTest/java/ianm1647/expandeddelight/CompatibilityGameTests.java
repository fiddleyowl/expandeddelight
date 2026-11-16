package ianm1647.expandeddelight;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ianm1647.expandeddelight.common.block.entity.JuicerBlockEntity;
import ianm1647.expandeddelight.common.registry.EDBlocks;
import ianm1647.expandeddelight.common.registry.EDEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.storage.TagValueOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/** External identifiers captured from the original 1.21.1 source, not the port. */
@EventBusSubscriber(modid = ExpandedDelight.MODID)
public final class CompatibilityGameTests {
    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> {
            registry.register(ExpandedDelight.loc("original_identifiers"), CompatibilityGameTests::identifiers);
            registry.register(ExpandedDelight.loc("original_resources"), CompatibilityGameTests::resources);
            registry.register(ExpandedDelight.loc("legacy_names"), CompatibilityGameTests::legacyNames);
        });
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(ExpandedDelight.loc("compatibility"));
        for (String name : new String[]{"original_identifiers", "original_resources", "legacy_names"}) {
            ResourceKey<Consumer<GameTestHelper>> function = ResourceKey.create(Registries.TEST_FUNCTION, ExpandedDelight.loc(name));
            event.registerTest(ExpandedDelight.loc(name), new FunctionGameTestInstance(function,
                    new TestData<>(environment, ExpandedDelight.loc("empty"), 100, 0, true)));
        }
    }

    private static JsonObject readJson(String resource) {
        var input = CompatibilityGameTests.class.getClassLoader().getResourceAsStream(resource);
        if (input == null) throw new IllegalStateException("Missing compatibility resource: " + resource);
        try (var reader = new InputStreamReader(input, StandardCharsets.UTF_8)) {
            return JsonParser.parseReader(reader).getAsJsonObject();
        } catch (java.io.IOException exception) {
            throw new IllegalStateException(exception);
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void identifiers(GameTestHelper helper) {
        var baseline = readJson("compatibility/original-1.21.1.json");
        helper.assertTrue(ExpandedDelight.MODID.equals(baseline.get("mod_id").getAsString()), "Mod namespace changed");
        for (var entry : baseline.getAsJsonObject("registries").entrySet()) {
            ResourceKey<Registry<Object>> key = ResourceKey.createRegistryKey(Identifier.parse(entry.getKey()));
            var registry = helper.getLevel().registryAccess().lookupOrThrow(key);
            Set<String> expected = new HashSet<>();
            entry.getValue().getAsJsonArray().forEach(value -> expected.add(value.getAsString()));
            Set<String> actual = registry.keySet().stream().filter(id -> id.getNamespace().equals(ExpandedDelight.MODID))
                    .map(Identifier::toString).collect(Collectors.toSet());
            helper.assertTrue(actual.equals(expected), "Registry names differ from upstream: " + entry.getKey());
        }
        for (var entry : baseline.getAsJsonObject("item_translation_keys").entrySet()) {
            var item = BuiltInRegistries.ITEM.getOptional(Identifier.parse(entry.getKey())).orElseThrow();
            helper.assertTrue(item.getDescriptionId().equals(entry.getValue().getAsString()),
                    "Item translation changed: " + entry.getKey() + " -> " + item.getDescriptionId());
        }
        for (var entry : baseline.getAsJsonObject("block_state_properties").entrySet()) {
            var block = BuiltInRegistries.BLOCK.getOptional(Identifier.parse(entry.getKey())).orElseThrow();
            for (var expectedProperty : entry.getValue().getAsJsonObject().entrySet()) {
                Property property = block.getStateDefinition().getProperty(expectedProperty.getKey());
                helper.assertTrue(property != null, "Missing block-state property: " + entry.getKey() + "/" + expectedProperty.getKey());
                Set<String> values = (Set<String>) property.getPossibleValues().stream()
                        .map(value -> property.getName((Comparable) value)).collect(Collectors.toSet());
                for (var value : expectedProperty.getValue().getAsJsonArray()) {
                    helper.assertTrue(values.contains(value.getAsString()), "Changed block-state value: " + entry.getKey() + "/" + value);
                }
            }
        }
        for (var recipe : baseline.getAsJsonArray("recipe_ids")) {
            helper.assertTrue(helper.getLevel().recipeAccess().byKey(ResourceKey.create(Registries.RECIPE,
                    Identifier.parse(recipe.getAsString()))).isPresent(), "Missing original recipe: " + recipe);
        }
        helper.assertTrue(RecipeBookType.valueOf(baseline.get("recipe_book_type").getAsString()) != null, "Recipe book type changed");
        helper.succeed();
    }

    private static void resources(GameTestHelper helper) {
        var baseline = readJson("compatibility/original-1.21.1.json");
        for (var resource : baseline.getAsJsonArray("resource_paths")) {
            String path = resource.getAsString();
            // NeoForge 26.1 discovers individual loot modifiers without the old index.
            if (path.startsWith(".cache/") || path.equals("data/neoforge/loot_modifiers/global_loot_modifiers.json")) continue;
            helper.assertTrue(CompatibilityGameTests.class.getClassLoader().getResource(path) != null,
                    "Original resource path missing: " + path);
        }
        var language = readJson("assets/expandeddelight/lang/en_us.json");
        for (var entry : baseline.getAsJsonObject("language_values").entrySet()) {
            helper.assertTrue(entry.getValue().equals(language.get(entry.getKey())), "Original translation changed: " + entry.getKey());
        }
        helper.succeed();
    }

    private static void legacyNames(GameTestHelper helper) {
        var level = helper.getLevel();
        var original = new CompoundTag();
        original.putString("id", "expandeddelight:juicer");
        original.putString("CustomName", "{\"text\":\"Legacy Juicer\",\"italic\":true}");
        original.putInt("JuiceTime", 15);
        original.putInt("JuiceTimeTotal", 200);
        var stack = new CompoundTag();
        stack.putString("id", "minecraft:apple");
        stack.putInt("count", 2);
        stack.putInt("Slot", 0);
        var items = new ListTag();
        items.add(stack);
        var inventory = new CompoundTag();
        inventory.putInt("Size", 9);
        inventory.put("Items", items);
        original.put("Inventory", inventory);
        var pos = helper.absolutePos(new BlockPos(1, 1, 1));
        var juicer = (JuicerBlockEntity) BlockEntity.loadStatic(pos, EDBlocks.JUICER.get().defaultBlockState(), original, level.registryAccess());
        helper.assertTrue(juicer != null && juicer.getName().getString().equals("Legacy Juicer")
                && juicer.getName().getStyle().isItalic(), "Original JSON CustomName no longer decodes");
        helper.assertTrue(juicer.juiceTime == 15 && juicer.getInventory().getStackInSlot(0).is(Items.APPLE)
                && juicer.getInventory().getStackInSlot(0).getCount() == 2, "Original juicer field names no longer decode");
        var current = juicer.saveWithFullMetadata(level.registryAccess());
        var restored = (JuicerBlockEntity) BlockEntity.loadStatic(pos, EDBlocks.JUICER.get().defaultBlockState(), current, level.registryAccess());
        helper.assertTrue(restored.getName().equals(juicer.getName()), "Styled custom name changed after save/reload");
        var structured = new CompoundTag();
        structured.putString("text", "Structured Juicer");
        structured.putBoolean("italic", true);
        current.put("CustomName", structured);
        restored = (JuicerBlockEntity) BlockEntity.loadStatic(pos, EDBlocks.JUICER.get().defaultBlockState(), current, level.registryAccess());
        helper.assertTrue(restored.getName().getString().equals("Structured Juicer") && restored.getName().getStyle().isItalic(),
                "Early port structured custom name changed");
        current.putString("CustomName", "New Juicer");
        var plain = (JuicerBlockEntity) BlockEntity.loadStatic(pos, EDBlocks.JUICER.get().defaultBlockState(), current, level.registryAccess());
        helper.assertTrue(plain.getName().getString().equals("New Juicer"), "Current plain custom name changed");
        current.put("CustomName", net.minecraft.network.chat.ComponentSerialization.CODEC.encodeStart(
                level.registryAccess().createSerializationContext(net.minecraft.nbt.NbtOps.INSTANCE),
                Component.literal("{\"text\":\"Literal JSON\"}").withStyle(net.minecraft.ChatFormatting.ITALIC)).getOrThrow());
        var literal = (JuicerBlockEntity) BlockEntity.loadStatic(pos, EDBlocks.JUICER.get().defaultBlockState(), current, level.registryAccess());
        var literalReloaded = (JuicerBlockEntity) BlockEntity.loadStatic(pos, EDBlocks.JUICER.get().defaultBlockState(),
                literal.saveWithFullMetadata(level.registryAccess()), level.registryAccess());
        helper.assertTrue(literalReloaded.getName().equals(literal.getName()), "JSON-looking literal name changed after save/reload");
        var out = TagValueOutput.createWithContext(ProblemReporter.DISCARDING, level.registryAccess());
        var boat = EDEntityTypes.CINNAMON_BOAT.get().create(level, EntitySpawnReason.COMMAND);
        boat.saveWithoutId(out);
        helper.assertTrue(out.buildResult().getStringOr("Type", "").equals("cinnamon"), "Original boat Type tag missing");
        var chestOut = TagValueOutput.createWithContext(ProblemReporter.DISCARDING, level.registryAccess());
        var chestBoat = EDEntityTypes.CINNAMON_CHEST_BOAT.get().create(level, EntitySpawnReason.COMMAND);
        chestBoat.saveWithoutId(chestOut);
        helper.assertTrue(chestOut.buildResult().getStringOr("Type", "").equals("cinnamon"), "Original chest boat Type tag missing");
        helper.succeed();
    }
}
