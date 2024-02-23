package net.makozort.advancedages.reg;

import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.Create;
import com.simibubi.create.content.fluids.FluidTransportBehaviour;
import com.simibubi.create.foundation.utility.Couple;
import com.simibubi.create.foundation.utility.Iterate;
import com.simibubi.create.foundation.utility.Lang;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;

import java.util.*;

public class AllPartialModels {

    public static final PartialModel


    GOGGLES = block("goggles");

    public static final Map<FluidTransportBehaviour.AttachmentTypes.ComponentPartials, Map<Direction, PartialModel>> PIPE_ATTACHMENTS =
            new EnumMap<>(FluidTransportBehaviour.AttachmentTypes.ComponentPartials.class);




    private static PartialModel block(String path) {
        return new PartialModel(Create.asResource("block/" + path));
    }

    private static PartialModel entity(String path) {
        return new PartialModel(Create.asResource("entity/" + path));
    }

    public static void init() {
        // init static fields
    }

}
