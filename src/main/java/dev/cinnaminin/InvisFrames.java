package dev.cinnaminin;

import dev.cinnaminin.InvisFrameCmd;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class InvisFrames extends JavaPlugin {

    public static NamespacedKey ENTITY_TAG_KEY;
    public static NamespacedKey INVIS_FLAG_KEY;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        ENTITY_TAG_KEY = new NamespacedKey(this, "entity_tag");
        INVIS_FLAG_KEY = new NamespacedKey(this, "invisible_frame");

        getServer().getPluginManager().registerEvents(
                new IFListener(),
                this
        );

        getServer().getPluginManager().registerEvents(
                new IFBreak(),
                this
        );

        InvisFrameCmd command = new InvisFrameCmd(this);
        getCommand("invisframe").setExecutor(command);
        getLogger().info("InvisFrames by Cinnaminin loaded!");
        getLogger().info("InvisFrames enabled.");

        if (getConfig().getBoolean("crafting.enabled")) {
            registerRecipe();
            getLogger().info("InvisFrames Crafting enabled.");
        }
    }

    public boolean commandsEnabled() {
        return getConfig().getBoolean("commands.enabled");
    }

    private void registerRecipe() {
        ItemStack result = new ItemStack(Material.ITEM_FRAME);
        org.bukkit.inventory.meta.ItemMeta meta = result.getItemMeta();

        meta.displayName(
                Component.text("Invisible Item Frame")
                        .color(TextColor.fromHexString("#10fece"))
                        .decoration(TextDecoration.ITALIC, false)
        );

        meta.setEnchantmentGlintOverride(true);

        meta.getPersistentDataContainer().set(
                INVIS_FLAG_KEY,
                PersistentDataType.BYTE,
                (byte) 1
        );

        meta.getPersistentDataContainer().set(
                ENTITY_TAG_KEY,
                PersistentDataType.STRING,
                "{id:\"minecraft:item_frame\",Invisible:1b}"
        );

        result.setItemMeta(meta);

        ShapedRecipe recipe = new ShapedRecipe(
                new NamespacedKey(this, "invisible_frame"),
                result
        );

        recipe.shape(
                "XAX",
                "AFA",
                "XAX"
        );

        recipe.setIngredient('A', Material.AMETHYST_SHARD);
        recipe.setIngredient('F', Material.ITEM_FRAME);
        // this recipe seems fair, right? Maybe ill make it customizable in the config someday :P

        Bukkit.addRecipe(recipe);
    }
}