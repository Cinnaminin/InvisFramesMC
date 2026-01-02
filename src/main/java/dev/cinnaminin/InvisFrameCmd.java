package dev.cinnaminin;

import dev.cinnaminin.InvisFrames;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class InvisFrameCmd implements CommandExecutor {

    private final InvisFrames plugin;

    public InvisFrameCmd(InvisFrames plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(
            CommandSender sender,
            Command command,
            String label,
            String[] args
    ) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("Players only.");
            return true;
        }

        if (!plugin.commandsEnabled()) {
            player.sendMessage(Component.text("This command is currently disabled."));
            return true;
        }

        if (!player.isOp() && !player.hasPermission("invisframes.use")) {
            player.sendMessage(
                    Component.text("You do not have permission to use this command.")
                            .color(TextColor.color(255, 85, 85))
            );
        }

            ItemStack item = player.getInventory().getItemInMainHand();
            Material type = item.getType();

            if (type != Material.ITEM_FRAME && type != Material.GLOW_ITEM_FRAME) {
                player.sendMessage(Component.text("Hold an item frame in your main hand."));
                return true;
            }

            ItemMeta meta = item.getItemMeta();
            PersistentDataContainer pdc = meta.getPersistentDataContainer();

            boolean invisible = pdc.has(
                    InvisFrames.INVIS_FLAG_KEY,
                    PersistentDataType.BYTE
            );

            if (invisible) {
                pdc.remove(InvisFrames.INVIS_FLAG_KEY);
                pdc.remove(InvisFrames.ENTITY_TAG_KEY);
                meta.displayName(null);
                meta.setEnchantmentGlintOverride(false);

                player.sendMessage(Component.text("Item frames reverted to normal."));
            } else {
                meta.displayName(
                        Component.text("Invisible Item Frame")
                                .color(TextColor.fromHexString("#10fece"))
                                .decoration(TextDecoration.ITALIC, false)
                );
                meta.setEnchantmentGlintOverride(true);
                pdc.set(
                        InvisFrames.ENTITY_TAG_KEY,
                        PersistentDataType.STRING,
                        "{id:\"minecraft:item_frame\",Invisible:1b}"
                );

                pdc.set(
                        InvisFrames.INVIS_FLAG_KEY,
                        PersistentDataType.BYTE,
                        (byte) 1
                );

                player.sendMessage(Component.text("Item frames converted to invisible."));
            }


            item.setItemMeta(meta);
            return true;
        }
    }