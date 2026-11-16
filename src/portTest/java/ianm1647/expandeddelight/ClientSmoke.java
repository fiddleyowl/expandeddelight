package ianm1647.expandeddelight;

import ianm1647.expandeddelight.client.gui.JuicerScreen;
import ianm1647.expandeddelight.client.gui.JuicerRecipeBookComponent;
import ianm1647.expandeddelight.common.block.entity.JuicerBlockEntity;
import ianm1647.expandeddelight.common.block.entity.container.JuicerMenu;
import ianm1647.expandeddelight.common.registry.EDBlocks;
import ianm1647.expandeddelight.common.registry.EDItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Screenshot;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

/** Optional visual smoke check in a disposable copy of the GameTest world. */
@EventBusSubscriber(modid = ExpandedDelight.MODID, value = Dist.CLIENT)
public final class ClientSmoke {
    private static int ticks;
    private static net.minecraft.core.BlockPos fixture;

    @SubscribeEvent
    public static void tick(ClientTickEvent.Post event) {
        if (!Boolean.getBoolean("expandeddelight.clientSmoke")) return;
        var minecraft = Minecraft.getInstance();
        var server = minecraft.getSingleplayerServer();
        if (minecraft.player == null || server == null) {
            if (ticks > 0) throw new IllegalStateException("Smoke-test client disconnected");
            return;
        }

        if (++ticks == 40) {
            var bottle = minecraft.getAtlasManager().getAtlasOrThrow(net.minecraft.data.AtlasIds.GUI)
                    .getSprite(JuicerMenu.EMPTY_CONTAINER_SLOT_BOTTLE);
            if (!bottle.contents().name().equals(JuicerMenu.EMPTY_CONTAINER_SLOT_BOTTLE)) {
                throw new IllegalStateException("Original bottle-slot sprite failed to load");
            }
            ExpandedDelight.LOGGER.info("PORT_CLIENT_SMOKE: original bottle-slot sprite loaded");
            var uuid = minecraft.player.getUUID();
            server.execute(() -> {
                var player = server.getPlayerList().getPlayer(uuid);
                if (player == null) throw new IllegalStateException("Missing smoke-test player");
                var level = player.level();
                var pos = player.blockPosition().offset(1, 0, 1);
                level.setBlock(pos, EDBlocks.JUICER.get().defaultBlockState(), 3);
                var juicer = (JuicerBlockEntity) level.getBlockEntity(pos);
                juicer.getInventory().setStackInSlot(0, new ItemStack(Items.APPLE, 8));
                juicer.getInventory().setStackInSlot(1, new ItemStack(Items.SUGAR, 8));
                juicer.getInventory().setStackInSlot(3, new ItemStack(Items.GLASS_BOTTLE, 8));
                player.getInventory().add(new ItemStack(EDItems.GOAT_MILK_BUCKET.get()));
                player.awardRecipes(level.recipeAccess().getRecipes());
                fixture = pos;
            });
        }
        if (ticks == 80) {
            var uuid = minecraft.player.getUUID();
            server.execute(() -> {
                var player = server.getPlayerList().getPlayer(uuid);
                player.openMenu((JuicerBlockEntity) player.level().getBlockEntity(fixture), fixture);
            });
        }
        if (ticks == 140 && minecraft.screen instanceof JuicerScreen screen) {
            var book = screen.children().stream().filter(JuicerRecipeBookComponent.class::isInstance)
                    .map(JuicerRecipeBookComponent.class::cast).findFirst().orElseThrow();
            if (!book.isVisible()) {
                screen.children().stream().filter(ImageButton.class::isInstance).map(ImageButton.class::cast)
                        .findFirst().orElseThrow().onPress(new KeyEvent(257, 0, 0));
            }
        }
        if (ticks == 180) {
            if (!(minecraft.screen instanceof JuicerScreen screen)) {
                throw new IllegalStateException("Juicer screen failed to remain open");
            }
            if (screen.children().stream().filter(JuicerRecipeBookComponent.class::isInstance)
                    .map(JuicerRecipeBookComponent.class::cast).noneMatch(JuicerRecipeBookComponent::isVisible)) {
                throw new IllegalStateException("Juicer recipe book failed to open");
            }
            Screenshot.grab(minecraft.gameDirectory, "expanded-delight-port-smoke.png",
                    minecraft.getMainRenderTarget(), 1,
                    message -> ExpandedDelight.LOGGER.info("PORT_CLIENT_SMOKE: {}", message.getString()));
        }
        if (ticks == 220) {
            ExpandedDelight.LOGGER.info("PORT_CLIENT_SMOKE: juicer screen and recipe book rendered successfully");
            minecraft.stop();
        }
    }
}
