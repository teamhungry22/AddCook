package io.hungry22.addcook.api.object;

import io.lumine.mythic.core.mobs.ActiveMob;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import io.hungry22.addcook.AddCook;
import io.hungry22.addcook.core.config.ConfigManager;
import io.hungry22.addcook.core.util.EntityUtils;
import io.hungry22.addcook.core.util.ItemUtils;
import io.hungry22.addcook.core.util.MessageUtils;
import io.hungry22.addcook.core.util.MythicUtils;

import java.util.*;

public class AddCookEntity {
    private final ConfigManager configManager = AddCook.getConfigManager();
    private static final Map<UUID, AddCookEntity> entityMap = new HashMap<>();
    private final Entity entity;
    private final ActiveMob activeMob;
    private final Location location;
    private final String ownerUUID;
    private final String furniture;
    private String storage;
    private Integer oil;
    private Integer cut;
    private final int maxOil = configManager.getFurnitureMap().get("fryer").getMaxOil();
    private final int maxCut = configManager.getFurnitureMap().get("chopping_board").getMaxCut();

    //private String modelId;
    //private ModeledEntity modelEntity;
    //private ActiveModel activeModel;

    // TODO 가구 업데이트 - 구조 전부 변경
    private AddCookEntity(String owner, Entity entity, String furniture) {
        this.entity = entity;
        this.activeMob = MythicUtils.getActiveMob(entity);
        this.location = entity.getLocation();
        this.ownerUUID = loadOwnerUUID(owner);

        this.furniture = loadFurniture(furniture);
        this.storage = loadStorage();

        if (furniture.equalsIgnoreCase("fryer")) {
            if (!EntityUtils.hasPDC(entity, "maxoil", PersistentDataType.INTEGER)) {
                EntityUtils.setPDC(entity, "maxoil", PersistentDataType.INTEGER, maxOil);
            }
            this.oil = loadOil();
        } else if (furniture.equalsIgnoreCase("chopping_board")) {
            if (!EntityUtils.hasPDC(entity, "maxcut", PersistentDataType.INTEGER)) {
                EntityUtils.setPDC(entity, "maxcut", PersistentDataType.INTEGER, maxCut);
            }
            this.cut = loadCut();
        }
        entityMap.put(entity.getUniqueId(), this);
    }

    // 새로 만드는 방식
    public static Optional<AddCookEntity> build(String owner, Entity entity, String furniture) {
        if (entity == null) {
            return Optional.empty();
        }
        AddCookEntity addCookEntity = new AddCookEntity(owner, entity, furniture);
        return Optional.of(addCookEntity);
    }

    // 기존 값을 불러오는 방식
    public static Optional<AddCookEntity> load(Entity entity) {
        if (entity == null) {
            return Optional.empty();
        }

        if (!entity.getScoreboardTags().contains("addcook_mob")) {
            return Optional.empty();
        }

        if (!getEntityMap().containsKey(entity.getUniqueId())) {
            String furniture = EntityUtils.getPDC(entity, "furniture", PersistentDataType.STRING);
            if (furniture == null) {
                return Optional.empty();
            }
            String owner = EntityUtils.getPDC(entity, "owner", PersistentDataType.STRING);
            return Optional.of(new AddCookEntity(owner, entity, furniture));
        }
        return Optional.of(getEntityMap().get(entity.getUniqueId()));
    }

    public static Optional<AddCookEntity> load(ActiveMob activeMob) {
        if (activeMob == null) {
            return Optional.empty();
        }
        return load(activeMob.getEntity().getBukkitEntity());
    }

    public Entity getEntity() {
        return entity;
    }

    public Location getLocation() {
        return location;
    }

    public String getOwnerUUID() {
        return ownerUUID;
    }

    public String getFurniture() {
        return furniture;
    }

    public String getStorage() {
        String itemList = EntityUtils.getPDC(entity, "itemlist", PersistentDataType.STRING);
        ;
        return (itemList == null) ? "" : itemList;
    }
    public void setStorage(String itemList) {
        this.storage = itemList;
        EntityUtils.setPDC(entity, "itemlist", PersistentDataType.STRING, itemList);
    }

    public Integer getOil() {
        return oil;
    }

    public void setOil(Integer amount) {
        if (amount >= maxOil) {
            this.oil = amount;
            EntityUtils.setPDC(entity, "oil", PersistentDataType.INTEGER, maxOil);
            return;
        } else if (amount <= 0) {
            this.oil = 0;
            EntityUtils.setPDC(entity, "oil", PersistentDataType.INTEGER, 0);
            return;
        }
        this.oil = amount;
        EntityUtils.setPDC(entity, "oil", PersistentDataType.INTEGER, amount);
    }

