package today.opai.api.interfaces.render;

import java.awt.*;

public class GradientTextRenderer {

    /**
     * 使用两种颜色渐变渲染文本
     *
     * @param font 字体接口实例
     * @param text 要渲染的文本
     * @param x 文本起始X坐标
     * @param y 文本Y坐标
     * @param startColor 起始颜色
     * @param endColor 结束颜色
     */
    public static void drawGradientString(Font font, String text, double x, double y,
                                          Color startColor, Color endColor) {
        // 如果文本为空则直接返回
        if (text == null || text.isEmpty()) {
            return;
        }

        // 获取文本总宽度
        int totalWidth = font.getWidth(text);

        // 分解起始颜色和结束颜色为RGB分量
        int startR = startColor.getRed();
        int startG = startColor.getGreen();
        int startB = startColor.getBlue();

        int endR = endColor.getRed();
        int endG = endColor.getGreen();
        int endB = endColor.getBlue();

        // 遍历文本中的每个字符
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            String charStr = String.valueOf(c);

            // 计算当前字符的宽度
            int charWidth = font.getWidth(charStr);

            // 计算当前字符在文本中的相对位置（0.0 到 1.0）
            double position = i / (double) text.length();

            // 计算当前字符的颜色（线性插值）
            int r = (int) (startR + position * (endR - startR));
            int g = (int) (startG + position * (endG - startG));
            int b = (int) (startB + position * (endB - startB));

            // 创建当前字符的颜色
            Color charColor = new Color(r, g, b);

            // 渲染当前字符
            font.drawString(charStr, x, y, charColor.getRGB());

            // 更新X坐标，准备渲染下一个字符
            x += charWidth;
        }
    }

    /**
     * 使用两种颜色渐变渲染带阴影的文本
     *
     * @param font 字体接口实例
     * @param text 要渲染的文本
     * @param x 文本起始X坐标
     * @param y 文本Y坐标
     * @param startColor 起始颜色
     * @param endColor 结束颜色
     */
    public static void drawGradientStringWithShadow(Font font, String text, double x, double y,
                                                    Color startColor, Color endColor) {
        // 先绘制阴影（稍微偏移并使用暗色）
        Color shadowColor = new Color(0, 0, 0, 150); // 半透明黑色阴影
        drawGradientString(font, text, x + 0.5, y + 0.5, shadowColor, shadowColor);

        // 再绘制渐变文本
        drawGradientString(font, text, x, y, startColor, endColor);
    }


}
