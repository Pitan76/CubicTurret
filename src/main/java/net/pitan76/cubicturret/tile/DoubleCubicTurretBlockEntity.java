package net.pitan76.cubicturret.tile;

import net.minecraft.block.entity.BlockEntityType;
import net.pitan76.mcpitanlib.api.event.block.TileCreateEvent;
import net.pitan76.mcpitanlib.api.event.tile.TileTickEvent;

public class DoubleCubicTurretBlockEntity extends CubicTurretBlockEntity {
    public DoubleCubicTurretBlockEntity(TileCreateEvent e) {
        this(BlockEntities.DOUBLE_CUBIC_TURRET.getOrNull(), e);
    }

    public DoubleCubicTurretBlockEntity(BlockEntityType<?> blockEntityType, TileCreateEvent e) {
        super(blockEntityType, e);
    }

    @Override
    public void tick(TileTickEvent<CubicTurretBlockEntity> e) {
        super.tick(e);

        shoot();
    }
}
