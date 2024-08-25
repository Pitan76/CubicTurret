package net.pitan76.cubicturret.tile;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.pitan76.mcpitanlib.api.event.block.TileCreateEvent;
import net.pitan76.mcpitanlib.api.event.nbt.ReadNbtArgs;
import net.pitan76.mcpitanlib.api.event.nbt.WriteNbtArgs;
import net.pitan76.mcpitanlib.api.event.tile.TileTickEvent;
import net.pitan76.mcpitanlib.api.tile.CompatBlockEntity;
import net.pitan76.mcpitanlib.api.tile.ExtendBlockEntityTicker;
import net.pitan76.mcpitanlib.api.util.InventoryUtil;

public class CubicTurretBlockEntity extends CompatBlockEntity implements ExtendBlockEntityTicker {

    public DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);

    public CubicTurretBlockEntity(TileCreateEvent event) {
        this(BlockEntities.CUBIC_TURRET.getOrNull(), event);
    }

    public CubicTurretBlockEntity(BlockEntityType<?> blockEntityType, TileCreateEvent event) {
        super(blockEntityType, event);
    }

    @Override
    public void writeNbt(WriteNbtArgs args) {
        super.writeNbt(args);
        InventoryUtil.writeNbt(args, inventory);
    }

    @Override
    public void readNbt(ReadNbtArgs args) {
        super.readNbt(args);
        InventoryUtil.readNbt(args, inventory);
    }


    @Override
    public void tick(TileTickEvent e) {
        if (e.world.isClient) return;


    }
}
