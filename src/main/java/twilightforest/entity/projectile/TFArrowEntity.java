package twilightforest.entity.projectile;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.network.NetworkHooks;

public abstract class TFArrowEntity extends AbstractArrow implements ITFProjectile {

	public TFArrowEntity(EntityType<? extends TFArrowEntity> type, Level worldIn) {
		super(type, worldIn);
	}

	public TFArrowEntity(EntityType<? extends TFArrowEntity> type, Level worldIn, LivingEntity shooter) {
		super(type, shooter, worldIn);
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected ItemStack getPickupItem() {
		return new ItemStack(Items.ARROW);
	}
}
