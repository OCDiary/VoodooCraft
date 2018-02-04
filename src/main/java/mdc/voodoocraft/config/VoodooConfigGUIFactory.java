package mdc.voodoocraft.config;

import java.util.Set;

import mdc.voodoocraft.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;

public class VoodooConfigGUIFactory implements IModGuiFactory {

	@Deprecated
    @Override
    public void initialize(Minecraft minecraftInstance) {
    }

    @Override
    public Class<? extends GuiScreen> mainConfigGuiClass() {
        return CoreConfigGUI.class;
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return null;
    }

    @Deprecated
    @Override
    public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
        return null;
    }

    public static class CoreConfigGUI extends GuiConfig {

        public CoreConfigGUI(GuiScreen parent) {
            super(parent, VoodooConfig.getEntries(),
                    Reference.MODID, false, false, I18n.format("config." + Reference.MODID + ".name"));
        }
    }
}
