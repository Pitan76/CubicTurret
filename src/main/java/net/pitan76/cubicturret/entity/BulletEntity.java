package net.pitan76.cubicturret.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.pitan76.cubicturret.tile.CubicTurretBlockEntity;
import net.pitan76.mcpitanlib.api.entity.CompatThrownItemEntity;
import net.pitan76.mcpitanlib.api.event.entity.CollisionEvent;
import net.pitan76.mcpitanlib.api.event.entity.EntityHitEvent;
import net.pitan76.mcpitanlib.api.util.particle.effect.CompatItemStackParticleEffect;
import net.pitan76.mcpitanlib.api.util.particle.effect.CompatParticleEffect;
import net.pitan76.mcpitanlib.midohra.entity.EntityWrapper;
import net.pitan76.mcpitanlib.midohra.item.ItemStack;
import net.pitan76.mcpitanlib.midohra.item.ItemWrapper;
import net.pitan76.mcpitanlib.midohra.item.MCItems;
import net.pitan76.mcpitanlib.midohra.util.math.Vector3d;
import net.pitan76.mcpitanlib.midohra.world.World;
import org.jetbrains.annotations.Nullable;

public class BulletEntity extends CompatThrownItemEntity {

    public CubicTurretBlockEntity turret;

    public float addedDamage = 0f;
    public @Nullable EntityWrapper owner = null;

    public BulletEntity(net.minecraft.world.World world, LivingEntity owner, CubicTurretBlockEntity turret) {
        super((EntityType<? extends CompatThrownItemEntity>) Entities.BULLET_ENTITY.get(), owner, world);
        this.turret = turret;
        this.owner = EntityWrapper.of(owner);
    }

    public BulletEntity(EntityType<?> entityType, net.minecraft.world.World world) {
        super((EntityType<? extends CompatThrownItemEntity>) entityType, world);
    }

    public BulletEntity(World world, Vector3d pos, CubicTurretBlockEntity turret) {
        super((EntityType<? extends CompatThrownItemEntity>) Entities.BULLET_ENTITY.get(), pos.x, pos.y, pos.z, world.toMinecraft());
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
        EntityWrapper entity = e.getEntityWrapper();
        if (turret == null) return;
        if (!turret.targetMode.isTarget(entity)) return;

        entity.damageWithThrownProjectile(turret.getShootDamage() + getAddedDamage(), this.getEntityWrapper(), this.owner == null ? this.getEntityWrapper() : this.owner);
        entity.discard();
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
        EntityWrapper entity = getEntityWrapper();

        if (!world.isNull() && !world.isClient()) {
            world.sendEntityStatus(entity, (byte)3);
            try {
                entity.discard();
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
}