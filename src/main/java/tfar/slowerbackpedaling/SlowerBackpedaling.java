package tfar.slowerbackpedaling;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovementInput;
import net.minecraft.util.math.Vec2f;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.logging.log4j.Logger;

@Mod(modid = SlowerBackpedaling.MODID, name = SlowerBackpedaling.NAME, version = SlowerBackpedaling.VERSION)
@Mod.EventBusSubscriber(Side.CLIENT)
public class SlowerBackpedaling
{
    public static final String MODID = "slowerbackpedaling";
    public static final String NAME = "Slower Backpedaling";
    public static final String VERSION = "1.0";

    @SubscribeEvent
    public static void move(InputUpdateEvent e) {
        MovementInput input = e.getMovementInput();
        double forwardMotion = input.moveForward;
        ItemStack stack = Minecraft.getMinecraft().player.getHeldItemMainhand();
        if (forwardMotion < 0 && causesSlowdown(stack)) {
            input.moveForward *= ModConfigs.backpedal_modifier;
        }
        if (input.moveStrafe != 0 && causesSlowdown(stack)) {
            input.moveStrafe *= ModConfigs.strafe_modifier;
        }
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        ModConfigs.bake();
    }

    public static boolean causesSlowdown(ItemStack stack) {
        if (ModConfigs.is_whitelist) {
            for (ItemStack stack1 : ModConfigs.stacks) {
                if (stack1.getItem() == stack.getItem() && (stack1.getItemDamage() == stack.getItemDamage() || stack1.getItemDamage() == OreDictionary.WILDCARD_VALUE)) {
                    return true;
                }
            }
            return false;
        } else {
            for (ItemStack stack1 : ModConfigs.stacks) {
                if (stack1.getItem() == stack.getItem() && (stack1.getItemDamage() == stack.getItemDamage() || stack1.getItemDamage() == OreDictionary.WILDCARD_VALUE)) {
                    return false;
                }
            }
            return true;
        }
    }

    @SubscribeEvent
    public static void processConfig(ConfigChangedEvent.OnConfigChangedEvent e) {
        if (e.getModID().equals(MODID)) {
            ModConfigs.bake();
        }
    }
}
