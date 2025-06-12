package today.opai.api.interfaces.modules;

import today.opai.api.features.ExtensionModule;

import java.util.Collection;
import static tech.Jiaxing.OpaiProExtension.openAPI;
import static today.opai.api.Extension.getAPI;
public interface PresetModule {
    void setEnabled(boolean enabled);

    boolean isEnabled();

    String getName();


    String getDescription();

    int getKey();

    void setKey(int key);

    boolean isHidden();

    void setHidden(boolean hidden);

    Collection<Value<?>> getValues();
}
