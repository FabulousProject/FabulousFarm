package me.alpho320.fabulous.farm.gui;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GUI {

    private final String id;

    private String name;
    private final int size;
    
    private boolean frame;
    private final ItemStack frameItem;

    private final boolean animation;
    private final String animationType;

    private final int itemPerPage;

    private List<Interact> interacts = new ArrayList<>();
    private Map<Integer, Button> slotMap = new HashMap<>();

    public GUI(String id, String name, int size, boolean frame, ItemStack frameItem, boolean animation, String animationType, int itemPerPage, List<Interact> interacts, Map<Integer, Button> slotMap) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.frame = frame;
        this.frameItem = frameItem;
        this.animation = animation;
        this.animationType = animationType;
        this.itemPerPage = itemPerPage;
        this.interacts = interacts;
        this.slotMap = slotMap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public boolean isFrame() {
        return frame;
    }

    public void setFrame(boolean frame) {
        this.frame = frame;
    }

    public ItemStack getFrameItem() {
        return frameItem.clone();
    }

    public boolean isAnimation() {
        return animation;
    }

    public String getAnimationType() {
        return animationType;
    }

    public Map<Integer, Button> getSlotMap() {
        return slotMap;
    }

    public void setSlotMap(Map<Integer, Button> slotMap) {
        this.slotMap = slotMap;
    }

    public int getItemPerPage() {
        return itemPerPage;
    }

    public String getId() {
        return id;
    }

    public List<Interact> getInteracts() {
        return interacts;
    }

    public void setInteracts(List<Interact> interacts) {
        this.interacts = interacts;
    }
}