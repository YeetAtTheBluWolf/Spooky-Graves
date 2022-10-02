package net.brasscord.mods.spookygraves;

import net.brasscord.mods.spookygraves.register.Registries;
import net.fabricmc.api.ModInitializer;

public class Spookygraves implements ModInitializer {

    public static final String id = "spookygraves";

    private final Registries registries = new Registries();

    @Override
    public void onInitialize() {
        registries.getRegistries();
    }
}
