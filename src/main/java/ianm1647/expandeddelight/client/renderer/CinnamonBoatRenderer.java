package ianm1647.expandeddelight.client.renderer;

import ianm1647.expandeddelight.ExpandedDelight;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class CinnamonBoatRenderer extends BoatRenderer {
    public static final ModelLayerLocation BOAT = new ModelLayerLocation(ExpandedDelight.loc("boat/cinnamon"), "main");
    public static final ModelLayerLocation CHEST_BOAT = new ModelLayerLocation(ExpandedDelight.loc("chest_boat/cinnamon"), "main");

    public CinnamonBoatRenderer(EntityRendererProvider.Context context, boolean chest) {
        super(context, chest ? CHEST_BOAT : BOAT);
    }
}
