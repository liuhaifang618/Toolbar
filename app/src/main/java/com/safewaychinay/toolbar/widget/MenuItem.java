package com.safewaychinay.toolbar.widget;

/**
 * @author liu_haifang
 * @version 1.0
 * @Title：SAFEYE@
 * @Description：
 * @date 2016-01-20
 */
public class MenuItem {

    private int icon;
    private String title;
    private int id;

    public MenuItem(int id, String title, int icon) {
        this.id = id;
        this.title = title;
        this.icon = icon;
    }

    public MenuItem() {
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
