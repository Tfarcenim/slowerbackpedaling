package com.tfarcenim.slowerbackpedaling;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovementInput;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.util.List;


@Mod(SlowerBackpedaling.MODID)
public class SlowerBackpedaling {
    public static final String MODID = "slowerbackpedaling";
    
    public SlowerBackpedaling() {
            MinecraftForge.EVENT_BUS.register(this);
            ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ModConfigs.COMMON_SPEC);
    }

    @SubscribeEvent
    public void move(InputUpdateEvent e) {
        MovementInput input = e.getMovementInput();
        ItemStack stack = e.getPlayer().getMainHandItem();
        if (!causesSlowdown(stack))
        	return;
        if (input.forwardImpulse < 0)
            input.forwardImpulse *= ModConfigs.COMMON.backpedal_modifier.get();
        if (input.leftImpulse != 0)
            input.leftImpulse *= ModConfigs.COMMON.strafe_modifier.get();
    }

    public boolean causesSlowdown(ItemStack stack) {
    	List<? extends String> list = ModConfigs.COMMON.list.get();
    	Item item = stack.getItem();
    	
    	boolean inList = (list.contains(item.getRegistryName().getNamespace()))
    			|| list.contains(item.getRegistryName().toString());
    	
    	return ModConfigs.COMMON.is_whitelist.get() ? inList : !inList;
    }
}