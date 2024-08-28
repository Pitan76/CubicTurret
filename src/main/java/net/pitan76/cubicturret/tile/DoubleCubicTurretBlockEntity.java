package net.pitan76.cubicturret.tile;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
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

        if (!hasBulletStack()) return;
        int bulletCount = getBulletAmount();
        ItemStack bulletStack = getBulletStack();

        if (bulletCount >= 2) {
            if (shoot(e, 1)) {
                ItemStackUtil.decrementCount(bulletStack, 1);
            }

            if (shoot(e, -1)) {
                ItemStackUtil.decrementCount(bulletStack, 1);
            }
        } else if (shoot(e, 1)) {
            ItemStackUtil.decrementCount(bulletStack, 1);
        }
    }

    public boolean shoot(TileTickEvent<CubicTurretBlockEntity> e, double shift) {
        Entity target = getTargetEntity(e);
        if (target == null) return false;

        double dx = target.getX() - (e.pos.getX() + shift);
        double dy = target.getY() - (e.pos.getY() + shift);
        double dz = target.getZ() - (e.pos.getZ() + shift);

        double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);

        double vx = dx / distance;
        double vy = dy / distance;
        double vz = dz / distance;

        float divergence = getDivergence();

        shoot(e, vx, vy, vz, divergence);
        return true;
    }
}
