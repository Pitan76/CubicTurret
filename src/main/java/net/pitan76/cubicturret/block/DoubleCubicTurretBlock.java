package net.pitan76.cubicturret.block;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.pitan76.cubicturret.tile.BlockEntities;
import net.pitan76.mcpitanlib.api.block.CompatibleBlockSettings;
import net.pitan76.mcpitanlib.api.util.VoxelShapeUtil;
import org.jetbrains.annotations.Nullable;

public class DoubleCubicTurretBlock extends CubicTurretBlock {
    public DoubleCubicTurretBlock(CompatibleBlockSettings settings) {
        this(settings, 1);
    }

    public DoubleCubicTurretBlock(CompatibleBlockSettings settings, int level) {
        super(settings, level);
    }

    @Override
    public VoxelShape getShape(Direction direction) {
        VoxelShape cannon = VoxelShapeUtil.union(
                VoxelShapeUtil.cuboid(0.375, 0.875, 0.5625, 0.4375, 0.9375, 0.75),
                VoxelShapeUtil.cuboid(0.375, 0.8125, 0.5, 0.4375, 1, 0.5625),
                VoxelShapeUtil.cuboid(0.375, 0.8125, 0.4375, 0.4375, 0.9375, 0.5),
                VoxelShapeUtil.cuboid(0.5625, 0.875, 0.5625, 0.625, 0.9375, 0.75),
                VoxelShapeUtil.cuboid(0.5625, 0.8125, 0.5, 0.625, 1, 0.5625),
                VoxelShapeUtil.cuboid(0.5625, 0.8125, 0.4375, 0.625, 0.9375, 0.5));

        switch (direction) {
            case SOUTH:
                cannon = VoxelShapeUtil.union(
                        VoxelShapeUtil.cuboid(0.375, 0.875, 0.25, 0.4375, 0.9375, 0.4375),
                        VoxelShapeUtil.cuboid(0.375, 0.8125, 0.4375, 0.4375, 1, 0.5),
                        VoxelShapeUtil.cuboid(0.375, 0.8125, 0.5, 0.4375, 0.9375, 0.5625),
                        VoxelShapeUtil.cuboid(0.5625, 0.875, 0.25, 0.625, 0.9375, 0.4375),
                        VoxelShapeUtil.cuboid(0.5625, 0.8125, 0.4375, 0.625, 1, 0.5),
                        VoxelShapeUtil.cuboid(0.5625, 0.8125, 0.5, 0.625, 0.9375, 0.5625));
                break;
            case EAST:
                cannon = VoxelShapeUtil.union(
                        VoxelShapeUtil.cuboid(0.25, 0.875, 0.375, 0.4375, 0.9375, 0.4375),
                        VoxelShapeUtil.cuboid(0.4375, 0.8125, 0.375, 0.5, 1, 0.4375),
                        VoxelShapeUtil.cuboid(0.5, 0.8125, 0.375, 0.5625, 0.9375, 0.4375),
                        VoxelShapeUtil.cuboid(0.25, 0.875, 0.5625, 0.4375, 0.9375, 0.625),
                        VoxelShapeUtil.cuboid(0.4375, 0.8125, 0.5625, 0.5, 1, 0.625),
                        VoxelShapeUtil.cuboid(0.5, 0.8125, 0.5625, 0.5625, 0.9375, 0.625));
                break;
            case WEST:
                cannon = VoxelShapeUtil.union(
                        VoxelShapeUtil.cuboid(0.5625, 0.875, 0.375, 0.75, 0.9375, 0.4375),
                        VoxelShapeUtil.cuboid(0.5, 0.8125, 0.375, 0.5625, 1, 0.4375),
                        VoxelShapeUtil.cuboid(0.4375, 0.8125, 0.375, 0.5, 0.9375, 0.4375),
                        VoxelShapeUtil.cuboid(0.5625, 0.875, 0.5625, 0.75, 0.9375, 0.625),
                        VoxelShapeUtil.cuboid(0.5, 0.8125, 0.5625, 0.5625, 1, 0.625),
                        VoxelShapeUtil.cuboid(0.4375, 0.8125, 0.5625, 0.5, 0.9375, 0.625));
                break;
        }

        return VoxelShapeUtil.union(
                VoxelShapeUtil.cuboid(0, 0, 0, 1, 0.0625, 1),
                VoxelShapeUtil.cuboid(0.1875, 0.0625, 0.1875, 0.8125, 0.625, 0.8125),
                VoxelShapeUtil.cuboid(0.3125, 0.625, 0.3125, 0.6875, 0.6875, 0.6875),
                VoxelShapeUtil.cuboid(0.375, 0.6875, 0.375, 0.625, 0.8125, 0.625),
                cannon
        );
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityType<T> getBlockEntityType() {
        return (BlockEntityType<T>) BlockEntities.DOUBLE_CUBIC_TURRET.getOrNull();
    }
}
