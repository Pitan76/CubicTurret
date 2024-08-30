package net.pitan76.cubicturret;

import net.fabricmc.api.ModInitializer;
import net.pitan76.cubicturret.block.Blocks;
import net.pitan76.cubicturret.entity.Entities;
import net.pitan76.cubicturret.item.ItemGroups;
import net.pitan76.cubicturret.item.Items;
import net.pitan76.cubicturret.screen.ScreenHandlers;
import net.pitan76.cubicturret.tile.BlockEntities;
import net.pitan76.mcpitanlib.api.registry.CompatRegistry;
import net.pitan76.mcpitanlib.api.registry.v2.CompatRegistryV2;
import net.pitan76.mcpitanlib.api.util.CompatIdentifier;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CubicTurret implements ModInitializer {

    public static final String MOD_NAME = "Cubic Turret";
    public static final String MOD_ID = "cubicturret";

    private static final Logger LOGGER = LogManager.getLogger();

    private static final CompatRegistry registry0 = CompatRegistry.create(MOD_ID);

    // 試験的にCompatRegistryV2を利用
    public static final CompatRegistryV2 registry = new CompatRegistryV2(registry0);

    @Override
    public void onInitialize() {
        log(Level.INFO, "Initializing");

        ItemGroups.init();
        Blocks.init();
        Items.init();
        BlockEntities.init();
        Entities.init();
        ScreenHandlers.init();

        registry.allRegister();
    }

    public static void log(Level level, String message){
        LOGGER.log(level, "[" + MOD_NAME + "] " + message);
    }

    public static CompatIdentifier id(String path) {
        return CompatIdentifier.of(MOD_ID, path);
    }
}
