package net.pitan76.cubicturret.block;

import net.pitan76.mcpitanlib.api.block.args.v2.CollisionShapeEvent;
import net.pitan76.mcpitanlib.api.block.args.v2.OutlineShapeEvent;
import net.pitan76.mcpitanlib.api.block.args.v2.PlacementStateArgs;
import net.pitan76.mcpitanlib.api.event.block.AppendPropertiesArgs;
import net.pitan76.mcpitanlib.api.event.block.BlockUseEvent;
import net.pitan76.mcpitanlib.api.event.block.StateReplacedEvent;
import net.pitan76.mcpitanlib.api.state.property.CompatProperties;
import net.pitan76.mcpitanlib.api.state.property.DirectionProperty;
import net.pitan76.mcpitanlib.api.util.CompatActionResult;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.shape.VoxelShape;
import net.pitan76.cubicturret.tile.BlockEntities;
import net.pitan76.cubicturret.tile.CubicTurretBlockEntity;
import net.pitan76.mcpitanlib.api.block.v2.CompatibleBlockSettings;
import net.pitan76.mcpitanlib.api.block.v2.CompatBlock;
import net.pitan76.mcpitanlib.api.block.ExtendBlockEntityProvider;
import net.pitan76.mcpitanlib.api.event.item.ItemAppendTooltipEvent;
import net.pitan76.mcpitanlib.api.util.TextUtil;
import net.pitan76.mcpitanlib.api.util.VoxelShapeUtil;
import net.pitan76.mcpitanlib.midohra.block.BlockState;
import net.pitan76.mcpitanlib.midohra.util.math.Direction;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractCubicTurretBlock extends CompatBlock implements ExtendBlockEntityProvider {

    public static DirectionProperty FACING = CompatProperties.HORIZONTAL_FACING;

    public AbstractCubicTurretBlock(CompatibleBlockSettings settings) {
        super(settings);
        setDefaultState(getDefaultMidohraState().with(FACING, Direction.NORTH));
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
        return args.with(FACING, args.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    public void onStateReplaced(StateReplacedEvent e) {
        e.spawnDropsInContainer();
        super.onStateReplaced(e);
    }

    @Override
    public CompatActionResult onRightClick(BlockUseEvent e) {
        if (e.isClient()) return e.success();
        if (!e.hasBlockEntity()) return e.pass();

        BlockEntity blockEntity = e.getBlockEntity();
        if (!(blockEntity instanceof CubicTurretBlockEntity)) return super.onRightClick(e);

        CubicTurretBlockEntity tile = (CubicTurretBlockEntity)blockEntity;

        if (e.isSneaking()) {
            tile.toggle(e.player);
            return e.consume();
        }

        e.player.openMenu(tile);

        return e.consume();
    }

    @Override
    public VoxelShape getOutlineShape(OutlineShapeEvent e) {
        return getShape(e.get(FACING));
    }

    @Override
    public VoxelShape getCollisionShape(CollisionShapeEvent e) {
        return getShape(e.get(FACING));
    }

    public VoxelShape getShape(Direction direction) {
        VoxelShape cannon = VoxelShapeUtil.union(VoxelShapeUtil.cuboid(0.4375, 0.875, 0.5625, 0.5625, 0.9375, 0.75),
                VoxelShapeUtil.cuboid(0.4375, 0.8125, 0.5, 0.5625, 1, 0.5625),
                VoxelShapeUtil.cuboid(0.4375, 0.8125, 0.4375, 0.5625, 0.9375, 0.5));

        if (direction.equals(Direction.SOUTH)) {
            cannon = VoxelShapeUtil.union(VoxelShapeUtil.cuboid(0.4375, 0.875, 0.25, 0.5625, 0.9375, 0.4375),
                    VoxelShapeUtil.cuboid(0.4375, 0.8125, 0.4375, 0.5625, 1, 0.5),
                    VoxelShapeUtil.cuboid(0.4375, 0.8125, 0.5, 0.5625, 0.9375, 0.5625));
        } else if (direction.equals(Direction.EAST)) {
            cannon = VoxelShapeUtil.union(VoxelShapeUtil.cuboid(0.25, 0.875, 0.4375, 0.4375, 0.9375, 0.5625),
                    VoxelShapeUtil.cuboid(0.4375, 0.8125, 0.4375, 0.5, 1, 0.5625),
                    VoxelShapeUtil.cuboid(0.5, 0.8125, 0.4375, 0.5625, 0.9375, 0.5625));
        } else if (direction.equals(Direction.WEST)) {
            cannon = VoxelShapeUtil.union(VoxelShapeUtil.cuboid(0.5625, 0.875, 0.4375, 0.75, 0.9375, 0.5625),
                    VoxelShapeUtil.cuboid(0.5, 0.8125, 0.4375, 0.5625, 1, 0.5625),
                    VoxelShapeUtil.cuboid(0.4375, 0.8125, 0.4375, 0.5, 0.9375, 0.5625));
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
