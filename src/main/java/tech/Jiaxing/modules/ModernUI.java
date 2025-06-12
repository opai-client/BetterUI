package tech.Jiaxing.modules;
import today.opai.api.enums.EnumModuleCategory;
import today.opai.api.events.EventRender2D;
import today.opai.api.features.ExtensionModule;
import today.opai.api.interfaces.EventHandler;
import today.opai.api.interfaces.modules.PresetModule;
import today.opai.api.interfaces.modules.values.*;
import today.opai.api.interfaces.render.ColorUtil;
import today.opai.api.interfaces.render.Font;
import today.opai.api.interfaces.render.RenderUtil;
import today.opai.api.interfaces.render.ShaderUtil;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import static tech.Jiaxing.OpaiProExtension.openAPI;
import static today.opai.api.Extension.getAPI;
/**
 @Author renjun
 renjun
 @Date 2025/6/12 16:55
 @Version 1.0
 */
public class ModernUI extends ExtensionModule implements EventHandler {
    public static ModernUI INSTANCE;
    private final Font font = openAPI.getFontUtil().getGoogleSansB18();
    private float width;
    private float height;
    public final BooleanValue showModuleList = getAPI().getValueManager().createBoolean("ModernArrayList", true);
    public final BooleanValue showWatermark = getAPI().getValueManager().createBoolean("ModernWaterMark", true);
    public final TextValue clientName = getAPI().getValueManager().createInput("ClientName", "SilenceFix");
    public static ModeValue colorMode = openAPI.getValueManager().createModes("Color Mode", "Fade", new String[]{"Fade", "Static", "Double"});
    public final BooleanValue WaterMarkColor = getAPI().getValueManager().createBoolean("WaterMarkColor", false);
    public final ColorValue colorValue = openAPI.getValueManager().createColor("Color", Color.WHITE);
    public final ColorValue colorValue2 = openAPI.getValueManager().createColor("Color2", Color.BLACK);
    public final NumberValue tick = openAPI.getValueManager().createDouble("Tick", 0, 0, 5, 1);

