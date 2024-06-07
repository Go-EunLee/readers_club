package org.example.domain.enums;

public enum BoardCategory {
    novel, poem, essay, cooking, health, sport, none;

    public static BoardCategory of(String category){
        if (category==null || category.equalsIgnoreCase("none")) return BoardCategory.none;
        else if(category.equalsIgnoreCase("novel")) return BoardCategory.novel;
        else if (category.equalsIgnoreCase("poem")) return BoardCategory.poem;
        else if (category.equalsIgnoreCase("essay")) return BoardCategory.essay;
        else if (category.equalsIgnoreCase("cooking")) return BoardCategory.cooking;
        else if (category.equalsIgnoreCase("health")) return BoardCategory.health;
        else if (category.equalsIgnoreCase("sport")) return BoardCategory.sport;

        return null;
    }
}
