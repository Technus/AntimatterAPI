package muramasa.antimatter.machines;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import muramasa.antimatter.Ref;
import muramasa.antimatter.registration.IAntimatterObject;
import muramasa.antimatter.texture.Texture;
import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;

public class Tier implements IAntimatterObject {

    private static HashMap<String, Tier> TIER_NAME_LOOKUP = new HashMap<>();
    private static Int2ObjectOpenHashMap<Tier> TIER_ID_LOOKUP = new Int2ObjectOpenHashMap<>();

    private static int lastInternalId = 0;

    /** Electricity Tiers **/
    public static Tier ULV = new Tier(Ref.ID, "ulv", 8, TextFormatting.WHITE); //Tier 1
    public static Tier LV = new Tier(Ref.ID, "lv", 32, TextFormatting.WHITE); //Tier 2
    public static Tier MV = new Tier(Ref.ID, "mv", 128, TextFormatting.WHITE); //Tier 3
    public static Tier HV = new Tier(Ref.ID, "hv", 512, TextFormatting.YELLOW); //Tier 4
    public static Tier EV = new Tier(Ref.ID, "ev", 2048, TextFormatting.AQUA); //Tier 5
    public static Tier IV = new Tier(Ref.ID, "iv", 8192, TextFormatting.LIGHT_PURPLE); //Tier 6
    public static Tier LUV = new Tier(Ref.ID, "luv", 32768, TextFormatting.LIGHT_PURPLE); //Tier 7
    public static Tier ZPM = new Tier(Ref.ID, "zpm", 131072, TextFormatting.LIGHT_PURPLE); //Tier 8
    public static Tier UV = new Tier(Ref.ID, "uv", 524288, TextFormatting.LIGHT_PURPLE); //Tier 9
    public static Tier MAX = new Tier(Ref.ID, "max", 2147483647, TextFormatting.LIGHT_PURPLE); //Tier 15

    /** Special Tiers **/
    //TODO make these Tier 0 and 1?
    public static Tier BRONZE = new Tier(Ref.ID, "bronze", 0, TextFormatting.WHITE);
    public static Tier STEEL = new Tier(Ref.ID, "steel", 0, TextFormatting.WHITE);

    private int internalId;
    private String namespace, id;
    private long voltage;
    private TextFormatting rarityColor;
    private Texture baseTexture;

    public Tier(String namespace, String id, long voltage, TextFormatting rarityColor) {
        internalId = lastInternalId++;
        this.namespace = namespace;
        this.id = id;
        this.voltage = voltage;
        this.rarityColor = rarityColor;
        this.baseTexture = new Texture(namespace, "block/machine/base/" + id);
        TIER_NAME_LOOKUP.put(id, this);
        TIER_ID_LOOKUP.put(internalId, this);
    }

    public int getInternalId() {
        return internalId;
    }

    @Override
    public String getId() {
        return id;
    }

    public long getVoltage() {
        return voltage;
    }

    public TextFormatting getRarityColor() {
        return rarityColor;
    }

    public Texture getBaseTexture() {
        return baseTexture;
    }

    public static int getCount() {
        return TIER_NAME_LOOKUP.size();
    }

    public static Tier[] getSteam() {
        return new Tier[]{BRONZE, STEEL};
    }

    public static Tier[] getStandard() {
        return new Tier[]{LV, MV, HV, EV, IV};
    }

    public static Tier[] getAllElectric() {
        return new Tier[]{ULV, LV, MV, HV, EV, IV, LUV, ZPM, UV, MAX};
    }

    @Nullable
    public static Tier get(String id) {
        return TIER_NAME_LOOKUP.get(id);
    }

    @Nullable
    public static Tier get(int id) {
        return TIER_ID_LOOKUP.get(id);
    }

    public static Tier[] getBasic() {
        return new Tier[]{BRONZE, STEEL, LV, MV, HV, EV, IV};
    }

    public static Tier getMax() {
        return Tier.IV; //TODO update...
    }

    public static Collection<Tier> getAll() {
        return TIER_NAME_LOOKUP.values();
    }

    public static Texture[] getTextures(Tier... tiers) {
        Texture[] textures = new Texture[tiers.length];
        for (int i = 0; i < tiers.length; i++) {
            textures[i] = tiers[i].getBaseTexture();
        }
        return textures;
    }

    public static Texture[] getAllTextures() {
        return getTextures(TIER_NAME_LOOKUP.values().toArray(new Tier[0]));
    }
}
