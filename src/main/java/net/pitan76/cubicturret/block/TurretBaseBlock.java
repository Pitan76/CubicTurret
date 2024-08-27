package net.pitan76.cubicturret.block;

import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.pitan76.mcpitanlib.api.block.CompatibleBlockSettings;
import net.pitan76.mcpitanlib.api.block.ExtendBlock;
import net.pitan76.mcpitanlib.api.event.block.CollisionShapeEvent;
import net.pitan76.mcpitanlib.api.event.block.OutlineShapeEvent;

public class TurretBaseBlock extends ExtendBlock {

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
