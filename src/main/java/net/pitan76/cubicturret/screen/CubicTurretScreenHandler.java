package net.pitan76.cubicturret.screen;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
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

        addPlayerMainInventorySlots(playerInventory, 8, 84);
        addPlayerHotbarSlots(playerInventory, 8, 142);
    }

    @Override
    public ItemStack quickMoveOverride(Player player, int index) {
        return super.quickMoveOverride(player, index);
    }
}
