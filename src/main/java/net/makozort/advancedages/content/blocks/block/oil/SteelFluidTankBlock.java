package net.makozort.advancedages.content.blocks.block.oil;

import com.simibubi.create.api.connectivity.ConnectivityHandler;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.fluids.tank.CreativeFluidTankBlockEntity.CreativeSmartFluidTank;
import com.simibubi.create.content.fluids.transfer.GenericItemEmptying;
import com.simibubi.create.content.fluids.transfer.GenericItemFilling;
import com.simibubi.create.foundation.advancement.AdvancementBehaviour;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.blockEntity.ComparatorUtil;
import com.simibubi.create.foundation.fluid.FluidHelper;
import com.simibubi.create.foundation.fluid.FluidHelper.FluidExchange;
import com.simibubi.create.foundation.utility.Lang;

import net.makozort.advancedages.content.blocks.Entity.SteelFluidTankBlockEntity;
import net.makozort.advancedages.reg.AllBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

import static net.minecraftforge.fluids.capability.CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;

public class SteelFluidTankBlock extends Block implements IWrenchable, IBE<SteelFluidTankBlockEntity> {

    public static final BooleanProperty TOP = BooleanProperty.create("top");
    public static final BooleanProperty BOTTOM = BooleanProperty.create("bottom");
    public static final EnumProperty<Shape> SHAPE = EnumProperty.create("shape", Shape.class);

    public static SteelFluidTankBlock regular(Properties p_i48440_1_) {
        return new SteelFluidTankBlock(p_i48440_1_, false);
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        AdvancementBehaviour.setPlacedBy(pLevel, pPos, pPlacer);
    }

    protected SteelFluidTankBlock(Properties p_i48440_1_, boolean creative) {
        super(p_i48440_1_);
        registerDefaultState(defaultBlockState().setValue(TOP, true)
                .setValue(BOTTOM, true)
                .setValue(SHAPE, Shape.WINDOW));
    }

