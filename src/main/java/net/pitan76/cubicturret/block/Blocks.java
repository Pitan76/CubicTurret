package net.pitan76.cubicturret.block;

import net.minecraft.block.Block;
import net.pitan76.mcpitanlib.api.block.CompatibleBlockSettings;
import net.pitan76.mcpitanlib.api.block.CompatibleMaterial;
import net.pitan76.mcpitanlib.api.registry.result.RegistryResult;

import static net.pitan76.cubicturret.CubicTurret.id;
import static net.pitan76.cubicturret.CubicTurret.registry;

public class Blocks {

    public static CompatibleBlockSettings turretSettings = CompatibleBlockSettings
            .of(CompatibleMaterial.METAL)
            .strength(3.0F, 6.0F);

    public static RegistryResult<Block> TURRET_BLOCK;

    public static void init() {
        TURRET_BLOCK = registry.registerBlock(id("cubic_turret"), () -> new CubicTurretBlock(turretSettings));
    }
}
