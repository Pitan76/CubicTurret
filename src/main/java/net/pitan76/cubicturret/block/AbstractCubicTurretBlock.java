package net.pitan76.cubicturret.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.pitan76.cubicturret.tile.BlockEntities;
import net.pitan76.mcpitanlib.api.block.CompatibleBlockSettings;
import net.pitan76.mcpitanlib.api.block.ExtendBlock;
import net.pitan76.mcpitanlib.api.block.ExtendBlockEntityProvider;
import net.pitan76.mcpitanlib.api.event.block.AppendPropertiesArgs;
import net.pitan76.mcpitanlib.api.event.block.CollisionShapeEvent;
import net.pitan76.mcpitanlib.api.event.block.OutlineShapeEvent;
import net.pitan76.mcpitanlib.api.util.BlockStateUtil;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractCubicTurretBlock extends ExtendBlock implements ExtendBlockEntityProvider {

    public static DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    public AbstractCubicTurretBlock(CompatibleBlockSettings settings) {
        super(settings);
        setDefaultState(BlockStateUtil.getDefaultState(this).with(FACING, Direction.NORTH));
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityType<T> getBlockEntityType() {
        return (BlockEntityType<T>) BlockEntities.CUBIC_TURRET.getOrNull();
    }

    @Override
    public boolean isTick() {
        return true;
    }

    @Override
    public void appendProperties(AppendPropertiesArgs args) {
        args.addProperty(FACING);
        super.appendProperties(args);
    }

    @Override
    public VoxelShape getOutlineShape(OutlineShapeEvent e) {
        return getShape();
    }

    @Override
    public VoxelShape getCollisionShape(CollisionShapeEvent e) {
        return getShape();
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return BlockStateUtil.getDefaultState(this).with(FACING, ctx.getPlayer().getHorizontalFacing().getOpposite());
    }

    public VoxelShape getShape() {
        return VoxelShapes.union(
                VoxelShapes.cuboid(0, 0, 0, 1, 0.0625, 1),
                VoxelShapes.cuboid(0.1875, 0.0625, 0.1875, 0.8125, 0.625, 0.8125),
                VoxelShapes.cuboid(0.3125, 0.625, 0.3125, 0.6875, 0.6875, 0.6875),
                VoxelShapes.cuboid(0.375, 0.6875, 0.375, 0.625, 0.8125, 0.625),
                VoxelShapes.cuboid(0.4375, 0.875, 0.5625, 0.5625, 0.9375, 0.75),
                VoxelShapes.cuboid(0.4375, 0.8125, 0.5, 0.5625, 1, 0.5625),
                VoxelShapes.cuboid(0.4375, 0.8125, 0.4375, 0.5625, 0.9375, 0.5)
        );
    }
}
