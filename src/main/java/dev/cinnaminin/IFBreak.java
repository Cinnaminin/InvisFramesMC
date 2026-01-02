package dev.cinnaminin;

import dev.cinnaminin.InvisFrames;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class IFBreak implements Listener {

    @EventHandler
    public void onFrameBreak(HangingBreakByEntityEvent event) {

        if (!(event.getEntity() instanceof ItemFrame frame)) return;

        if (!frame.isInvisible()) return;

        event.setCancelled(true);
        frame.remove();

        ItemStack drop = new ItemStack(Material.ITEM_FRAME);
        ItemMeta meta = drop.getItemMeta();

        meta.displayName(
                Component.text("Invisible Item Frame")
                        .color(TextColor.fromHexString("#10fece"))
                        .decoration(TextDecoration.ITALIC, false)
        );

        meta.setEnchantmentGlintOverride(true);

        meta.getPersistentDataContainer().set(
                InvisFrames.INVIS_FLAG_KEY,
                PersistentDataType.BYTE,
                (byte) 1
        );

        drop.setItemMeta(meta);

        frame.getWorld().dropItemNaturally(frame.getLocation(), drop);
    }
}