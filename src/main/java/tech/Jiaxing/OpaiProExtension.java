package tech.Jiaxing;

import tech.Jiaxing.modules.*;
import today.opai.api.Extension;
import today.opai.api.OpenAPI;
import today.opai.api.annotations.ExtensionInfo;
import tech.Jiaxing.commands.BindsCommand;
import tech.Jiaxing.widgets.MyScoreboard;

import java.awt.*;

// Required @ExtensionInfo annotation
@ExtensionInfo(name = "BasicUI",author = "renjun",version = "0.3")
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
        openAPI.registerFeature(new ClassicalScoreboard());
        openAPI.registerFeature(new BasicUI());
        openAPI.registerFeature(new classicalNameTags());
        // Widgets
        openAPI.registerFeature(new MyScoreboard());


    }
}
