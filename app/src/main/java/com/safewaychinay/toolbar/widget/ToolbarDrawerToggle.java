package com.safewaychinay.toolbar.widget;

import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;

/**
 * @author liu_haifang
 * @version 1.0
 * @Title：SAFEYE@
 * @Description：
 * @date 2016-01-19
 */
public class ToolbarDrawerToggle {

    private final DrawerLayout drawerLayout;
    private final Toolbar toolbar;
    private boolean direction;
    private MaterialMenuView materialMenuView;

    public ToolbarDrawerToggle(DrawerLayout drawerLayout, Toolbar toolbar) {
        this.drawerLayout = drawerLayout;
        this.toolbar = toolbar;
        init();
    }

    private void init() {
        materialMenuView = toolbar.getMaterialMenuView();
        drawerLayout.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                materialMenuView.setTransformationOffset(
                        MaterialMenuDrawable.AnimationState.BURGER_ARROW,
                        direction ? 2 - slideOffset : slideOffset
                );
            }

            @Override
            public void onDrawerOpened(android.view.View drawerView) {
                direction = true;
            }

            @Override
            public void onDrawerClosed(android.view.View drawerView) {
                direction = false;
            }
        });

        materialMenuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialMenuDrawable.IconState state = materialMenuView.getState();
                if (state == MaterialMenuDrawable.IconState.ARROW) {
                    drawerLayout.closeDrawer(Gravity.LEFT);
                } else if (state == MaterialMenuDrawable.IconState.BURGER) {
                    drawerLayout.openDrawer(Gravity.LEFT);
                }
            }
        });

    }


    public void syncState() {
        this.direction = drawerLayout.isDrawerOpen(Gravity.LEFT);
        materialMenuView.setState(MaterialMenuDrawable.IconState.BURGER);
    }

}
