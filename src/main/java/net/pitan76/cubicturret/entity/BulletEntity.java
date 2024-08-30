package net.pitan76.cubicturret.entity;

import net.minecraft.item.Items;
import net.pitan76.cubicturret.tile.CubicTurretBlockEntity;
import net.pitan76.mcpitanlib.api.entity.CompatThrownItemEntity;
import net.pitan76.mcpitanlib.api.event.entity.CollisionEvent;
import net.pitan76.mcpitanlib.api.event.entity.EntityHitEvent;
import net.pitan76.mcpitanlib.api.util.EntityUtil;
import net.pitan76.mcpitanlib.api.util.ItemStackUtil;
import net.pitan76.mcpitanlib.api.util.ParticleEffectUtil;
import net.pitan76.mcpitanlib.api.util.WorldUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BulletEntity extends CompatThrownItemEntity {

    public CubicTurretBlockEntity turret;

    public float addedDamage = 0f;
    public @Nullable LivingEntity owner = null;

    public BulletEntity(World world, LivingEntity owner, CubicTurretBlockEntity turret) {
        super((EntityType<? extends CompatThrownItemEntity>) Entities.BULLET_ENTITY.get(), owner, world);
        this.turret = turret;
        this.owner = owner;
    }

    public BulletEntity(EntityType<?> entityType, World world) {
        super((EntityType<? extends CompatThrownItemEntity>) entityType, world);
    }

    public BulletEntity(World world, double x, double y, double z, CubicTurretBlockEntity turret) {
        super((EntityType<? extends CompatThrownItemEntity>) Entities.BULLET_ENTITY.get(), x, y, z, world);
        this.turret = turret;
    }

    private ParticleEffect getParticleParameters() {
        ItemStack stack = this.callGetItem();
        return ParticleEffectUtil.itemStack.createTypedItem(stack.isEmpty() ? ItemStackUtil.getDefaultStack(getDefaultItemOverride()) : stack);
    }

    @Override
    public void callHandleStatus(byte status) {
        super.callHandleStatus(status);
        if (status == 3) {
            ParticleEffect particleEffect = this.getParticleParameters();

            for(int i = 0; i < 8; ++i) {
                WorldUtil.addParticle(EntityUtil.getWorld(this), particleEffect, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    public void onEntityHit(EntityHitEvent e) {
        super.onEntityHit(e);
        Entity entity = e.getEntity();
        if (turret == null) return;

        EntityUtil.damageWithThrownProjectile(entity, turret.getShootDamage() + getAddedDamage(), this, this.owner == null ? this : this.owner);
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
        World world = EntityUtil.getWorld(this);
        if (world != null && !WorldUtil.isClient(world)) {
            WorldUtil.sendEntityStatus(world, this, (byte)3);
            try {
                EntityUtil.discard(this);
            } catch (ArrayIndexOutOfBoundsException ignore) {}
        }
    }

    @Override
    public Item getDefaultItemOverride() {
        return Items.FIRE_CHARGE;
    }
}