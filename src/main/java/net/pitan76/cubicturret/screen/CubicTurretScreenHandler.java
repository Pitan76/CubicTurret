package net.pitan76.cubicturret.screen;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.pitan76.mcpitanlib.api.entity.Player;
import net.pitan76.mcpitanlib.api.gui.SimpleScreenHandler;
import net.pitan76.mcpitanlib.api.util.ItemStackUtil;
import net.pitan76.mcpitanlib.api.util.ScreenHandlerUtil;
import net.pitan76.mcpitanlib.api.util.SlotUtil;
import net.pitan76.mcpitanlib.api.util.inventory.CompatInventory;

public class CubicTurretScreenHandler extends SimpleScreenHandler {

    public PlayerInventory playerInventory;
    public Inventory inventory;

    public CubicTurretScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new CompatInventory(9));
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
        ItemStack originStack = ItemStackUtil.empty();
        Slot slot = ScreenHandlerUtil.getSlot(this, index);
        if (SlotUtil.hasStack(slot)) {
            ItemStack stack = SlotUtil.getStack(slot);
            originStack = ItemStackUtil.copy(stack);
            if (index < 9) {
                if (!this.callInsertItem(stack, 9, 18, true)) {
                    if (!this.callInsertItem(stack, 18, 45, true)) {
                        return ItemStackUtil.empty();
                    }
                }
            } else if (!this.callInsertItem(stack, 0, 9, false)) {
                return ItemStackUtil.empty();
            }

            if (ItemStackUtil.isEmpty(stack)) {
                SlotUtil.setStack(slot, ItemStackUtil.empty());
            } else {
                SlotUtil.markDirty(slot);
            }

            if (ItemStackUtil.getCount(stack) == ItemStackUtil.getCount(originStack)) {
                return ItemStackUtil.empty();
            }

            SlotUtil.onTakeItem(slot, player, stack);
        }

        return originStack;
    }
}
