package com.safewaychinay.toolbar.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.XmlResourceParser;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.safewaychinay.toolbar.R;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liu_haifang
 * @version 1.0
 * @Title：SAFEYE@
 * @Description：
 * @date 2016-01-19
 */
public class Toolbar extends LinearLayout {

    private static final int MAX_MENU = 3;
    private static final int DEFAULT_MENU_SIZE = 50;

    private MaterialMenuView materialMenuView;
    private TextView mTitle;
    private LinearLayout mCotent;
    private LinearLayout mMenu;
    private int menuResId = View.NO_ID;

    private OnMenuClickLisnter onMenuClickLisnter;


    public interface OnMenuClickLisnter {
        void onMenuClick(int id);
    }


    public Toolbar(Context context) {
        super(context);
        init();
    }

    public Toolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Toolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.toolbar_layout, this, true);
        materialMenuView = (MaterialMenuView) view.findViewById(R.id.icon_view);

        mTitle = (TextView) view.findViewById(R.id.title_view);
        mCotent = (LinearLayout) view.findViewById(R.id.content_view);
        mMenu = (LinearLayout) view.findViewById(R.id.menu_view);
        //默认是箭头
        materialMenuView.setState(MaterialMenuDrawable.IconState.ARROW);
        initEvent();


    }

    /**
     * 设置默认事件
     */
    private void initEvent() {
        materialMenuView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (materialMenuView.getState() == MaterialMenuDrawable.IconState.ARROW) {
                    if (getContext() instanceof Activity) {
                        Activity activity = (Activity) getContext();
                        activity.finish();
                    }

                }
            }
        });
    }


    /**
     * 设置menu
     *
     * @param resId
     */
    public void addMenu(int resId) {
        if (resId != View.NO_ID) {
            menuResId = resId;
        }
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeGlobalOnLayoutListener(this);
                genarlMenu();
            }
        });
    }


    /**
     * 生成menu
     */
    private void genarlMenu() {
        List<MenuItem> menuItems = menuInflate(getContext(), menuResId);
        if (menuItems != null) {
            int canAddMemnuConut = getCanAddMemnuConut();
            if (canAddMemnuConut >= MAX_MENU) {
                canAddMemnuConut = MAX_MENU;
            }
            if (menuItems.size() <= canAddMemnuConut) {
                for (int i = 0; i < menuItems.size(); i++) {
                    addMenuItem(menuItems.get(i));
                }
            } else {
                for (int i = 0; i < canAddMemnuConut; i++) {
                    addMenuItem(menuItems.get(i));
                }
                List<MenuItem> moreItems = new ArrayList<>();
                for (int i = canAddMemnuConut; i < menuItems.size(); i++) {
                    moreItems.add(menuItems.get(i));
                }

                addMoreMenuItem(moreItems);


            }
        }
    }


    /**
     * menu剩余的长度
     *
     * @return
     */
    private int getLeaveSpace() {
        return getMeasuredWidth() / 2 - materialMenuView.getMeasuredWidth() - mTitle.getMeasuredWidth();
    }


    private int getCanAddMemnuConut() {
        int leaveSpace = getLeaveSpace();
        return leaveSpace / DEFAULT_MENU_SIZE;
    }


    /**
     * 添加一个menuitem
     *
     * @param menuItem
     */
    private void addMenuItem(final MenuItem menuItem) {
        String title = menuItem.getTitle();
        int icon = menuItem.getIcon();
        final int id = menuItem.getId();
        View menuItemView = LayoutInflater.from(getContext()).inflate(R.layout.view_menu_item, this, false);
        ImageView iconView = (ImageView) menuItemView.findViewById(R.id.menu_icon);
        TextView titleView = (TextView) menuItemView.findViewById(R.id.menu_title);
        if (icon != View.NO_ID) {
            iconView.setBackgroundResource(icon);
            iconView.setVisibility(View.VISIBLE);
            titleView.setVisibility(View.GONE);
        } else {
            iconView.setVisibility(View.GONE);
            iconView.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(title)) {
            titleView.setText(title);
        }
        if (id != View.NO_ID) {
            menuItemView.setId(id);
        }

        LinearLayout.LayoutParams layoutParams = (LayoutParams) menuItemView.getLayoutParams();
        layoutParams.gravity = Gravity.CENTER_VERTICAL;
        menuItemView.setLayoutParams(layoutParams);

        menuItemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onMenuClickLisnter != null) {
                    onMenuClickLisnter.onMenuClick(id);
                }
            }
        });

        mMenu.addView(menuItemView);
    }


    /**
     * 添加更多menu
     *
     * @param moreItems
     */
    private void addMoreMenuItem(List<MenuItem> moreItems) {

        View menuItemView = LayoutInflater.from(getContext()).inflate(R.layout.view_menu_more, this, false);


        final ActionBarWindow actionBarWindow = new ActionBarWindow(getContext());

        for (MenuItem menuItem : moreItems) {
            actionBarWindow.addActionItem(new ActionItem(menuItem.getId(), menuItem.getTitle(), menuItem.getIcon()));
        }

        actionBarWindow.setOnActionItemClickListener(new ActionBarWindow.OnActionItemClickListener() {
            @Override
            public void onItemClick(ActionBarWindow source, int pos, int actionId) {
                if (onMenuClickLisnter != null) {
                    onMenuClickLisnter.onMenuClick(actionId);
                }

            }
        });

        menuItemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                actionBarWindow.show(v);
            }
        });


        mMenu.addView(menuItemView);


    }


    public MaterialMenuView getMaterialMenuView() {
        return materialMenuView;
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        mTitle.setText(title);
    }

    public void setOnMenuClickLisnter(OnMenuClickLisnter onMenuClickLisnter) {
        this.onMenuClickLisnter = onMenuClickLisnter;
    }


    /**
     * 解析menu xml文件
     *
     * @param context
     * @param xmlRes
     * @return
     */
    private List<MenuItem> menuInflate(Context context, int xmlRes) {
        List<MenuItem> result = new ArrayList<>();
        XmlResourceParser xrp = context.getResources().getXml(xmlRes);
        MenuItem menuItem;
        try {
            while (xrp.getEventType() != XmlResourceParser.END_DOCUMENT) {
                if (xrp.getEventType() == XmlResourceParser.START_TAG) {
                    String tagName = xrp.getName();
                    if (tagName.equals("item")) {
                        menuItem = new MenuItem();
                        String id = xrp.getAttributeValue("http://schemas.android.com/apk/res/android", "id");
                        if (!TextUtils.isEmpty(id)) {
                            id = id.replaceAll("@", "");
                        }
                        menuItem.setId(TextUtils.isEmpty(id) ? View.NO_ID : Integer.parseInt(id));
                        String title = xrp.getAttributeValue("http://schemas.android.com/apk/res/android", "title");
                        if (!TextUtils.isEmpty(title)) {
                            title = title.replaceAll("@", "");
                        }
                        menuItem.setTitle(title);
                        String icon = xrp.getAttributeValue("http://schemas.android.com/apk/res/android", "icon");
                        if (!TextUtils.isEmpty(icon)) {
                            icon = icon.replaceAll("@", "");
                        }
                        menuItem.setIcon(TextUtils.isEmpty(icon) ? View.NO_ID : Integer.parseInt(icon));
                        result.add(menuItem);
                    }
                }
                xrp.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
