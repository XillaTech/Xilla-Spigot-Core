package net.xilla.spigotcore.util;

import net.xilla.core.library.json.XillaJson;
import org.apache.commons.lang.Validate;
import org.bukkit.*;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

/**
 * Parse {@link ItemStack} to JSON
 *
 * @author DevSrSouza
 * @version 1.0
 *
 * https://github.com/DevSrSouza/
 * You can find updates here https://gist.github.com/DevSrSouza
 */
public class JsonItemStack {
    private static final String[] BYPASS_CLASS = {"CraftMetaBlockState", "CraftMetaItem"
            /*Glowstone Support*/ ,"GlowMetaItem"};
    /**
     * Parse the {@link ItemStack} to JSON
     *
     * @param itemStack The {@link ItemStack} instance
     * @return The JSON string
     */
    public static String toJson(ItemStack itemStack) {

        JSONObject itemJson = new JSONObject();


        itemJson.put("type", itemStack.getType().name());
        if (itemStack.getDurability() > 0) itemJson.put("data", itemStack.getDurability());
        if (itemStack.getAmount() != 1) itemJson.put("amount", itemStack.getAmount());


        if (itemStack.hasItemMeta()) {
            JSONObject metaJson = new JSONObject();

            ItemMeta meta = itemStack.getItemMeta();


            if (meta.hasDisplayName()) {
                metaJson.put("displayname", translate('&', meta.getDisplayName()));
            }
            if (meta.hasLore()) {
                JSONArray lore = new JSONArray();
                meta.getLore().forEach(str -> lore.add(translate('&', str)));
                metaJson.put("lore", lore);
            }
            if (meta.hasEnchants()) {
                JSONArray enchants = new JSONArray();
                meta.getEnchants().forEach((enchantment, integer) -> {
                    enchants.add(enchantment.getName() + ":" + integer);
                });
                metaJson.put("enchants", enchants);
            }
            if (!meta.getItemFlags().isEmpty()) {
                JSONArray flags = new JSONArray();
                meta.getItemFlags().stream().map(ItemFlag::name).forEach(str -> flags.add(str));
                metaJson.put("flags", flags);
            }

            for (String clazz : BYPASS_CLASS) {
                if (meta.getClass().getSimpleName().equals(clazz)) {
                    itemJson.put("item-meta", metaJson);
                    return itemJson.toJSONString();
                }
            }

            if (meta instanceof SkullMeta) {
                SkullMeta skullMeta = (SkullMeta) meta;
                if (skullMeta.hasOwner()) {
                    JSONObject extraMeta = new JSONObject();
                    extraMeta.put("owner", skullMeta.getOwner());
                    metaJson.put("extra-meta", extraMeta);
                }
            } else if (meta instanceof BannerMeta) {
                BannerMeta bannerMeta = (BannerMeta) meta;
                JSONObject extraMeta = new JSONObject();
                extraMeta.put("base-color", bannerMeta.getBaseColor().name());

                if (bannerMeta.numberOfPatterns() > 0) {
                    JSONArray patterns = new JSONArray();
                    bannerMeta.getPatterns()
                            .stream()
                            .map(pattern ->
                                    pattern.getColor().name() + ":" + pattern.getPattern().getIdentifier())
                            .forEach(str -> patterns.add((str)));
                    extraMeta.put("patterns", patterns);
                }

                metaJson.put("extra-meta", extraMeta);
            } else if (meta instanceof EnchantmentStorageMeta) {
                EnchantmentStorageMeta esmeta = (EnchantmentStorageMeta) meta;
                if (esmeta.hasStoredEnchants()) {
                    JSONObject extraMeta = new JSONObject();
                    JSONArray storedEnchants = new JSONArray();
                    esmeta.getStoredEnchants().forEach((enchantment, integer) -> {
                        storedEnchants.add((enchantment.getName() + ":" + integer));
                    });
                    extraMeta.put("stored-enchants", storedEnchants);
                    metaJson.put("extra-meta", extraMeta);
                }
            } else if (meta instanceof LeatherArmorMeta) {
                LeatherArmorMeta lameta = (LeatherArmorMeta) meta;
                JSONObject extraMeta = new JSONObject();
                extraMeta.put("color", Integer.toHexString(lameta.getColor().asRGB()));
                metaJson.put("extra-meta", extraMeta);
            } else if (meta instanceof BookMeta) {
                BookMeta bmeta = (BookMeta) meta;
                if (bmeta.hasAuthor() || bmeta.hasPages() || bmeta.hasTitle()) {
                    JSONObject extraMeta = new JSONObject();
                    if (bmeta.hasTitle()) {
                        extraMeta.put("title", bmeta.getTitle());
                    }
                    if (bmeta.hasAuthor()) {
                        extraMeta.put("author", bmeta.getAuthor());
                    }
                    if (bmeta.hasPages()) {
                        JSONArray pages = new JSONArray();
                        bmeta.getPages().forEach(str -> pages.add((str)));
                        extraMeta.put("pages", pages);
                    }
                    metaJson.put("extra-meta", extraMeta);
                }
            } else if (meta instanceof PotionMeta) {
                PotionMeta pmeta = (PotionMeta) meta;
                if (pmeta.hasCustomEffects()) {
                    JSONObject extraMeta = new JSONObject();

                    JSONArray customEffects = new JSONArray();
                    pmeta.getCustomEffects().forEach(potionEffect -> {
                        customEffects.add((potionEffect.getType().getName()
                                + ":" + potionEffect.getAmplifier()
                                + ":" + potionEffect.getDuration() / 20));
                    });
                    extraMeta.put("custom-effects", customEffects);

                    metaJson.put("extra-meta", extraMeta);
                }
            } else if (meta instanceof FireworkEffectMeta) {
                FireworkEffectMeta femeta = (FireworkEffectMeta) meta;
                if (femeta.hasEffect()) {
                    FireworkEffect effect = femeta.getEffect();
                    JSONObject extraMeta = new JSONObject();

                    extraMeta.put("type", effect.getType().name());
                    if (effect.hasFlicker()) extraMeta.put("flicker", true);
                    if (effect.hasTrail()) extraMeta.put("trail", true);

                    if (!effect.getColors().isEmpty()) {
                        JSONArray colors = new JSONArray();
                        effect.getColors().forEach(color ->
                                colors.add((Integer.toHexString(color.asRGB()))));
                        extraMeta.put("colors", colors);
                    }

                    if (!effect.getFadeColors().isEmpty()) {
                        JSONArray fadeColors = new JSONArray();
                        effect.getFadeColors().forEach(color ->
                                fadeColors.add((Integer.toHexString(color.asRGB()))));
                        extraMeta.put("fade-colors", fadeColors);
                    }

                    metaJson.put("extra-meta", extraMeta);
                }
            } else if (meta instanceof FireworkMeta) {
                FireworkMeta fmeta = (FireworkMeta) meta;

                JSONObject extraMeta = new JSONObject();

                extraMeta.put("power", fmeta.getPower());

                if (fmeta.hasEffects()) {
                    JSONArray effects = new JSONArray();
                    fmeta.getEffects().forEach(effect -> {
                        JSONObject JSONObject = new JSONObject();

                        JSONObject.put("type", effect.getType().name());
                        if (effect.hasFlicker()) JSONObject.put("flicker", true);
                        if (effect.hasTrail()) JSONObject.put("trail", true);

                        if (!effect.getColors().isEmpty()) {
                            JSONArray colors = new JSONArray();
                            effect.getColors().forEach(color ->
                                    colors.add((Integer.toHexString(color.asRGB()))));
                            JSONObject.put("colors", colors);
                        }

                        if (!effect.getFadeColors().isEmpty()) {
                            JSONArray fadeColors = new JSONArray();
                            effect.getFadeColors().forEach(color ->
                                    fadeColors.add((Integer.toHexString(color.asRGB()))));
                            JSONObject.put("fade-colors", fadeColors);
                        }

                        effects.add(JSONObject);
                    });
                    extraMeta.put("effects", effects);
                }
                metaJson.put("extra-meta", extraMeta);
            } else if (meta instanceof MapMeta) {
                MapMeta mmeta = (MapMeta) meta;
                JSONObject extraMeta = new JSONObject();

                /* 1.11
                if(mmeta.hasLocationName()) {
                    extraMeta.put("location-name", mmeta.getLocationName());
                }
                if(mmeta.hasColor()) {
                    extraMeta.put("color", Integer.toHexString(mmeta.getColor().asRGB()));
                }*/
                extraMeta.put("scaling", mmeta.isScaling());

                metaJson.put("extra-meta", extraMeta);
            }

            itemJson.put("item-meta", metaJson);
        }
        return itemJson.toJSONString();
    }

