package com.eastcompeace.lpa.sdk.language;

import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageConfig {
    private static volatile LanguageConfig instance;
    private ResourceBundle bundle;

    private LanguageConfig() {
    }

    public static LanguageConfig getInstance() {
        if (instance == null) {
            synchronized (LanguageConfig.class) {
                if (instance == null) {
                    instance = new LanguageConfig();
                }
            }
        }
        return instance;
    }

    public void setLanguage(Locale locale) {
        this.bundle = ResourceBundle.getBundle("LanguageBundle", locale);
        System.out.println("getString:" + getString("profileExists"));
    }

    public String getString(String str) {
        return this.bundle.getString(str);
    }

    public String profileExists() {
        return this.bundle.getString("profileExists");
    }

    public String profileInstallFailedWithApdu() {
        return this.bundle.getString("profileInstallFailedWithApdu");
    }

    public String euiccNotSupportUpdate() {
        return this.bundle.getString("euiccNotSupportUpdate");
    }

    public String imeiLengthErrorTip() {
        return this.bundle.getString("imeiLengthErrorTip");
    }

    public String UnableToResolveHost() {
        return this.bundle.getString("unableToResolveHost");
    }

    public String noAddressAssociatedWithHostname() {
        return this.bundle.getString("noAddressAssociatedWithHostname");
    }
}
