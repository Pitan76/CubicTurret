package net.pitan76.cubicturret.block;

import net.minecraft.block.Block;
import net.pitan76.mcpitanlib.api.block.CompatibleBlockSettings;
import net.pitan76.mcpitanlib.api.block.CompatibleMaterial;
import net.pitan76.mcpitanlib.api.registry.result.RegistryResult;
import net.pitan76.mcpitanlib.api.util.BlockUtil;

import static net.pitan76.cubicturret.CubicTurret.id;
import static net.pitan76.cubicturret.CubicTurret.registry;

public class Blocks {

    public static CompatibleBlockSettings turretSettings = CompatibleBlockSettings
            .of(CompatibleMaterial.METAL)
            .strength(3.0F, 6.0F);

    public static RegistryResult<Block> TURRET_BASE;

    public static RegistryResult<Block> CUBIC_TURRET_BLOCK;
    public static RegistryResult<Block> DISPENSER_CUBIC_TURRET_BLOCK;
    public static RegistryResult<Block> DOUBLE_CUBIC_TURRET_BLOCK;



    public static void init() {
        TURRET_BASE = registry.registerBlock(id("turret_base"), () -> BlockUtil.of(turretSettings));

        CUBIC_TURRET_BLOCK = registry.registerBlock(id("cubic_turret"), () -> new CubicTurretBlock(turretSettings));
        DOUBLE_CUBIC_TURRET_BLOCK = registry.registerBlock(id("double_cubic_turret"), () -> new DoubleCubicTurretBlock(turretSettings));
    }
}
