package today.opai.api.interfaces.render;

import today.opai.api.interfaces.Math.MathHelper;
import today.opai.api.interfaces.Math.MathUtils;
import today.opai.api.interfaces.modules.Value;
import today.opai.api.interfaces.modules.values.ColorValue;

import java.awt.*;

import static tech.Jiaxing.OpaiProExtension.openAPI;

public class ColorUtil {
    public static Color rainbow(int speed, int index) {
        int angle = (int)((System.currentTimeMillis() / (long)speed + (long)index) % 360L);
        float hue = (float)angle / 360.0F;
        return new Color(Color.HSBtoRGB(hue, 0.7F, 1.0F));
    }
    public static int getColor(int red, int green, int blue, int alpha) {
        int color = MathHelper.clamp_int(alpha, 0, 255) << 24;
        color |= MathHelper.clamp_int(red, 0, 255) << 16;
        color |= MathHelper.clamp_int(green, 0, 255) << 8;
        color |= MathHelper.clamp_int(blue, 0, 255);
        return color;
    }
    public static int BaseColor(boolean rainbow,Color color){
        int olor=Color.WHITE.getRGB();
        if (rainbow){
            olor = ColorUtil.rainbow(10, 1).getRGB();
        }else olor = color.getRGB();
        return olor;
    }
    public static Color interpolateColorsBackAndForth(int speed, int index, Color start, Color end, boolean trueColor) {
        int angle = (int)((System.currentTimeMillis() / (long)speed + (long)index) % 360L);
        angle = (angle >= 180 ? 360 - angle : angle) * 2;
        return trueColor ? interpolateColorHue(start, end, (float)angle / 360.0F) : interpolateColorC(start, end, (float)angle / 360.0F);
    }
    public static Color interpolateColorHue(Color color1, Color color2, float amount) {
        amount = Math.min(1.0F, Math.max(0.0F, amount));
        float[] color1HSB = Color.RGBtoHSB(color1.getRed(), color1.getGreen(), color1.getBlue(), (float[])null);
        float[] color2HSB = Color.RGBtoHSB(color2.getRed(), color2.getGreen(), color2.getBlue(), (float[])null);
        Color resultColor = Color.getHSBColor(MathUtils.interpolateFloat(color1HSB[0], color2HSB[0], (double)amount), MathUtils.interpolateFloat(color1HSB[1], color2HSB[1], (double)amount), MathUtils.interpolateFloat(color1HSB[2], color2HSB[2], (double)amount));
        return new Color(resultColor.getRed(), resultColor.getGreen(), resultColor.getBlue(), MathUtils.interpolateInt(color1.getAlpha(), color2.getAlpha(), (double)amount));
    }
    public static Color interpolateColorC(Color color1, Color color2, float amount) {
        amount = Math.min(1.0F, Math.max(0.0F, amount));
        return new Color(MathUtils.interpolateInt(color1.getRed(), color2.getRed(), (double)amount), MathUtils.interpolateInt(color1.getGreen(), color2.getGreen(), (double)amount), MathUtils.interpolateInt(color1.getBlue(), color2.getBlue(), (double)amount), MathUtils.interpolateInt(color1.getAlpha(), color2.getAlpha(), (double)amount));
    }
    public static Color fade(int speed, int index, Color color, float alpha) {
        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        int angle = (int)((System.currentTimeMillis() / (long)speed + (long)index) % 360L);
        angle = (angle > 180 ? 360 - angle : angle) + 180;
        Color colorHSB = new Color(Color.HSBtoRGB(hsb[0], hsb[1], (float)angle / 360.0f));
        return new Color(colorHSB.getRed(), colorHSB.getGreen(), colorHSB.getBlue(), Math.max(0, Math.min(255, (int)(alpha * 255.0f))));
    }

}
