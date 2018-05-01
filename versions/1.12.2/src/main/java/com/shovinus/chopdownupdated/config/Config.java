package com.shovinus.chopdownupdated.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.google.gson.Gson;
import com.shovinus.chopdownupdated.ChopDown;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class Config {

	public static String CATEGORY = "General";

	public static boolean lowerLogs;
	public static boolean breakLeaves;
	public static int maxDropsPerTickPerTree;
	public static int maxFallingBlockBeforeManualMove;
	public static boolean useFallingEntities;

	public static HashMap<UUID, PersonalConfig> playerConfigs = new HashMap<UUID, PersonalConfig>();
	public static TreeConfiguration[] treeConfigurations;

	public static PersonalConfig getPlayerConfig(UUID player) {
		PersonalConfig playerConfig;
		if (playerConfigs.containsKey(player)) {
			playerConfig = Config.playerConfigs.get(player);
		} else {
			playerConfig = new PersonalConfig();
			playerConfigs.put(player, playerConfig);
		}
		return playerConfig;

	}

	public static Configuration config;

	public static void load(FMLPreInitializationEvent event) {
		config = new Configuration(event.getSuggestedConfigurationFile(),"1.0.8");
		reloadConfig();
	}
	public static String[] DefaultTreeConfigs () {
		return new String[] {
				"{\"radius\":16,\"trunk_radius\":3,\"blocks\":[\"biomesoplenty:log_0:4\",\"biomesoplenty:leaves_3:7\"]}",
				"{\"blocks\":[\"biomesoplenty:log_0:5\",\"biomesoplenty:leaves_2:2\",\"biomesoplenty:leaves_2:1\"]}",
				"{\"blocks\":[\"biomesoplenty:log_0:7\",\"biomesoplenty:leaves_1:6\"]}",
				"{\"blocks\":[\"biomesoplenty:log_1:4\",\"biomesoplenty:leaves_0:6\"]}",
				"{\"blocks\":[\"biomesoplenty:log_1:5\",\"biomesoplenty:leaves_0:3\"]}",
				"{\"blocks\":[\"biomesoplenty:log_1:6\",\"biomesoplenty:leaves_4:0\"]}",
				"{\"blocks\":[\"biomesoplenty:log_1:7\",\"biomesoplenty:leaves_4:9\"]}",
				"{\"trunk_radius\":5,\"blocks\":[\"biomesoplenty:log_2:4\",\"biomesoplenty:leaves_4:2\"]}",
				"{\"min_vertical_logs\":4,\"blocks\":[\"biomesoplenty:log_2:5\",\"biomesoplenty:leaves_4:3\"]}",
				"{\"blocks\":[\"biomesoplenty:log_2:6\",\"biomesoplenty:leaves_5:4\"]}",
				"{\"blocks\":[\"biomesoplenty:log_3:4\",\"biomesoplenty:leaves_3:6\"]}",
				"{\"blocks\":[\"biomesoplenty:log_3:5\",\"biomesoplenty:leaves_5:5\"]}",
				"{\"blocks\":[\"biomesoplenty:log_3:6\",\"biomesoplenty:leaves_5:6\"]}",
				"{\"blocks\":[\"biomesoplenty:log_3:7\",\"biomesoplenty:leaves_5:7\"]}",
				"{\"blocks\":[\"biomesoplenty:log_4:5\"]}",
				"{\"blocks\":[\"extratrees:logs:0\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.0:0\",\"forestry:leaves.default.2:3\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.0:1\",\"forestry:leaves.default.4:0\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.0:2\",\"forestry:leaves.default.5:3\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.0:3\",\"forestry:leaves.default.0:3\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.1:0\",\"forestry:leaves.default.1:1\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.1:1\",\"forestry:leaves.default.6:3\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.1:2\",\"forestry:leaves.default.7:0\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.1:3\",\"forestry:leaves.default.3:1\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.2:0\",\"forestry:leaves.default.4:2\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.2:1\",\"forestry:leaves.default.4:3\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.2:2\",\"forestry:leaves.default.5:1\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.2:3\",\"forestry:leaves.default.6:1\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.3:0\",\"forestry:leaves.default.7:2\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.3:1\",\"forestry:leaves.default.1:0\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.3:2\",\"forestry:leaves.default.7:3\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.3:3\",\"forestry:leaves.default.1:2\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.4:0\",\"forestry:leaves.default.7:1\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.4:1\",\"forestry:leaves.default.8:2\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.4:2\",\"forestry:leaves.default.8:1\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.4:3\",\"forestry:leaves.default.8:0\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.5:0\",\"forestry:leaves.default.3:0\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.5:1\",\"forestry:leaves.default.2:0\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.5:2\",\"forestry:leaves.default.2:1\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.5:3\",\"forestry:leaves.default.1:3\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.6:0\",\"forestry:leaves.default.3:2\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.6:1\",\"forestry:leaves.default.4:1\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.6:2\",\"forestry:leaves.default.6:0\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.6:3\",\"forestry:leaves.default.6:2\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.7:0\",\"forestry:leaves.default.5:0\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.fireproof.0:0\",\"forestry:leaves.default.2:3\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.fireproof.0:1\",\"forestry:leaves.default.4:0\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.fireproof.0:2\",\"forestry:leaves.default.5:3\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.fireproof.0:3\",\"forestry:leaves.default.0:3\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.fireproof.1:0\",\"forestry:leaves.default.1:1\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.fireproof.1:1\",\"forestry:leaves.default.6:3\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.fireproof.1:2\",\"forestry:leaves.default.7:0\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.fireproof.1:3\",\"forestry:leaves.default.3:1\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.fireproof.2:0\",\"forestry:leaves.default.4:2\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.fireproof.2:1\",\"forestry:leaves.default.4:3\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.fireproof.2:2\",\"forestry:leaves.default.5:1\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.fireproof.2:3\",\"forestry:leaves.default.6:1\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.fireproof.3:0\",\"forestry:leaves.default.7:2\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.fireproof.3:1\",\"forestry:leaves.default.1:0\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.fireproof.3:2\",\"forestry:leaves.default.7:3\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.fireproof.3:3\",\"forestry:leaves.default.1:2\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.fireproof.4:0\",\"forestry:leaves.default.7:1\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.fireproof.4:1\",\"forestry:leaves.default.8:2\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.fireproof.4:2\",\"forestry:leaves.default.8:1\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.fireproof.4:3\",\"forestry:leaves.default.8:0\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.fireproof.5:0\",\"forestry:leaves.default.3:0\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.fireproof.5:1\",\"forestry:leaves.default.2:0\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.fireproof.5:2\",\"forestry:leaves.default.2:1\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.fireproof.5:3\",\"forestry:leaves.default.1:3\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.fireproof.6:0\",\"forestry:leaves.default.3:2\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.fireproof.6:1\",\"forestry:leaves.default.4:1\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.fireproof.6:2\",\"forestry:leaves.default.6:0\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.fireproof.6:3\",\"forestry:leaves.default.6:2\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.fireproof.7:0\",\"forestry:leaves.default.5:0\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.vanilla.fireproof.0:0\",\"forestry:leaves.default.0:0\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.vanilla.fireproof.0:1\",\"forestry:leaves.default.2:2\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.vanilla.fireproof.0:2\",\"forestry:leaves.default.0:2\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.vanilla.fireproof.0:3\",\"forestry:leaves.default.3:3\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.vanilla.fireproof.1:0\",\"forestry:leaves.default.5:2\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"forestry:logs.vanilla.fireproof.1:1\",\"forestry:leaves.default.0:1\",\"forestry:leaves:0\"]}",
				"{\"blocks\":[\"harvestcraft:pamcinnamon:0\",\"minecraft:leaves:3\"]}",
				"{\"blocks\":[\"harvestcraft:pammaple:0\",\"minecraft:leaves:1\"]}",
				"{\"blocks\":[\"ic2:rubber_wood:0\",\"ic2:leaves:0\"]}",
				"{\"blocks\":[\"integrateddynamics:menrilLog:0\",\"integrateddynamics:menrilFilledLog:0\",\"integrateddynamics:menrilLeaves:0\",\"integrateddynamics:menril_log:0\",\"integrateddynamics:menril_leaves:0\",\"integrateddynamics:menril_log_filled:0\"]}",
				"{\"blocks\":[\"minecraft:log:0\",\"minecraft:leaves:0\",\"biomesoplenty:leaves_0:1\",\"biomesoplenty:leaves_3:9\",\"biomesoplenty:leaves_3:5\",\"biomesoplenty:leaves_2:3\",\"biomesoplenty:leaves_1:5\",\"forestry:leaves:0\",\"traverse:red_autumnal_leaves:0\",\"traverse:brown_autumnal_leaves:0\"]}",
				"{\"blocks\":[\"minecraft:log:1\",\"minecraft:leaves:1\",\"forestry:leaves:0\",\"forestry:leaves.default.2:2\"]}",
				"{\"blocks\":[\"minecraft:log:2\",\"minecraft:leaves:2\",\"biomesoplenty:leaves_0:0\",\"forestry:leaves:0\",\"forestry:leaves.default.0:2\"]}",
				"{\"blocks\":[\"minecraft:log:3\",\"minecraft:leaves:3\",\"harvestcraft:pampaperbark:0\",\"forestry:leaves:0\",\"forestry:leaves.default.3:3\"]}",
				"{\"blocks\":[\"minecraft:log2:0\",\"minecraft:leaves2:0\",\"biomesoplenty:leaves_0:1\",\"forestry:leaves:0\",\"forestry:leaves.default.5:2\"]}",
				"{\"blocks\":[\"minecraft:log2:1\",\"minecraft:leaves2:1\",\"biomesoplenty:leaves_0:1\",\"biomesoplenty:leaves_1:5\",\"forestry:leaves:0\",\"forestry:leaves.default.0:1\"]}",
				"{\"blocks\":[\"natura:nether_logs:0\",\"natura:nether_leaves:0\"]}",
				"{\"blocks\":[\"natura:nether_logs:1\",\"natura:nether_leaves2:0\",\"natura:nether_leaves2:1\",\"natura:nether_leaves2:2\",\"natura:nether_leaves2:10\"]}",
				"{\"blocks\":[\"natura:nether_logs:2\",\"natura:nether_leaves:1\"]}",
				"{\"blocks\":[\"natura:nether_logs2:0\",\"natura:nether_logs2:1\",\"natura:nether_leaves:0\"]}",
				"{\"blocks\":[\"natura:overworld_logs:0\",\"natura:overworld_leaves:0\"]}",
				"{\"blocks\":[\"natura:overworld_logs:1\",\"natura:overworld_leaves:1\"]}",
				"{\"blocks\":[\"natura:overworld_logs:2\",\"natura:overworld_leaves:2\"]}",
				"{\"blocks\":[\"natura:overworld_logs:3\",\"natura:overworld_leaves:3\"]}",
				"{\"blocks\":[\"natura:overworld_logs2:0\",\"natura:overworld_leaves2:0\"]}",
				"{\"blocks\":[\"natura:overworld_logs2:1\",\"natura:overworld_leaves2:1\"]}",
				"{\"blocks\":[\"natura:overworld_logs2:2\",\"natura:overworld_leaves2:2\"]}",
				"{\"blocks\":[\"natura:overworld_logs2:3\",\"natura:overworld_leaves2:3\"]}",
				"{\"radius\":32,\"leaf_limit\":32,\"trunk_radius\":8,\"blocks\":[\"natura:redwood_logs:0\",\"natura:redwood_logs:1\",\"natura:redwood_logs:2\",\"natura:redwood_leaves:0\"]}",
				"{\"blocks\":[\"rustic:log:0\",\"rustic:leaves:0\"]}",
				"{\"blocks\":[\"rustic:log:1\",\"rustic:leaves:1\"]}",
				"{\"blocks\":[\"terra:blackspruce_log:0\",\"terra:blackspruce_leaves:0\"]}",
				"{\"blocks\":[\"terra:bluespruce_log:0\",\"terra:bluespruce_leaves:0\"]}",
				"{\"blocks\":[\"terra:cherry_log:0\",\"terra:cherry_leaves_white:0\",\"terra:cherry_leaves_purple:0\"]}",
				"{\"blocks\":[\"terra:ebony_log:0\",\"terra:palm_leaves:0\"]}",
				"{\"blocks\":[\"terra:elm_log:0\",\"terra:elm_leaves:0\"]}",
				"{\"blocks\":[\"terra:jacaranda_log:0\",\"terra:jacaranda_leaves_magenta:0\",\"terra:jacaranda_leaves_green:0\"]}",
				"{\"blocks\":[\"terra:mahogany_log:0\",\"terra:mahogany_leaves:0\"]}",
				"{\"blocks\":[\"terra:palm_log:0\",\"terra:palm_leaves:0\"]}",
				"{\"blocks\":[\"terra:paulownia_log:0\",\"terra:paulownia_leaves_green:0\",\"terra:paulownia_leaves_white:0\",\"terra:paulownia_leaves_blue:0\"]}",
				"{\"blocks\":[\"terra:redspruce_log:0\",\"terra:redspruce_leaves:0\"]}",
				"{\"blocks\":[\"terra:whitespruce_log:0\",\"terra:whitespruce_leaves:0\"]}",
				"{\"blocks\":[\"thaumcraft:log_greatwood:0\",\"thaumcraft:leaves_greatwood:0\"]}",
				"{\"blocks\":[\"thaumcraft:log_silverwood:0\",\"thaumcraft:leaves_silverwood:0\"]}",
				"{\"blocks\":[\"traverse:fir_log:0\",\"traverse:fir_leaves:0\"]}",
				"{\"radius\":15,\"leaf_limit\":10,\"blocks\":[\"tropicraft:log:0\",\"tropicraft:leaves:0\"]}", };
	}
	public static void reloadConfig() {

		lowerLogs = config.getBoolean("lowerLogs", CATEGORY, true,
				"Whether to move logs down through leaves prior to dropping, makes for a better looking fallen tree but adds a few extra iterations, try setting to false if server not handling well.");
		maxDropsPerTickPerTree = config.getInt("maxDropsPerTickPerTree", CATEGORY, 150, 1, 1000000,
				"Maximum number of blocks to drop per tick for each tree thats falling");
		maxFallingBlockBeforeManualMove = config.getInt("maxFallingBlockBeforeManualMove", CATEGORY, 1500, 1, 1000000,
				"If the total blocks in the tree is above this amount instead of creating entities then it will place the blocks directly on the floor, this is for really large trees like the natura Redwood");
		String[] tempTreeConfig = config.getStringList("treeConfigurations", CATEGORY,
				DefaultTreeConfigs(),
				"List of possible trees, i.e. spruce log and spruce leaves, this makes felling trees more acurate for mixed trees, it also allows large trees like natura redwoods to be chopped more acurately, normally this tree would get ignored because its leaves reach further than a normal tree and its radius is much wider");
		breakLeaves = config.getBoolean("breakLeaves", CATEGORY, false,
				"When you chop a tree down the leaves all fall off and do their drops instead of falling with the tree, this can be better as a) less load and b)The falling of trees gets less messy, you still need to chop the logs but the leaves don't get in the way");
		useFallingEntities = config.getBoolean("useFallingEntities", CATEGORY, true,
				"Whether to use falling entities for the block fall, looks slightly smoother but can be abused to pop off logs instead of having to chop them");

		List<TreeConfiguration> tempTreeConfigurations = new ArrayList<TreeConfiguration>();
		for (String treeConfig : tempTreeConfig) {
			tempTreeConfigurations.add(new Gson().fromJson(treeConfig, TreeConfiguration.class));
		}
		treeConfigurations = tempTreeConfigurations.toArray(new TreeConfiguration[tempTreeConfigurations.size()]);
		config.save();

	}

	@SubscribeEvent
	public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equals(ChopDown.MODID)) {
			reloadConfig();
		}
	}

}
