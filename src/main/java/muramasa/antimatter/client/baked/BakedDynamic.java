package muramasa.antimatter.client.baked;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import muramasa.antimatter.AntimatterProperties;
import muramasa.antimatter.blocks.BlockDynamic;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ILightReader;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class BakedDynamic extends BakedBase {

    private Int2ObjectOpenHashMap<IBakedModel> bakedLookup;
    private IBakedModel defaultModel;
    private boolean hasConfig, onlyNullSide;

    private BlockPos.Mutable mutablePos = new BlockPos.Mutable();
    private IModelData configData = new ModelDataMap.Builder().withInitial(AntimatterProperties.DYNAMIC_CONFIG, new int[0]).build();

    public BakedDynamic(Int2ObjectOpenHashMap<IBakedModel> bakedLookup, IBakedModel defaultModel, ResourceLocation particle) {
        super(particle);
        this.bakedLookup = bakedLookup;
        this.defaultModel = defaultModel;
        this.hasConfig = bakedLookup.size() > 0;
    }

    public BakedDynamic onlyNullSide() { //If the model only has "general quads", like pipes
        this.onlyNullSide = true;
        return this;
    }

    @Nonnull
    @Override
    public IModelData getModelData(@Nonnull ILightReader world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull IModelData data) {
        if (!hasConfig || !(state.getBlock() instanceof BlockDynamic)) return data;
        mutablePos.setPos(pos);
        configData.setData(AntimatterProperties.DYNAMIC_CONFIG, ((BlockDynamic) state.getBlock()).getConfig(state, world, mutablePos, pos));
        return configData;
    }

    @Override
    public List<BakedQuad> getBakedQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData data) {
        if (!hasConfig) return defaultModel.getQuads(state, side, rand, data);
        if (onlyNullSide && side != null) return Collections.emptyList();
        List<BakedQuad> quads = new LinkedList<>();
        int[] ct = data.getData(AntimatterProperties.DYNAMIC_CONFIG);
        if (ct == null || ct.length == 0 || ct[0] == -1) return defaultModel.getQuads(state, side, rand, data);
        IBakedModel baked;
        for (int i = 0; i < ct.length; i++) {
            baked = bakedLookup.get(ct[i]);
            if (baked != null) quads.addAll(baked.getQuads(state, side, rand, data));
        }
        return quads;
    }
}
