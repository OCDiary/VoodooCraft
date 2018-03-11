package mdc.voodoocraft;

import net.minecraftforge.common.config.Config;

@Config(modid = VoodooCraft.MODID)
@Config.LangKey(VoodooCraft.MODID + ".config.title")
public class VoodooConfig
{
	@Config.Comment("Provide additional feedback to your actions to get used to the mod quickly")
	public static boolean advancedOutput = true;

	@Config.Comment("Whether the glyph placed on player death has a last resort option of replacing the block at the player's last position (and the one below if it's not solid)")
	public static boolean replaceOnDeath = false;

	//TODO: What is this doing here?? Move this into the lang file!
	public static String[] commandResultDetails = {
			"Hexes are what you use to cast either a light or a dark \'spell\' on an entity. Hexes are categorized into two different kinds of hexes, as suggested: light and dark. Light hexes consist of any hexes that don\'t cause any kind of harm to another entity. Dark hexes consist of any hexes that DO cause harm to another entity. To apply a hex, you must first have a certain totem poll and symbols drawn on the ground surrounding the pedestal. For more information on rituals, do /vdcraft help ritual or go to our wiki page here: <TODO: Create wiki page and provide link>.",
			"Rituals are used to cast hexes upon dolls (see /vdcraft help items for more information). I can't put much here so I will refer you to the wiki if you want more info on the rituals and how they work ----> <TODO: Create wiki page and provide link>.",
			"The various items in this mod are used to cast hexes upon the dolls which are then used to cast hexes upon living entities. For more information please refer to this command ---> /vdcraft help items <item_name:item_unlocalized_name>",
			"Chalk is used to draw out the patterns necessary for specific hexes. More info here ---> <TODO: Create wiki page and provide link>",
			"Dolls are used to hold the hexes that are used on living entities. You must acquire a shard to craft the dolls to have a certain hex on a specific living entity.",
			"Shards are acquired from certain entities and they contain the UUID of the entity it was acquired from, which allows you to hex that specific entity. For more information, please refer to the wiki here ----> <TODO: Create wiki page and provide link>.",
			"Invalid command. Please use: /vdcraft help <feature>. For a list of features, please refer to our wiki."
	};
}
