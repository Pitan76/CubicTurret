package net.pitan76.cubicturret.block;

import net.minecraft.block.Block;
import net.pitan76.mcpitanlib.api.block.v2.BlockSettingsBuilder;
import net.pitan76.mcpitanlib.api.block.v2.CompatibleBlockSettings;
import net.pitan76.mcpitanlib.api.block.CompatibleMaterial;
import net.pitan76.mcpitanlib.api.registry.result.RegistryResult;

import static net.pitan76.cubicturret.CubicTurret._id;
import static net.pitan76.cubicturret.CubicTurret.registry;

public class Blocks {

    public static final BlockSettingsBuilder TURRET_SETTINGS = new BlockSettingsBuilder()
            .material(CompatibleMaterial.METAL)
            .strength(2.5F, 6.0F);

    public static RegistryResult<Block> TURRET_BASE;

    public static RegistryResult<Block> CUBIC_TURRET_BLOCK;
    public static RegistryResult<Block> DOUBLE_CUBIC_TURRET_BLOCK;

    public static RegistryResult<Block> CUBIC_TURRET_LV2_BLOCK;
    public static RegistryResult<Block> DOUBLE_CUBIC_TURRET_LV2_BLOCK;

    public static RegistryResult<Block> CUBIC_TURRET_LV3_BLOCK;
    public static RegistryResult<Block> DOUBLE_CUBIC_TURRET_LV3_BLOCK;

    public static RegistryResult<Block> CUBIC_TURRET_LV4_BLOCK;
    public static RegistryResult<Block> DOUBLE_CUBIC_TURRET_LV4_BLOCK;

    public static RegistryResult<Block> CUBIC_TURRET_LV5_BLOCK;
    public static RegistryResult<Block> DOUBLE_CUBIC_TURRET_LV5_BLOCK;

    public static RegistryResult<Block> CUBIC_TURRET_INF_BLOCK;
    public static RegistryResult<Block> DOUBLE_CUBIC_TURRET_INF_BLOCK;

    public static void init() {
        TURRET_BASE = registry.registerBlock(_id("turret_base"), () -> new TurretBaseBlock(CompatibleBlockSettings
                .of(_id("turret_base"), CompatibleMaterial.METAL)
                .strength(1.0F, 6.0F)
        ));

        CUBIC_TURRET_BLOCK = registry.registerBlock(_id("cubic_turret"), () -> new CubicTurretBlock(TURRET_SETTINGS.build(_id("cubic_turret")), 1));
        DOUBLE_CUBIC_TURRET_BLOCK = registry.registerBlock(_id("double_cubic_turret"), () -> new DoubleCubicTurretBlock(TURRET_SETTINGS.build(_id("double_cubic_turret")), 1));

        CUBIC_TURRET_LV2_BLOCK = registry.registerBlock(_id("cubic_turret_lv2"), () -> new CubicTurretBlock(TURRET_SETTINGS.build(_id("cubic_turret_lv2")), 2));
        DOUBLE_CUBIC_TURRET_LV2_BLOCK = registry.registerBlock(_id("double_cubic_turret_lv2"), () -> new DoubleCubicTurretBlock(TURRET_SETTINGS.build(_id("double_cubic_turret_lv2")), 2));

        CUBIC_TURRET_LV3_BLOCK = registry.registerBlock(_id("cubic_turret_lv3"), () -> new CubicTurretBlock(TURRET_SETTINGS.build(_id("cubic_turret_lv3")), 3));
        DOUBLE_CUBIC_TURRET_LV3_BLOCK = registry.registerBlock(_id("double_cubic_turret_lv3"), () -> new DoubleCubicTurretBlock(TURRET_SETTINGS.build(_id("double_cubic_turret_lv3")), 3));

        CUBIC_TURRET_LV4_BLOCK = registry.registerBlock(_id("cubic_turret_lv4"), () -> new CubicTurretBlock(TURRET_SETTINGS.build(_id("cubic_turret_lv4")), 4));
        DOUBLE_CUBIC_TURRET_LV4_BLOCK = registry.registerBlock(_id("double_cubic_turret_lv4"), () -> new DoubleCubicTurretBlock(TURRET_SETTINGS.build(_id("double_cubic_turret_lv4")), 4));

        CUBIC_TURRET_LV5_BLOCK = registry.registerBlock(_id("cubic_turret_lv5"), () -> new CubicTurretBlock(TURRET_SETTINGS.build(_id("cubic_turret_lv5")), 5));
        DOUBLE_CUBIC_TURRET_LV5_BLOCK = registry.registerBlock(_id("double_cubic_turret_lv5"), () -> new DoubleCubicTurretBlock(TURRET_SETTINGS.build(_id("double_cubic_turret_lv5")), 5));

        CUBIC_TURRET_INF_BLOCK = registry.registerBlock(_id("cubic_turret_infinity"), () -> new CubicTurretBlock(TURRET_SETTINGS.build(_id("cubic_turret_infinity")), -1));
        DOUBLE_CUBIC_TURRET_INF_BLOCK = registry.registerBlock(_id("double_cubic_turret_infinity"), () -> new DoubleCubicTurretBlock(TURRET_SETTINGS.build(_id("double_cubic_turret_infinity")), -1));
    }
}
