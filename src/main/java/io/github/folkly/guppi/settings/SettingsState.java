package io.github.folkly.guppi.settings;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializer;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(
        name = "io.github.folkly.guppi.settings.SettingsState",
        storages = @Storage("GuppiPluginSettings.xml")
)
public class SettingsState implements PersistentStateComponent<SettingsState> {

    public ChatSettingsState chatSettingsState = new ChatSettingsState();
    public OpenAiSettingsState openAiSettingsState = new OpenAiSettingsState();

    public static SettingsState getInstance() {
        return ApplicationManager.getApplication().getService(SettingsState.class);
    }

    /**
     * @return a component state. All properties, public and annotated fields are serialized.
     * Only values which differ from the default (i.e. the value of newly instantiated class) are serialized.
     * {@code null} value indicates that the returned state won't be stored, as a result previously stored state will be used.
     * @see XmlSerializer
     */
    @Override
    public @Nullable SettingsState getState() {
        return this;
    }

    /**
     * This method is called when a new component state is loaded.
     * The method can and will be called several times if config files are externally changed while the IDE is running.
     * <p>
     * State object should be used directly, defensive copying is not required.
     *
     * @param state loaded component state
     * @see XmlSerializerUtil#copyBean(Object, Object)
     */
    @Override
    public void loadState(@NotNull SettingsState state) {
        XmlSerializerUtil.copyBean(state, this);
    }
}
