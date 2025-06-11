package today.opai.api.interfaces.managers;

import today.opai.api.enums.EnumModuleCategory;
import today.opai.api.interfaces.modules.PresetModule;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public interface ModuleManager {
    /**
     * Retrieves a module by its name.
     * Some modules have special methods, see {@link today.opai.api.interfaces.modules.special}
     *
     * @param name The name of the module to retrieve.
     * @return The {@link PresetModule}, or {@code null} if not found.
     */
    PresetModule getModule(String name);

    /**
     * Retrieves a collection of modules in the specified category.
     *
     * @param category The category of modules to retrieve.
     * @return A collection of {@link PresetModule} objects in the specified category.
     */
    Collection<PresetModule> getModulesInCategory(EnumModuleCategory category);

    /**
     * Retrieves all modules.
     *
     * @return A collection of all {@link PresetModule} objects.
     */
    Collection<PresetModule> getModules();

    /**
     * 获取所有已启用的模块
     * @return 已启用模块的列表
     */
    default List<PresetModule> getEnabledModules() {
        return getModules().stream()
                .filter(PresetModule::isEnabled)
                .collect(Collectors.toList());
    }

    /**
     * 获取指定分类中已启用的模块
     * @param category 模块分类
     * @return 指定分类中已启用模块的列表
     */
    default List<PresetModule> getEnabledModulesInCategory(EnumModuleCategory category) {
        return getModulesInCategory(category).stream()
                .filter(PresetModule::isEnabled)
                .collect(Collectors.toList());
    }

    /**
     * 检查指定名称的模块是否已启用
     * @param name 模块名称
     * @return 模块存在且已启用时返回true，否则返回false
     */
    default boolean isModuleEnabled(String name) {
        PresetModule module = getModule(name);
        return module != null && module.isEnabled();
    }
}