    public ModernUI () {
        super ("ModernUI", "现代 UI 界面", EnumModuleCategory.VISUAL);
        setEventHandler (this);
        addValues (showModuleList, showWatermark, clientName, colorMode,WaterMarkColor, colorValue, colorValue2, tick);
        INSTANCE = this;
    }
    @Override
    public void onRender2D(EventRender2D e) {
        width = e.getWindowResolution().getWidth();
        height = e.getWindowResolution().getHeight();
        if (showModuleList.getValue()) {
            drawModuleList();
        }
        if (showWatermark.getValue()) {
            drawWaterMark();
        }
    }
    private void drawWaterMark() {
        float clientNameWidth = font.getWidth(clientName.getValue()) + 16 + 1;
        float clientNameHeight = font.getHeight() + 8;
        int fps = openAPI.getFrameRate();
        float fpsWidth = getAPI().getFontUtil().getGoogleSansB16().getWidth(fps + " FPS") + 16;
        float totalWidth = clientNameWidth + fpsWidth;
        Color watermarkColor = Color.WHITE;
        if (WaterMarkColor.getValue()){
            watermarkColor =getTextColor();
        }
        RenderUtil renderUtil = getAPI().getRenderUtil();
        ShaderUtil shaderUtil = getAPI().getShaderUtil();

        shaderUtil.drawWithBlur (() -> renderWatermarkBackground (totalWidth, clientNameHeight));
        shaderUtil.drawWithBloom (() -> renderWatermarkBackground (totalWidth, clientNameHeight));
        renderWatermarkBackground (totalWidth, clientNameHeight);
        font.drawStringWithShadow (clientName.getValue (), 13, 9, watermarkColor.getRGB());
        getAPI ().getFontUtil ().getGoogleSansB16 ().drawString (
                fps + "FPS",
                clientNameWidth + 8,
                5 + (clientNameHeight - getAPI ().getFontUtil ().getGoogleSans16 ().getHeight ()) / 2,
                watermarkColor.getRGB ()
        );
    }
    private void renderWatermarkBackground(float width, float height) {
        int fps = openAPI.getFrameRate();
        float fpsWidth = getAPI().getFontUtil().getGoogleSansB16().getWidth(fps + " FPS") + 16;
        RenderUtil renderUtil = getAPI().getRenderUtil();
        renderUtil.drawRoundRect(5, 5, width, height, 8, new Color(0, 0, 0, 50));
        renderUtil.drawRoundRect(5, 5, width - fpsWidth, height, 8, new Color(0, 0, 0, 100));
    }
    private void drawModuleList() {
        List<PresetModule> modules = getEnabledVisibleModules();
        if (modules.isEmpty()) return;
        ShaderUtil shaderUtil = getAPI().getShaderUtil();
        shaderUtil.drawWithBlur(() -> renderModules(modules, font, true));
        shaderUtil.drawWithBloom(() -> renderModules(modules, font, true));
        renderModules(modules, font, true);
    }
    private List<PresetModule> getEnabledVisibleModules() {
        List<PresetModule> enabledModules = new ArrayList<>();
        for (PresetModule module : openAPI.getModuleManager().getModules()) {
            if (module.isEnabled() && !module.isHidden()) {
                enabledModules.add(module);
            }
        }
        enabledModules.sort(Comparator.comparingInt(m -> -font.getWidth(m.getName())));
        return enabledModules;
    }
    private void renderModules(List<PresetModule> modules, Font font, boolean drawBackground) {
        float yOffset = 5;
        for (PresetModule module : modules) {
            int nameWidth = font.getWidth(module.getName());
            float xPos = width - nameWidth - 5;
            if (drawBackground) {
                openAPI.getRenderUtil().drawRoundRect(
                        xPos - 2, yOffset,
                        nameWidth + 4, font.getHeight(),
                        2, new Color(0, 0, 0, 50)
                );
            }
            font.drawString(module.getName(), xPos, yOffset, getTextColor().getRGB());
            yOffset += font.getHeight() + 5;
        }
    }
    private Color getTextColor() {
        int currentTick = tick.getValue().intValue();
        String mode = colorMode.getValue();
        if (mode.equals("Fade")) {
            return ColorUtil.fade(5, currentTick * 20, colorValue.getValue(), 1);
        } else if (mode.equals("Static")) {
            return colorValue.getValue();
        } else if (mode.equals("Double")) {
            int adjustedTick = currentTick * 200;
            return new Color(colorSwitch(colorValue.getValue(), colorValue2.getValue(), 2000, -adjustedTick / 40, 75, 2));
        }
        return Color.WHITE;
    }
    public static int colorSwitch(Color firstColor, Color secondColor, float time, int index, long timePerIndex, double speed) {
        return colorSwitch2(firstColor, secondColor, time, index, timePerIndex, speed, 255.0);
    }
    public static int colorSwitch2(Color firstColor, Color secondColor, float time, int index, long timePerIndex, double speed, double alpha) {
        long now = (long)(speed * System.currentTimeMillis() + ((long)index * timePerIndex));
        float redDiff = (firstColor.getRed() - secondColor.getRed()) / time;
        float greenDiff = (firstColor.getGreen() - secondColor.getGreen()) / time;
        float blueDiff = (firstColor.getBlue() - secondColor.getBlue()) / time;
        int red = Math.round(secondColor.getRed() + redDiff * (now % (long)time));
        int green = Math.round(secondColor.getGreen() + greenDiff * (now % (long)time));
        int blue = Math.round(secondColor.getBlue() + blueDiff * (now % (long)time));
        float redInverseDiff = (secondColor.getRed() - firstColor.getRed()) / time;
        float greenInverseDiff = (secondColor.getGreen() - firstColor.getGreen()) / time;
        float blueInverseDiff = (secondColor.getBlue() - firstColor.getBlue()) / time;
        int inverseRed = Math.round(firstColor.getRed() + redInverseDiff * (now % (long)time));
        int inverseGreen = Math.round(firstColor.getGreen() + greenInverseDiff * (now % (long)time));
        int inverseBlue = Math.round(firstColor.getBlue() + blueInverseDiff * (now % (long)time));
        return (now % ((long)time * 2L) < (long)time)
                ? new Color(inverseRed, inverseGreen, inverseBlue, (int)alpha).getRGB()
                : new Color(red, green, blue, (int)alpha).getRGB();
    }
}