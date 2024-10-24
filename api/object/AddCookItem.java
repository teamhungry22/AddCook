package io.hungry22.addcook.api.object;

import de.tr7zw.changeme.nbtapi.NBT;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import io.hungry22.addcook.AddCook;
import io.hungry22.addcook.core.config.ConfigManager;
import io.hungry22.addcook.core.object.NbtType;
import io.hungry22.addcook.core.util.ItemUtils;
import io.hungry22.addcook.core.util.MessageUtils;

import java.util.List;
import java.util.Optional;

public class AddCookItem {
    private ItemStack itemStack;
    private int amount;
    private String type;
    private String id;
    private Component displayName;
    private List<Component> lore;
    private int customModelData;

    private boolean infinite;
    private boolean returnEnabled;
    private String returnItem;
    private boolean waterEnabled;
    private String waterColor;
    private int water;
    private boolean oilEnabled;
    private String OilColor;
    private int oil;
    private int costAmount;
    private String useSound;

    private String toolType;
    private int durabilityAmount;

    private AddCookItem(ItemStack itemStack, String type, String id) {
        if (itemStack == null || itemStack.getType() == Material.AIR) {
            return;
        }

        this.amount = itemStack.getAmount();
        this.type = type;
        this.id = id;
        NBT.modify(itemStack, nbt -> {
            nbt.resolveOrCreateCompound("addcook").setString("type", type);
            nbt.resolveOrCreateCompound("addcook").setString("id", id);
        });
        this.itemStack = itemStack;

        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            this.displayName = meta.displayName();
            this.lore = meta.lore();
            if (meta.hasCustomModelData()) {
                this.customModelData = meta.getCustomModelData();
            }
        }
    }

    // 새로 만드는 방식
    public static Optional<AddCookItem> build(ItemStack itemStack, String type, String id) {
        if (itemStack == null || itemStack.getType() == Material.AIR) {
            return Optional.empty();
        }
        return Optional.of(new AddCookItem(itemStack, type, id));
    }

    public static Optional<AddCookItem> build(String itemName, String type, String id) {
        ItemStack item = ItemUtils.getCustomItem(itemName);
        return build(item, type, id);
    }

    // 기존 값을 불러오는 방식
    public static Optional<AddCookItem> load(String addCookItemStringId) {
        if (!addCookItemStringId.startsWith("addcook:")) {
            return Optional.empty();
        }

        String[] itemArray = addCookItemStringId.split(":", 3);
        if (itemArray.length < 3) {
            return Optional.empty();
        }

        ConfigManager configManager = AddCook.getConfigManager();
        return switch (itemArray[1]) {
            case "material" -> Optional.ofNullable(configManager.getMaterialMap().get(itemArray[2]));
            case "furniture" -> {
                AddCookFurniture addCookFurniture = configManager.getFurnitureMap().get(itemArray[2]);
                if (addCookFurniture == null) {
                    yield Optional.empty();
                }
                yield Optional.ofNullable(addCookFurniture.getItem());
            }
            case "recipe" -> {
                AddCookRecipe addCookRecipe = configManager.getRecipeMap().get(itemArray[2]);
                if (addCookRecipe == null) {
                    yield Optional.empty();
                }
                yield Optional.ofNullable(addCookRecipe.getRecipeBook());
            }
            case "tool" -> Optional.ofNullable(configManager.getToolMap().get(itemArray[2]));
            default -> Optional.empty();
        };
    }

    public static Optional<AddCookItem> load(ItemStack itemStack) {
        if (!ItemUtils.hasNbt(itemStack, "addcook")) {
            return Optional.empty();
        }

        if (itemStack.getType() == Material.AIR) {
            return Optional.empty();
        }

        String type = ItemUtils.getNbt(itemStack, "addcook;type", NbtType.STRING);
        String id = ItemUtils.getNbt(itemStack, "addcook;id", NbtType.STRING);
        if (type == null || id == null) {
            return Optional.empty();
        }

        return load("addcook:" + type + ":" + id);
    }

    // 기존 값에 추가하는 방식
    public static Optional<AddCookItem> create(String addCookItemStringId, String name) {
       Optional<AddCookItem> addCookItemOpt = load(addCookItemStringId);
       if (addCookItemOpt.isEmpty()) {
           return Optional.empty();
       }

       AddCookItem addCookItem = addCookItemOpt.get();
       Component component = MessageUtils.getComponent(name);
       addCookItem.setDisplayName(component);

       return Optional.of(addCookItem);
    }

    public static Optional<AddCookItem> create(String addCookItemStringId, String name, List<String> lore) {
        Optional<AddCookItem> addCookItemOpt = create(addCookItemStringId, name);
        if (addCookItemOpt.isEmpty()) {
            return Optional.empty();
        }

        AddCookItem addCookItem = addCookItemOpt.get();
        List<Component> componentList = MessageUtils.getComponentList(lore);
        addCookItem.setLore(componentList);

        return Optional.of(addCookItem);
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Component getDisplayName() {
        Component itemName = displayName;
        return (itemName == null) ? itemStack.getItemMeta().displayName() : null;
    }
    public void setDisplayName(Component displayName) {
        this.displayName = displayName;
        ItemMeta meta = itemStack.getItemMeta();
        meta.displayName(displayName);
        itemStack.setItemMeta(meta);
    }

    public List<Component> getLore() {
        List<Component> itemLore = lore;
        return (itemLore == null) ? itemStack.getItemMeta().lore() : null;
    }
    public void setLore(List<Component> lore) {
        this.lore = lore;
        ItemMeta meta = itemStack.getItemMeta();
        meta.lore(lore);
        itemStack.setItemMeta(meta);
    }

    public void setCustomModelData(int customModelData) {
        this.customModelData = customModelData;
    }
    public int getCustomModelData() {
        return customModelData;
    }

    public boolean isInfinite() {
        return infinite;
    }
    public void setInfinite(boolean infinite) {
        this.infinite = infinite;
    }

    public boolean isReturnEnabled() {
        return returnEnabled;
    }
    public void setReturnEnabled(boolean returnEnabled) {
        this.returnEnabled = returnEnabled;
    }
    public String getReturnItem() {
        return returnItem;
    }
    public void setReturnItem(String returnItem) {
        this.returnItem = returnItem;
    }

    public boolean isWaterEnabled() {
        return waterEnabled;
    }
    public void setWaterEnabled(boolean waterEnabled) {
        this.waterEnabled = waterEnabled;
    }
//    public int getWater() {
//        return water;
//    }
//    public void setWater(int water) {
//        this.water = water;
//    }
    public String getWaterColor() {
        return waterColor;
    }
    public void setWaterColor(String waterColor) {
        this.waterColor = waterColor;
    }

    public boolean isOilEnabled() {
        return oilEnabled;
    }
    public void setOilEnabled(boolean oilEnabled) {
        this.oilEnabled = oilEnabled;
    }
    public int getOil() {
        return oil;
    }
    public void setOil(int oil) {
        this.oil = oil;
    }
    public String getOilColor() {
        return OilColor;
    }
    public void setOilColor(String oilColor) {
        OilColor = oilColor;
    }

    public int getCostAmount() {
        return costAmount;
    }
    public void setCostAmount(int costAmount) {
        this.costAmount = costAmount;
    }

    public String getUseSound() {
        return useSound;
    }
    public void setUseSound(String useSound) {
        this.useSound = useSound;
    }

    public String getToolType() {
        return toolType;
    }
    public void setToolType(String toolType) {
        this.toolType = toolType;
        NBT.modify(itemStack, nbt -> {
            nbt.resolveOrCreateCompound("addcook").setString("tool", toolType);
        });
    }

    public int getDurabilityAmount() {
        return durabilityAmount;
    }
    public void setDurabilityAmount(int durabilityAmount) {
        this.durabilityAmount = durabilityAmount;
    }

    public String toStringId() {
        return "addcook:" + this.type + ":" + this.id;
    }
}
