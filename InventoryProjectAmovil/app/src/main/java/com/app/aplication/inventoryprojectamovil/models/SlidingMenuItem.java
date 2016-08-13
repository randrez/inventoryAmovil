package com.app.aplication.inventoryprojectamovil.models;

/**
 * Created by andres on 23/10/15.
 */
public class SlidingMenuItem {
    private int imageResource;
    private String menuItemName;

    public SlidingMenuItem(int image, String name) {
        this.imageResource = image;
        this.menuItemName = name;
    }

    public SlidingMenuItem(){

    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getMenuItemName() {
        return menuItemName;
    }

    public void setMenuItemName(String menuItemName) {
        this.menuItemName = menuItemName;
    }

    @Override
    public String toString() {
        return "SlidingMenuItem{" +
                "imageResource=" + imageResource +
                ", menuItemName='" + menuItemName + '\'' +
                '}';
    }
}
