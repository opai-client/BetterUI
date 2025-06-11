package tech.Jiaxing;

import tech.Jiaxing.modules.*;
import today.opai.api.Extension;
import today.opai.api.OpenAPI;
import today.opai.api.annotations.ExtensionInfo;
import tech.Jiaxing.commands.BindsCommand;
import tech.Jiaxing.widgets.MyScoreboard;
import today.opai.api.interfaces.modules.PresetModule;
import today.opai.api.interfaces.modules.Value;
import today.opai.api.interfaces.modules.values.ColorValue;

import java.awt.*;

// Required @ExtensionInfo annotation
@ExtensionInfo(name = "OpaiPro",author = "renjun",version = "0.2")
public class OpaiProExtension extends Extension {
    public static OpenAPI openAPI;
    public Color BaseColor;
    public Color GradientColor;
    @Override
    public void initialize(OpenAPI openAPI) {
        OpaiProExtension.openAPI = openAPI;
        // Command
        openAPI.registerFeature(new BindsCommand());

        // Modules
        openAPI.registerFeature(new ModernScoreboard());
        openAPI.registerFeature(new BasicUI());
        // Widgets
        openAPI.registerFeature(new MyScoreboard());


    }
}
