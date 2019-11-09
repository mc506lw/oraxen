package io.th0rgal.oraxen.mechanics.provided.thor;

import io.th0rgal.oraxen.items.OraxenItems;
import io.th0rgal.oraxen.mechanics.MechanicFactory;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ThorMechanicsListener implements Listener {

    private final MechanicFactory factory;

    public ThorMechanicsListener(MechanicFactory factory) {
        this.factory = factory;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onCall(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;

        ItemStack item = event.getItem();
        String itemID = OraxenItems.getIdByItem(item);

        if (factory.isNotImplementedIn(itemID))
            return;

        ThorMechanic mechanic = (ThorMechanic) factory.getMechanic(itemID);

        Player player = event.getPlayer();
        for (int i = 0; i < mechanic.getLightningBoltsAmount(); i++) {
            Location target = event.getPlayer().getTargetBlock(null, 50).getLocation();
            player.getWorld().strikeLightning(mechanic.getRandomizedLocation(target));
        }

    }

}
