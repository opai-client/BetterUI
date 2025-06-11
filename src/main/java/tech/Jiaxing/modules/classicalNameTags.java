package tech.Jiaxing.modules;

import org.lwjgl.opengl.GL11;
import tech.Jiaxing.OpaiProExtension;
import today.opai.api.enums.EnumModuleCategory;
import today.opai.api.events.EventRender2D;
import today.opai.api.events.EventRender3D;
import today.opai.api.features.ExtensionModule;
import today.opai.api.interfaces.EventHandler;
import today.opai.api.interfaces.game.entity.Entity;
import today.opai.api.interfaces.game.entity.LivingEntity;
import today.opai.api.interfaces.game.entity.Player;
import today.opai.api.interfaces.modules.Value;
import today.opai.api.interfaces.modules.values.BooleanValue;
import today.opai.api.interfaces.modules.values.NumberValue;
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
    private final BooleanValue self = openAPI.getValueManager().createBoolean("self",true);

    public classicalNameTags() {
        super("ClassicalNameTags", "NameTags0", EnumModuleCategory.VISUAL);
        INSTANCE = this;
        addValues(self);
        setEventHandler(this);
    }
    @Override
    public void onRender2D(EventRender2D e) {

    }
    public void onRender3D(EventRender3D event) {
        for (Player e : OpaiProExtension.openAPI.getWorld().getLoadedPlayerEntities()) {
            if (e.getEntityId() != OpaiProExtension.openAPI.getLocalPlayer().getEntityId() || ((Boolean) this.self.getValue()).booleanValue()) {
                double x = (e.getLastTickPosition().getX() + ((e.getPosition().getX() - e.getLastTickPosition().getX()) * event.getRenderPartialTicks())) - OpaiProExtension.openAPI.getLocalPlayer().getViewPosition().getX();
                double y = (e.getLastTickPosition().getY() + ((e.getPosition().getY() - e.getLastTickPosition().getY()) * event.getRenderPartialTicks())) - OpaiProExtension.openAPI.getLocalPlayer().getViewPosition().getY();
                double z = (e.getLastTickPosition().getZ() + ((e.getPosition().getZ() - e.getLastTickPosition().getZ()) * event.getRenderPartialTicks())) - OpaiProExtension.openAPI.getLocalPlayer().getViewPosition().getZ();
                OpaiProExtension.openAPI.getGLStateManager().pushMatrix();
                double r = e.getHealth() / e.getMaxHealth();
                int width = (int) (74.0d * r);
                int c = r < 0.3d ? Color.red.getRGB() : r < 0.5d ? Color.orange.getRGB() : r < 0.7d ? Color.yellow.getRGB() : Color.green.getRGB();
                GL11.glTranslated(x, y +2.3d, z);
                GL11.glRotated(-OpaiProExtension.openAPI.getLocalPlayer().getPlayerViewY(), 0.0d, 1.0d, 0.0d);
                OpaiProExtension.openAPI.getGLStateManager().disableDepth();
                GL11.glScalef(0.03f, 0.03f, 0.03f);
                openAPI.getFontUtil().getVanillaFont().drawString(e.getDisplayName(),(-37+74)/2-(getAPI().getFontUtil().getVanillaFont().getWidth(e.getDisplayName())),2,Color.WHITE.getRGB());
                OpaiProExtension.openAPI.getRenderUtil().drawRect(-37.0f, -3.0f, 74.0f, 5.0f, Color.black);
                OpaiProExtension.openAPI.getRenderUtil().drawRect(width - 37.0f, -2.0f, 74 - width, 3.0f, Color.darkGray);
                OpaiProExtension.openAPI.getRenderUtil().drawRect(-37.0f, -2.0f, width, 3.0f, new Color(c));
                OpaiProExtension.openAPI.getGLStateManager().enableDepth();
                OpaiProExtension.openAPI.getGLStateManager().popMatrix();
            }
        }
    }





}