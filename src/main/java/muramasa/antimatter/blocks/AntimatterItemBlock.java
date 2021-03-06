package muramasa.antimatter.blocks;

import muramasa.antimatter.Ref;
import muramasa.antimatter.registration.IItemBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class AntimatterItemBlock extends BlockItem {

    public AntimatterItemBlock(Block block) {
        super(block, new Item.Properties().group(block instanceof IItemBlock ? ((IItemBlock) block).getItemGroup() : Ref.TAB_BLOCKS));
        if (block.getRegistryName() != null) setRegistryName(block.getRegistryName());
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        return getBlock() instanceof IItemBlock ? ((IItemBlock) getBlock()).getDisplayName(stack) : new TranslationTextComponent(stack.getTranslationKey());
    }
}
