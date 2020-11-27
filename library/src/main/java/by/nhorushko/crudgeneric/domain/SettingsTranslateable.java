package by.nhorushko.crudgeneric.domain;

public class SettingsTranslateable implements SettingsVoid {

    private String lang;
    private Object cacheLanguage;

    public Object getCachedLanguage(){
        return cacheLanguage;
    }

    public void setCachedLanguage(Object o) {
        this.cacheLanguage = o;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
