package net.pitan76.cubicturret.client;

import net.fabricmc.api.ClientModInitializer;
import net.pitan76.cubicturret.client.screen.CubicTurretScreen;
import net.pitan76.cubicturret.entity.Entities;
import net.pitan76.cubicturret.screen.ScreenHandlers;
import net.pitan76.mcpitanlib.api.client.registry.CompatRegistryClient;
import net.pitan76.mcpitanlib.api.client.registry.EntityRendererRegistry;

public class CubicTurretClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.registerEntityRendererAsFlyingItem(() -> Entities.BULLET_ENTITY.get());

        CompatRegistryClient.registerScreen(ScreenHandlers.CUBIC_TURRET.getOrNull(), CubicTurretScreen::new);
    }
}
