package twilightforest.world.feature.tree;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Random;

public class SnowUnderTrees extends Feature<NoneFeatureConfiguration> {

    public SnowUnderTrees(Codec<NoneFeatureConfiguration> config) {
        super(config);
    }

    @Override
    public boolean place(WorldGenLevel world, ChunkGenerator generator, Random rand, BlockPos pos, NoneFeatureConfiguration config) {
        BlockPos.MutableBlockPos mPos = new BlockPos.MutableBlockPos();
        BlockPos.MutableBlockPos mPosDown = new BlockPos.MutableBlockPos();

        for (int xi = 0; xi < 16; xi++) {
            for (int zi = 0; zi < 16; zi++) {
                int x = pos.getX() + xi;
                int z = pos.getZ() + zi;
                mPos.set(x, world.getHeight(Heightmap.Types.MOTION_BLOCKING, x, z) - 1, z);

                if (world.getBlockState(mPos).getBlock() instanceof LeavesBlock) {
                    BlockState state;
                    mPos.set(x, world.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z), z);
                    state = world.getBlockState(mPos);

                    if (state.isAir(world, mPos)) {
                        BlockState stateBelow;
                        mPosDown.set(mPos).move(Direction.DOWN);
                        stateBelow = world.getBlockState(mPosDown);

                        if (stateBelow.isFaceSturdy(world, mPosDown, Direction.UP)) {
                            world.setBlock(mPos, Blocks.SNOW.defaultBlockState(), 2);

                            if (stateBelow.hasProperty(SnowyDirtBlock.SNOWY)) {
                                world.setBlock(mPosDown, stateBelow.setValue(SnowyDirtBlock.SNOWY, true), 2);
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}