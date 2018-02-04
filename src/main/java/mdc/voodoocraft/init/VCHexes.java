package mdc.voodoocraft.init;

import mdc.voodoocraft.hexes.*;

import java.util.HashMap;
import java.util.Map;

public class VCHexes
{
	public static Map<String, HexEntry> HEXES = new HashMap<>();

	public static void init()
	{
		//Add the hexes
		addHex(new HexRegeneration());
		addHex(new HexEntry("feather"));
		addHex(new HexEntry("health_bind"));
		addHex(new HexGreenFingers());
		addHex(new HexDeath());
		addHex(new HexEntry("fireaura"));
		addHex(new HexEntry("heat_protection"));
		addHex(new HexSpawnPoint());
		addHex(new HexEntry("suffocation"));
		addHex(new HexEntry("teleport"));
		addHex(new HexEntry("spirit_walk"));
		addHex(new HexFertility());
		addHex(new HexFreeze());
		addHex(new HexEntry("fear_the_darkness"));
		addHex(new HexEntry("waterbreathing"));
		addHex(new HexEntry("protection"));
		addHex(new HexInsomnia());
		addHex(new HexEntry("safety"));
		addHex(new HexEntry("danger"));
		addHex(new HexEntry("fire_protection"));
		addHex(new HexEntry("water_walking"));
		addHex(new HexEntry("wither"));
		addHex(new HexZombify());
		addHex(new HexEntry("time_sprint"));
		addHex(new HexEntry("life_drain"));
		addHex(new HexEntry("from_ashes"));
		addHex(new HexEntry("wither_protection"));
		addHex(new HexEntry("chest_deposit"));
		addHex(new HexEntry("lone_wolf"));
		addHex(new HexIntoxicate());
		addHex(new HexSpiritTaint());
		addHex(new HexRocket());
		addHex(new HexZoom());
	}
	
	private static void addHex(HexEntry hex)
	{
		HEXES.put(hex.getRawName(), hex);
	}

	public static HexEntry getHex(String name)
	{
		return HEXES.get(name);
	}
}