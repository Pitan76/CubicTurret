package net.pitan76.cubicturret.client;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.pitan76.cubicturret.block.Blocks;
import net.pitan76.cubicturret.client.screen.CubicTurretScreen;
import net.pitan76.cubicturret.entity.Entities;
import net.pitan76.cubicturret.screen.ScreenHandlers;
import net.pitan76.mcpitanlib.api.client.registry.CompatRegistryClient;

public class CubicTurretClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        //CompatRegistryClient.registerRenderTypeBlock(RenderLayer.getCutout(), Blocks.TURRET_BASE.getOrNull());
        CompatRegistryClient.registerRenderTypeBlock(RenderLayer.getCutout(), Blocks.CUBIC_TURRET_BLOCK.getOrNull());
        CompatRegistryClient.registerRenderTypeBlock(RenderLayer.getCutout(), Blocks.DOUBLE_CUBIC_TURRET_BLOCK.getOrNull());

        CompatRegistryClient.registerEntityRenderer(() -> Entities.BULLET_ENTITY.get(), (ctx -> (EntityRenderer) new FlyingItemEntityRenderer<>(ctx)));

        CompatRegistryClient.registerScreen(ScreenHandlers.CUBIC_TURRET.getOrNull(), CubicTurretScreen::new);
    }
}
