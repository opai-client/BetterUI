package tech.Jiaxing.widgets;
import today.opai.api.features.ExtensionWidget;
import tech.Jiaxing.modules.ScoreboardPlus;
import today.opai.api.interfaces.render.Font;
import today.opai.api.interfaces.render.ShaderUtil;
import java.awt.*;
import static tech.Jiaxing.OpaiProExtension.openAPI;
import static today.opai.api.Extension.getAPI;
public class MyScoreboard extends ExtensionWidget {
    public MyScoreboard() {
        super("MyScoreboard");
    }
    @Override
    public void render() {
        if (openAPI.getWorld().getScoreboardLines().isEmpty() || openAPI.getWorld().getScoreboardTitle() == null) {
            return;
        }
        String mode = ScoreboardPlus.Mode.getValue();
        float startY = getY() + 10;
        float currentWidth = 0;
        float lineHeight = mode.equals("Classical") ? 10 : 12;
        Font font = mode.equals("Classical")
                ? openAPI.getFontUtil().getVanillaFont()
                : openAPI.getFontUtil().getProduct18();
// 绘制背景
        drawBackground (mode, getX (), getY (), getWidth (), getHeight ());
// 绘制标题
        String title = "❁花雨庭❁";
        font.drawCenteredString (title, getX () + getWidth () / 2 - 1, getY () + 4, -1);
        currentWidth = font.getWidth (title);
// 绘制计分板行
        for (String line : openAPI.getWorld ().getScoreboardLines ()) {
            String displayText = line.contains ("www.hypixel.net")
                    ? line.replace("www.hypixel.net"," 布吉岛 ")
                    : line;
            currentWidth = Math.max(font.getWidth(displayText), currentWidth);
            font.drawString(displayText, getX() + 3, startY += lineHeight, 0xffffffff);
        }
// 设置组件尺寸
        setWidth (currentWidth + 8);
        setHeight (startY - getY () + 12);
    }
    private void drawBackground(String mode, float x, float y, float width, float height) {
        if (mode.equals("Classical")) {
            openAPI.getRenderUtil().drawRoundRect(x, y, width, height, 0, new Color(0, 0, 0, 100));
        } else {
            openAPI.getRenderUtil().drawRoundRect(x, y, width, height, 3, new Color(0, 0, 0, 100));
            ShaderUtil shaderUtil = getAPI().getShaderUtil();
            shaderUtil.drawWithBloom(() ->
                    shaderUtil.drawWithBlur(() ->
                            openAPI.getRenderUtil().drawRoundRect(x, y, width, height, 3, new Color(0, 0, 0, 100))
                    )
            );
        }
    }
    @Override
    public boolean renderPredicate() {
        return ScoreboardPlus.INSTANCE.isEnabled();
    }
}