package net.brasscord.mods.spookygraves.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public interface PlayerDeathCallback {

    Event<PlayerDeathCallback> EVENT = EventFactory.createArrayBacked(PlayerDeathCallback.class, (listeners) -> (player) -> {
        for(PlayerDeathCallback listener : listeners) {
            ActionResult actionResult = listener.death(player);
        }
        return ActionResult.PASS;
    });

    ActionResult death(PlayerEntity player);

}