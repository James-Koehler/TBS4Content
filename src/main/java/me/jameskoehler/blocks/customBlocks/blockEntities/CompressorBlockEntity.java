package me.jameskoehler.blocks.customBlocks.blockEntities;

import me.jameskoehler.screen.CompressorScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static me.jameskoehler.TBS4Content.COMPRESOR_BLOCK_ENTITY;
import static me.jameskoehler.TBS4Content.DANITE_CRYSTAL;

public class CompressorBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);
    /*
    Slot 1 - To compress
    Slot 2 - Fuel
    Slot 3 - Output
     */

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 72;
    private int fuelTime = 0;
    private int maxFuelTime = 100;

    public CompressorBlockEntity(BlockPos pos, BlockState state) {

        super(COMPRESOR_BLOCK_ENTITY, pos, state);

        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                switch(index) {
                    case 0: return CompressorBlockEntity.this.progress;
                    case 1: return CompressorBlockEntity.this.maxProgress;
                    case 2: return CompressorBlockEntity.this.fuelTime;
                    case 3: return CompressorBlockEntity.this.maxFuelTime;
                    default: return 0;
                }
            }

            @Override
            public void set(int index, int value) {
                switch (index){
                    case 0: CompressorBlockEntity.this.progress = value; break;
                    case 1: CompressorBlockEntity.this.maxProgress = value; break;
                    case 2: CompressorBlockEntity.this.fuelTime = value; break;
                    case 3: CompressorBlockEntity.this.maxFuelTime = value; break;
                    default: break;
                }
            }

            @Override
            public int size() {
                return 4;
            }
        };
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.inventory;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("compressor");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new CompressorScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    public static void tick(World world, BlockPos blockPos, BlockState state, CompressorBlockEntity entity) {


        if(world.isClient()){

            return;
        }

        if(hasRecipe(entity)) {

            entity.progress++;
            entity.fuelTime--;
            markDirty(world, blockPos, state);

            if(entity.fuelTime <= 0){

                if(hasFuel(entity)){

                    consumeFuel(entity);
                }else{

                    resetProgress(entity);
                }
            }

            if(entity.progress >= entity.maxProgress){

                craftItem(entity);
            }
        }else{

            resetProgress(entity);
            markDirty(world, blockPos, state);
        }

    }

    private static boolean hasRecipe(CompressorBlockEntity e){

        SimpleInventory inv = new SimpleInventory(e.size());

        for(int i = 0; i < e.size(); i++){

            inv.setStack(i, e.getStack(i));
        }

        boolean hasDaniteInFirstSlot = e.getStack(1).getItem() == DANITE_CRYSTAL;

        return hasDaniteInFirstSlot && canInsertAmountIntoOutputSlot(inv, 1)
                && canInsertItemIntoOutputSlot(inv, DANITE_CRYSTAL) && (hasFuel(e) || e.fuelTime > 0);
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleInventory inv, Item out){

        return inv.getStack(2).getItem() == out || inv.getStack(2).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleInventory inv, int count){

        return inv.getStack(3).getMaxCount() > inv.getStack(3).getCount() + count;
    }

    private static void craftItem(CompressorBlockEntity e){

        SimpleInventory inv = new SimpleInventory(e.size());

        for(int i = 0; i < e.size(); i++){

            inv.setStack(i, e.getStack(i));
        }

        if(hasRecipe(e)){

            e.removeStack(1, 1);
            e.setStack(3, new ItemStack(DANITE_CRYSTAL, e.getStack(3).getCount() + 1));

            resetProgress(e);
        }
    }

    private static boolean hasFuel(CompressorBlockEntity e){

        SimpleInventory inv = new SimpleInventory(e.size());

        for(int i = 0; i < e.size(); i++){

            inv.setStack(i, e.getStack(i));
        }

        return inv.getStack(2).getItem() == Items.BLAZE_ROD;
    }

    private static void resetProgress(CompressorBlockEntity e){

        e.progress = 0;
    }

    private static void consumeFuel(CompressorBlockEntity e){

        if(hasFuel(e)){

            SimpleInventory inv = new SimpleInventory(e.size());

            for(int i = 0; i < e.size(); i++){

                inv.setStack(i, e.getStack(i));
            }

            e.setStack(2, new ItemStack(Items.BLAZE_ROD, e.getStack(2).getCount() - 1));

            e.fuelTime = e.maxFuelTime;
        }
    }
}

