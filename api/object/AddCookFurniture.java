package io.hungry22.addcook.api.object;

import java.util.List;
import java.util.Map;

public class AddCookFurniture {
    private Map<String, List<String>> soundMap;
    private Map<String, List<String>> stateMap;
    private Map<String, Double> cooldownMap;
    private String permission;
    private AddCookItem item;
    private Double yoffset;
    private Integer maxOil;
    private Integer maxCut;

    public String getPermission() {
        return permission;
    }
    public void setPermission(String per) {
        this.permission = per;
    }

    public AddCookItem getItem() {
        return item;
    }
    public void setItem(AddCookItem addCookItem) {
        this.item = addCookItem;
    }

    public Map<String, List<String>> getSoundMap() {
        return soundMap;
    }
    public void setSoundMap(Map<String, List<String>> soundMap) {
        this.soundMap = soundMap;
    }

    public Map<String, List<String>> getStateMap() {
        return stateMap;
    }
    public void setStateMap(Map<String, List<String>> stateMap) {
        this.stateMap = stateMap;
    }

    public Map<String, Double> getCooldownMap() {
        return cooldownMap;
    }
    public void setCooldownMap(Map<String, Double> cooldownMap) {
        this.cooldownMap = cooldownMap;
    }

    public Double getYoffset() {
        return yoffset;
    }
    public void setYoffset(Double yoffset) {
        this.yoffset = yoffset;
    }

    public Integer getMaxOil() {
        return maxOil;
    }

    public void setMaxOil(Integer maxOil) {
        this.maxOil = maxOil;
    }

    public Integer getMaxCut() {
        return maxCut;
    }

    public void setMaxCut(Integer maxCut) {
        this.maxCut = maxCut;
    }
}
