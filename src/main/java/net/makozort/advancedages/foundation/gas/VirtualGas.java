package net.makozort.advancedages.foundation.gas;

import com.simibubi.create.content.fluids.VirtualFluid;

public class VirtualGas extends VirtualFluid {

    public VirtualGas(Properties properties) {
        super(properties);
    }

    public GasStack<?> toStack(int amount) {
        return new GasStack<>(this, amount);
    }
}
