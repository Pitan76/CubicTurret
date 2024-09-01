package net.pitan76.cubicturret;

import net.pitan76.cubicturret.block.Blocks;
import net.pitan76.cubicturret.entity.Entities;
import net.pitan76.cubicturret.item.ItemGroups;
import net.pitan76.cubicturret.item.Items;
import net.pitan76.cubicturret.screen.ScreenHandlers;
import net.pitan76.cubicturret.tile.BlockEntities;
import net.pitan76.mcpitanlib.api.registry.v2.CompatRegistryV2;
import net.pitan76.mcpitanlib.api.util.CompatIdentifier;
import net.pitan76.mcpitanlib.fabric.ExtendModInitializer;

public class CubicTurret extends ExtendModInitializer {

    public static final String MOD_NAME = "Cubic Turret";
    public static final String MOD_ID = "cubicturret";

    public static CubicTurret INSTANCE;

    public static CompatRegistryV2 registry;

    @Override
    public void init() {
        INSTANCE = this;
        registry = super.registry;

        ItemGroups.init();
        Blocks.init();
        Items.init();
        BlockEntities.init();
        Entities.init();
        ScreenHandlers.init();
    }

    @Override
    public String getId() {
        return MOD_ID;
    }

    @Override
    public String getName() {
        return MOD_NAME;
    }

    public static CompatIdentifier _id(String path) {
        return INSTANCE.compatId(path);
    }
}
