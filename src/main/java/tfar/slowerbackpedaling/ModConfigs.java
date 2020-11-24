package tfar.slowerbackpedaling;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;

import java.util.ArrayList;
import java.util.List;

@Config(modid = SlowerBackpedaling.MODID)
public class ModConfigs {

    @Config.Name("list")
    public static String[] list = new String[]{};

    @Config.Name("is_whitelist")
    public static boolean is_whitelist = false;

    @Config.Name("strafe_modifier")
    public static double strafe_modifier = .75;

    @Config.Name("backpedal_modifier")
    public static double backpedal_modifier = .75;

    @Config.Ignore
    public static final List<ItemStack> stacks = new ArrayList<>();

    static void bake() {
        stacks.clear();
        for (String s : list) {
            String[] parts = s.split("@");
            Item item = Item.REGISTRY.getObject(new ResourceLocation(parts[0]));
            int meta = Integer.parseInt(parts[1]);
            stacks.add(new ItemStack(item, 1, meta));
        }
    }
}
