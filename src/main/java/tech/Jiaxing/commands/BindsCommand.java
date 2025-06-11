package tech.Jiaxing.commands;

import org.lwjgl.input.Keyboard;
import today.opai.api.features.ExtensionCommand;
import today.opai.api.interfaces.modules.PresetModule;
import tech.Jiaxing.OpaiProExtension;

public class BindsCommand extends ExtensionCommand {
    public BindsCommand() {
        super(new String[]{"binds"}, "Show binds list", ".binds");
    }

    @Override
    public void onExecute(String[] strings) {
        OpaiProExtension.openAPI.printMessage("§aBinds:");
        for (PresetModule module : OpaiProExtension.openAPI.getModuleManager().getModules()) {
            if(module.getKey() != -1){
                OpaiProExtension.openAPI.printMessage("§7" + module.getName() + ": §f" + Keyboard.getKeyName(module.getKey()));
            }
        }
    }
}
