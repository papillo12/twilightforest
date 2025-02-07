package twilightforest.world.surfacebuilders;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilderBaseConfiguration;

import java.util.Random;

public class TFHighlandsSurfaceBuilder extends TFDefaultSurfaceBuilder {

	public TFHighlandsSurfaceBuilder(Codec<SurfaceBuilderBaseConfiguration> config) {
		super(config);
	}

	@Override
	public void apply(Random rand, ChunkAccess primer, Biome biome, int x, int z, int startheight, double noiseVal, BlockState defaultBlock, BlockState defaultFluid, int sealevel, long seed, SurfaceBuilderBaseConfiguration config) {
		BlockState topBlock = config.getTopMaterial();
		BlockState fillerBlock = config.getUnderMaterial();

		if (noiseVal > 1.75D) {
			topBlock = Blocks.COARSE_DIRT.defaultBlockState();
		} else if (noiseVal > -0.95D) {
			topBlock = Blocks.PODZOL.defaultBlockState();
		}

		this.genTwilightBiomeTerrain(rand, primer, biome, x, z, startheight, noiseVal, defaultBlock, defaultFluid, topBlock, fillerBlock, config.getUnderwaterMaterial(), sealevel);
	}
}
