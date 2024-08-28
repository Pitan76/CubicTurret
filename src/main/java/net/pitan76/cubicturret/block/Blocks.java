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
            .strength(2.5F, 6.0F);

    public static RegistryResult<Block> TURRET_BASE;

    public static RegistryResult<Block> CUBIC_TURRET_BLOCK;
    public static RegistryResult<Block> DOUBLE_CUBIC_TURRET_BLOCK;

    public static void init() {
        TURRET_BASE = registry.registerBlock(id("turret_base"), () -> new TurretBaseBlock(CompatibleBlockSettings
                .of(CompatibleMaterial.METAL)
                .strength(1.0F, 6.0F)
        ));

        CUBIC_TURRET_BLOCK = registry.registerBlock(id("cubic_turret"), () -> new CubicTurretBlock(turretSettings));
        DOUBLE_CUBIC_TURRET_BLOCK = registry.registerBlock(id("double_cubic_turret"), () -> new DoubleCubicTurretBlock(turretSettings));
    }
}
