package ianm1647.expandeddelight.common.entity;

import ianm1647.expandeddelight.common.registry.EDItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.boat.ChestBoat;
import net.minecraft.world.level.Level;

public class CinnamonChestBoat extends ChestBoat {
    public CinnamonChestBoat(EntityType<? extends CinnamonChestBoat> type, Level level) {
        super(type, level, EDItems.CINNAMON_CHEST_BOAT);
    }
}
