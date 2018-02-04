package mdc.voodoocraft.config;

import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.IConfigElement;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;
import static net.minecraftforge.common.config.Configuration.CATEGORY_CLIENT;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import mdc.voodoocraft.Reference;

@EventBusSubscriber
public class VoodooConfig
{
	/**CONFIG VALUES**/
	public static boolean advancedOutput, replaceOnDeath;

	public static Configuration config;
	
	public static String[] commandResultDetails = {
			"Hexes are what you use to cast either a light or a dark \'spell\' on an entity. Hexes are categorized into two different kinds of hexes, as suggested: light and dark. Light hexes consist of any hexes that don\'t cause any kind of harm to another entity. Dark hexes consist of any hexes that DO cause harm to another entity. To apply a hex, you must first have a certain totem poll and symbols drawn on the ground surrounding the pedestal. For more information on rituals, do /vdcraft help ritual or go to our wiki page here: <TODO: Create wiki page and provide link>.",
			"Rituals are used to cast hexes upon dolls (see /vdcraft help items for more information). I can't put much here so I will refer you to the wiki if you want more info on the rituals and how they work ----> <TODO: Create wiki page and provide link>.",
			"The various items in this mod are used to cast hexes upon the dolls which are then used to cast hexes upon living entities. For more information please refer to this command ---> /vdcraft help items <item_name:item_unlocalized_name>",
			"Chalk is used to draw out the patterns necessary for specific hexes. More info here ---> <TODO: Create wiki page and provide link>",
			"Dolls are used to hold the hexes that are used on living entities. You must acquire a shard to craft the dolls to have a certain hex on a specific living entity.",
			"Shards are acquired from certain entities and they contain the UUID of the entity it was acquired from, which allows you to hex that specific entity. For more information, please refer to the wiki here ----> <TODO: Create wiki page and provide link>.",
			"Invalid command. Please use: /vdcraft help <feature>. For a list of features, please refer to our wiki."
	};

	public static void init(File configFile)
	{
		config = new Configuration(configFile); //Create configuration object from the given configuration file

		/**CUSTOM CATEGORY COMMENTS**/
		config.addCustomCategoryComment("Tweaks", "Tweak certian things");

		syncConfiguration();
	}

	private static void syncConfiguration()
	{
		/**CONFIG START**/
		advancedOutput = config.getBoolean("advancedOutput", CATEGORY_CLIENT, true, "Provide additional feedback to your actions to get used to the mod quickly ;)");
		replaceOnDeath = config.getBoolean("replaceOnDeath", CATEGORY_GENERAL, false, "Whether the glyph placed on player death has a last resort option of replacing the block at the player's last position (and the one below if it's not solid)");

		/**CONFIG END**/
		if(config.hasChanged())
			config.save();
	}

	@SubscribeEvent
	public static void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
	{
		if(event.getModID().equalsIgnoreCase(Reference.MODID))
			//Resync configs
			syncConfiguration();
	}

	public static List<IConfigElement> getEntries() {
        List<IConfigElement> entries = new ArrayList<IConfigElement>();
        Set<String> categories = config.getCategoryNames();
        Iterator<String> i = categories.iterator();
        while (i.hasNext()) {
            String categoryName = i.next();
            ConfigCategory category = config.getCategory(categoryName);
            entries.addAll(new ConfigElement(category).getChildElements());
        }
        return entries;
    }
}
