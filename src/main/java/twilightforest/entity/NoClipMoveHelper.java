package twilightforest.entity;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.util.Mth;

public class NoClipMoveHelper extends MoveControl {
	private final LivingEntity parentEntity;
	private int courseChangeCooldown;

	public NoClipMoveHelper(Mob entity) {
		super(entity);
		this.parentEntity = entity;
	}

	@Override
	public void tick() {
		if (this.operation == MoveControl.Operation.MOVE_TO) {
			double dx = this.getWantedX() - this.parentEntity.getX();
			double dy = this.getWantedY() - this.parentEntity.getY();
			double dz = this.getWantedZ() - this.parentEntity.getZ();
			double dist = dx * dx + dy * dy + dz * dz;

			if (this.courseChangeCooldown-- <= 0) {
				this.courseChangeCooldown += this.parentEntity.getRandom().nextInt(5) + 2;
				dist = Mth.sqrt(dist);

				this.parentEntity.setDeltaMovement(this.parentEntity.getDeltaMovement().add((dx / dist * 0.1D) * speedModifier, (dy / dist * 0.1D) * speedModifier, (dz / dist * 0.1D) * speedModifier));
			}
		}
	}
}
