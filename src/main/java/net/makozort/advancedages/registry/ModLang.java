package net.makozort.advancedages.registry;

import net.makozort.advancedages.Advancedages;

public class ModLang {
    static {
        customLang("fluid_type.bronze_age.molten_bronze_fluid","Molten Bronze");
        customLang("itemGroup.cooltab","Create: Bronze Age");
    }
    public static void customLang(String key, String value) {
        Advancedages.REGISTRATE.addRawLang(key,value);
    }
}
