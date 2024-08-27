package net.pitan76.cubicturret.block;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.pitan76.cubicturret.tile.BlockEntities;
import net.pitan76.mcpitanlib.api.block.CompatibleBlockSettings;
import org.jetbrains.annotations.Nullable;

public class DoubleCubicTurretBlock extends AbstractCubicTurretBlock {
    public DoubleCubicTurretBlock(CompatibleBlockSettings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getShape(Direction direction) {
        return super.getShape(direction);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityType<T> getBlockEntityType() {
        return (BlockEntityType<T>) BlockEntities.DOUBLE_CUBIC_TURRET.getOrNull();
    }
}
