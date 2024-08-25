package net.pitan76.cubicturret.tile;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.pitan76.cubicturret.block.Blocks;
import net.pitan76.mcpitanlib.api.registry.result.RegistryResult;
import net.pitan76.mcpitanlib.api.tile.BlockEntityTypeBuilder;

import static net.pitan76.cubicturret.CubicTurret.id;
import static net.pitan76.cubicturret.CubicTurret.registry;

public class BlockEntities {
    public static RegistryResult<BlockEntityType<?>> CUBIC_TURRET;

    public static void init() {
        CUBIC_TURRET = registry.registerBlockEntityType(id("cubic_turret"), () -> create(
                CubicTurretBlockEntity::new, Blocks.TURRET_BLOCK.getOrNull()));
    }

    public static <T extends BlockEntity> BlockEntityType<T> create(BlockEntityTypeBuilder.Factory<T> supplier, Block... blocks) {
        return BlockEntityTypeBuilder.create(supplier, blocks).build();
    }
}
