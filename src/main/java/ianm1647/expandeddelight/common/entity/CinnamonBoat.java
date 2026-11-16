package ianm1647.expandeddelight.common.entity;

import ianm1647.expandeddelight.common.registry.EDItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.boat.Boat;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueOutput;

public class CinnamonBoat extends Boat {
    public CinnamonBoat(EntityType<? extends CinnamonBoat> type, Level level) {
        super(type, level, EDItems.CINNAMON_BOAT);
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putString("Type", "cinnamon");
    }
}
