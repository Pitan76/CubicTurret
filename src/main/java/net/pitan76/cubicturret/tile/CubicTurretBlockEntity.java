package net.pitan76.cubicturret.tile;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.pitan76.cubicturret.entity.BulletEntity;
import net.pitan76.cubicturret.screen.CubicTurretScreenHandler;
import net.pitan76.mcpitanlib.api.event.block.TileCreateEvent;
import net.pitan76.mcpitanlib.api.event.nbt.ReadNbtArgs;
import net.pitan76.mcpitanlib.api.event.nbt.WriteNbtArgs;
import net.pitan76.mcpitanlib.api.event.tile.TileTickEvent;
import net.pitan76.mcpitanlib.api.gui.inventory.IInventory;
import net.pitan76.mcpitanlib.api.tile.CompatBlockEntity;
import net.pitan76.mcpitanlib.api.tile.ExtendBlockEntityTicker;
import net.pitan76.mcpitanlib.api.util.InventoryUtil;
import net.pitan76.mcpitanlib.api.util.ItemStackUtil;
import net.pitan76.mcpitanlib.api.util.TextUtil;
import net.pitan76.mcpitanlib.api.util.math.BoxUtil;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CubicTurretBlockEntity extends CompatBlockEntity implements ExtendBlockEntityTicker<CubicTurretBlockEntity>, NamedScreenHandlerFactory, SidedInventory, IInventory {

    public DefaultedList<ItemStack> inventory = DefaultedList.ofSize(9, ItemStack.EMPTY);

    public CubicTurretBlockEntity(TileCreateEvent e) {
        this(BlockEntities.CUBIC_TURRET.getOrNull(), e);
    }

    public CubicTurretBlockEntity(BlockEntityType<?> blockEntityType, TileCreateEvent e) {
        super(blockEntityType, e);
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
    public void tick(TileTickEvent<CubicTurretBlockEntity> e) {
        if (e.world.isClient) return;
        if (e.world.getTime() % getFireSpeed() != 0) return;
        if (inventory.isEmpty()) return;

        // if (e.world.getClosestPlayer(e.pos.getX(), e.pos.getY(), e.pos.getZ(), getBulletRange(), false) == null) return;

        if (!hasBulletStack()) return;
        ItemStack bulletStack = getBulletStack();

        if (shoot(e)) {
            ItemStackUtil.decrementCount(bulletStack, 1);
        }
    }

    public boolean hasBulletStack() {
        for (ItemStack stack : inventory) {
            if (stack.isEmpty()) continue;
            if (stack.getItem() != getBulletItem()) continue;
            return true;
        }
        return false;
    }

    public int getBulletAmount() {
        int amount = 0;
        for (ItemStack stack : inventory) {
            if (stack.isEmpty()) continue;
            if (stack.getItem() != getBulletItem()) continue;
            amount += stack.getCount();
        }
        return amount;
    }

    public ItemStack getBulletStack() {
        for (ItemStack stack : inventory) {
            if (stack.isEmpty()) continue;
            if (stack.getItem() != getBulletItem()) continue;
            return stack;
        }
        return ItemStack.EMPTY;
    }

    public float getDivergence() {
        return 0.1F;
    }

    public boolean shoot(TileTickEvent<CubicTurretBlockEntity> e) {
        Entity target = getTargetEntity(e);
        if (target == null) return false;

        double dx = target.getX() - e.pos.getX();
        double dy = target.getY() - e.pos.getY();
        double dz = target.getZ() - e.pos.getZ();

        double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);

        double vx = dx / distance;
        double vy = dy / distance;
        double vz = dz / distance;

        float divergence = getDivergence();

        shoot(e, vx, vy, vz, divergence);
        return true;
    }

    public void shoot(TileTickEvent<CubicTurretBlockEntity> e, double vx, double vy, double vz, float divergence) {
        BulletEntity bullet = new BulletEntity(e.world, e.pos.getX() + 0.5 + vx, e.pos.getY() + 0.8 + vy, e.pos.getZ() + 0.5 + vz, this);
        bullet.setItem(ItemStackUtil.getDefaultStack(getBulletItem()));
        bullet.setVelocity(vx, vy, vz, getBulletSpeed(), divergence);
        e.world.spawnEntity(bullet);
    }

    // 周辺の敵を取得
    public List<Entity> getTargetEntities(TileTickEvent<CubicTurretBlockEntity> e) {
        List<Entity> list = new ArrayList<>();

        // MobEntity
        Box box = BoxUtil.createBox(e.pos.getX() - getShootRange(), e.pos.getY() - getShootRange(), e.pos.getZ() - getShootRange(),
                e.pos.getX() + getShootRange(), e.pos.getY() + getShootRange(), e.pos.getZ() + getShootRange());

        list.addAll(e.world.getEntitiesByClass(MobEntity.class, box, Entity::isAlive));

        return list;
    }

    public Entity getTargetEntity(TileTickEvent<CubicTurretBlockEntity> e) {
        List<Entity> list = getTargetEntities(e);
        if (list.isEmpty()) return null;

        return list.get(0);
    }

    // 発射速度
    public int getFireSpeed() {
        return 20;
    }

    // 弾のダメージ
    public int getShootDamage() {
        return 3;
    }

    // 弾の速度
    public float getBulletSpeed() {
        return 1.0f;
    }

    // 弾の範囲
    public double getShootRange() {
        return 8.0;
    }

    // 弾のアイテム
    public Item getBulletItem() {
        return Items.FIRE_CHARGE;
    }

    @Override
    public Text getDisplayName() {
        return TextUtil.translatable("container.cubicturret.cubic_turret");
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new CubicTurretScreenHandler(syncId, inv, this);
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        int[] result = new int[getItems().size() - 1];
        for (int i = 0; i < result.length; i++) {
            result[i] = i + 1;
        }
        return result;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return dir != Direction.DOWN;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return dir == Direction.DOWN;
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }
}
