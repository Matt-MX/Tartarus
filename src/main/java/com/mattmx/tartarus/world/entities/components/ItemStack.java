package com.mattmx.tartarus.world.entities.components;

import com.mattmx.tartarus.components.Component;
import com.mattmx.tartarus.components.SpriteRenderer;
import com.mattmx.tartarus.gameengine.GameObject;

public class ItemStack extends Component {
    private Item item;
    private int amount;
    public final static int MAX_STACK = 64;

    public ItemStack() {

    }

    public ItemStack(Item item) {
        this.item = item;
        this.amount = 1;
    }

    public ItemStack(Item item, int amount) {
        this.item = item;
        this.amount = amount;
    }

    // Will return the amount left over from merging stacks
    public int add(ItemStack i) {
        if (canStack(i)) {
            if (this.item == null) {
                this.item = i.getItem();
                this.amount = i.getAmount();
                return 0;
            }
            if (amount + i.getAmount() >= MAX_STACK) {
                int old = amount;
                amount = MAX_STACK;
                return i.getAmount() - (MAX_STACK - old);
            } else {
                amount += i.getAmount();
                return 0;
            }
        }
        return i.getAmount();
    }

    public boolean use(int amount) {
        if (item == null || amount == 0) {
            emptyStack();
            return false;
        }
        if (amount > this.amount) {
            item.onUse(this.amount);
            emptyStack();
            return true;
        } else {
            item.onUse(amount);
            this.amount -= amount;
            return true;
        }
    }

    public void emptyStack() {
        this.amount = 0;
        this.item = null;
    }

    public boolean canStack(Item i) {
        return canStack(new ItemStack(i));
    }

    public boolean canStack(ItemStack i) {
        if (this.item == null) {
            return true;
        }
        if (i.item == this.item) {
            if (i.amount >= MAX_STACK) {
                return false;
            }
            return true;
        }
        return false;
    }

    public Item getItem() {
        return this.item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isEmpty() {
        return item == null || amount == 0;
    }
}
