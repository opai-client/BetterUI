package tech.Jiaxing.modules;

import org.lwjgl.opengl.GL11;
import tech.Jiaxing.OpaiProExtension;
import today.opai.api.enums.EnumModuleCategory;
import today.opai.api.events.EventRender2D;
import today.opai.api.events.EventRender3D;
import today.opai.api.features.ExtensionModule;
import today.opai.api.interfaces.EventHandler;
import today.opai.api.interfaces.game.entity.Player;
import today.opai.api.interfaces.modules.values.BooleanValue;
import today.opai.api.interfaces.render.RenderUtil;

import java.awt.*;

import static tech.Jiaxing.OpaiProExtension.openAPI;
import static today.opai.api.Extension.getAPI;

/**
 * @Author renjun
 * @Date 2025/6/12 03:40
 * @Version 1.0
 */
public class classicalNameTags extends ExtensionModule implements EventHandler {
    public static classicalNameTags INSTANCE;
    private final BooleanValue showSelf = openAPI.getValueManager().createBoolean("Show Self", true);

    public classicalNameTags() {
        super("ClassicalNameTags", "ClassicalNameTags", EnumModuleCategory.VISUAL);
        INSTANCE = this;
        addValues(showSelf);
        setEventHandler(this);
    }


    @Override
    public void onRender3D(EventRender3D event) {
        for (Player player : openAPI.getWorld().getLoadedPlayerEntities()) {
            if (player.getEntityId() != openAPI.getLocalPlayer().getEntityId() || showSelf.getValue()) {
                renderNameTag(player, event);
            }
        }
    }

    private void renderNameTag(Player player, EventRender3D event) {
        // 计算玩家的相对位置
        double x = calculatePosition(player.getLastTickPosition().getX(),
                player.getPosition().getX(),
                event.getRenderPartialTicks()) -
                openAPI.getLocalPlayer().getViewPosition().getX();

        double y = calculatePosition(player.getLastTickPosition().getY(),
                player.getPosition().getY(),
                event.getRenderPartialTicks()) -
                openAPI.getLocalPlayer().getViewPosition().getY() + 2.3;

        double z = calculatePosition(player.getLastTickPosition().getZ(),
                player.getPosition().getZ(),
                event.getRenderPartialTicks()) -
                openAPI.getLocalPlayer().getViewPosition().getZ();

        openAPI.getGLStateManager().pushMatrix();

        float healthRatio = player.getHealth() / player.getMaxHealth();
        int healthBarWidth = (int) (74 * healthRatio);
        int healthColor = getHealthColor(healthRatio);

        GL11.glTranslated(x, y, z);
        GL11.glRotated(-openAPI.getLocalPlayer().getPlayerViewY(), 0, 1, 0);
        openAPI.getGLStateManager().disableDepth();
        GL11.glScalef(0.03f, 0.03f, 0.03f);

        String displayName = player.getDisplayName();
        int textX = (74 - getAPI().getFontUtil().getVanillaFont().getWidth(displayName)) / 2 - 37;
        getAPI().getFontUtil().getVanillaFont().drawString(displayName, textX, 2, Color.WHITE.getRGB());

        openAPI.getRenderUtil().drawRect(-37, -3, 74, 5, Color.BLACK);
        openAPI.getRenderUtil().drawRect(healthBarWidth - 37, -2, 74 - healthBarWidth, 3, Color.DARK_GRAY);
        openAPI.getRenderUtil().drawRect(-37, -2, healthBarWidth, 3, new Color(healthColor));

        openAPI.getGLStateManager().enableDepth();
        openAPI.getGLStateManager().popMatrix();
    }

    private double calculatePosition(double lastPos, double currentPos, float partialTicks) {
        return lastPos + (currentPos - lastPos) * partialTicks;
    }

    private int getHealthColor(float healthRatio) {
        if (healthRatio < 0.3f) return Color.RED.getRGB();
        if (healthRatio < 0.5f) return Color.ORANGE.getRGB();
        if (healthRatio < 0.7f) return Color.YELLOW.getRGB();
        return Color.GREEN.getRGB();
    }
}
