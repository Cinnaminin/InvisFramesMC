package dev.cinnaminin;

import dev.cinnaminin.InvisFrames;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class IFListener implements Listener {

    @EventHandler
    public void onFramePlace(HangingPlaceEvent event) {

        if (!(event.getEntity() instanceof ItemFrame frame)) return;

        ItemStack item = event.getItemStack();
        if (item == null || !item.hasItemMeta()) return;

        if (!item.getItemMeta().getPersistentDataContainer().has(
                InvisFrames.INVIS_FLAG_KEY,
                PersistentDataType.BYTE
        )) return;
        frame.setInvisible(true);
    }
}
