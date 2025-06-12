package tech.Jiaxing;

import tech.Jiaxing.modules.*;
import today.opai.api.Extension;
import today.opai.api.OpenAPI;
import today.opai.api.annotations.ExtensionInfo;
import tech.Jiaxing.widgets.MyScoreboard;

import java.awt.*;

// Required @ExtensionInfo annotation
@ExtensionInfo(name = "BetterUI",author = "renjun",version = "0.4")
public class OpaiProExtension extends Extension {
    public static OpenAPI openAPI;
    @Override
    public void initialize(OpenAPI openAPI) {
;
        OpaiProExtension.openAPI = openAPI;

        // Modules
        openAPI.registerFeature(new ScoreboardPlus());
        openAPI.registerFeature(new BasicUI());
        openAPI.registerFeature(new classicalNameTags());
        openAPI.registerFeature(new ModernUI());

        // Widgets
        openAPI.registerFeature(new MyScoreboard());



    }
}