    public Integer getCut() {
        return cut;
    }

    public void setCut(Integer amount) {
        if (amount >= maxCut) {
            this.cut = maxCut;
            EntityUtils.setPDC(entity, "cut", PersistentDataType.INTEGER, maxCut);
            return;
        } else if (amount <= 0) {
            this.cut = 0;
            EntityUtils.setPDC(entity, "cut", PersistentDataType.INTEGER, 0);
            return;
        }
        this.cut = amount;
        EntityUtils.setPDC(entity, "cut", PersistentDataType.INTEGER, amount);
    }

    public int getMaxOil() {
        return maxOil;
    }

    public int getMaxCut() {
        return maxCut;
    }

    public static Map<UUID, AddCookEntity> getEntityMap() {
        return entityMap;
    }

    public String loadOwnerUUID(String rawOwnerUUID) {
        if (ownerUUID == null) {
            String ownerUUIDPDC = EntityUtils.getPDC(entity, "owner", PersistentDataType.STRING);
            if (ownerUUIDPDC == null) {
                EntityUtils.setPDC(entity, "owner", PersistentDataType.STRING, rawOwnerUUID);
                return rawOwnerUUID;
            }
            return ownerUUIDPDC;
        }
        return ownerUUID;
    }

    public String loadFurniture(String rawFurniture) {
        if (furniture == null) {
            String furniturePDC = EntityUtils.getPDC(entity, "furniture", PersistentDataType.STRING);
            if (furniturePDC == null) {
                EntityUtils.setPDC(entity, "furniture", PersistentDataType.STRING, rawFurniture);
                return rawFurniture;
            }
            return furniturePDC;
        }
        return furniture;
    }

    public String loadStorage() {
        if (storage == null) {
            String storagePDC = EntityUtils.getPDC(entity, "itemlist", PersistentDataType.STRING);
            if (storagePDC == null) {
                EntityUtils.setPDC(entity, "itemlist", PersistentDataType.STRING, "");
                return "";
            }
            return storagePDC;
        }
        return storage;
    }

    public Integer loadOil() {
        if (oil == null) {
            Integer oilPDC = EntityUtils.getPDC(entity, "oil", PersistentDataType.INTEGER);
            if (oilPDC == null) {
                EntityUtils.setPDC(entity, "oil", PersistentDataType.INTEGER, 0);
                return 0;
            }
            return oilPDC;
        }
        return oil;
    }

    public Integer loadCut() {
        if (cut == null) {
            Integer cutPDC = EntityUtils.getPDC(entity, "cut", PersistentDataType.INTEGER);
            if (cutPDC == null) {
                EntityUtils.setPDC(entity, "cut", PersistentDataType.INTEGER, 0);
                return 0;
            }
            return cutPDC;
        }
        return cut;
    }

    public int getCooldown() {
        return this.activeMob.getGlobalCooldown();
    }

    public int getStorageSize() {
        if (storage.isEmpty()) {
            return 0;
        }
        return storage.split(", ").length;
    }

    public List<String> getStorageFromList() {
        if (storage.isEmpty()) {
            return null;
        }
        return new ArrayList<>(Arrays.asList(storage.split(", ")));
    }

    public String getStorageValue() {
        if (storage.isEmpty()) {
            return "";
        }

        int index = storage.lastIndexOf(", ");
        if (index == -1) {
            String itemName = storage;
            resetStorage();
            return itemName;
        }

        String itemName = storage.substring(index + 2);
        setStorage(storage.substring(0, index));
        return itemName;
    }

    public boolean addStorageValue(Player player, ItemStack itemStack) {
        String itemId;
        String rawItemId = ItemUtils.getCustomItemId(itemStack);

        Optional<AddCookItem> addCookItemOpt = AddCookItem.load(itemStack);
        if (addCookItemOpt.isEmpty()) {
            itemId = rawItemId;
        } else {
            itemId = addCookItemOpt.get().toStringId();
        }

        if (getStorageSize() >= 7) {
            MessageUtils.send(player, this, configManager.getMessageMap().get("FULL_MATERIAL_SPACE"));
            return false;
        }

        setStorage(addList(storage, itemId));
        return true;
    }

    public void resetStorage() {
        storage = "";
        setStorage(storage);
    }

    private String addList(String list, String value) {
        return list.isEmpty() ? value : list + ", " + value;
    }
}
