package com.wordmemoriser.Word;

public enum Language {
    TR("TR"),
    EN("EN");

    private String value;

    Language(String value) {
        this.value = value;
    }

    public boolean equals(Language language){
        return this.value.equals(language.value);
    }

}
