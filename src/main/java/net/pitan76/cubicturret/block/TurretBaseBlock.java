package net.pitan76.cubicturret.block;

import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.pitan76.mcpitanlib.api.block.args.v2.CollisionShapeEvent;
import net.pitan76.mcpitanlib.api.block.args.v2.OutlineShapeEvent;
import net.pitan76.mcpitanlib.api.block.v2.CompatibleBlockSettings;
import net.pitan76.mcpitanlib.api.block.v2.CompatBlock;

public class TurretBaseBlock extends CompatBlock  {

    public TurretBaseBlock(CompatibleBlockSettings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(OutlineShapeEvent e) {
        return getShape();
    }

    @Override
    public VoxelShape getCollisionShape(CollisionShapeEvent e) {
        return getShape();
    }

    public VoxelShape getShape() {
        return VoxelShapes.cuboid(0, 0, 0, 1, 0.0625, 1);
    }
}
