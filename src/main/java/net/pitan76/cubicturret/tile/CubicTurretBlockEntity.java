package net.pitan76.cubicturret.tile;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.FireChargeItem;
import net.minecraft.item.SnowballItem;
import net.minecraft.item.SpectralArrowItem;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.pitan76.cubicturret.block.Blocks;
import net.pitan76.cubicturret.block.CubicTurretBlock;
import net.pitan76.cubicturret.entity.BulletEntity;
import net.pitan76.cubicturret.item.Items;
import net.pitan76.cubicturret.screen.CubicTurretScreenHandler;
import net.pitan76.cubicturret.turret.TargetMode;
import net.pitan76.mcpitanlib.api.entity.Player;
import net.pitan76.mcpitanlib.api.event.block.TileCreateEvent;
import net.pitan76.mcpitanlib.api.event.container.factory.DisplayNameArgs;
import net.pitan76.mcpitanlib.api.event.nbt.ReadNbtArgs;
import net.pitan76.mcpitanlib.api.event.nbt.WriteNbtArgs;
import net.pitan76.mcpitanlib.api.event.tile.TileTickEvent;
import net.pitan76.mcpitanlib.api.gui.args.CreateMenuEvent;
import net.pitan76.mcpitanlib.api.gui.inventory.IInventory;
import net.pitan76.mcpitanlib.api.gui.inventory.sided.ChestStyleSidedInventory;
import net.pitan76.mcpitanlib.api.gui.inventory.sided.args.AvailableSlotsArgs;
import net.pitan76.mcpitanlib.api.gui.v2.SimpleScreenHandlerFactory;
import net.pitan76.mcpitanlib.api.sound.CompatSoundCategory;
import net.pitan76.mcpitanlib.api.sound.CompatSoundEvents;
import net.pitan76.mcpitanlib.api.tile.CompatBlockEntity;
import net.pitan76.mcpitanlib.api.tile.ExtendBlockEntityTicker;
import net.pitan76.mcpitanlib.api.util.*;
import net.pitan76.mcpitanlib.api.util.collection.ItemStackList;
import net.pitan76.mcpitanlib.api.util.math.random.CompatRandom;
import net.pitan76.mcpitanlib.midohra.block.BlockWrapper;
import net.pitan76.mcpitanlib.midohra.entity.EntityWrapper;
import net.pitan76.mcpitanlib.midohra.entity.projectile.*;
import net.pitan76.mcpitanlib.midohra.item.ItemStack;
import net.pitan76.mcpitanlib.midohra.item.ItemWrapper;
import net.pitan76.mcpitanlib.midohra.item.MCItems;
import net.pitan76.mcpitanlib.midohra.nbt.NbtCompound;
import net.pitan76.mcpitanlib.midohra.util.math.BlockPos;
import net.pitan76.mcpitanlib.midohra.util.math.Box;
import net.pitan76.mcpitanlib.midohra.util.math.Vector3d;
import net.pitan76.mcpitanlib.midohra.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CubicTurretBlockEntity extends CompatBlockEntity implements ExtendBlockEntityTicker<CubicTurretBlockEntity>, SimpleScreenHandlerFactory, ChestStyleSidedInventory, IInventory {

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
        NbtCompound nbt = args.getNbtM();

        InventoryUtil.writeNbt(args, inventory);
        if (level != 0)
            nbt.putInt("level", level);
    }

    @Override
    public void readNbt(ReadNbtArgs args) {
        super.readNbt(args);
        NbtCompound nbt = args.getNbtM();

        InventoryUtil.readNbt(args, inventory);
        if (nbt.has("level"))
            level = nbt.getInt("level");
    }

    @Override
    public void tick(TileTickEvent<CubicTurretBlockEntity> e) {
        if (e.isClient()) return;
        World world = e.getMidohraWorld();

        if (world.getTime() % getFireSpeed() != 0) return;
        if (inventory.isEmpty()) return;

        if (level == 0) {
            BlockWrapper block = e.getBlockWrapper();
            if (block.instanceOf(CubicTurretBlock.class)) {
                level = ((CubicTurretBlock) block.getOrDefault(Blocks.CUBIC_TURRET_BLOCK.get())).getLevel();
                if (level == 0) level = 1;
            }
        }

        // if (e.world.getClosestPlayer(e.pos.getX(), e.pos.getY(), e.pos.getZ(), getBulletRange(), false) == null) return;

        if (!hasBulletStack()) return;
        ItemStack bulletStack = getBulletStack();

        if (shoot(e, bulletStack) && level != -1) {
            bulletStack.decrement(1);
        }
    }

    public boolean hasBulletStack() {
        for (ItemStack stack : inventory.toMidohra()) {
            if (stack.isEmpty()) continue;
            if (!isBulletItem(stack.getItem())) continue;
            return true;
        }
        return false;
    }

    public int getBulletAmount() {
        int amount = 0;
        for (ItemStack stack : inventory.toMidohra()) {
            if (stack.isEmpty()) continue;
            if (!isBulletItem(stack.getItem())) continue;
            amount += stack.getCount();
        }
        return amount;
    }

    public ItemStack getBulletStack() {
        for (ItemStack stack : inventory.toMidohra()) {
            if (stack.isEmpty()) continue;
            if (!isBulletItem(stack.getItem())) continue;

            return stack;
        }
        return ItemStack.EMPTY;
    }

    public float getDivergence() {
        return 0.1F;
    }

    public boolean shoot(TileTickEvent<CubicTurretBlockEntity> e, ItemStack bulletStack) {
        EntityWrapper target = getTargetEntity(e);
        if (target == null) return false;

        Vector3d targetPos = target.getPos();
        BlockPos pos = e.getMidohraPos();

        double dx = targetPos.getX() - pos.getX();
        double dy = targetPos.getY() - pos.getY();
        double dz = targetPos.getZ() - pos.getZ();

        double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);

        double vx = dx / distance;
        double vy = dy / distance;
        double vz = dz / distance;

        float divergence = getDivergence();

        shoot(e, vx, vy, vz, divergence, bulletStack);
        return true;
    }

    public void shoot(TileTickEvent<CubicTurretBlockEntity> e, double vx, double vy, double vz, float divergence, ItemStack bulletStack) {
        Vector3d pos = e.getMidohraPos().toCenterVector3d().add(0, 0.3, 0);
        Vector3d velocity = new Vector3d(vx, vy, vz);
        shoot(e, pos, velocity, divergence, bulletStack);
    }

    public void shoot(TileTickEvent<CubicTurretBlockEntity> e, Vector3d _pos, Vector3d velocity, float divergence, ItemStack bulletStack) {
        ItemWrapper item = bulletStack.getItem();
        World world = e.getMidohraWorld();
        Vector3d pos = _pos.add(velocity);

        if (item.instanceOf(FireChargeItem.class)) {
            SmallFireballEntityWrapper fireball = SmallFireballEntityWrapper.create(world, pos, velocity);
            fireball.setStack(bulletStack);
            world.spawnEntity(fireball);
            return;
        }
        if (item.instanceOf(ArrowItem.class)) {
            ArrowEntityWrapper arrow = ArrowEntityWrapper.create(world, pos, bulletStack);
            arrow.setVelocity(velocity, 1.0f, divergence);
            world.spawnEntity(arrow);
            return;
        }
        if (item.instanceOf(SpectralArrowItem.class)) {
            SpectralArrowEntityWrapper arrow = SpectralArrowEntityWrapper.create(world, pos);
            arrow.setVelocity(velocity, 1.0f, divergence);
            world.spawnEntity(arrow);
            return;
        }
        if (item.instanceOf(SnowballItem.class)) {
            SnowballEntityWrapper snowball = SnowballEntityWrapper.create(world, pos);
            snowball.setStack(bulletStack);
            snowball.setVelocity(velocity, 1.0f, divergence);
            world.spawnEntity(snowball);
            return;
        }

        ThrownItemEntityWrapper<BulletEntity> bullet = ThrownItemEntityWrapper.ofRaw(new BulletEntity(world, pos, this));

        bullet.setStack(bulletStack);
        bullet.setVelocity(velocity, getBulletSpeed() + 2.0f, divergence);
        world.spawnEntity(bullet);

        CompatRandom random = world.getRandom();

        world.playSound(null, e.getMidohraPos(), CompatSoundEvents.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR, CompatSoundCategory.NEUTRAL, 0.5F, 0.3F / (random.nextFloat() * 0.4F + 0.8F));
    }

    // 周辺の敵を取得
    public List<EntityWrapper> getTargetEntities(TileTickEvent<CubicTurretBlockEntity> e) {
        if (targetMode == TargetMode.NONE) return new ArrayList<>();

        World world = e.getMidohraWorld();
        Vector3d pos = e.getMidohraPos().toVector3d();

        // MobEntity
        Vector3d pos1 = pos.sub(getShootRange(), getShootBottom(), getShootRange());
        Vector3d pos2 = pos.add(getShootRange(), getShootTop(), getShootRange());

        Box box = new Box(pos1, pos2);

        List<EntityWrapper> list = new ArrayList<>(world.getEntitiesByClassM(LivingEntity.class, box));

        if (targetMode == TargetMode.ALL) return list;

        if (!list.isEmpty())
            list.removeIf(entity -> !targetMode.isTarget(entity));

        return list;
    }

    public EntityWrapper getTargetEntity(TileTickEvent<CubicTurretBlockEntity> e) {
        List<EntityWrapper> list = getTargetEntities(e);
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
    public ItemWrapper[] getBulletItems() {
        return new ItemWrapper[]{
                MCItems.FIRE_CHARGE, MCItems.ARROW, MCItems.TIPPED_ARROW, MCItems.SPECTRAL_ARROW, MCItems.SNOWBALL,
                ItemWrapper.of(Items.TURRET_BULLET_ITEM.getOrNull())
        };
    }

    public boolean isBulletItem(ItemWrapper item) {
        return Arrays.stream(getBulletItems()).collect(Collectors.toList()).contains(item);
    }

    @Override
    public Text getDisplayName(DisplayNameArgs args) {
        return TextUtil.translatable("container.cubicturret.cubic_turret");
    }

    @Override
    public ScreenHandler createMenu(CreateMenuEvent e) {
        return new CubicTurretScreenHandler(e.getSyncId(), e.getPlayerInventory(), this);
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
    public ItemStackList getItems() {
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
