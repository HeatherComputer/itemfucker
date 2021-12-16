package com.github.raveninthedark.itemfucker;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Itemfucker.MODID, version = Itemfucker.VERSION, name = Itemfucker.NAME, acceptableRemoteVersions = "*",
        dependencies = "required-after:spongemixins@[1.3.0,);")
public class Itemfucker {

    public static final Logger log = LogManager.getLogger("Itemfucker");
    public static final String MODID = "itemfucker";
    public static final String VERSION = "GRADLETOKEN_VERSION";
    public static final String NAME = "Basic item overflow protection.";


}
