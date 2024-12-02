package net.pitan76.cubicturret.client.screen;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.pitan76.cubicturret.screen.CubicTurretScreenHandler;
import net.pitan76.mcpitanlib.api.client.gui.screen.CompatInventoryScreen;
import net.pitan76.mcpitanlib.api.client.gui.screen.SimpleInventoryScreen;
import net.pitan76.mcpitanlib.api.client.render.handledscreen.DrawForegroundArgs;
import net.pitan76.mcpitanlib.api.util.CompatIdentifier;
import net.pitan76.mcpitanlib.api.util.IdentifierUtil;
import net.pitan76.mcpitanlib.api.util.client.ClientUtil;

public class CubicTurretScreen extends CompatInventoryScreen<CubicTurretScreenHandler> {

    public CubicTurretScreen(CubicTurretScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        setBackgroundWidth(176);
        setBackgroundHeight(166);
    }

    @Override
    public void initOverride() {
        if (this.textRenderer == null)
            this.textRenderer = ClientUtil.getTextRenderer();

        setTitleX(backgroundWidth / 2 - textRenderer.getWidth(title) / 2);
    }

    @Override
    public CompatIdentifier getCompatTexture() {
        return CompatIdentifier.of("minecraft", "textures/gui/container/dispenser.png");
    }

    @Override
    public void drawForegroundOverride(DrawForegroundArgs args) {
        super.drawForegroundOverride(args);
    }
}
