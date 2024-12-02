package net.pitan76.cubicturret.tile;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.entity.projectile.SpectralArrowEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.*;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Box;
import net.pitan76.cubicturret.block.CubicTurretBlock;
import net.pitan76.cubicturret.entity.BulletEntity;
import net.pitan76.cubicturret.screen.CubicTurretScreenHandler;
import net.pitan76.cubicturret.turret.TargetMode;
import net.pitan76.mcpitanlib.api.entity.Player;
import net.pitan76.mcpitanlib.api.event.block.TileCreateEvent;
import net.pitan76.mcpitanlib.api.event.nbt.ReadNbtArgs;
import net.pitan76.mcpitanlib.api.event.nbt.WriteNbtArgs;
import net.pitan76.mcpitanlib.api.event.tile.TileTickEvent;
import net.pitan76.mcpitanlib.api.gui.inventory.IInventory;
import net.pitan76.mcpitanlib.api.gui.inventory.sided.ChestStyleSidedInventory;
import net.pitan76.mcpitanlib.api.gui.inventory.sided.args.AvailableSlotsArgs;
import net.pitan76.mcpitanlib.api.sound.CompatSoundCategory;
import net.pitan76.mcpitanlib.api.sound.CompatSoundEvents;
import net.pitan76.mcpitanlib.api.tile.CompatBlockEntity;
import net.pitan76.mcpitanlib.api.tile.ExtendBlockEntityTicker;
import net.pitan76.mcpitanlib.api.util.*;
import net.pitan76.mcpitanlib.api.util.collection.ItemStackList;
import net.pitan76.mcpitanlib.api.util.entity.ArrowEntityUtil;
import net.pitan76.mcpitanlib.api.util.entity.SmallFireballEntityUtil;
import net.pitan76.mcpitanlib.api.util.entity.SnowballEntityUtil;
import net.pitan76.mcpitanlib.api.util.entity.SpectralArrowEntityUtil;
import net.pitan76.mcpitanlib.api.util.math.BoxUtil;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CubicTurretBlockEntity extends CompatBlockEntity implements ExtendBlockEntityTicker<CubicTurretBlockEntity>, NamedScreenHandlerFactory, ChestStyleSidedInventory, IInventory {

    public int level = 0;

    public ItemStackList inventory = ItemStackList.ofSize(9);

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
        if (level != 0)
            NbtUtil.putInt(args.nbt, "level", level);
    }

    @Override
    public void readNbt(ReadNbtArgs args) {
        super.readNbt(args);
        InventoryUtil.readNbt(args, inventory);
        if (NbtUtil.has(args.nbt, "level"))
            level = NbtUtil.getInt(args.nbt, "level");
    }

    @Override
    public void tick(TileTickEvent<CubicTurretBlockEntity> e) {
        if (e.isClient()) return;
        if (e.world.getTime() % getFireSpeed() != 0) return;
        if (inventory.isEmpty()) return;

        if (level == 0) {
            Block block = e.getBlockState().getBlock().get();
            if (block instanceof CubicTurretBlock) {
                level = ((CubicTurretBlock) block).getLevel();
                if (level == 0) level = 1;
            }
        }

        // if (e.world.getClosestPlayer(e.pos.getX(), e.pos.getY(), e.pos.getZ(), getBulletRange(), false) == null) return;

        if (!hasBulletStack()) return;
        ItemStack bulletStack = getBulletStack();

        if (shoot(e, bulletStack) && level != -1) {
            ItemStackUtil.decrementCount(bulletStack, 1);
        }
    }

    public boolean hasBulletStack() {
        for (ItemStack stack : inventory) {
            if (stack.isEmpty()) continue;
            if (!isBulletItem(stack.getItem())) continue;
            return true;
        }
        return false;
    }

    public int getBulletAmount() {
        int amount = 0;
        for (ItemStack stack : inventory) {
            if (stack.isEmpty()) continue;
            if (!isBulletItem(stack.getItem())) continue;
            amount += stack.getCount();
        }
        return amount;
    }

    public ItemStack getBulletStack() {
        for (ItemStack stack : inventory) {
            if (stack.isEmpty()) continue;
            if (!isBulletItem(stack.getItem())) continue;

            return stack;
        }
        return ItemStackUtil.empty();
    }

    public float getDivergence() {
        return 0.1F;
    }

    public boolean shoot(TileTickEvent<CubicTurretBlockEntity> e, ItemStack bulletStack) {
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

        shoot(e, vx, vy, vz, divergence, bulletStack);
        return true;
    }

    public void shoot(TileTickEvent<CubicTurretBlockEntity> e, double vx, double vy, double vz, float divergence, ItemStack bulletStack) {
        shoot(e, e.pos.getX() + 0.5, e.pos.getY() + 0.8, e.pos.getZ() + 0.5, vx, vy, vz, divergence, bulletStack);
    }

    public void shoot(TileTickEvent<CubicTurretBlockEntity> e, double x, double y, double z, double vx, double vy, double vz, float divergence, ItemStack bulletStack) {
        Item item = bulletStack.getItem();
        if (item instanceof FireChargeItem) {
            SmallFireballEntity fireball = SmallFireballEntityUtil.create(e.world, x + vx, y + vy, z + vz, vx, vy, vz);
            SmallFireballEntityUtil.setItem(fireball, bulletStack);
            WorldUtil.spawnEntity(e.world, fireball);
            return;
        }
        if (item instanceof ArrowItem) {
            ArrowEntity arrow = ArrowEntityUtil.create(e.world, x + vx, y + vy, z + vz, bulletStack);
            ArrowEntityUtil.setVelocity(arrow, vx, vy, vz, 1.0f, divergence);
            WorldUtil.spawnEntity(e.world, arrow);
            return;
        }
        if (item instanceof SpectralArrowItem) {
            SpectralArrowEntity arrow = SpectralArrowEntityUtil.create(e.world, x + vx, y + vy, z + vz);
            SpectralArrowEntityUtil.setVelocity(arrow, vx, vy, vz, 1.0f, divergence);
            WorldUtil.spawnEntity(e.world, arrow);
            return;
        }
        if (item instanceof SnowballItem) {
            SnowballEntity snowball = SnowballEntityUtil.create(e.world, x + vx, y + vy, z + vz);
            SnowballEntityUtil.setItem(snowball, bulletStack);
            SnowballEntityUtil.setVelocity(snowball, vx, vy, vz, 1.0f, divergence);
            WorldUtil.spawnEntity(e.world, snowball);
            return;
        }
        BulletEntity bullet = new BulletEntity(e.world, x + vx, y + vy, z + vz, this);
        bullet.callSetItem(bulletStack);
        bullet.setVelocity(vx, vy, vz, getBulletSpeed() + 2.0f, divergence);
        WorldUtil.spawnEntity(e.world, bullet);

        WorldUtil.playSound(e.world, null, e.pos, CompatSoundEvents.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR, CompatSoundCategory.NEUTRAL, 0.5F, 0.3F / (WorldRandomUtil.nextFloat(e.world) * 0.4F + 0.8F));
    }

    // 周辺の敵を取得
    public List<Entity> getTargetEntities(TileTickEvent<CubicTurretBlockEntity> e) {
        if (targetMode == TargetMode.NONE) return new ArrayList<>();

        // MobEntity
        Box box = BoxUtil.createBox(e.pos.getX() - getShootRange(), e.pos.getY() - getShootBottom(), e.pos.getZ() - getShootRange(),
                e.pos.getX() + getShootRange(), e.pos.getY() + getShootTop(), e.pos.getZ() + getShootRange());

        List<Entity> list = new ArrayList<>(WorldUtil.getEntitiesByClass(e.world, LivingEntity.class, box, Entity::isAlive));

        if (targetMode == TargetMode.ALL) return list;

        if (!list.isEmpty())
            list.removeIf(entity -> !targetMode.isTarget(entity));

        return list;
    }

    public Entity getTargetEntity(TileTickEvent<CubicTurretBlockEntity> e) {
        List<Entity> list = getTargetEntities(e);
        if (list.isEmpty()) return null;

        return list.get(0);
    }

    // 発射速度
    public int getFireSpeed() {
        if (level == 1) return 30;
        if (level == 2) return 25;
        if (level == 3) return 20;
        if (level == 4) return 15;
        return 10;
    }

    // 弾のダメージ (矢や火炎玉などの場合は無視)
    public int getShootDamage() {
        return 3;
    }

    // 弾の速度
    public float getBulletSpeed() {
        if (level == 1) return 1.0f;
        if (level == 2) return 1.2f;
        if (level == 3) return 1.5f;
        if (level == 4) return 2.0f;
        return 3.0f;
    }

    // 弾の範囲
    public double getShootRange() {
        if (level == 1) return 8.0;
        if (level == 2) return 10.0;
        if (level == 3) return 12.0;
        if (level == 4) return 15.0;
        return 18.0;
    }

    public double getShootTop() {
        if (level == 1) return 12.0;
        if (level == 2) return 15.0;
        if (level == 3) return 18.0;
        if (level == 4) return 20.0;
        return 24.0;
    }

    public double getShootBottom() {
        if (level == 1) return 0.0;
        if (level == 2) return 1.0;
        if (level == 3) return 2.0;
        if (level == 4) return 4.0;
        return 6.0;
    }

    // 弾のアイテム
    public Item[] getBulletItems() {
        return new Item[]{Items.FIRE_CHARGE, Items.ARROW, Items.TIPPED_ARROW, Items.SPECTRAL_ARROW, Items.SNOWBALL, net.pitan76.cubicturret.item.Items.TURRET_BULLET_ITEM.getOrNull()};
    }

    public boolean isBulletItem(Item item) {
        return Arrays.stream(getBulletItems()).collect(Collectors.toList()).contains(item);
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
    public int[] getAvailableSlots(AvailableSlotsArgs args) {
        int[] result = new int[getItems().size() - 1];
        for (int i = 0; i < result.length; i++) {
            result[i] = i + 1;
        }
        return result;
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    public TargetMode targetMode = TargetMode.MONSTER;

    public void toggle(@Nullable Player player) {
        targetMode = targetMode.next();
        if (player != null) {
            player.sendMessage(targetMode.getTranslation());
        }
    }

    public void toggle() {
        toggle(null);
    }
}
