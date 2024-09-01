package net.pitan76.cubicturret.tile;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.pitan76.cubicturret.block.Blocks;
import net.pitan76.mcpitanlib.api.registry.result.RegistryResult;
import net.pitan76.mcpitanlib.api.tile.BlockEntityTypeBuilder;

import static net.pitan76.cubicturret.CubicTurret._id;
import static net.pitan76.cubicturret.CubicTurret.registry;

public class BlockEntities {
    public static RegistryResult<BlockEntityType<?>> CUBIC_TURRET;
    public static RegistryResult<BlockEntityType<?>> DOUBLE_CUBIC_TURRET;

    public static void init() {
        CUBIC_TURRET = registry.registerBlockEntityType(_id("cubic_turret"), () -> create(
                CubicTurretBlockEntity::new,
                Blocks.CUBIC_TURRET_BLOCK.getOrNull(),
                Blocks.CUBIC_TURRET_LV2_BLOCK.getOrNull(),
                Blocks.CUBIC_TURRET_LV3_BLOCK.getOrNull(),
                Blocks.CUBIC_TURRET_LV4_BLOCK.getOrNull(),
                Blocks.CUBIC_TURRET_LV5_BLOCK.getOrNull(),
                Blocks.CUBIC_TURRET_INF_BLOCK.getOrNull()
        ));
        DOUBLE_CUBIC_TURRET = registry.registerBlockEntityType(_id("double_cubic_turret"), () -> create(
                DoubleCubicTurretBlockEntity::new,
                Blocks.DOUBLE_CUBIC_TURRET_BLOCK.getOrNull(),
                Blocks.DOUBLE_CUBIC_TURRET_LV2_BLOCK.getOrNull(),
                Blocks.DOUBLE_CUBIC_TURRET_LV3_BLOCK.getOrNull(),
                Blocks.DOUBLE_CUBIC_TURRET_LV4_BLOCK.getOrNull(),
                Blocks.DOUBLE_CUBIC_TURRET_LV5_BLOCK.getOrNull(),
                Blocks.DOUBLE_CUBIC_TURRET_INF_BLOCK.getOrNull()

        ));
    }

    public static <T extends BlockEntity> BlockEntityType<T> create(BlockEntityTypeBuilder.Factory<T> supplier, Block... blocks) {
        return BlockEntityTypeBuilder.create(supplier, blocks).build();
    }
}
