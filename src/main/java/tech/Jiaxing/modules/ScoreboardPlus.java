package tech.Jiaxing.modules;

import today.opai.api.enums.EnumModuleCategory;
import today.opai.api.features.ExtensionModule;
import today.opai.api.interfaces.EventHandler;
import today.opai.api.interfaces.modules.Value;
import today.opai.api.interfaces.modules.values.BooleanValue;
import today.opai.api.interfaces.modules.values.ModeValue;
import today.opai.api.interfaces.modules.values.MultiBooleanValue;

import static tech.Jiaxing.OpaiProExtension.openAPI;
import static today.opai.api.Extension.getAPI;

public class ScoreboardPlus extends ExtensionModule implements EventHandler {
    public static ScoreboardPlus INSTANCE;
    private boolean state;
    public static ModeValue Mode = openAPI.getValueManager().createModes("Mode", "Classical",new String[]{"Classical","Modern"});

    public ScoreboardPlus() {
        super("ScoreboardPlus", "ScoreboardPlus", EnumModuleCategory.VISUAL);
        setEventHandler(this);
        INSTANCE = this;
        addValues(Mode);
    }

    @Override
    public void onEnabled() {

        super.onEnabled();

        // Disable vanilla scoreboard
        for (Value<?> value : openAPI.getModuleManager().getModule("HUD").getValues()) {
            if(value.getName().equals("Widgets") && value instanceof MultiBooleanValue){
                MultiBooleanValue multiBooleanValue = (MultiBooleanValue) value;
                for (Value<Boolean> booleanValue : multiBooleanValue.getValue()) {
                    if(booleanValue.getName().equals("Scoreboard")){
                        state = true;
                        booleanValue.setValue(false);
                    }
                }
            }
        }
    }

    @Override
    public void onDisabled() {
        super.onDisabled();

        // Re-enable vanilla scoreboard
        if(state){
            for (Value<?> value : openAPI.getModuleManager().getModule("HUD").getValues()) {
                if(value.getName().equals("Widgets") && value instanceof MultiBooleanValue){
                    MultiBooleanValue multiBooleanValue = (MultiBooleanValue) value;
                    for (Value<Boolean> booleanValue : multiBooleanValue.getValue()) {
                        if(booleanValue.getName().equals("Scoreboard")){
                            booleanValue.setValue(true);
                        }
                    }
                }
            }
        }
    }

    // Always hidden in array list
    @Override
    public boolean isHidden() {
        return true;
    }


}
