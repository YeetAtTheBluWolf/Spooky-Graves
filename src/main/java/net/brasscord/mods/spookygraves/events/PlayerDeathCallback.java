package net.brasscord.mods.spookygraves.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

import javax.swing.*;

public interface PlayerDeathCallback {

    Event<PlayerDeathCallback> EVENT = EventFactory.createArrayBacked(PlayerDeathCallback.class, (listeners) -> (PlayerEntity player) -> {
        for(PlayerDeathCallback listener : listeners) {
            ActionResult result = listener.onDeath(player);

            if(result != ActionResult.PASS)
                return result;
        }
        return ActionResult.PASS;
    });

    ActionResult onDeath(PlayerEntity player);

}