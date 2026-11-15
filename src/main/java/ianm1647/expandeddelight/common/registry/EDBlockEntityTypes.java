package ianm1647.expandeddelight.common.registry;

import ianm1647.expandeddelight.ExpandedDelight;
import ianm1647.expandeddelight.common.block.entity.CinnamonCabinetBlockEntity;
import ianm1647.expandeddelight.common.block.entity.CinnamonHangingSignBlockEntity;
import ianm1647.expandeddelight.common.block.entity.CinnamonSignBlockEntity;
import ianm1647.expandeddelight.common.block.entity.JuicerBlockEntity;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

public class EDBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> TILES;

    public static final Supplier<BlockEntityType<CinnamonSignBlockEntity>> CINNAMON_SIGN;
    public static final Supplier<BlockEntityType<CinnamonHangingSignBlockEntity>> CINNAMON_HANGING_SIGN;
    public static final Supplier<BlockEntityType<CinnamonCabinetBlockEntity>> CINNAMON_CABINET;

    public static final Supplier<BlockEntityType<JuicerBlockEntity>> JUICER;

    public EDBlockEntityTypes() {
    }

    static {
        TILES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, ExpandedDelight.MODID);

        CINNAMON_SIGN = TILES.register("cinnamon_sign",
                () -> new BlockEntityType<>(CinnamonSignBlockEntity::new, new Block[]{EDBlocks.CINNAMON_SIGN.get(), EDBlocks.CINNAMON_WALL_SIGN.get()}));
        CINNAMON_HANGING_SIGN = TILES.register("cinnamon_hanging_sign",
                () -> new BlockEntityType<>(CinnamonHangingSignBlockEntity::new, new Block[]{EDBlocks.CINNAMON_CEILING_HANGING_SIGN.get(), EDBlocks.CINNAMON_WALL_HANGING_SIGN.get()}));
        CINNAMON_CABINET = TILES.register("cinnamon_cabinet",
                () -> new BlockEntityType<>(CinnamonCabinetBlockEntity::new, new Block[]{EDBlocks.CINNAMON_CABINET.get()}));

        JUICER = TILES.register("juicer",
                () -> new BlockEntityType<>(JuicerBlockEntity::new, new Block[]{EDBlocks.JUICER.get()}));
    }
}
