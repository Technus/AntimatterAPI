package muramasa.antimatter.client.baked;

import muramasa.antimatter.client.ModelUtils;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.IDynamicBakedModel;
import net.minecraftforge.client.model.data.IModelData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BakedBase implements IDynamicBakedModel {

    protected IBakedModel bakedModel;
    protected TextureAtlasSprite particle;

    public BakedBase(@Nullable IBakedModel bakedModel, ResourceLocation particle) {
        this.bakedModel = bakedModel;
        this.particle = ModelUtils.getSprite(particle);
    }

    public BakedBase(@Nullable IBakedModel bakedModel) {
        this(bakedModel, ModelUtils.ERROR);
    }

    public BakedBase(ResourceLocation texture) {
        this(null, texture);
    }

    public BakedBase() {
        this(null, ModelUtils.ERROR);
    }

    public List<BakedQuad> getBakedQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData data) {
        return bakedModel != null ? bakedModel.getQuads(state, side, rand, data) : Collections.emptyList();
    }

    @Nonnull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData data) {
        try {
            return getBakedQuads(state, side, rand, data);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public ItemOverrideList getOverrides() {
        return ItemOverrideList.EMPTY;
    }

    @Override
    public boolean isAmbientOcclusion() {
        return true;
    }

    @Override
    public boolean isGui3d() {
        return true;
    }

    @Override
    public boolean func_230044_c_() {
        return true; //TODO huh?
    }

    @Override
    public boolean isBuiltInRenderer() {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return getParticleTexture(EmptyModelData.INSTANCE);
    }

    @Override
    public TextureAtlasSprite getParticleTexture(@Nonnull IModelData data) {
        return particle;
    }
}
