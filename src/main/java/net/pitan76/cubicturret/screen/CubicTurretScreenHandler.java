package net.pitan76.cubicturret.screen;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.pitan76.mcpitanlib.api.entity.Player;
import net.pitan76.mcpitanlib.api.gui.SimpleScreenHandler;
import net.pitan76.mcpitanlib.api.util.ItemStackUtil;
import net.pitan76.mcpitanlib.api.util.ScreenHandlerUtil;
import net.pitan76.mcpitanlib.api.util.SlotUtil;

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
        ItemStack itemStack = ItemStackUtil.empty();
        Slot slot = ScreenHandlerUtil.getSlot(this, index);
        if (slot.hasStack()) {
            ItemStack itemStack2 = SlotUtil.getStack(slot);
            itemStack = itemStack2.copy();
            if (index < 9) {
                if (!this.callInsertItem(itemStack2, 9, 18, true)) {
                    if (!this.callInsertItem(itemStack2, 18, 45, true)) {
                        return ItemStackUtil.empty();
                    }
                }
            } else if (!this.callInsertItem(itemStack2, 0, 9, false)) {
                return ItemStackUtil.empty();
            }

            if (itemStack2.isEmpty()) {
                SlotUtil.setStack(slot, ItemStackUtil.empty());
            } else {
                SlotUtil.markDirty(slot);
            }

            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStackUtil.empty();
            }

            SlotUtil.onTakeItem(slot, player, itemStack2);
        }

        return itemStack;
    }
}
