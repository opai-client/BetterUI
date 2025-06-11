package tech.Jiaxing.modules;

import tech.Jiaxing.OpaiProExtension;
import today.opai.api.enums.EnumModuleCategory;
import today.opai.api.events.EventRender2D;
import today.opai.api.features.ExtensionModule;
import today.opai.api.interfaces.EventHandler;
import today.opai.api.interfaces.modules.PresetModule;
import today.opai.api.interfaces.modules.Value;
import today.opai.api.interfaces.modules.values.*;
import today.opai.api.interfaces.render.ColorUtil;

import java.awt.*;
import java.util.Collection;
import java.util.List;

import static tech.Jiaxing.OpaiProExtension.openAPI;
import static today.opai.api.Extension.getAPI;

public class BasicUI extends ExtensionModule implements EventHandler {
    OpaiProExtension opaiProExtension;
    public static BasicUI INSTANCE;
//    public final BooleanValue MODULELIST = getAPI().getValueManager().createBoolean("ModuleList",false);
//    public final NumberValue spacing = getAPI().getValueManager().createDouble("spacing", 2, 0, 5, 0.5);
//    public final ModeValue LR=getAPI().getValueManager().createModes("LeftOrRight","right",new String[]{"left", "right"});
    public final BooleanValue INFO = getAPI().getValueManager().createBoolean("Info",false);
    public final BooleanValue Text = getAPI().getValueManager().createBoolean("Text",false);
    public final TextValue ClientValue = getAPI().getValueManager().createInput("ClientName", "SilenceFix");
    public final NumberValue x = getAPI().getValueManager().createDouble("X", 3, 0, 10, 1);
    public final NumberValue y = getAPI().getValueManager().createDouble("Y", 3, 0, 10, 1);
    public final BooleanValue rainbow = getAPI().getValueManager().createBoolean("Rainbow",false);



    public BasicUI() {
        super("BasicUI","Some UI",EnumModuleCategory.VISUAL);
        setEventHandler(this);
        super.addValues(/*MODULELIST,LR,spacing,*/INFO,Text,ClientValue, x, y,rainbow);
        INSTANCE = this;
    }
    @Override
    public void onRender2D(EventRender2D e) {
        int color = ColorUtil.BaseColor(rainbow.getValue().booleanValue());
//        if (MODULELIST.getValue()) {
//            int width = e.getWindowResolution().getWidth();
//            int height = e.getWindowResolution().getHeight();
//            List<PresetModule> enableModules = getAPI().getModuleManager().getEnabledModules();
//            enableModules.sort(((o1, o2) -> openAPI.getFontUtil().getVanillaFont().getWidth(o2.getName()) - openAPI.getFontUtil().getVanillaFont().getWidth(o1.getName())));
//            double y = 0;
//            double x;
//
//            for (PresetModule enableModule : enableModules) {
//                openAPI.getFontUtil().getVanillaFont().drawString(enableModule.getName(), 0, y, color);
//                y += openAPI.getFontUtil().getVanillaFont().getHeight() + spacing.getValue().doubleValue();
//                if (LR.getValue().equals("left")) {
//                    x = 0;
//                }
//                if (LR.getValue().equals("Right")) {
//                    x = width - openAPI.getFontUtil().getVanillaFont().getWidth(enableModule.getName());
//                }
//            }
//        }


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

}
