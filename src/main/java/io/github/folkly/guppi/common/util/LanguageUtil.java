package io.github.folkly.guppi.common.util;

import com.intellij.lang.Language;

public class LanguageUtil {
    public static Language findLanguageById(String id) {
        if (id == null) {
            return null;
        }
        for (Language language : Language.getRegisteredLanguages()) {
            if (language.getID().toLowerCase().equals(id.toLowerCase())) {
                return language;
            }
        }
        return null;
    }
}
