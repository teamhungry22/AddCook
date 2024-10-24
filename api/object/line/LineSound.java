package io.hungry22.addcook.api.object.line;

public class LineSound {
    private String sound;
    private String line;
    private float volume;
    private float pitch;
    private double chance;

    public LineSound(String line, String sound, float volume, float pitch, double chance) {
        this.line = line;
        this.sound = sound.replaceFirst("sound:", "");
        this.volume = volume;
        this.pitch = pitch;
        this.chance = chance;
    }

    public String getLine() {
        return line;
    }
    public void setLine(String line) {
        this.line = line;
    }

    public String getSound() {
        return sound;
    }
    public void setSound(String sound) {
        this.sound = sound;
    }

    public float getVolume() {
        return volume;
    }
    public void setVolume(float volume) {
        this.volume = volume;
    }

    public float getPitch() {
        return pitch;
    }
    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public double getChance() {
        return chance;
    }
    public void setChance(double chance) {
        this.chance = chance;
    }
}

