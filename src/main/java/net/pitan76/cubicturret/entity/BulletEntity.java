package net.pitan76.cubicturret.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.pitan76.cubicturret.tile.CubicTurretBlockEntity;
import net.pitan76.mcpitanlib.api.entity.CompatThrownItemEntity;
import net.pitan76.mcpitanlib.api.event.entity.CollisionEvent;
import net.pitan76.mcpitanlib.api.event.entity.EntityHitEvent;
import net.pitan76.mcpitanlib.api.util.EntityUtil;
import net.pitan76.mcpitanlib.api.util.WorldUtil;
import net.pitan76.mcpitanlib.api.util.particle.effect.CompatItemStackParticleEffect;
import net.pitan76.mcpitanlib.api.util.particle.effect.CompatParticleEffect;
import net.pitan76.mcpitanlib.midohra.item.ItemStack;
import net.pitan76.mcpitanlib.midohra.item.ItemWrapper;
import net.pitan76.mcpitanlib.midohra.item.MCItems;
import net.pitan76.mcpitanlib.midohra.world.World;
import org.jetbrains.annotations.Nullable;

public class BulletEntity extends CompatThrownItemEntity {

    public CubicTurretBlockEntity turret;

    public float addedDamage = 0f;
    public @Nullable LivingEntity owner = null;

    public BulletEntity(net.minecraft.world.World world, LivingEntity owner, CubicTurretBlockEntity turret) {
        super((EntityType<? extends CompatThrownItemEntity>) Entities.BULLET_ENTITY.get(), owner, world);
        this.turret = turret;
        this.owner = owner;
    }

    public BulletEntity(EntityType<?> entityType, net.minecraft.world.World world) {
        super((EntityType<? extends CompatThrownItemEntity>) entityType, world);
    }

    public BulletEntity(net.minecraft.world.World world, double x, double y, double z, CubicTurretBlockEntity turret) {
        super((EntityType<? extends CompatThrownItemEntity>) Entities.BULLET_ENTITY.get(), x, y, z, world);
        this.turret = turret;
    }

    private CompatParticleEffect getParticleParameters() {
        ItemStack stack = ItemStack.of(this.callGetItem());
        return CompatItemStackParticleEffect.of(stack.isEmpty() ? getDefaultItemWrapper().createStack() : stack);
    }

    @Override
    public void callHandleStatus(byte status) {
        super.callHandleStatus(status);
        if (status == 3) {
            CompatParticleEffect particleEffect = getParticleParameters();
            World world = getMidohraWorld();

            for (int i = 0; i < 8; ++i) {
                world.addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    public void onEntityHit(EntityHitEvent e) {
        super.onEntityHit(e);
        Entity entity = e.getEntity();
        if (turret == null) return;
        if (!turret.targetMode.isTarget(entity)) return;

        EntityUtil.damageWithThrownProjectile(entity, turret.getShootDamage() + getAddedDamage(), this, this.owner == null ? this : this.owner);
        EntityUtil.discard(this);
    }

    public float getAddedDamage() {
        return addedDamage;
    }

    public void setAddedDamage(float addedDamage) {
        this.addedDamage = addedDamage;
    }

    @Override
    public void onCollision(CollisionEvent e) {
        super.onCollision(e);

        World world = getMidohraWorld();
        if (world.toMinecraft() != null && !world.isClient()) {
            WorldUtil.sendEntityStatus(world.toMinecraft(), this, (byte)3);
            try {
                EntityUtil.discard(this);
            } catch (ArrayIndexOutOfBoundsException ignore) {}
        }
    }

    @Override
    public Item getDefaultItemOverride() {
        return getDefaultItemWrapper().get();
    }

    public ItemWrapper getDefaultItemWrapper() {
        return MCItems.FIRE_CHARGE;
    }

    public World getMidohraWorld() {
        return World.of(EntityUtil.getWorld(this));
    }
}