    public static boolean isTank(BlockState state) {
        return state.getBlock() instanceof SteelFluidTankBlock;
    }

    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean moved) {
        if (oldState.getBlock() == state.getBlock())
            return;
        if (moved)
            return;
        withBlockEntityDo(world, pos, SteelFluidTankBlockEntity::updateConnectivity);
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(TOP, BOTTOM, SHAPE);
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter world, BlockPos pos) {
        SteelFluidTankBlockEntity tankAt = ConnectivityHandler.partAt(getBlockEntityType(), world, pos);
        if (tankAt == null)
            return 0;
        SteelFluidTankBlockEntity controllerBE = tankAt.getControllerBE();
        if (controllerBE == null || !controllerBE.isWindow())
            return 0;
        return tankAt.getLuminosity();
    }

    @Override
    public InteractionResult onWrenched(BlockState state, UseOnContext context) {
        withBlockEntityDo(context.getLevel(), context.getClickedPos(), SteelFluidTankBlockEntity::toggleWindows);
        return InteractionResult.SUCCESS;
    }

    static final VoxelShape CAMPFIRE_SMOKE_CLIP = Block.box(0, 4, 0, 16, 16, 16);

    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos,
                                        CollisionContext pContext) {
        if (pContext == CollisionContext.empty())
            return CAMPFIRE_SMOKE_CLIP;
        return pState.getShape(pLevel, pPos);
    }

    @Override
    public VoxelShape getBlockSupportShape(BlockState pState, BlockGetter pReader, BlockPos pPos) {
        return Shapes.block();
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState,
                                  LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        if (pDirection == Direction.DOWN && pNeighborState.getBlock() != this)
            withBlockEntityDo(pLevel, pCurrentPos, SteelFluidTankBlockEntity::updateBoilerTemperature);
        return pState;
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand,
                                 BlockHitResult ray) {
        ItemStack heldItem = player.getItemInHand(hand);
        boolean onClient = world.isClientSide;

        if (heldItem.isEmpty())
            return InteractionResult.PASS;
        if (!player.isCreative())
            return InteractionResult.PASS;

        FluidExchange exchange = null;
        SteelFluidTankBlockEntity be = ConnectivityHandler.partAt(getBlockEntityType(), world, pos);
        if (be == null)
            return InteractionResult.FAIL;

        LazyOptional<IFluidHandler> tankCapability = be.getCapability(FLUID_HANDLER_CAPABILITY);
        if (!tankCapability.isPresent())
            return InteractionResult.PASS;
        IFluidHandler fluidTank = tankCapability.orElse(null);
        FluidStack prevFluidInTank = fluidTank.getFluidInTank(0)
                .copy();

        if (FluidHelper.tryEmptyItemIntoBE(world, player, hand, heldItem, be))
            exchange = FluidExchange.ITEM_TO_TANK;
        else if (FluidHelper.tryFillItemFromBE(world, player, hand, heldItem, be))
            exchange = FluidExchange.TANK_TO_ITEM;

        if (exchange == null) {
            if (GenericItemEmptying.canItemBeEmptied(world, heldItem)
                    || GenericItemFilling.canItemBeFilled(world, heldItem))
                return InteractionResult.SUCCESS;
            return InteractionResult.PASS;
        }

        SoundEvent soundevent = null;
        BlockState fluidState = null;
        FluidStack fluidInTank = tankCapability.map(fh -> fh.getFluidInTank(0))
                .orElse(FluidStack.EMPTY);

        if (exchange == FluidExchange.ITEM_TO_TANK) {
            Fluid fluid = fluidInTank.getFluid();
            fluidState = fluid.defaultFluidState()
                    .createLegacyBlock();
            soundevent = FluidHelper.getEmptySound(fluidInTank);
        }

        if (exchange == FluidExchange.TANK_TO_ITEM) {
            Fluid fluid = prevFluidInTank.getFluid();
            fluidState = fluid.defaultFluidState()
                    .createLegacyBlock();
            soundevent = FluidHelper.getFillSound(prevFluidInTank);
        }

        if (soundevent != null && !onClient) {
            float pitch = Mth
                    .clamp(1 - (1f * fluidInTank.getAmount() / (SteelFluidTankBlockEntity.getCapacityMultiplier() * 16)), 0, 1);
            pitch /= 1.5f;
            pitch += .5f;
            pitch += (world.random.nextFloat() - .5f) / 4f;
            world.playSound(null, pos, soundevent, SoundSource.BLOCKS, .5f, pitch);
        }

        if (!fluidInTank.isFluidStackIdentical(prevFluidInTank)) {
            if (be instanceof SteelFluidTankBlockEntity) {
                SteelFluidTankBlockEntity controllerBE = ((SteelFluidTankBlockEntity) be).getControllerBE();
                if (controllerBE != null) {
                    if (fluidState != null && onClient) {
                        BlockParticleOption blockParticleData =
                                new BlockParticleOption(ParticleTypes.BLOCK, fluidState);
                        float level = (float) fluidInTank.getAmount() / fluidTank.getTankCapacity(0);

                        boolean reversed = fluidInTank.getFluid()
                                .getFluidType()
                                .isLighterThanAir();
                        if (reversed)
                            level = 1 - level;

                        Vec3 vec = ray.getLocation();
                        vec = new Vec3(vec.x, controllerBE.getBlockPos()
                                .getY() + level * (controllerBE.getHeight() - .5f) + .25f, vec.z);
                        Vec3 motion = player.position()
                                .subtract(vec)
                                .scale(1 / 20f);
                        vec = vec.add(motion);
                        world.addParticle(blockParticleData, vec.x, vec.y, vec.z, motion.x, motion.y, motion.z);
                        return InteractionResult.SUCCESS;
                    }

                    controllerBE.sendDataImmediately();
                    controllerBE.setChanged();
                }
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.hasBlockEntity() && (state.getBlock() != newState.getBlock() || !newState.hasBlockEntity())) {
            BlockEntity be = world.getBlockEntity(pos);
            if (!(be instanceof SteelFluidTankBlockEntity))
                return;
            SteelFluidTankBlockEntity tankBE = (SteelFluidTankBlockEntity) be;
            world.removeBlockEntity(pos);
            ConnectivityHandler.splitMulti(tankBE);
        }
    }

    @Override
    public Class<SteelFluidTankBlockEntity> getBlockEntityClass() {
        return SteelFluidTankBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends SteelFluidTankBlockEntity> getBlockEntityType() {
        return AllBlockEntities.STEEL_FLUID_TANK.get();
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        if (mirror == Mirror.NONE)
            return state;
        boolean x = mirror == Mirror.FRONT_BACK;
        switch (state.getValue(SHAPE)) {
            case WINDOW_NE:
                return state.setValue(SHAPE, x ? Shape.WINDOW_NW : Shape.WINDOW_SE);
            case WINDOW_NW:
                return state.setValue(SHAPE, x ? Shape.WINDOW_NE : Shape.WINDOW_SW);
            case WINDOW_SE:
                return state.setValue(SHAPE, x ? Shape.WINDOW_SW : Shape.WINDOW_NE);
            case WINDOW_SW:
                return state.setValue(SHAPE, x ? Shape.WINDOW_SE : Shape.WINDOW_NW);
            default:
                return state;
        }
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        for (int i = 0; i < rotation.ordinal(); i++)
            state = rotateOnce(state);
        return state;
    }

    private BlockState rotateOnce(BlockState state) {
        switch (state.getValue(SHAPE)) {
            case WINDOW_NE:
                return state.setValue(SHAPE, Shape.WINDOW_SE);
            case WINDOW_NW:
                return state.setValue(SHAPE, Shape.WINDOW_NE);
            case WINDOW_SE:
                return state.setValue(SHAPE, Shape.WINDOW_SW);
            case WINDOW_SW:
                return state.setValue(SHAPE, Shape.WINDOW_NW);
            default:
                return state;
        }
    }

    public enum Shape implements StringRepresentable {
        PLAIN, WINDOW, WINDOW_NW, WINDOW_SW, WINDOW_NE, WINDOW_SE;

        @Override
        public String getSerializedName() {
            return Lang.asId(name());
        }
    }

    // Tanks are less noisy when placed in batch
    public static final SoundType SILENCED_METAL =
            new ForgeSoundType(0.1F, 1.5F, () -> SoundEvents.METAL_BREAK, () -> SoundEvents.METAL_STEP,
                    () -> SoundEvents.METAL_PLACE, () -> SoundEvents.METAL_HIT, () -> SoundEvents.METAL_FALL);

    @Override
    public SoundType getSoundType(BlockState state, LevelReader world, BlockPos pos, Entity entity) {
        SoundType soundType = super.getSoundType(state, world, pos, entity);
        if (entity != null && entity.getPersistentData()
                .contains("SilenceTankSound"))
            return SILENCED_METAL;
        return soundType;
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level worldIn, BlockPos pos) {
        return getBlockEntityOptional(worldIn, pos).map(SteelFluidTankBlockEntity::getControllerBE)
                .map(be -> ComparatorUtil.fractionToRedstoneLevel(be.getFillState()))
                .orElse(0);
    }

    public static void updateBoilerState(BlockState pState, Level pLevel, BlockPos tankPos) {
        BlockState tankState = pLevel.getBlockState(tankPos);
        if (!(tankState.getBlock()instanceof SteelFluidTankBlock tank))
            return;
        SteelFluidTankBlockEntity tankBE = tank.getBlockEntity(pLevel, tankPos);
        if (tankBE == null)
            return;
        SteelFluidTankBlockEntity controllerBE = tankBE.getControllerBE();
        if (controllerBE == null)
            return;
        controllerBE.updateBoilerState();
    }

}