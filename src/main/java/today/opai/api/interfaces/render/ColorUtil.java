package today.opai.api.interfaces.render;

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
    public static int BaseColor(boolean rainbow){
        int color=Color.WHITE.getRGB();
        for (Value<?> value : openAPI.getModuleManager().getModule("HUD").getValues()) {

            if (rainbow){
                color = ColorUtil.rainbow(10, 1).getRGB();
            }else {
                if(value.getName().equals("Base Color")){
                    ColorValue cv = (ColorValue) value;
                    color= cv.getValue().getRGB();
                }
            }
        }
        return color;
    }
}
