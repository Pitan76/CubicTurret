package net.pitan76.cubicturret.screen;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.pitan76.mcpitanlib.api.entity.Player;
import net.pitan76.mcpitanlib.api.gui.SimpleScreenHandler;

public class CubicTurretScreenHandler extends SimpleScreenHandler {

    public PlayerInventory playerInventory;
    public Inventory inventory;

    public CubicTurretScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(9));
    }

    public CubicTurretScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(ScreenHandlers.CUBIC_TURRET.getOrNull(), syncId);
        this.playerInventory = playerInventory;
        this.inventory = inventory;

        addSlots(inventory, 0, 62, 17, 18, 3, 3);

        addPlayerHotbarSlots(playerInventory, 8, 142);
        addPlayerMainInventorySlots(playerInventory, 8, 84);
    }

    @Override
    public ItemStack quickMoveOverride(Player player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (index < 9) {
                if (!this.insertItem(itemStack2, 9, 18, true)) {
                    if (!this.insertItem(itemStack2, 18, 44, true)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.insertItem(itemStack2, 0, 9, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }

            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTakeItem(player.getEntity(), itemStack2);
        }

        return itemStack;
    }
}
