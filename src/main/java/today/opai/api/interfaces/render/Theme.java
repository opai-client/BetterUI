package today.opai.api.interfaces.render;

import lombok.Getter;
import tech.Jiaxing.modules.ModernUI;
import today.opai.api.interfaces.modules.values.ModeValue;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import static tech.Jiaxing.OpaiProExtension.openAPI;
/**
 * @author FuMeng
 * @since 2024/7/28 下午6:35
 */
@Getter
public enum Theme {
    SPEARMINT("Spearmint", new Color(97, 194, 162), new Color(65, 130, 108)),
    JADE_GREEN("Jade Green", new Color(0, 168, 107), new Color(0, 105, 66)),
    GREEN_SPIRIT("Green Spirit", new Color(0, 135, 62), new Color(159, 226, 191), true),
    ROSY_PINK("Rosy Pink", new Color(255, 102, 204), new Color(191, 77, 153)),
    MAGENTA("Magenta", new Color(213, 63, 119), new Color(157, 68, 110)),
    HOT_PINK("Hot Pink", new Color(231, 84, 128), new Color(172, 79, 198), true),
    LAVENDER("Lavender", new Color(219, 166, 247), new Color(152, 115, 172)),
    AMETHYST("Amethyst", new Color(144, 99, 205), new Color(98, 67, 140)),
    PURPLE_FIRE("Purple Fire", new Color(0, 61, 175), new Color(118, 35, 255), true),
    SUNSET_PINK("Sunset Pink", new Color(255, 145, 20), new Color(245, 105, 231), true),
    BLAZE_ORANGE("Blaze Orange", new Color(255, 169, 77), new Color(255, 130, 0)),
    PINK_BLOOD("White Color", new Color(255,255,255), new Color(255, 255, 255), true),
    PASTEL("Pastel", new Color(255, 109, 106), new Color(191, 82, 80)),
    NEON_RED("Neon Red", new Color(210, 39, 48), new Color(184, 25, 42)),
    RED_COFFEE("Red Coffee", Color.BLACK, new Color(225, 34, 59)),
    DEEP_OCEAN("Deep Ocean", new Color(60, 82, 145), new Color(0, 20, 64), true),
    CHAMBRAY_BLUE("Chambray Blue", new Color(33, 46, 182), new Color(60, 82, 145)),
    MINT_BLUE("Mint Blue", new Color(66, 158, 157), new Color(40, 94, 93)),
    PACIFIC_BLUE("Pacific Blue", new Color(5, 169, 199), new Color(4, 115, 135)),
    TROPICAL_ICE("Tropical Ice", new Color(102, 255, 209), new Color(6, 149, 255), true),
    User("User", ModernUI.INSTANCE.colorValue.getValue(), ModernUI.INSTANCE.colorValue2.getValue(), true);


    private static final Map<String, Theme> themeMap = new HashMap<>();

    private final String name;
    @Getter
    private final Pair<Color, Color> colors;
    private final boolean gradient;

    Theme(String name, Color color, Color colorAlt) {
        this(name, color, colorAlt, false);
    }

    Theme(String name, Color color, Color colorAlt, boolean gradient) {
        this.name = name;
        colors = Pair.of(color, colorAlt);
        this.gradient = gradient;
    }

    public static void init() {
        Arrays.stream(values()).forEach(theme -> themeMap.put(theme.getName(), theme));
    }

    public static Pair<Color, Color> getThemeColors(String name) {
        return get(name).getColors();
    }

    public static ModeValue getModeSetting(String name, String defaultValue) {
        return openAPI.getValueManager().createModes(name, defaultValue, Arrays.stream(Theme.values()).map(Theme::getName).toArray(String[]::new));
    }

    public static Theme get(String name) {
        return themeMap.get(name);
    }

}
