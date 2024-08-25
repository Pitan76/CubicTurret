package net.pitan76.cubicturret.client;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.render.RenderLayer;
import net.pitan76.cubicturret.block.Blocks;
import net.pitan76.mcpitanlib.api.client.registry.CompatRegistryClient;

public class CubicTurretClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        CompatRegistryClient.registerRenderTypeBlock(RenderLayer.getCutout(), Blocks.TURRET_BLOCK.getOrNull());
    }
}
