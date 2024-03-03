package net.makozort.advancedages.foundation.gas;

import java.util.List;

public class MixedVirtualGas {
    public List<GasStack<?>> virtualGasses;

    public MixedVirtualGas(List<GasStack<?>> virtualGasses) {
        super();
    }

    public List<GasStack<?>> getVirtualGasses() {
        return virtualGasses;
    }

    public void setVirtualGasses(List<GasStack<?>> virtualGasses) {
        this.virtualGasses = virtualGasses;
    }

    public int getGas(VirtualGas gas) {
        for (GasStack<?> gasStack : virtualGasses) {
            if (gas == gasStack.getGas()) {
                return gasStack.getAmount();
            }
        }
        return 0;
    }

    public void setVirtualGas(GasStack<?> gas) {
        for (int i = 0; i < virtualGasses.size(); i++) {
            if (gas.getGas() == virtualGasses.get(i).getGas()) {
                virtualGasses.set(i, gas);
                break;
            }
        }
    }

    public void addGas(GasStack<?> gas) {
        for (GasStack<?> pGas : virtualGasses) {
            if (gas.getGas() == pGas.getGas()) {
                setVirtualGas(gas);
                break;
            }
        }
        this.virtualGasses.add(gas);
    }

    public void addGas(VirtualGas gas, int amount) {
        addGas(new GasStack<>(gas, amount));
    }
}
