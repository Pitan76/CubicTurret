package net.pitan76.cubicturret.block;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.pitan76.cubicturret.tile.BlockEntities;
import net.pitan76.mcpitanlib.api.block.v2.CompatibleBlockSettings;
import org.jetbrains.annotations.Nullable;

public class CubicTurretBlock extends AbstractCubicTurretBlock {

    public int level;

    public CubicTurretBlock(CompatibleBlockSettings settings) {
        this(settings, 1);
    }

    public CubicTurretBlock(CompatibleBlockSettings settings, int level) {
        super(settings);
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public boolean isInfinite() {
        return level == -1;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityType<T> getBlockEntityType() {
        return (BlockEntityType<T>) BlockEntities.CUBIC_TURRET.get();
    }
}
