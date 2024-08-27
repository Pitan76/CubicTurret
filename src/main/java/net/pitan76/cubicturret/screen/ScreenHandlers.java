package net.pitan76.cubicturret.screen;

import net.minecraft.screen.ScreenHandlerType;
import net.pitan76.mcpitanlib.api.gui.SimpleScreenHandlerTypeBuilder;
import net.pitan76.mcpitanlib.api.registry.result.RegistryResult;

import static net.pitan76.cubicturret.CubicTurret.id;
import static net.pitan76.cubicturret.CubicTurret.registry;

public class ScreenHandlers {

    public static RegistryResult<ScreenHandlerType<?>> CUBIC_TURRET;

    public static void init() {
        CUBIC_TURRET = registry.registerScreenHandlerType(id("cubic_turret"), () -> new SimpleScreenHandlerTypeBuilder<>(CubicTurretScreenHandler::new).build());
    }
}
