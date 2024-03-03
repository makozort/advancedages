package net.makozort.advancedages.reg;

import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.Create;
import com.simibubi.create.content.fluids.FluidTransportBehaviour;
import net.minecraft.core.Direction;

import java.util.EnumMap;
import java.util.Map;

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
