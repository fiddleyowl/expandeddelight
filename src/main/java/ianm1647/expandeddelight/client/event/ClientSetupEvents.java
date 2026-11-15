package ianm1647.expandeddelight.client.event;

import ianm1647.expandeddelight.client.gui.JuicerScreen;
import ianm1647.expandeddelight.client.gui.JuicerTooltip;
import ianm1647.expandeddelight.client.recipebook.RecipeCategories;
import ianm1647.expandeddelight.client.renderer.CinnamonBoatRenderer;
import ianm1647.expandeddelight.common.registry.EDBlockEntityTypes;
import ianm1647.expandeddelight.common.registry.EDEntityTypes;
import ianm1647.expandeddelight.common.registry.EDMenuTypes;
import net.minecraft.client.model.object.boat.BoatModel;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.StandingSignRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.event.RegisterRecipeBookSearchCategoriesEvent;


@EventBusSubscriber(
        modid = "expandeddelight",
        value = {Dist.CLIENT}
)
public class ClientSetupEvents {
    public ClientSetupEvents() {
    }

    @SubscribeEvent
    public static void registerRecipeBookCategories(RegisterRecipeBookSearchCategoriesEvent event) {
        RecipeCategories.init(event);
    }

    @SubscribeEvent
    public static void registerCustomTooltipRenderers(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(JuicerTooltip.JuicerTooltipComponent.class, JuicerTooltip::new);
    }

    @SubscribeEvent
    public static void registerGuiLayers(RegisterGuiLayersEvent event) {
    }

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EDEntityTypes.CINNAMON_BOAT.get(), context -> new CinnamonBoatRenderer(context, false));
        event.registerEntityRenderer(EDEntityTypes.CINNAMON_CHEST_BOAT.get(), context -> new CinnamonBoatRenderer(context, true));

        event.registerBlockEntityRenderer(EDBlockEntityTypes.CINNAMON_SIGN.get(), context -> new StandingSignRenderer(context));
        event.registerBlockEntityRenderer(EDBlockEntityTypes.CINNAMON_HANGING_SIGN.get(), context -> new HangingSignRenderer(context));
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(CinnamonBoatRenderer.BOAT, BoatModel::createBoatModel);
        event.registerLayerDefinition(CinnamonBoatRenderer.CHEST_BOAT, BoatModel::createChestBoatModel);
    }

    @SubscribeEvent
    public static void registerMenuScreens(RegisterMenuScreensEvent event) {
        event.register(EDMenuTypes.JUICER.get(), JuicerScreen::new);
    }

    @SubscribeEvent(
            priority = EventPriority.LOWEST
    )
    public static void registerParticles(RegisterParticleProvidersEvent event) {

    }
}
