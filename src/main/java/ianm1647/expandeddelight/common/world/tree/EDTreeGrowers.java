package ianm1647.expandeddelight.common.world.tree;

import ianm1647.expandeddelight.ExpandedDelight;
import ianm1647.expandeddelight.common.world.EDConfiguredFeatures;
import java.util.Optional;
import net.minecraft.world.level.block.grower.TreeGrower;

public class EDTreeGrowers {

    public static final TreeGrower CINNAMON;

    static {
        CINNAMON = new TreeGrower(ExpandedDelight.MODID + ":cinnamon",
                Optional.empty(), Optional.of(EDConfiguredFeatures.CINNAMON_TREE), Optional.empty());
    }
}
