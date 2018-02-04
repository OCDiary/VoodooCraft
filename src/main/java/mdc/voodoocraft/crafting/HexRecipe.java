package mdc.voodoocraft.crafting;

import java.util.Arrays;

import mdc.voodoocraft.hexes.HexEntry;

public class HexRecipe
{
    private final HexEntry hexOutput;
    public final String[] totemGlyphLayoutInput;

    public HexRecipe(HexEntry hexOutput, String... totemGlyphLayout)
    {
        this.hexOutput = hexOutput;
        this.totemGlyphLayoutInput = new String[totemGlyphLayout.length];

        //Check layout input and sort them alphabetically
        for(int i = 0; i < totemGlyphLayout.length; i++)
        {
            String totem = totemGlyphLayout[i];
            if(totem.length() != 4)
                throw new RuntimeException("Each totem glyph layout must be 4 characters long!");
            totemGlyphLayoutInput[i] = sortCharsInString(totem);
        }
    }

    /**
     * Sorts the characters in the given string, and returns the result in a new string
     */
    private String sortCharsInString(String string)
    {
        char[] stringChars = string.toCharArray();
        Arrays.sort(stringChars);
        return String.valueOf(stringChars);
    }

    /**
     * Gets the required amount of totem blocks for this recipe
     */
    public int getTotemHeight()
    {
        return totemGlyphLayoutInput.length;
    }

    /**
     * Gets the totem glyph layout for this recipe
     */
    public String[] getTotemGlyphLayout()
    {
        return totemGlyphLayoutInput;
    }

    /**
     * Gets the Hex output of this recipe
     */
    public HexEntry getHexOutput()
    {
        return hexOutput;
    }

    /**
     * Checks if the totems matches this recipe's
     */
    public boolean matches(String[] totemGlyphLayout)
    {
        for(int i = 0; i < totemGlyphLayout.length; i++)
        {
            String sortedTotem = sortCharsInString(totemGlyphLayout[i]);
            if(sortedTotem.compareToIgnoreCase(totemGlyphLayoutInput[i]) != 0)
                return false;
        }
        return true;
    }
}
