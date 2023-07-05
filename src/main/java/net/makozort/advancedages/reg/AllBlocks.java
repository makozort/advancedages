package net.makozort.advancedages.reg;


import com.tterrag.registrate.util.entry.BlockEntry;

import net.makozort.advancedages.Advancedages;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


import java.util.function.Supplier;

import static com.simibubi.create.foundation.data.BlockStateGen.simpleCubeAll;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;
import static net.makozort.advancedages.ModRegistrate.REGISTRATE;


public class AllBlocks {
    static {
        REGISTRATE.creativeModeTab(() -> ModCreativeModeTab.BIG_TAB);
    }

    public static final BlockEntry<DropExperienceBlock> TIN_ORE = REGISTRATE.block("tin_ore", p ->
                    new DropExperienceBlock(BlockBehaviour.Properties.of(Material.METAL)
                            .requiresCorrectToolForDrops().explosionResistance(6).strength(3),
                            UniformInt.of(3,7)))
            .item(BlockItem::new)
            .build()
            .register();


    public static final BlockEntry<LiquidBlock> CRUDE_OIL_BLOCK = REGISTRATE.block("crude_oil_block",b ->
                    new LiquidBlock(Allfluids.SOURCE_CRUDE_OIL, BlockBehaviour.Properties.copy(Blocks.LAVA)))
            .lang("Crude Oil")
            .register();


    public static void register() {

    }
}