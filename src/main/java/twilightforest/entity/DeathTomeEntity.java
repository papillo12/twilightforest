package twilightforest.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import twilightforest.TFSounds;
import twilightforest.entity.projectile.TomeBoltEntity;
import twilightforest.loot.TFTreasure;

import javax.annotation.Nullable;

import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;

public class DeathTomeEntity extends Monster implements RangedAttackMob {

	public DeathTomeEntity(EntityType<? extends DeathTomeEntity> type, Level world) {
		super(type, world);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(4, new RangedAttackGoal(this, 1, 100, 5));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
	}

	public static AttributeSupplier.Builder registerAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.MAX_HEALTH, 30.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.25D)
				.add(Attributes.ATTACK_DAMAGE, 4);
	}

	@Override
	public void aiStep() {
		super.aiStep();

		for (int i = 0; i < 1; ++i) {
			this.level.addParticle(ParticleTypes.ENCHANT, this.getX() + (this.random.nextDouble() - 0.5D) * this.getBbWidth(), this.getY() + this.random.nextDouble() * (this.getBbHeight() - 0.75D) + 0.5D, this.getZ() + (this.random.nextDouble() - 0.5D) * this.getBbWidth(),
					0, 0.5, 0);
		}
	}

	@Override
	public boolean hurt(DamageSource src, float damage) {
		if (src.isFire()) {
			damage *= 2;
		}

		if (super.hurt(src, damage)) {
			if (!level.isClientSide) {
				LootContext ctx = createLootContext(true, src).create(LootContextParamSets.ENTITY);

				level.getServer().getLootTables().get(TFTreasure.DEATH_TOME_HURT).getRandomItems(ctx, s -> spawnAtLocation(s, 1.0F));
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected boolean canRide(Entity entityIn) {
		return false;
	}

	@Nullable
	@Override
	protected SoundEvent getAmbientSound() {
		return TFSounds.TOME_IDLE;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return TFSounds.TOME_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return TFSounds.TOME_DEATH;
	}

	@Override
	public void performRangedAttack(LivingEntity target, float distanceFactor) {
		ThrowableProjectile projectile = new TomeBoltEntity(TFEntities.tome_bolt, this.level, this);
		double tx = target.getX() - this.getX();
		double ty = target.getY() + target.getEyeHeight() - 1.100000023841858D - projectile.getY();
		double tz = target.getZ() - this.getZ();
		float heightOffset = Mth.sqrt(tx * tx + tz * tz) * 0.2F;
		projectile.shoot(tx, ty + heightOffset, tz, 0.6F, 6.0F);
		this.level.addFreshEntity(projectile);
	}
}
