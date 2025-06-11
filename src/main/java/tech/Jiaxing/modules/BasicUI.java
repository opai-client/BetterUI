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
    OpaiProExtension opaiProExtension;
    public static BasicUI INSTANCE;
    public final BooleanValue simpleBlockCount = getAPI().getValueManager().createBoolean("simpleBlockCount",false);
    public final BooleanValue Notification = getAPI().getValueManager().createBoolean("Notification",false);
    public final BooleanValue INFO = getAPI().getValueManager().createBoolean("Info",false);
    public final BooleanValue Text = getAPI().getValueManager().createBoolean("Text",false);
    public final TextValue ClientValue = getAPI().getValueManager().createInput("ClientName", "SilenceFix");
    public final NumberValue x = getAPI().getValueManager().createDouble("X", 3, 0, 10, 1);
    public final NumberValue y = getAPI().getValueManager().createDouble("Y", 3, 0, 10, 1);
    public final BooleanValue rainbow = getAPI().getValueManager().createBoolean("Rainbow",false);
    PresetModule sca = getAPI().getModuleManager().getModule("Scaffold");

    public BasicUI() {
        super("BasicUI","Some UI",EnumModuleCategory.VISUAL);
        setEventHandler(this);
        super.addValues(/*MODULELIST,LR,spacing,*/simpleBlockCount,INFO,Text,ClientValue, x, y,rainbow);
        INSTANCE = this;
    }
    @Override
    public void onRender2D(EventRender2D e) {
        int color = ColorUtil.BaseColor(rainbow.getValue().booleanValue());
//
        if (simpleBlockCount.getValue()&&sca.isEnabled()){
            int blocks = countBlocks();;
           getAPI().getFontUtil().getVanillaFont().drawString(String.valueOf(blocks)+" Blocks", (double) e.getWindowResolution().getWidth() /2+1, (double) e.getWindowResolution().getHeight() /2+1,Color.WHITE.getRGB());
        }

        if (INFO.getValue()) {


            String rightText = "[release] B" + openAPI.getClientVersion() + " [" + openAPI.getClientUsername() + "] ";
            if (openAPI.getLocalPlayer() != null) {
                String text = " X " + (int) openAPI.getLocalPlayer().getPosition().getX() + " Y " + (int) openAPI.getLocalPlayer().getPosition().getY() + " Z " + (int) openAPI.getLocalPlayer().getPosition().getZ();
                openAPI.getFontUtil().getVanillaFont().drawStringWithShadow(text, 0, (e.getWindowResolution().getHeight() - openAPI.getFontUtil().getVanillaFont().getHeight()), color);
                openAPI.getFontUtil().getVanillaFont().drawStringWithShadow(rightText, e.getWindowResolution().getWidth() - openAPI.getFontUtil().getVanillaFont().getWidth(rightText), (e.getWindowResolution().getHeight() - openAPI.getFontUtil().getVanillaFont().getHeight()), color);

            }

        }
        if (Text.getValue()) {

            String sprint = "Sprinting";
            if (openAPI.getLocalPlayer().isSprinting()) {
                sprint = "Sprinting";
            } else {
                sprint = "UnSprinting";
            }
            int fps = openAPI.getFrameRate();
            String text = ClientValue.getValue() + " [B"
                    + openAPI.getClientVersion()
                    + "] [" + fps
                    + "fps] ["
                    + sprint
                    + "]";
            openAPI.getFontUtil().getVanillaFont().drawStringWithShadow(text, x.getValue(), y.getValue(), color);


        }
    }
    private int countBlocks(){
        int count = 0;
        for (ItemStack itemStack : openAPI.getLocalPlayer().getInventory().getMainInventory()) {
            if(itemStack == null) continue;
            if (itemStack.getName().contains("tile.") ||
                    itemStack.getName().contains("block.") ||
                    itemStack.getName().contains("minecraft:")) {
                count += itemStack.getStackSize();
            }
        }
        return count;
    }
}
