package net.pitan76.cubicturret.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.pitan76.cubicturret.tile.BlockEntities;
import net.pitan76.cubicturret.tile.CubicTurretBlockEntity;
import net.pitan76.mcpitanlib.api.block.CompatibleBlockSettings;
import net.pitan76.mcpitanlib.api.block.ExtendBlock;
import net.pitan76.mcpitanlib.api.block.ExtendBlockEntityProvider;
import net.pitan76.mcpitanlib.api.event.block.*;
import net.pitan76.mcpitanlib.api.event.item.ItemAppendTooltipEvent;
import net.pitan76.mcpitanlib.api.util.BlockStateUtil;
import net.pitan76.mcpitanlib.api.util.TextUtil;
import net.pitan76.mcpitanlib.api.util.VoxelShapeUtil;
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
    public void appendTooltip(ItemAppendTooltipEvent e) {
        super.appendTooltip(e);
        e.addTooltip(TextUtil.translatable("tooltip.cubicturret.cubic_turret.line1"));
        e.addTooltip(TextUtil.translatable("tooltip.cubicturret.cubic_turret.line2"));
    }

    @Override
    public BlockState getPlacementState(PlacementStateArgs args) {
        return args.withBlockState(FACING, args.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    public void onStateReplaced(StateReplacedEvent e) {
        e.spawnDropsInContainer();
        super.onStateReplaced(e);
    }

    @Override
    public ActionResult onRightClick(BlockUseEvent e) {
        if (e.isClient()) return ActionResult.SUCCESS;
        if (!e.hasBlockEntity()) return ActionResult.PASS;

        BlockEntity blockEntity = e.getBlockEntity();
        if (!(blockEntity instanceof CubicTurretBlockEntity)) return super.onRightClick(e);

        CubicTurretBlockEntity tile = (CubicTurretBlockEntity)blockEntity;

        if (e.isSneaking()) {
            tile.toggle(e.player);
            return ActionResult.CONSUME;
        }

        e.player.openMenu(tile);

        return ActionResult.CONSUME;
    }

    @Override
    public VoxelShape getOutlineShape(OutlineShapeEvent e) {
        return getShape(e.getProperty(FACING));
    }

    @Override
    public VoxelShape getCollisionShape(CollisionShapeEvent e) {
        return getShape(e.getProperty(FACING));
    }

    public VoxelShape getShape(Direction direction) {
        VoxelShape cannon = VoxelShapeUtil.union(VoxelShapeUtil.cuboid(0.4375, 0.875, 0.5625, 0.5625, 0.9375, 0.75),
                VoxelShapeUtil.cuboid(0.4375, 0.8125, 0.5, 0.5625, 1, 0.5625),
                VoxelShapeUtil.cuboid(0.4375, 0.8125, 0.4375, 0.5625, 0.9375, 0.5));

        switch (direction) {
            case SOUTH:
                cannon = VoxelShapeUtil.union(VoxelShapeUtil.cuboid(0.4375, 0.875, 0.25, 0.5625, 0.9375, 0.4375),
                        VoxelShapeUtil.cuboid(0.4375, 0.8125, 0.4375, 0.5625, 1, 0.5),
                        VoxelShapeUtil.cuboid(0.4375, 0.8125, 0.5, 0.5625, 0.9375, 0.5625));
                break;
            case EAST:
                cannon = VoxelShapeUtil.union(VoxelShapeUtil.cuboid(0.25, 0.875, 0.4375, 0.4375, 0.9375, 0.5625),
                        VoxelShapeUtil.cuboid(0.4375, 0.8125, 0.4375, 0.5, 1, 0.5625),
                        VoxelShapeUtil.cuboid(0.5, 0.8125, 0.4375, 0.5625, 0.9375, 0.5625));
                break;
            case WEST:
                cannon = VoxelShapeUtil.union(VoxelShapeUtil.cuboid(0.5625, 0.875, 0.4375, 0.75, 0.9375, 0.5625),
                        VoxelShapeUtil.cuboid(0.5, 0.8125, 0.4375, 0.5625, 1, 0.5625),
                        VoxelShapeUtil.cuboid(0.4375, 0.8125, 0.4375, 0.5, 0.9375, 0.5625));
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
}
