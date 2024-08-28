package net.pitan76.cubicturret.entity;

import net.minecraft.item.Items;
import net.pitan76.cubicturret.tile.CubicTurretBlockEntity;
import net.pitan76.mcpitanlib.api.util.EntityUtil;
import net.pitan76.mcpitanlib.api.util.ItemStackUtil;
import net.pitan76.mcpitanlib.api.util.WorldUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BulletEntity extends ThrownItemEntity {

    public CubicTurretBlockEntity turret;
    public float addedDamage = 0f;
    public @Nullable LivingEntity owner = null;

    public BulletEntity(World world, LivingEntity owner, CubicTurretBlockEntity turret) {
        super((EntityType<? extends ThrownItemEntity>) Entities.BULLET_ENTITY.get(), owner, world);
        this.turret = turret;
        this.owner = owner;
    }

    public BulletEntity(EntityType<?> entityType, World world) {
        super((EntityType<? extends ThrownItemEntity>) entityType, world);
    }

    public BulletEntity(World world, double x, double y, double z, CubicTurretBlockEntity turret) {
        super((EntityType<? extends ThrownItemEntity>) Entities.BULLET_ENTITY.get(), x, y, z, world);
        this.turret = turret;
    }

    private ParticleEffect getParticleParameters() {
        ItemStack stack = this.getItem();
        return new ItemStackParticleEffect(ParticleTypes.ITEM, stack.isEmpty() ? ItemStackUtil.getDefaultStack(getDefaultItem()) : stack);
    }

    public void handleStatus(byte status) {
        if (status == 3) {
            ParticleEffect particleEffect = this.getParticleParameters();

            for(int i = 0; i < 8; ++i) {
                EntityUtil.getWorld(this).addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            }
        }

    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        if (turret == null) return;
        EntityUtil.damageWithThrownProjectile(entity, turret.getShootDamage() + getAddedDamage(), this, this.owner == null ? this : this.owner);
    }

    public float getAddedDamage() {
        return addedDamage;
    }

    public void setAddedDamage(float addedDamage) {
        this.addedDamage = addedDamage;
    }

    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        World world = EntityUtil.getWorld(this);
        if (world != null && !WorldUtil.isClient(world)) {
            WorldUtil.sendEntityStatus(world, this, (byte)3);
            try {
                this.discard();
            } catch (ArrayIndexOutOfBoundsException ignore) {}
        }
    }

    @Override
    protected Item getDefaultItem() {
        if (turret == null) return Items.FIRE_CHARGE;
        return turret.getBulletItem();
    }
}