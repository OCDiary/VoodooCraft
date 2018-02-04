package mdc.voodoocraft.crafting;

import java.util.ArrayList;
import java.util.List;

import mdc.voodoocraft.hexes.HexEntry;

public class HexCraftingManager
{
    private static List<HexRecipe> RECIPES = new ArrayList<>();

    public static List<HexRecipe> getRecipeList()
    {
        return RECIPES;
    }

    public static void addRecipe(HexRecipe recipe)
    {
        if(findMatchingRecipe(recipe.getTotemGlyphLayout()) != null)
            throw new RuntimeException("Tried registering a recipe with the same glyph layout as an existing one!");
        RECIPES.add(recipe);
    }

    public static void addRecipe(HexEntry hexOutput, String... totemGlyphLayout)
    {
        addRecipe(new HexRecipe(hexOutput, totemGlyphLayout));
    }

    /**
     * Tries to find a recipe that has the same totem glyph layout and returns it.
     * Returns null if no recipe found.
     */
    public static HexRecipe findMatchingRecipe(String... totemGlyphLayout)
    {
        for(HexRecipe recipe : RECIPES)
            if(recipe.matches(totemGlyphLayout))
                return recipe;
        return null;
    }
}
