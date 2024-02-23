//package net.makozort.advancedages.content.fluid.tank;
//
//
//
//import com.simibubi.create.foundation.data.AssetLookup;
//import com.simibubi.create.foundation.data.SpecialBlockStateGen;
//import com.tterrag.registrate.providers.DataGenContext;
//import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
//import net.makozort.advancedages.content.blocks.block.oil.SteelFluidTankBlock;
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraftforge.client.model.generators.ModelFile;
//@SuppressWarnings("removal")
//public class SteelFluidTankGenerator extends SpecialBlockStateGen {
//
//    private String prefix;
//
//    public SteelFluidTankGenerator() {
//        this("");
//    }
//
//    public SteelFluidTankGenerator(String prefix) {
//        this.prefix = prefix;
//    }
//
//    @Override
//    protected int getXRotation(BlockState state) {
//        return 0;
//    }
//
//    @Override
//    protected int getYRotation(BlockState state) {
//        return 0;
//    }
//
//    @Override
//    public <T extends Block> ModelFile getModel(DataGenContext<Block, T> ctx, RegistrateBlockstateProvider prov,
//                                                BlockState state) {
//        Boolean top = state.getValue(SteelFluidTankBlock.TOP);
//        Boolean bottom = state.getValue(SteelFluidTankBlock.BOTTOM);
//        SteelFluidTankBlock.Shape shape = state.getValue(SteelFluidTankBlock.SHAPE);
//
//        String shapeName = "middle";
//        if (top && bottom)
//            shapeName = "single";
//        else if (top)
//            shapeName = "top";
//        else if (bottom)
//            shapeName = "bottom";
//
//        String modelName = shapeName + (shape == SteelFluidTankBlock.Shape.PLAIN ? "" : "_" + shape.getSerializedName());
//
//        if (!prefix.isEmpty())
//            return prov.models()
//                    .withExistingParent(prefix + modelName, prov.modLoc("block/steel_fluid_tank/block_" + modelName))
//                    .texture("0", prov.modLoc("block/" + prefix + "steel_casing"))
//                    .texture("1", prov.modLoc("block/" + prefix + "steel_fluid_tank"))
//                    .texture("3", prov.modLoc("block/" + prefix + "steel_fluid_tank_window"))
//                    .texture("4", prov.modLoc("block/" + prefix + "steel_casing"))
//                    .texture("5", prov.modLoc("block/" + prefix + "steel_fluid_tank_window_single"))
//                    .texture("particle", prov.modLoc("block/" + prefix + "steel_fluid_tank"));
//
//        return AssetLookup.partialBaseModel(ctx, prov, modelName);
//    }
//
//}