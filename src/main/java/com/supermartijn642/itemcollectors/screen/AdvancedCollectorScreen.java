package com.supermartijn642.itemcollectors.screen;

import com.supermartijn642.itemcollectors.CollectorTile;
import com.supermartijn642.itemcollectors.ItemCollectors;
import com.supermartijn642.itemcollectors.packet.*;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TranslationTextComponent;

/**
 * Created 7/15/2020 by SuperMartijn642
 */
public class AdvancedCollectorScreen extends CollectorScreen<AdvancedCollectorContainer> {

    private ArrowButton upXButton, downXButton;
    private ArrowButton upYButton, downYButton;
    private ArrowButton upZButton, downZButton;
    private WhitelistButton whitelistButton;

    public AdvancedCollectorScreen(AdvancedCollectorContainer container){
        super(container, ItemCollectors.advanced_collector.getTranslationKey());
    }

    @Override
    protected void addButtons(CollectorTile tile){
        this.upXButton = this.addButton(new ArrowButton(this.guiLeft + 40, this.guiTop + 37, false, () -> ItemCollectors.CHANNEL.sendToServer(new PacketIncreaseXRange(this.container.pos))));
        this.downXButton = this.addButton(new ArrowButton(this.guiLeft + 40, this.guiTop + 63, true, () -> ItemCollectors.CHANNEL.sendToServer(new PacketDecreaseXRange(this.container.pos))));
        this.upYButton = this.addButton(new ArrowButton(this.guiLeft + 93, this.guiTop + 37, false, () -> ItemCollectors.CHANNEL.sendToServer(new PacketIncreaseYRange(this.container.pos))));
        this.downYButton = this.addButton(new ArrowButton(this.guiLeft + 93, this.guiTop + 63, true, () -> ItemCollectors.CHANNEL.sendToServer(new PacketDecreaseYRange(this.container.pos))));
        this.upZButton = this.addButton(new ArrowButton(this.guiLeft + 146, this.guiTop + 37, false, () -> ItemCollectors.CHANNEL.sendToServer(new PacketIncreaseZRange(this.container.pos))));
        this.downZButton = this.addButton(new ArrowButton(this.guiLeft + 146, this.guiTop + 63, true, () -> ItemCollectors.CHANNEL.sendToServer(new PacketDecreaseZRange(this.container.pos))));
        this.whitelistButton = this.addButton(new WhitelistButton(this.guiLeft + 175, this.guiTop + 88, () -> ItemCollectors.CHANNEL.sendToServer(new PacketToggleWhitelist(this.container.pos))));
        this.whitelistButton.update(tile.filterWhitelist);
    }

    @Override
    protected void drawToolTips(CollectorTile tile, int mouseX, int mouseY){
        if(this.upXButton.isHovered() || this.upYButton.isHovered() || this.upZButton.isHovered())
            this.renderToolTip(true, "gui.itemcollectors.basic_collector.range.increase", mouseX, mouseY);
        if(this.downXButton.isHovered() || this.downYButton.isHovered() || this.downZButton.isHovered())
            this.renderToolTip(true, "gui.itemcollectors.basic_collector.range.decrease", mouseX, mouseY);
        if(this.whitelistButton.isHovered())
            this.renderToolTip(true, "gui.itemcollectors.advanced_collector.whitelist.on", mouseX, mouseY);
    }

    @Override
    protected void tick(CollectorTile tile){
        this.whitelistButton.update(tile.filterWhitelist);
    }

    @Override
    protected String getBackground(){
        return "advanced_collector_screen.png";
    }

    @Override
    protected void drawText(CollectorTile tile){
        this.drawCenteredString(this.title, this.xSize / 2f, 6);
        this.drawString(this.playerInventory.getDisplayName(), 21, 112);

        String range = I18n.format("gui.itemcollectors.basic_collector.range")
            .replace("$numberx$","" + (tile.rangeX * 2 + 1))
            .replace("$numbery$","" + (tile.rangeY * 2 + 1))
            .replace("$numberz$","" + (tile.rangeZ * 2 + 1));
        this.drawString(range,8, 26);
        this.drawCenteredString("x:", 35, 51);
        this.drawCenteredString("" + tile.rangeX, 49, 52);
        this.drawCenteredString("y:", 88, 51);
        this.drawCenteredString("" + tile.rangeY, 102, 52);
        this.drawCenteredString("z:", 141, 51);
        this.drawCenteredString("" + tile.rangeZ, 155, 52);
        this.drawString(new TranslationTextComponent("gui.itemcollectors.advanced_collector.filter"), 8, 78);
    }
}
