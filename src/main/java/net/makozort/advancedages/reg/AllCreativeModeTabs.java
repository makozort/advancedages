package net.makozort.advancedages.reg;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

import com.simibubi.create.AllItems;
import net.makozort.advancedages.AdvancedAges;
import net.minecraft.network.chat.Component;
import org.apache.commons.lang3.mutable.MutableObject;

import com.simibubi.create.content.contraptions.actors.seat.SeatBlock;
import com.simibubi.create.content.decoration.palettes.AllPaletteBlocks;
import com.simibubi.create.content.equipment.armor.BacktankUtil;
import com.simibubi.create.content.equipment.toolbox.ToolboxBlock;
import com.simibubi.create.content.kinetics.crank.ValveHandleBlock;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.TagDependentIngredientItem;
import com.simibubi.create.foundation.utility.Components;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;

import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceOpenHashMap;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import it.unimi.dsi.fastutil.objects.ReferenceLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import net.minecraftforge.eventbus.api.IEventBus;

import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(bus = Bus.MOD)
public class AllCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AdvancedAges.MOD_ID);

    public static final RegistryObject<CreativeModeTab> BASE_TAB = CREATIVE_MODE_TABS.register("base_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(AllBlocks.OIL_FILTER.get().asItem()))
                    .title(Component.translatable("creativetab.base_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(Allitems.HEAVY_OIL_BUCKET.get());
                        pOutput.accept(Allitems.MYSTERY_MEAT.get());
                        pOutput.accept(Allitems.EMPTY_IV_BAG.get());
                        pOutput.accept(Allitems.IV_BAG.get());
                        pOutput.accept(Allitems.NUTRITIOUS_BAG.get());
                        pOutput.accept(Allitems.MYSTERY_MEAT_SANDWICH.get());
                        pOutput.accept(Allitems.OIL_SCANNER_ITEM.get());
                        pOutput.accept(Allitems.POLLUTION_DETECTOR_ITEM.get());
                        pOutput.accept(Allitems.POLLUTION_MASK.get());
                        pOutput.accept(Allitems.POLLUTION_SPONGE.get());

                        pOutput.accept(AllBlocks.STEEL_FLUID_TANK.asItem());

                        pOutput.accept(AllBlocks.OIL_FILTER.get().asItem());
                        pOutput.accept(AllBlocks.TITAN_HORN_BLOCK.get().asItem());
                        pOutput.accept(AllBlocks.GJALLAR_HORN_BLOCK.get().asItem());
                        pOutput.accept(AllBlocks.GRAND_HORN_BLOCK.get().asItem());
                        pOutput.accept(AllBlocks.OMINOUS_HORN_BLOCK.get().asItem());
                        pOutput.accept(AllBlocks.REAPER_HORN_BLOCK.get().asItem());





                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}