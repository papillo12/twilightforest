package twilightforest.block;

import net.minecraft.block.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.util.*;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;
import twilightforest.TFSounds;
import twilightforest.entity.projectile.MoonwormShotEntity;

import javax.annotation.Nullable;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;

public abstract class CritterBlock extends DirectionalBlock implements SimpleWaterloggedBlock {
	private final float WIDTH = getWidth();
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	private final VoxelShape DOWN_BB  = Shapes.create(new AABB(0.5F -WIDTH, 1.0F -WIDTH * 2.0F, 0.2F, 0.5F +WIDTH, 1.0F, 0.8F));
	private final VoxelShape UP_BB    = Shapes.create(new AABB(0.5F - WIDTH, 0.0F, 0.2F, 0.5F + WIDTH, WIDTH * 2.0F, 0.8F));
	private final VoxelShape NORTH_BB = Shapes.create(new AABB(0.5F - WIDTH, 0.2F, 1.0F - WIDTH * 2.0F, 0.5F + WIDTH, 0.8F, 1.0F));
	private final VoxelShape SOUTH_BB = Shapes.create(new AABB(0.5F - WIDTH, 0.2F, 0.0F, 0.5F + WIDTH, 0.8F, WIDTH * 2.0F));
	private final VoxelShape WEST_BB  = Shapes.create(new AABB(1.0F - WIDTH * 2.0F, 0.2F, 0.5F - WIDTH, 1.0F, 0.8F, 0.5F + WIDTH));
	private final VoxelShape EAST_BB  = Shapes.create(new AABB(0.0F, 0.2F, 0.5F - WIDTH, WIDTH * 2.0F, 0.8F, 0.5F + WIDTH));

	protected CritterBlock(Properties props) {
		super(props);
		this.registerDefaultState(stateDefinition.any().setValue(FACING, Direction.UP).setValue(WATERLOGGED, Boolean.valueOf(false)));
	}

	public float getWidth() {
		return 0.15F;
	}

	@Override
	@Deprecated
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		switch (state.getValue(FACING)) {
			case DOWN:
				return DOWN_BB;
			case UP:
			default:
				return UP_BB;
			case NORTH:
				return NORTH_BB;
			case SOUTH:
				return SOUTH_BB;
			case WEST:
				return WEST_BB;
			case EAST:
				return EAST_BB;
		}
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		Direction clicked = context.getClickedFace();
		FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
		BlockState state = defaultBlockState().setValue(FACING, clicked).setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));

		if (canSurvive(state, context.getLevel(), context.getClickedPos())) {
			return state;
		}

		for (Direction dir : context.getNearestLookingDirections()) {
			state = defaultBlockState().setValue(FACING, dir.getOpposite());
			if (canSurvive(state, context.getLevel(), context.getClickedPos())) {
				return state;
			}
		}
		return null;
	}

	@Override
	@Deprecated
	public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
		if (state.getValue(WATERLOGGED)) {
			world.getLiquidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
		}
		if (!canSurvive(state, world, pos)) {
			return Blocks.AIR.defaultBlockState();
		} else {
			return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
		}
	}

	@Override
	@Deprecated
	public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
		Direction facing = state.getValue(DirectionalBlock.FACING);
		BlockPos restingPos = pos.relative(facing.getOpposite());
		return canSupportCenter(world, restingPos, facing);
	}

	public abstract ItemStack getSquishResult(); // oh no!

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	@Override
	public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (newState.getBlock() instanceof AnvilBlock) {
			worldIn.playSound(null, pos, TFSounds.BUG_SQUISH, SoundSource.BLOCKS, 1, 1);
			ItemEntity squish = new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ());
			squish.spawnAtLocation(this.getSquishResult().getStack());
		}
		super.onRemove(state, worldIn, pos, newState, isMoving);
	}

	@Override
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
		ItemStack stack = player.getItemInHand(handIn);
		if(stack.getItem() == Items.GLASS_BOTTLE) {
			if(this == TFBlocks.firefly.get()) {
				if(!player.isCreative()) stack.shrink(1);
				player.inventory.add(new ItemStack(TFBlocks.firefly_jar.get()));
				worldIn.setBlockAndUpdate(pos,state.getValue(WATERLOGGED) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState());
				return InteractionResult.SUCCESS;
			} else if(this == TFBlocks.cicada.get()) {
				if(!player.isCreative()) stack.shrink(1);
				player.inventory.add(new ItemStack(TFBlocks.cicada_jar.get()));
				worldIn.setBlockAndUpdate(pos,state.getValue(WATERLOGGED) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState());
				return InteractionResult.SUCCESS;
			}
		}
		return InteractionResult.PASS;
	}

	@Override
	public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
		if (entityIn instanceof Projectile && !(entityIn instanceof MoonwormShotEntity)) {
			worldIn.setBlockAndUpdate(pos, state.getValue(WATERLOGGED) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState());
			ItemEntity squish = new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ());
			squish.spawnAtLocation(this.getSquishResult().getStack());
		}
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Nullable
	@Override
	public abstract BlockEntity createTileEntity(BlockState state, BlockGetter world);

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING, WATERLOGGED);
	}

}
