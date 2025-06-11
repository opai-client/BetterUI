package tech.Jiaxing.widgets;

import today.opai.api.features.ExtensionWidget;
import tech.Jiaxing.modules.ModernScoreboard;

import java.awt.*;

import static tech.Jiaxing.OpaiProExtension.openAPI;

public class MyScoreboard extends ExtensionWidget {
    public MyScoreboard() {
        super("MyScoreboard");
    }

    @Override
    public void render() {
        int height = 0;

        int renderWidth;

        if(openAPI.getWorld().getScoreboardLines().isEmpty() || openAPI.getWorld().getScoreboardTitle() == null)
            return;

        openAPI.getRenderUtil().drawRoundRect(getX(), getY(), getWidth(), getHeight(), 5, new Color(0, 0, 0, 150));

        height += 10;

        openAPI.getFontUtil().getVanillaFont().drawCenteredString("❁花雨庭❁", getX() + (this.getWidth() / 2) - 1, getY() + 4, -1);

        renderWidth = openAPI.getFontUtil().getVanillaFont().getWidth("❁花雨庭❁");

        for (String s1 : openAPI.getWorld().getScoreboardLines()) {
            String displayText = s1; // 初始化显示文本
//            if (s1.contains("www.hypixel.net")) {
//                displayText = s1.replace("www.hypixel.net", "布吉岛");
//            }
            if (s1.contains("HYPIXEL")) {
                displayText = s1.replace("HYPIXEL", "SB"); // 替换特定字符串
            }
            renderWidth = Math.max(openAPI.getFontUtil().getVanillaFont().getWidth(s1), renderWidth);
            openAPI.getFontUtil().getVanillaFont().drawString(
                    s1,
                    getX() + 3,
                    getY() + (height += 10),
                    0xffffffff
            );
        }

        setWidth(renderWidth + 8);
        setHeight(height + 12);
    }

    @Override
    public boolean renderPredicate() {
        return ModernScoreboard.INSTANCE.isEnabled();
    }
}