    /**
     * Parse a JSON to {@link ItemStack}
     *
     * @param string The JSON string
     * @return The {@link ItemStack} or null if not succeed
     */
    public static ItemStack fromJson(String string) {

        XillaJson itemJson = new XillaJson().parse(string);

        String typeElement = itemJson.get("type");
        Object dataElement = itemJson.get("data");
        Object amountElement = itemJson.get("amount");

        if (typeElement != null) {

            short data = dataElement != null ? Short.parseShort(dataElement.toString()) : 0;
            int amount = amountElement != null ? Integer.parseInt(amountElement.toString()) : 1;

            ItemStack itemStack = new ItemStack(Material.getMaterial(typeElement));
            itemStack.setDurability(data);
            itemStack.setAmount(amount);

            if (itemJson.get("item-meta") != null) {

                XillaJson metaJson = new XillaJson(itemJson.get("item-meta"));

                ItemMeta meta = itemStack.getItemMeta();

                String displaynameElement = metaJson.get("displayname");
                JSONArray loreElement = metaJson.get("lore");
                JSONArray enchants = metaJson.get("enchants");
                JSONArray flagsElement = metaJson.get("flags");
                if (meta != null && displaynameElement != null) {
                    meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displaynameElement));
                }
                if (loreElement != null) {
                    List<String> lore = new ArrayList<>(loreElement.size());
                    loreElement.forEach(obj -> {
                        lore.add(ChatColor.translateAlternateColorCodes('&', obj.toString()));
                    });
                    meta.setLore(lore);
                }
                if (enchants != null) {
                    enchants.forEach(obj -> {
                        String enchantString = obj.toString();
                        if (enchantString.contains(":")) {
                            try {
                                String[] splitEnchant = enchantString.split(":");
                                Enchantment enchantment = Enchantment.getByName(splitEnchant[0]);
                                int level = Integer.parseInt(splitEnchant[1]);
                                if (enchantment != null && level > 0) {
                                    meta.addEnchant(enchantment, level, true);
                                }
                            } catch (NumberFormatException ex) {
                            }
                        }
                    });
                }
                if (flagsElement != null) {
                    flagsElement.forEach(obj -> {
                        for (ItemFlag flag : ItemFlag.values()) {
                            if (flag.name().equalsIgnoreCase(obj.toString())) {
                                meta.addItemFlags(flag);
                                break;
                            }
                        }
                    });
                }
                for (String clazz : BYPASS_CLASS) {
                    if (meta.getClass().getSimpleName().equals(clazz)) {
                        itemStack.setItemMeta(meta);
                        return itemStack;
                    }
                }

                XillaJson extraJson = new XillaJson(metaJson.get("extra-meta"));

                if (extraJson != null) {
                    try {
                        if (meta instanceof SkullMeta) {
                            String ownerElement = extraJson.get("owner");
                            if (ownerElement != null) {
                                SkullMeta smeta = (SkullMeta) meta;
                                smeta.setOwner(ownerElement);
                            }
                        } else if (meta instanceof BannerMeta) {
                            String baseColorElement = extraJson.get("base-color");
                            JSONArray patternsElement = extraJson.get("patterns");
                            BannerMeta bmeta = (BannerMeta) meta;
                            if (baseColorElement != null) {
                                try {
                                    Optional<DyeColor> color = Arrays.stream(DyeColor.values())
                                            .filter(dyeColor -> dyeColor.name().equalsIgnoreCase(baseColorElement))
                                            .findFirst();
                                    if (color.isPresent()) {
                                        bmeta.setBaseColor(color.get());
                                    }
                                } catch (NumberFormatException ex) {
                                }
                            }
                            if (patternsElement != null) {
                                List<Pattern> patterns = new ArrayList<>(patternsElement.size());
                                patternsElement.forEach(obj -> {
                                    String patternString = obj.toString();
                                    if (patternString.contains(":")) {
                                        String[] splitPattern = patternString.split(":");
                                        Optional<DyeColor> color = Arrays.stream(DyeColor.values())
                                                .filter(dyeColor -> dyeColor.name().equalsIgnoreCase(splitPattern[0]))
                                                .findFirst();
                                        PatternType patternType = PatternType.getByIdentifier(splitPattern[1]);
                                        if (color.isPresent() && patternType != null) {
                                            patterns.add(new Pattern(color.get(), patternType));
                                        }
                                    }
                                });
                                if (!patterns.isEmpty()) bmeta.setPatterns(patterns);
                            }
                        } else if (meta instanceof EnchantmentStorageMeta) {
                            JSONArray storedEnchantsElement = extraJson.get("stored-enchants");
                            if (storedEnchantsElement != null) {
                                EnchantmentStorageMeta esmeta = (EnchantmentStorageMeta) meta;
                                storedEnchantsElement.forEach(obj -> {
                                    String enchantString = obj.toString();
                                    if (enchantString.contains(":")) {
                                        try {
                                            String[] splitEnchant = enchantString.split(":");
                                            Enchantment enchantment = Enchantment.getByName(splitEnchant[0]);
                                            int level = Integer.parseInt(splitEnchant[1]);
                                            if (enchantment != null && level > 0) {
                                                esmeta.addStoredEnchant(enchantment, level, true);
                                            }
                                        } catch (NumberFormatException ex) {
                                        }
                                    }
                                });
                            }
                        } else if (meta instanceof LeatherArmorMeta) {
                            String colorElement = extraJson.get("color");
                            if (colorElement != null) {
                                LeatherArmorMeta lameta = (LeatherArmorMeta) meta;
                                try {
                                    lameta.setColor(Color.fromRGB(Integer.parseInt(colorElement, 16)));
                                } catch (NumberFormatException ex) {
                                }
                            }
                        } else if (meta instanceof BookMeta) {
                            String titleElement = extraJson.get("title");
                            String authorElement = extraJson.get("author");
                            JSONArray pagesElement = extraJson.get("pages");

                            BookMeta bmeta = (BookMeta) meta;
                            if (titleElement != null) {
                                bmeta.setTitle(titleElement);
                            }
                            if (authorElement != null) {
                                bmeta.setAuthor(authorElement);
                            }
                            if (pagesElement != null) {
                                List<String> pages = new ArrayList<>(pagesElement.size());
                                pagesElement.forEach(obj -> {
                                    pages.add(obj.toString());
                                });
                                bmeta.setPages(pages);
                            }

                        } else if (meta instanceof PotionMeta) {
                            JSONArray customEffectsElement = extraJson.get("custom-effects");
                            if (customEffectsElement != null) {
                                PotionMeta pmeta = (PotionMeta) meta;
                                customEffectsElement.forEach(obj -> {
                                    String enchantString = obj.toString();
                                    if (enchantString.contains(":")) {
                                        try {
                                            String[] splitPotions = enchantString.split(":");
                                            PotionEffectType potionType = PotionEffectType.getByName(splitPotions[0]);
                                            int amplifier = Integer.parseInt(splitPotions[1]);
                                            int duration = Integer.parseInt(splitPotions[2]) * 20;
                                            if (potionType != null) {
                                                pmeta.addCustomEffect(new PotionEffect(potionType, amplifier, duration), true);
                                            }
                                        } catch (NumberFormatException ex) {
                                        }
                                    }
                                });
                            }
                        } else if (meta instanceof FireworkEffectMeta) {
                            String effectTypeElement = extraJson.get("type");
                            Boolean flickerElement = extraJson.get("flicker");
                            Boolean trailElement = extraJson.get("trail");
                            JSONArray colorsElement = extraJson.get("colors");
                            JSONArray fadeColorsElement = extraJson.get("fade-colors");

                            if (effectTypeElement != null) {
                                FireworkEffectMeta femeta = (FireworkEffectMeta) meta;

                                FireworkEffect.Type effectType = FireworkEffect.Type.valueOf(effectTypeElement);

                                if (effectType != null) {
                                    List<Color> colors = new ArrayList<>();
                                    if (colorsElement != null)
                                        colorsElement.forEach(colorElement -> {
                                            colors.add(Color.fromRGB(Integer.parseInt(colorElement.toString(), 16)));
                                        });

                                    List<Color> fadeColors = new ArrayList<>();
                                    if (fadeColorsElement != null)
                                        fadeColorsElement.forEach(colorElement -> {
                                            fadeColors.add(Color.fromRGB(Integer.parseInt(colorElement.toString(), 16)));
                                        });

                                    FireworkEffect.Builder builder = FireworkEffect.builder().with(effectType);

                                    if (flickerElement != null)
                                        builder.flicker(flickerElement);
                                    if (trailElement != null)
                                        builder.trail(trailElement);

                                    if (!colors.isEmpty()) builder.withColor(colors);
                                    if (!fadeColors.isEmpty()) builder.withFade(fadeColors);

                                    femeta.setEffect(builder.build());
                                }
                            }
                        } else if (meta instanceof FireworkMeta) {
                            FireworkMeta fmeta = (FireworkMeta) meta;

                            JSONArray effectArrayElement = extraJson.get("effects");
                            Integer powerElement = extraJson.get("power");

                            if (powerElement != null) {
                                fmeta.setPower(powerElement);
                            }

                            if (effectArrayElement != null) {

                                effectArrayElement.forEach(obj -> {
                                    XillaJson json = new XillaJson((JSONObject)obj);

                                    String effectTypeElement = json.get("type");
                                    Boolean flickerElement = json.get("flicker");
                                    Boolean trailElement = json.get("trail");
                                    JSONArray colorsElement = json.get("colors");
                                    JSONArray fadeColorsElement = json.get("fade-colors");

                                    if (effectTypeElement != null) {

                                        FireworkEffect.Type effectType = FireworkEffect.Type.valueOf(effectTypeElement);

                                        if (effectType != null) {
                                            List<Color> colors = new ArrayList<>();
                                            if (colorsElement != null)
                                                colorsElement.forEach(colorElement -> {
                                                    colors.add(Color.fromRGB(Integer.parseInt(colorElement.toString(), 16)));
                                                });

                                            List<Color> fadeColors = new ArrayList<>();
                                            if (fadeColorsElement != null)
                                                fadeColorsElement.forEach(colorElement -> {
                                                    fadeColors.add(Color.fromRGB(Integer.parseInt(colorElement.toString(), 16)));
                                                });

                                            FireworkEffect.Builder builder = FireworkEffect.builder().with(effectType);

                                            if (flickerElement != null)
                                                builder.flicker(flickerElement);
                                            if (trailElement != null)
                                                builder.trail(trailElement);

                                            if (!colors.isEmpty()) builder.withColor(colors);
                                            if (!fadeColors.isEmpty()) builder.withFade(fadeColors);

                                            fmeta.addEffect(builder.build());
                                        }
                                    }
                                });
                            }
                        } else if (meta instanceof MapMeta) {
                            MapMeta mmeta = (MapMeta) meta;

                            Boolean scalingElement = extraJson.get("scaling");
                            if (scalingElement != null) {
                                mmeta.setScaling(scalingElement);
                            }

                            /* 1.11
                            JSONObject locationNameElement = extraJson.get("location-name");
                            if(locationNameElement != null && locationNameElement.isJsonPrimitive()) {
                                mmeta.setLocationName(locationNameElement.getAsString());
                            }
                            JSONObject colorElement = extraJson.get("color");
                            if(colorElement != null && colorElement.isJsonPrimitive()) {
                                mmeta.setColor(Color.fromRGB(Integer.parseInt(colorElement.getAsString(), 16)));
                            }*/
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }
                itemStack.setItemMeta(meta);
            }
            return itemStack;
        } else return null;
    }

    @NotNull
    public static String translate(char altColorChar, @NotNull String textToTranslate) {
        Validate.notNull(textToTranslate, "Cannot translate null text");
        char[] b = textToTranslate.toCharArray();



        for(int i = 0; i < b.length - 1; ++i) {
            if (b[i] == 167 && "0123456789AaBbCcDdEeFfKkLlMmNnOoRrXx".indexOf(b[i + 1]) > -1) {
                b[i] = altColorChar;
                b[i + 1] = Character.toLowerCase(b[i + 1]);
            }
        }

        return new String(b);
    }
}
