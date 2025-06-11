package tech.Jiaxing.modules;

import today.opai.api.enums.EnumModuleCategory;
import today.opai.api.features.ExtensionModule;
import today.opai.api.interfaces.EventHandler;
import today.opai.api.interfaces.modules.Value;
import today.opai.api.interfaces.modules.values.MultiBooleanValue;

import static tech.Jiaxing.OpaiProExtension.openAPI;

public class ClassicalScoreboard extends ExtensionModule implements EventHandler {
    public static ClassicalScoreboard INSTANCE;
    private boolean state;
    public ClassicalScoreboard() {
        super("ClassicalScoreboard", "A Classical scoreboard implementation", EnumModuleCategory.VISUAL);
        setEventHandler(this);
        INSTANCE = this;
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
