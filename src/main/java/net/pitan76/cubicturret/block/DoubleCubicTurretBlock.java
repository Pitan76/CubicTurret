package net.pitan76.cubicturret.block;

import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.pitan76.mcpitanlib.api.block.CompatibleBlockSettings;

public class DoubleCubicTurretBlock extends AbstractCubicTurretBlock {
    public DoubleCubicTurretBlock(CompatibleBlockSettings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getShape(Direction direction) {
        return super.getShape(direction);
    }
}
