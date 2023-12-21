package net.makozort.advancedages.reg;

import com.simibubi.create.Create;
import com.simibubi.create.foundation.block.connected.AllCTTypes;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.CTSpriteShifter;
import net.makozort.advancedages.AdvancedAges;

public class AllSpriteShifts extends com.simibubi.create.AllSpriteShifts {
    public static final CTSpriteShiftEntry
            STEEL_FLUID_TANK = getCT("steel_fluid_tank"),
            STEEL_FLUID_TANK_TOP = getCT("steel_fluid_tank_top"),
            STEEL_FLUID_TANK_INNER = getCT("steel_fluid_tank_inner");

    private static CTSpriteShiftEntry getCT(String blockTextureName) {
        return CTSpriteShifter.getCT(AllCTTypes.RECTANGLE, AdvancedAges.asResource("block/" + blockTextureName),
                AdvancedAges.asResource("block/" + blockTextureName + "_connected"));
    }
}
