package net.pitan76.cubicturret.tile;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.projectile.SpectralArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.FireChargeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpectralArrowItem;
import net.minecraft.util.math.Direction;
import net.pitan76.cubicturret.block.AbstractCubicTurretBlock;
import net.pitan76.cubicturret.block.CubicTurretBlock;
import net.pitan76.cubicturret.entity.BulletEntity;
import net.pitan76.mcpitanlib.api.event.block.TileCreateEvent;
import net.pitan76.mcpitanlib.api.event.tile.TileTickEvent;
import net.pitan76.mcpitanlib.api.util.ItemStackUtil;

public class DoubleCubicTurretBlockEntity extends CubicTurretBlockEntity {
    public DoubleCubicTurretBlockEntity(TileCreateEvent e) {
        this(BlockEntities.DOUBLE_CUBIC_TURRET.getOrNull(), e);
    }

    public DoubleCubicTurretBlockEntity(BlockEntityType<?> blockEntityType, TileCreateEvent e) {
        super(blockEntityType, e);
    }

    @Override
    public void tick(TileTickEvent<CubicTurretBlockEntity> e) {
        if (e.world.isClient) return;
        if (e.world.getTime() % getFireSpeed() != 0) return;
        if (inventory.isEmpty()) return;

        if (level == 0) {
            Block block = e.world.getBlockState(e.pos).getBlock();
            if (block instanceof CubicTurretBlock) {
                level = ((CubicTurretBlock) block).getLevel();
                if (level == 0) level = 1;
            }
        }

        if (!hasBulletStack()) return;
        int bulletCount = getBulletAmount();
        ItemStack bulletStack = getBulletStack();

        if (bulletCount >= 2) {
            if (shoot(e, 0.2, bulletStack) && level != -1) {
                ItemStackUtil.decrementCount(bulletStack, 1);
            }

            if (shoot(e, -0.2, bulletStack) && level != -1) {
                ItemStackUtil.decrementCount(bulletStack, 1);
            }
        } else if (shoot(e, 0.2, bulletStack) && level != -1) {
            ItemStackUtil.decrementCount(bulletStack, 1);
        }
    }

    public boolean shoot(TileTickEvent<CubicTurretBlockEntity> e, double shift, ItemStack bulletStack) {
        Entity target = getTargetEntity(e);
        if (target == null) return false;

        double dx = target.getX() - e.pos.getX();
        double dy = target.getY() - e.pos.getY();
        double dz = target.getZ() - e.pos.getZ();

        double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);

        double vx = dx / distance;
        double vy = dy / distance;
        double vz = dz / distance;

        float divergence = getDivergence();

        shoot(e, vx, vy, vz, divergence, shift, bulletStack);
        return true;
    }

    public void shoot(TileTickEvent<CubicTurretBlockEntity> e, double vx, double vy, double vz, float divergence, double shift, ItemStack bulletStack) {
        double shiftX = 0;
        double shiftZ = 0;
        
        Direction dir = e.world.getBlockState(e.pos).get(AbstractCubicTurretBlock.FACING);
        switch (dir) {
            case SOUTH:
                shiftZ -= shift;
                break;
            case EAST:
                shiftX -= shift;
                break;
            case WEST:
                shiftX += shift;
                break;
            default:
                shiftZ += shift;
                break;
        }

        shoot(e, e.pos.getX() + 0.5 + shiftX, e.pos.getY() + 0.8, e.pos.getZ() + 0.5 + shiftZ, vx, vy, vz, divergence, bulletStack);
    }
}
