package twilightforest.tileentity.spawner;

import net.minecraft.world.entity.player.Player;
import twilightforest.entity.TFEntities;
import twilightforest.entity.boss.SnowQueenEntity;
import twilightforest.tileentity.TFTileEntities;

public class SnowQueenSpawnerTileEntity extends BossSpawnerTileEntity<SnowQueenEntity> {

	public SnowQueenSpawnerTileEntity() {
		super(TFTileEntities.SNOW_QUEEN_SPAWNER.get(), TFEntities.snow_queen);
	}

	@Override
	public boolean anyPlayerInRange() {
		Player closestPlayer = level.getNearestPlayer(worldPosition.getX() + 0.5D, worldPosition.getY() + 0.5D, worldPosition.getZ() + 0.5D, getRange(), false);
		return closestPlayer != null && closestPlayer.getY() > worldPosition.getY() - 4;
	}
}
