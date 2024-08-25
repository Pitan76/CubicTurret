package net.pitan76.cubicturret;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.pitan76.cubicturret.block.Blocks;
import net.pitan76.cubicturret.item.Items;
import net.pitan76.cubicturret.tile.BlockEntities;
import net.pitan76.mcpitanlib.api.registry.CompatRegistry;
import net.pitan76.mcpitanlib.api.util.IdentifierUtil;

public class CubicTurret implements ModInitializer {

    public static final String MOD_NAME = "Cubic Turret";
    public static final String MOD_ID = "cubicturret";

    public static final CompatRegistry registry = CompatRegistry.create(MOD_ID);

    @Override
    public void onInitialize() {
        BlockEntities.init();
        Blocks.init();
        Items.init();

        registry.allRegister();
    }

    public static Identifier id(String path) {
        return IdentifierUtil.id(MOD_ID, path);
    }
}
