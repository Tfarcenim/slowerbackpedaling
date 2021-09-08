package com.tfarcenim.slowerbackpedaling;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Lists;


public class ModConfigs {
    
	public static class Common {

		public final ConfigValue<List<? extends String>> list;
		public final ConfigValue<Boolean> is_whitelist;
		public final ConfigValue<Double> strafe_modifier;
		public final ConfigValue<Double> backpedal_modifier;
		
		public Common(ForgeConfigSpec.Builder builder) {			
	        list = builder.comment("List of item IDs, for example 'minecraft:sand'")
	        		.define("list", Lists.newArrayList());
	        is_whitelist = builder.comment("If this is set to false, the player will be normal speed while holding the items in the list, and"
	        		+ " slowed down the rest of the time. If this is set to true, the player will be slowed down while holding the items"
	        		+ " in the list, and normal speed the rest of the time.")
	        		.define("is_whitelist", false);
	        strafe_modifier = builder.define("strafe_modifier", .75);
	        backpedal_modifier = builder.define("backpedal_modifier", .75);
		}
	}

	public static final Common COMMON;
	public static final ForgeConfigSpec COMMON_SPEC;

	static { //constructor
		Pair<Common, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(Common::new);
		COMMON = commonSpecPair.getLeft();
		COMMON_SPEC = commonSpecPair.getRight();
	}
}
