package muramasa.gregtech.common.blocks;

import muramasa.gregtech.Ref;
import muramasa.gregtech.api.data.Textures;
import muramasa.gregtech.api.enums.Casing;
import muramasa.gregtech.api.texture.Texture;
import muramasa.gregtech.api.texture.TextureData;
import muramasa.gregtech.client.render.GTModelLoader;
import muramasa.gregtech.client.render.StateMapperRedirect;
import muramasa.gregtech.client.render.overrides.ItemOverrideCasing;
import muramasa.gregtech.common.tileentities.base.multi.TileEntityCasing;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.actors.threadpool.Arrays;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class BlockCasing extends BlockBaked {

    private Casing type;

    public BlockCasing(Casing type) {
        super(TextureData.get().base(type.getTexture()), new ModelResourceLocation(Ref.MODID + ":layered"));
        setUnlocalizedName("casing_" + type.getName());
        setRegistryName("casing_" + type.getName());
        setCreativeTab(Ref.TAB_BLOCKS);
        setSoundType(SoundType.METAL);
        this.type = type;
    }

    public Casing getType() {
        return type;
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        items.add(new ItemStack(this));
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityCasing();
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(Ref.MODID + ":block_casing", "casing_type=" + type.getName()));
        ModelLoader.setCustomStateMapper(this, new StateMapperRedirect(new ModelResourceLocation(Ref.MODID + ":block_casing", "casing_type=" + type.getName())));
        GTModelLoader.register("block_casing", this);
    }

    @Override
    public List<Texture> getTextures() {
        ArrayList<Texture> textures = new ArrayList<>();
        for (Casing type : Casing.getAll()) {
            textures.add(type.getTexture());
        }
        textures.addAll(Arrays.asList(Textures.LARGE_TURBINE));
        textures.addAll(Arrays.asList(Textures.LARGE_TURBINE_ACTIVE));
        return textures;
    }

    @Override
    public ItemOverrideList getOverride(IBakedModel baked) {
        return new ItemOverrideCasing(baked);
    }
}
