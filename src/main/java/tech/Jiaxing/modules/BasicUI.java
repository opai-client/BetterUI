package tech.Jiaxing.modules;

import tech.Jiaxing.OpaiProExtension;
import today.opai.api.enums.EnumModuleCategory;
import today.opai.api.events.EventRender2D;
import today.opai.api.features.ExtensionModule;
import today.opai.api.interfaces.EventHandler;
import today.opai.api.interfaces.game.item.ItemStack;
import today.opai.api.interfaces.modules.PresetModule;
import today.opai.api.interfaces.modules.values.*;
import today.opai.api.interfaces.render.ColorUtil;

import java.awt.*;

import static tech.Jiaxing.OpaiProExtension.openAPI;
import static today.opai.api.Extension.getAPI;

public class BasicUI extends ExtensionModule implements EventHandler {
    public static BasicUI INSTANCE;
    private final BooleanValue showBlockCount = getAPI().getValueManager().createBoolean("Show Block Count", false);
    private final BooleanValue showInfo = getAPI().getValueManager().createBoolean("Show Info", false);
    private final BooleanValue showText = getAPI().getValueManager().createBoolean("Show Text", false);
    private final TextValue clientName = getAPI().getValueManager().createInput("Client Name", "SilenceFix");
    private final NumberValue positionX = getAPI().getValueManager().createDouble("X", 3, 0, 10, 1);
    private final NumberValue positionY = getAPI().getValueManager().createDouble("Y", 3, 0, 10, 1);
    private final BooleanValue useRainbow = getAPI().getValueManager().createBoolean("Rainbow", false);
    public final ColorValue colorValue = openAPI.getValueManager().createColor("Color", Color.WHITE);
    private final PresetModule scaffoldModule = getAPI().getModuleManager().getModule("Scaffold");


    public BasicUI() {
        super("SimpleUI", "显示各种UI信息", EnumModuleCategory.VISUAL);
        setEventHandler(this);
        super.addValues(showBlockCount, showInfo, showText, clientName, positionX, positionY, useRainbow,colorValue);
        INSTANCE = this;
    }

    @Override
    public void onRender2D(EventRender2D event) {
        int color = ColorUtil.BaseColor(useRainbow.getValue(),colorValue.getValue());
        if (showBlockCount.getValue() && scaffoldModule.isEnabled()) {
            drawBlockCount(event);
        }

        if (showInfo.getValue() && openAPI.getLocalPlayer() != null) {
            drawPlayerInfo(event);
        }

        if (showText.getValue() && openAPI.getLocalPlayer() != null) {
            drawClientText(event);
        }
    }

    private void drawBlockCount(EventRender2D event) {
        int blocks = countBlocks();
        getAPI().getFontUtil().getVanillaFont().drawString(
                blocks + " Blocks",
                event.getWindowResolution().getWidth() / 2 + 1,
                event.getWindowResolution().getHeight() / 2 + 1,
                Color.WHITE.getRGB()
        );
    }

    private void drawPlayerInfo(EventRender2D event) {
        int color = ColorUtil.BaseColor(useRainbow.getValue(),colorValue.getValue());
        String positionInfo = " X " + (int) openAPI.getLocalPlayer().getPosition().getX() +
                " Y " + (int) openAPI.getLocalPlayer().getPosition().getY() +
                " Z " + (int) openAPI.getLocalPlayer().getPosition().getZ();

        String versionInfo = "[release] B" + openAPI.getClientVersion() +
                " [" + openAPI.getClientUsername() + "] ";

        getAPI().getFontUtil().getVanillaFont().drawStringWithShadow(
                positionInfo,
                0,
                event.getWindowResolution().getHeight() - getAPI().getFontUtil().getVanillaFont().getHeight(),
                color
        );

        getAPI().getFontUtil().getVanillaFont().drawStringWithShadow(
                versionInfo,
                event.getWindowResolution().getWidth() - getAPI().getFontUtil().getVanillaFont().getWidth(versionInfo),
                event.getWindowResolution().getHeight() - getAPI().getFontUtil().getVanillaFont().getHeight(),
                color
        );
    }

    private void drawClientText(EventRender2D event) {
        int color = ColorUtil.BaseColor(useRainbow.getValue(),colorValue.getValue());
        String sprintStatus = openAPI.getLocalPlayer().isSprinting() ? "Sprinting" : "UnSprinting";
        int fps = openAPI.getFrameRate();

        String clientText = clientName.getValue() + " [B" + openAPI.getClientVersion() +
                "] [" + fps + "fps] [" + sprintStatus + "]";

        getAPI().getFontUtil().getVanillaFont().drawStringWithShadow(
                clientText,
                positionX.getValue(),
                positionY.getValue(),
                color
        );
    }

    private int countBlocks() {
        int count = 0;
        for (ItemStack itemStack : openAPI.getLocalPlayer().getInventory().getMainInventory()) {
            if (itemStack == null) continue;
            if (itemStack.getName().contains("tile.") ||
                    itemStack.getName().contains("block.") ||
                    itemStack.getName().contains("minecraft:")) {
                count += itemStack.getStackSize();
            }
        }
        return count;
    }
}
