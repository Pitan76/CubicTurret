package net.pitan76.cubicturret.block;

import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.pitan76.mcpitanlib.api.block.CompatibleBlockSettings;
import net.pitan76.mcpitanlib.api.event.block.AppendPropertiesArgs;
import net.pitan76.mcpitanlib.api.event.block.CollisionShapeEvent;
import net.pitan76.mcpitanlib.api.event.block.OutlineShapeEvent;

public class CubicTurretBlock extends AbstractCubicTurretBlock {

    public CubicTurretBlock(CompatibleBlockSettings settings) {
        super(settings);
    }
}
