package net.makozort.advancedages.registry;

import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockEntity;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockEntityInstance;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockEntityRenderer;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.makozort.advancedages.Advancedages;


public class ModBlockEntityType {
    // Kinetics
    public static final BlockEntityEntry<BracketedKineticBlockEntity> BRONZE_COGWHEELS = Advancedages.REGISTRATE
            .blockEntity("bronze_cogwheels", BracketedKineticBlockEntity::new)
            .instance(() -> BracketedKineticBlockEntityInstance::new, false)
            .validBlocks(ModBlocks.LARGE_BRONZE_COGWHEEL, ModBlocks.BRONZE_COGWHEEL)
            .renderer(() -> BracketedKineticBlockEntityRenderer::new)
            .register();
    // Load this class
    public static void register() {}
}
