package me.jameskoehler.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.Nullable;

import static me.jameskoehler.screen.ModScreenHandlers.IMBUER_SCREEN_HANDLER;

public class ImbuerScreenHandler extends ScreenHandler {

    private final Inventory inv;
    private final PropertyDelegate propertyDelegate;

    public ImbuerScreenHandler(int syncId, PlayerInventory playerInv){
        this(syncId, playerInv, new SimpleInventory(4), new ArrayPropertyDelegate(4));
    }

    public ImbuerScreenHandler(int syncId, PlayerInventory playerInv, Inventory inv, PropertyDelegate delegate) {

        super(IMBUER_SCREEN_HANDLER, syncId);

        checkSize(inv, 4);
        this.inv = inv;
        inv.onOpen(playerInv.player);

        this.propertyDelegate = delegate;

        this.addSlot(new Slot(inv, 0, 12, 10)); // input 1
        this.addSlot(new Slot(inv, 1, 12, 20)); // input 2
        this.addSlot(new Slot(inv, 2, 86, 60)); // fuel
        this.addSlot(new Slot(inv, 3, 86, 60)); // finished product

        addPlayerInventory(playerInv);
        addPlayerHotbar(playerInv);

        addProperties(delegate);
    }

    public boolean isCrafting(){

        return propertyDelegate.get(0) > 0;
    }

    public int getScaledProgress(){

        int progress = this.propertyDelegate.get(0);
        int maxProgress = this.propertyDelegate.get(1);
        int progressArrowSize = 26;

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {

        ItemStack newStack = ItemStack.EMPTY;

        Slot slot = this.slots.get(invSlot);

        if(slot != null && slot.hasStack()) {

            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();

            if(invSlot < this.inv.size()){
                if(!this.insertItem(originalStack, this.inv.size(), this.slots.size(), true)){
                    return ItemStack.EMPTY;
                }
            }else if (!this.insertItem(originalStack, 0, this.inv.size(), false)){
                return ItemStack.EMPTY;
            }

            if(originalStack.isEmpty()){
                slot.setStack(ItemStack.EMPTY);
            }else{
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inv.canPlayerUse(player);
    }

    private void addPlayerInventory(PlayerInventory playerInventory){

        for(int i = 0; i < 3; i++){

            for(int l = 0; l < 9; ++l){

                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 86 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory){

        for(int i = 0; i < 9; ++i){

            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 144));
        }
    }
}
