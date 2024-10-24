package io.hungry22.addcook.api.object;

import net.kyori.adventure.text.Component;
import io.hungry22.addcook.core.util.MessageUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AddCookRecipe {
    private String id;
    private boolean enabled;
    private String type;
    private List<String> state;
    private String color;
    private double time;
    private int oil;
    private int cut;
    private AddCookItem recipeBook;
    private Map<Integer, List<String>> stageMap;
    private List<String> resultList;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<String> getState() {
        return state;
    }
    public void setState(List<String> state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }

    public double getTime() {
        return time;
    }
    public void setTime(double time) {
        this.time = time;
    }

    public int getOil() {
        return oil;
    }
    public void setOil(int oil) {
        this.oil = oil;
    }

    public int getCut() {
        return cut;
    }
    public void setCut(int cut) {
        this.cut = cut;
    }

    public AddCookItem getRecipeBook() {
        return recipeBook;
    }

    public void setRecipeBook(String itemName, String name, List<String> lore) {
        Optional<AddCookItem> addCookItemOpt = AddCookItem.build(itemName, "recipe", id);
        if (addCookItemOpt.isEmpty()) {
            return;
        }

        this.recipeBook = addCookItemOpt.get();

        if (name != null) {
            Component component = MessageUtils.getComponent(name);
            recipeBook.setDisplayName(component);
        }

        if (lore != null) {
            List<Component> componentList = MessageUtils.getComponentList(lore);
            recipeBook.setLore(componentList);
        }
    }

    public Map<Integer, List<String>> getStageMap() {
        return stageMap;
    }

    public void setStageMap(Integer index, List<String> value) {
        if (index > 7) {
            return;
        }
        this.stageMap = Optional.ofNullable(stageMap).orElseGet(HashMap::new);
        this.stageMap.put(index, value);
    }

    public List<String> getResultList() {
        return resultList;
    }
    public void setResultList(List<String> resultList) {
        this.resultList = resultList;
    }
}
