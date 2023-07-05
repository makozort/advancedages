package net.makozort.advancedages.reg;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.makozort.advancedages.Advancedages;
import net.makozort.advancedages.content.Modblocks.Myblock.Myblocktile;

public class AllBlockEntities {

    public static final BlockEntityEntry<Myblocktile> MyBlocktile = Advancedages.REGISTRATE
            .blockEntity("Myblock", Myblocktile::new)
            .instance(() -> HalfShaftInstance::new)
            .validBlocks(AllBlocks.TIN_ORE)
            .renderer(() -> ElectricMotorRenderer::new)
            .register();
    public static void register() {}

}
