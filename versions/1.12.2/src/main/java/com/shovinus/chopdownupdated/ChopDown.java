package com.shovinus.chopdownupdated;


import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.LinkedList;
import java.util.concurrent.*;

import com.shovinus.chopdownupdated.command.CDUCommand;
import com.shovinus.chopdownupdated.config.Config;
import com.shovinus.chopdownupdated.config.TreeConfiguration;
import com.shovinus.chopdownupdated.tree.Tree;

@Mod(
		modid = ChopDown.MODID,
		name = ChopDown.MODNAME,
		version = ChopDown.VERSION, 
		acceptedMinecraftVersions = "[1.12.2,)",
		acceptableRemoteVersions = "*", 
guiFactory = "com.shovinus.chopdownupdated.config.GuiConfigFactoryChopDown")
public class ChopDown {
	ExecutorService executor;
	
	public static final String MODID = "chopdownupdated";
	public static final String MODNAME = "ChopDownUpdated";
	public static final String VERSION = "1.0.1";
	public static final String AUTHOR = "Shovinus";/*Original Idea by Ternsip,however the mod does not really 
	resemble that in any way other that the turning of blocks in to falling entities with a push out of 1 per y height.*/
	public static LinkedList<Tree> FallingTrees = new LinkedList<Tree>();

	@EventHandler
	public void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);
	}
	@EventHandler
	
		public void preinit(FMLPreInitializationEvent event) {
		   Config.load(event);
		}
	@EventHandler
	public void serverLoad(FMLServerStartingEvent event)
	{
	    // register server commands
		event.registerServerCommand(new CDUCommand());
		executor = Executors.newFixedThreadPool(2);
	}
	@SubscribeEvent
	public void onBlockBreak(BlockEvent.BreakEvent event) {
		World world = event.getWorld();
		BlockPos pos = event.getPos();	
		if(!Tree.isWood(pos, world)) {
			return;
		}
		TreeConfiguration config = Tree.findConfig(world,pos);
		
		if (config == null || !Tree.isTrunk(pos, world,config) || !Tree.isWood(pos.add(0, 1, 0), world)) {
			return;
		}
		//Check to see if this player has already started a tree chop event.
		for (Tree tree : FallingTrees) {
			if (tree.player == event.getPlayer()) {
				event.getPlayer().sendMessage(new TextComponentString("Still chopping down the last tree"));
				event.setCanceled(true);
				return;
			}
		}
		//Initialise the tree and add it to the list, get the executor to start chopping it down;;
		Tree tree;
		try {
			tree = new Tree(pos, world, event.getPlayer());
			FallingTrees.add(tree);
			executor.submit(tree);
		} catch (Exception e) {
			event.getPlayer().sendMessage(new TextComponentString("Can't find a tree configuration for this log."));
		}
		
	}

	@SubscribeEvent
	public void onTick(TickEvent.ServerTickEvent event) {
		try {
			
			for (Tree tree : FallingTrees) {
				if (tree.finishedCalculation) {
					if(tree.dropBlocks()) {
						FallingTrees.remove(tree);
					}
				}
				if(tree.failedToBuild) {
					FallingTrees.remove(tree);
				}
			}
		} catch (Exception ex) {
			System.out.println("Error while continuing to chop trees");
		}
	}
	@SubscribeEvent
	public void clickBlock(PlayerInteractEvent.LeftClickBlock event) {
		if(!(event.getEntityPlayer() instanceof EntityPlayerMP)) {
			return;
		}
		if(Config.getPlayerConfig(event.getEntityPlayer().getUniqueID()).showBlockName) {
			World world = event.getWorld();
			BlockPos pos = event.getPos();
			if(Tree.isLeaves(pos, world) || Tree.isWood(pos, world)) {
				event.getEntityPlayer().sendMessage(new TextComponentString(Tree.blockName(pos, world)));
			}
		}
	}
}