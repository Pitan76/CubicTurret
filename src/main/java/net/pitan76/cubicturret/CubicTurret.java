package net.pitan76.cubicturret;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.pitan76.cubicturret.block.Blocks;
import net.pitan76.cubicturret.entity.Entities;
import net.pitan76.cubicturret.item.ItemGroups;
import net.pitan76.cubicturret.item.Items;
import net.pitan76.cubicturret.screen.ScreenHandlers;
import net.pitan76.cubicturret.tile.BlockEntities;
import net.pitan76.mcpitanlib.api.registry.CompatRegistry;
import net.pitan76.mcpitanlib.api.util.IdentifierUtil;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CubicTurret implements ModInitializer {

    public static final String MOD_NAME = "Cubic Turret";
    public static final String MOD_ID = "cubicturret";

    private static final Logger LOGGER = LogManager.getLogger();

    public static final CompatRegistry registry = CompatRegistry.create(MOD_ID);

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

    public static Identifier id(String path) {
        return IdentifierUtil.id(MOD_ID, path);
    }
}
