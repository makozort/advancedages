//package net.makozort.advancedages.worldgen;
//
//import com.mojang.serialization.Codec;
//import com.mojang.serialization.codecs.RecordCodecBuilder;
//import net.minecraft.core.BlockPos;
//import net.minecraft.tags.BlockTags;
//import net.minecraft.tags.FluidTags;
//import net.minecraft.util.RandomSource;
//import net.minecraft.world.level.WorldGenLevel;
//import net.minecraft.world.level.block.Blocks;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.level.levelgen.feature.Feature;
//import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
//import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
//import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
//import net.minecraft.world.level.material.Material;
//import net.minecraft.world.phys.Vec3;
//
//public class VeryLargeLakeFeature extends Feature<VeryLargeLakeFeature.Configuration> {
//
//    private static final BlockState AIR = Blocks.CAVE_AIR.defaultBlockState();
//
//    public VeryLargeLakeFeature(Codec<Configuration> pCodec) {
//        super(pCodec);
//    }
//
//    /**
//     * Places the given feature at the given location.
//     * During world generation, features are provided with a 3x3 region of chunks, centered on the chunk being generated,
//     * that they can safely generate into.
//     *
//     * @param pContext A context object with a reference to the level and the position the feature is being placed at
//     */
//    public boolean place(FeaturePlaceContext<Configuration> pContext) {
//        BlockPos origin = pContext.origin();
//        WorldGenLevel worldgenlevel = pContext.level();
//        RandomSource randomsource = pContext.random();
//        Configuration configuration = pContext.config();
//        int depth = configuration.min_depth + randomsource.nextInt(configuration.max_depth - configuration.min_depth + 1);//pick random number in range [min_depth,max_depth)
//        //check if too low
//        if (origin.getY() <= worldgenlevel.getMinBuildHeight() + depth / 2) {
//            return false;
//        } else {
//            int width = configuration.max_width + randomsource.nextInt(configuration.max_width - configuration.min_width + 1);//pick random number in range [min_width,max_width)
//
//            //first make the shape
//            boolean[] fill = new boolean[width * depth * width];
//
//
//            //an oblate spheroid looks like x^2/a^2 + y^2/b^2 + z^2/c^2 = 1
//            //in this case it's 4 * x^2/max_width^2 +4 * y^2/max_depth^2 + 4 * z^2/max_width^2 = 1
//            Vec3 center = new Vec3(width / 2d, depth / 2d, width / 2d);
//            for (int y = 0; y < depth; y++) {
//                for (int z = 0; z < width; z++) {
//                    for (int x = 0; x < width; x++) {
//                        int index = x + width * z + width * width * y;
//                        double x1 = x - center.x;
//                        double y1 = y - center.y;
//                        double z1 = z - center.z;
//
//                        double xd = 2 * x1 / width;
//                        double yd = 2 * y1 / depth;
//                        double zd = 2 * z1 / width;
//                        double dist = xd * xd + yd * yd + zd * zd;
//                        if (dist < 1) {
//                            fill[index] = true;
//                        }
//                    }
//                }
//            }
//
//            //check for obstructions that would cause placement to completely fail (such as water or other fluids)
//
//            for (int y = 0; y < depth; y++) {
//                for (int z = 0; z < width; z++) {
//                    for (int x = 0; x < width; x++) {
//                        int index = x + width * z + width * width * y;
//                        if (fill[index]) {
//                            //offset so that we're centered on the lake, then offset again for the loop
//                            BlockPos check = origin.offset(x - center.x, y - center.y, z - center.z);
//                            BlockState state = worldgenlevel.getBlockState(check);
//                            Material material = state.getMaterial();
//                            if (material.isLiquid() || state.isAir()) {//don't fill cave systems or oceans, etc
//                                return false;
//                            }
//                        }
//                    }
//                }
//            }
//
//
//            //now place the feature itself being careful not to replace the wrong blocks
//            BlockState fluidState = configuration.fluid().getState(randomsource, origin);
//            for (int y = 0; y < depth; y++) {
//                for (int z = 0; z < width; z++) {
//                    for (int x = 0; x < width; x++) {
//                        int index = x + width * z + width * width * y;
//                        //offset so that we're centered on the lake, then offset again for the loop
//                        BlockPos check = origin.offset((int) (x - center.x), (int) (y - center.y), (int) (z - center.z));
//                        if (fill[index] && canReplaceBlock(worldgenlevel.getBlockState(check))) {
//                            worldgenlevel.setBlock(check, fluidState, 2);
//                        }
//                    }
//                }
//            }
//
//            if (fluidState.getFluidState().is(FluidTags.WATER)) {
//                for (int x = 0; x < 16; ++x) {
//                    for (int z = 0; z < 16; ++z) {
//                        int y = 4;
//                        BlockPos offset = origin.offset((int) (x - center.x), (int) (y - center.y), (int) (z - center.z));
//                        if (worldgenlevel.getBiome(offset).value().shouldFreeze(worldgenlevel, offset, false) && this.canReplaceBlock(worldgenlevel.getBlockState(offset))) {
//                            worldgenlevel.setBlock(offset, Blocks.ICE.defaultBlockState(), 2);
//                        }
//                    }
//                }
//            }
//
//            //System.out.println("Placed Very Large Lake at:" + origin);
//            return true;
//        }
//    }
//
//    private boolean canReplaceBlock(BlockState pState) {
//        return !pState.is(BlockTags.FEATURES_CANNOT_REPLACE);
//    }
//
//    public record Configuration(BlockStateProvider fluid, int min_width, int min_depth, int max_width,
//                                int max_depth) implements FeatureConfiguration {
//        public static final Codec<Configuration> CODEC = RecordCodecBuilder.create((group) -> group.group(
//                        BlockStateProvider.CODEC.fieldOf("fluid").forGetter(Configuration::fluid),
//                        Codec.intRange(1, 24).fieldOf("min_width").forGetter(Configuration::min_width),
//                        Codec.intRange(1, 48).fieldOf("min_depth").forGetter(Configuration::min_depth),
//                        Codec.intRange(1, 24).fieldOf("max_width").forGetter(Configuration::max_width),
//                        Codec.intRange(1, 48).fieldOf("max_depth").forGetter(Configuration::max_depth)
//                )
//                .apply(group, Configuration::new));
//    }
//
//}
//