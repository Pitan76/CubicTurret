package net.pitan76.cubicturret.tile;

import net.minecraft.block.Block;
import net.pitan76.cubicturret.block.Blocks;
import net.pitan76.mcpitanlib.api.tile.BlockEntityTypeBuilder;
import net.pitan76.mcpitanlib.api.tile.CompatBlockEntity;
import net.pitan76.mcpitanlib.midohra.block.BlockWrapper;
import net.pitan76.mcpitanlib.midohra.block.entity.TypedBlockEntityTypeWrapper;

import java.util.Arrays;

import static net.pitan76.cubicturret.CubicTurret._id;
import static net.pitan76.cubicturret.CubicTurret.registry;

public class BlockEntities {
    public static TypedBlockEntityTypeWrapper<CubicTurretBlockEntity> CUBIC_TURRET;
    public static TypedBlockEntityTypeWrapper<DoubleCubicTurretBlockEntity> DOUBLE_CUBIC_TURRET;

    public static void init() {
        CUBIC_TURRET = registry.registerBlockEntityType(_id("cubic_turret"), create(
                CubicTurretBlockEntity::new,
                Blocks.CUBIC_TURRET_BLOCK,
                Blocks.CUBIC_TURRET_LV2_BLOCK,
                Blocks.CUBIC_TURRET_LV3_BLOCK,
                Blocks.CUBIC_TURRET_LV4_BLOCK,
                Blocks.CUBIC_TURRET_LV5_BLOCK,
                Blocks.CUBIC_TURRET_INF_BLOCK
        ));
        DOUBLE_CUBIC_TURRET = registry.registerBlockEntityType(_id("double_cubic_turret"), create(
                DoubleCubicTurretBlockEntity::new,
                Blocks.DOUBLE_CUBIC_TURRET_BLOCK,
                Blocks.DOUBLE_CUBIC_TURRET_LV2_BLOCK,
                Blocks.DOUBLE_CUBIC_TURRET_LV3_BLOCK,
                Blocks.DOUBLE_CUBIC_TURRET_LV4_BLOCK,
                Blocks.DOUBLE_CUBIC_TURRET_LV5_BLOCK,
                Blocks.DOUBLE_CUBIC_TURRET_INF_BLOCK

        ));
    }

    public static <T extends CompatBlockEntity> BlockEntityTypeBuilder<T> create(BlockEntityTypeBuilder.Factory<T> supplier, BlockWrapper... blockWrappers) {
        return BlockEntityTypeBuilder.create(supplier,
                Arrays.stream(blockWrappers).map(BlockWrapper::get).toArray(Block[]::new));
    }
}
