package ianm1647.expandeddelight.common.block.entity.inventory;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import org.jspecify.annotations.Nullable;

public class JuicerItemHandler implements IItemHandler {
    private static final int SLOTS_INPUT = 2;
    private static final int SLOT_CONTAINER_INPUT = 3;
    private static final int SLOT_MEAL_OUTPUT = 4;
    private final IItemHandler itemHandler;
    private final Direction side;

    public JuicerItemHandler(IItemHandler itemHandler, @Nullable Direction side) {
        this.itemHandler = itemHandler;
        this.side = side;
    }

    public boolean isItemValid(int slot, ItemStack stack) {
        return this.itemHandler.isItemValid(slot, stack);
    }

    public int getSlots() {
        return this.itemHandler.getSlots();
    }

        public ItemStack getStackInSlot(int slot) {
        return this.itemHandler.getStackInSlot(slot);
    }

        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (this.side != null && !this.side.equals(Direction.UP)) {
            return slot == 3 ? this.itemHandler.insertItem(slot, stack, simulate) : stack;
        } else {
            return slot < 2 ? this.itemHandler.insertItem(slot, stack, simulate) : stack;
        }
    }

        public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (this.side != null && !this.side.equals(Direction.UP)) {
            return slot == 4 ? this.itemHandler.extractItem(slot, amount, simulate) : ItemStack.EMPTY;
        } else {
            return slot < 2 ? this.itemHandler.extractItem(slot, amount, simulate) : ItemStack.EMPTY;
        }
    }

    public int getSlotLimit(int slot) {
        return this.itemHandler.getSlotLimit(slot);
    }
}
