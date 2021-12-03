package com.mattmx.tartarus.world.entities.components;

import com.mattmx.tartarus.gameengine.renderer.DebugDraw;
import org.lwjgl.system.CallbackI;

import java.util.*;

public class Inventory {
    public static final int DEFAULT_MAX_SIZE = 4;
    private ItemStack[] inventory;
    private boolean isFull;
    private int held;

    public Inventory(int override) {
        if (override > -1) this.inventory = new ItemStack[override];
        if (override <= -1) this.inventory = new ItemStack[DEFAULT_MAX_SIZE];

        for (int i = 0; i < inventory.length; i++) {
            inventory[i] = new ItemStack();
        }
    }

    public ItemStack dropItem(int slot, int amount) {
        if (inventory[slot].isEmpty()) return null;
        Item i = inventory[slot].getItem();
        int o = inventory[slot].getAmount();
        inventory[slot].setAmount(inventory[slot].getAmount() - amount);
        if (inventory[slot].getAmount() <= 0) {
            inventory[slot] = new ItemStack();
            return new ItemStack(i, o);
        }
        return new ItemStack(i, amount);
    }

    public ItemStack dropStack(ItemStack i) {
        int slot = getSlot(i);
        if (slot != -1) {
            dropStack(slot);
            return i;
        }
        return null;
    }

    public void dropStack(int slot) {
        if (slot != -1) {
            inventory[slot] = new ItemStack();
        }
    }

    public int addStack(ItemStack i) {
        int left = i.getAmount();
        for (int c = 0; c < inventory.length; c++) {
            if (canAddStack(i, c)) {
                left = addStack(i, c);
                i.setAmount(left);
                if (i.getAmount() == 0) {
                    return 0;
                }
            }
        }
        return Math.max(left, 0);
    }

    public int addStack(ItemStack i, int slot) {
        if (inventory[slot].canStack(i)) {
            return inventory[slot].add(i);
        } else return -1;
    }

    public boolean canAddStack(ItemStack i, int slot) {
        if (inventory[slot].canStack(i)) {
            return true;
        } else return false;
    }

    public boolean checkIsFull() {
        if (inventory == null) return false;
        for (ItemStack i : inventory) {
            if (i.getAmount() == ItemStack.MAX_STACK) {
                return true;
            }
        }
        return false;
    }

    public void setStack(ItemStack i, int slot) {
        inventory[slot] = i;
    }

    public boolean isFull() {
        return isFull;
    }

    public ItemStack[] getContents() {
        return this.inventory;
    }

    public int getHeld() {
        return held;
    }

    public void setHeld(int slot) {
        this.held = slot;
    }

    public ItemStack getHeldItemStack() {
        return inventory[held];
    }

    public int getSlot(ItemStack i) {
        int index = 0;
        for (ItemStack item : inventory) {
            if (i == item) {
                return index;
            } else {
                index++;
            }
        }
        return -1;
    }

    public int getUsedSlots() {
        int count = 0;
        for (ItemStack i : inventory) {
            if (!i.isEmpty()) {
                count++;
            }
        }
        return count;
    }
}
