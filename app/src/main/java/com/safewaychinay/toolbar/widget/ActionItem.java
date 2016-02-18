package com.safewaychinay.toolbar.widget;

import android.view.View;

/**
 * 显示项
 *
 * @author liu_haifang
 * @version 1.0
 * @Title：SAFEYE@
 * @Description：
 * @date 2015-2-9 上午11:44:08
 */
public class ActionItem {
    private int icon;
    private String title;
    private int actionId = -1;


    public ActionItem() {
        this(-1, null, View.NO_ID);
    }

    public ActionItem(int actionId, String title) {
        this(actionId, title, View.NO_ID);
    }

    public ActionItem(int actionId, String title, int icon) {
        this.title = title;
        this.icon = icon;
        this.actionId = actionId;
    }


    public ActionItem(int icon) {
        this(-1, null, icon);
    }

    public ActionItem(int actionId, int icon) {
        this(actionId, null, icon);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getIcon() {
        return this.icon;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

    public int getActionId() {
        return actionId;
    }

}
