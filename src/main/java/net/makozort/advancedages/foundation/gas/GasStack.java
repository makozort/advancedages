package net.makozort.advancedages.foundation.gas;

import net.minecraft.core.Holder;
import net.minecraftforge.fluids.FluidStack;

public class GasStack<T extends VirtualGas> extends FluidStack {
    private final T gasDelegate;

    public GasStack(T gas, int amount) {
        super(gas, amount);
        gasDelegate = gas;
    }

    @Override
    public int getAmount() {
        return super.getAmount();
    }

    public T getGas() {
        return this.isEmpty() ? null : this.gasDelegate;
    }


}
