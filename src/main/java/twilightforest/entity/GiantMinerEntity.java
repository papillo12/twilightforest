package twilightforest.entity;

import net.minecraft.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;
import net.minecraft.world.*;
import twilightforest.block.TFBlocks;
import twilightforest.util.TFDamageSources;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;

public class GiantMinerEntity extends Monster {

	public GiantMinerEntity(EntityType<? extends GiantMinerEntity> type, Level world) {
		super(type, world);

		for (EquipmentSlot slot : EquipmentSlot.values()) {
			setDropChance(slot, 0);
		}
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new FloatGoal(this));
		this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, false) {
			@Override
			protected double getAttackReachSqr(LivingEntity attackTarget) {
				return this.mob.getBbWidth() * this.mob.getBbHeight();
			}
		});
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
	}

	public static AttributeSupplier.Builder registerAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.MAX_HEALTH, 80.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.23D)
				.add(Attributes.ATTACK_DAMAGE, 2.0D)
				.add(Attributes.FOLLOW_RANGE, 40.0D);
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
		SpawnGroupData data = super.finalizeSpawn(worldIn, difficulty, reason, spawnDataIn, dataTag);
		populateDefaultEquipmentSlots(difficulty);
		populateDefaultEquipmentEnchantments(difficulty);

		return data;
	}

	@Override
	protected void populateDefaultEquipmentSlots(DifficultyInstance difficulty) {
		setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.STONE_PICKAXE));
	}

	@Override
	public boolean doHurtTarget(Entity entityIn) {
		entityIn.hurt(TFDamageSources.ANT(this), (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE));
		return super.doHurtTarget(entityIn);
	}

	@Override
	public int getMaxSpawnClusterSize() {
		return 1;
	}

	@Override
	public boolean checkSpawnRules(LevelAccessor worldIn, MobSpawnType spawnReasonIn) {
		List<GiantMinerEntity> giantsNearby = worldIn.getEntitiesOfClass(GiantMinerEntity.class, this.getBoundingBox().inflate(50));
		return giantsNearby.size() < 7;
	}

	public static boolean canSpawn(EntityType<? extends GiantMinerEntity> type, LevelAccessor world, MobSpawnType reason, BlockPos pos, Random rand) {
		return pos.getY() > 60 && (Mob.checkMobSpawnRules(type, world, reason, pos, rand) || world.getBlockState(pos).getBlock() == TFBlocks.wispy_cloud.get() || world.getBlockState(pos).getBlock() == TFBlocks.fluffy_cloud.get());
	}

	@Override
	protected boolean canRide(Entity entityIn) {
		return false;
	}
}
