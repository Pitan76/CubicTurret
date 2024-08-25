package net.pitan76.cubicturret.block;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.pitan76.cubicturret.tile.BlockEntities;
import net.pitan76.mcpitanlib.api.block.CompatibleBlockSettings;
import net.pitan76.mcpitanlib.api.block.ExtendBlock;
import net.pitan76.mcpitanlib.api.block.ExtendBlockEntityProvider;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractCubicTurretBlock extends ExtendBlock implements ExtendBlockEntityProvider {

    public AbstractCubicTurretBlock(CompatibleBlockSettings settings) {
        super(settings);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityType<T> getBlockEntityType() {
        return (BlockEntityType<T>) BlockEntities.CUBIC_TURRET.getOrNull();
    }

    @Override
    public boolean isTick() {
        return true;
    }
}
