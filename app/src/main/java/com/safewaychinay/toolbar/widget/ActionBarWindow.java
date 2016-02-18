package com.safewaychinay.toolbar.widget;

import android.content.Context;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.ScrollView;
import android.widget.TextView;

import com.safewaychinay.toolbar.R;

import java.util.ArrayList;
import java.util.List;


/**
 * 自定义actionBar的PopupWindow效果
 *
 * @author liu_haifang
 * @version 1.0
 * @Title：SAFEYE@
 * @Description：
 * @date 2015-2-9 上午11:47:45
 */
public class ActionBarWindow extends PopupWindows implements OnDismissListener {

    private View mRootView;        //显示item的view
    private LayoutInflater mInflater;
    private ViewGroup mTrack;
    private ScrollView mScroller;

    private boolean mDidAction;
    private int mChildPos;
    private int mInsertPos;
    private int mOrientation;
    private int rootWidth = 0;

    private OnActionItemClickListener mItemClickListener;   //点击事件
    private OnDismissListener mDismissListener;

    private List<ActionItem> actionItems = new ArrayList<ActionItem>();

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;


    public ActionBarWindow(Context context) {
        super(context);


        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        setRootViewId(R.layout.popup_vertical);

        mChildPos = 0;
    }

    public ActionItem getActionItem(int index) {
        return actionItems.get(index);
    }

    public void setRootViewId(int id) {
        mRootView = (ViewGroup) mInflater.inflate(id, null);
        mTrack = (ViewGroup) mRootView.findViewById(R.id.tracks);


        mScroller = (ScrollView) mRootView.findViewById(R.id.scroller);

        // This was previously defined on show() method, moved here to prevent
        // force close that occured
        // when tapping fastly on a view to show quickaction dialog.
        // Thanx to zammbi (github.com/zammbi)
        mRootView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        setContentView(mRootView);
    }


    public void setOnActionItemClickListener(OnActionItemClickListener listener) {
        mItemClickListener = listener;
    }

    public void addActionItem(ActionItem action) {
        actionItems.add(action);

        String title = action.getTitle();
        int icon = action.getIcon();

        View container;

        container = mInflater.inflate(R.layout.action_item_vertical, null);

        ImageView img = (ImageView) container.findViewById(R.id.iv_icon);
        TextView text = (TextView) container.findViewById(R.id.tv_title);

        if (icon != View.NO_ID) {
            img.setBackgroundResource(icon);
        } else {
            img.setVisibility(View.GONE);
        }

        if (title != null) {
            text.setText(title);
        } else {
            text.setVisibility(View.GONE);
        }

        final int pos = mChildPos;
        final int actionId = action.getActionId();

        container.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(ActionBarWindow.this, pos, actionId);
                }
            }
        });

        container.setFocusable(true);
        container.setClickable(true);

        mTrack.addView(container, mInsertPos);

        mChildPos++;
        mInsertPos++;
    }

    public void show(View anchor) {
        preShow();

        int xPos, yPos, arrowPos;

        mDidAction = false;

        int[] location = new int[2];

        anchor.getLocationOnScreen(location);

        Rect anchorRect = new Rect(location[0], location[1], location[0] + anchor.getWidth(), location[1] + anchor.getHeight());

        // mRootView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
        // LayoutParams.WRAP_CONTENT));

        mRootView.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        int rootHeight = mRootView.getMeasuredHeight();

        if (rootWidth == 0) {
            rootWidth = mRootView.getMeasuredWidth();
        }

        int screenWidth = mWindowManager.getDefaultDisplay().getWidth();
        int screenHeight = mWindowManager.getDefaultDisplay().getHeight();

        // automatically get X coord of popup (top left)
        if ((anchorRect.left + rootWidth) > screenWidth) {
            xPos = anchorRect.left - (rootWidth - anchor.getWidth());
            xPos = (xPos < 0) ? 0 : xPos;

            arrowPos = anchorRect.centerX() - xPos;

        } else {
            if (anchor.getWidth() > rootWidth) {
                xPos = anchorRect.centerX() - (rootWidth / 2);
            } else {
                xPos = anchorRect.left;
            }

            arrowPos = anchorRect.centerX() - xPos;
        }

        int dyTop = anchorRect.top;
        int dyBottom = screenHeight - anchorRect.bottom;

        boolean onTop = (dyTop > dyBottom) ? true : false;

        if (onTop) {
            if (rootHeight > dyTop) {
                yPos = 15;
                LayoutParams l = mScroller.getLayoutParams();
                l.height = dyTop - anchor.getHeight();
            } else {
                yPos = anchorRect.top - rootHeight;
            }
        } else {
            yPos = anchorRect.bottom;

            if (rootHeight > dyBottom) {
                LayoutParams l = mScroller.getLayoutParams();
                l.height = dyBottom;
            }
        }

        setAnimationStyle(screenWidth, anchorRect.centerX(), onTop);

        mWindow.showAtLocation(anchor, Gravity.NO_GRAVITY, xPos, yPos);
    }

    private void setAnimationStyle(int screenWidth, int requestedX, boolean onTop) {
        int arrowPos = requestedX;


        if (arrowPos <= screenWidth / 4) {
            mWindow.setAnimationStyle((onTop) ? R.style.Animations_PopUpMenu_Left : R.style.Animations_PopDownMenu_Left);
        } else if (arrowPos > screenWidth / 4 && arrowPos < 3 * (screenWidth / 4)) {
            mWindow.setAnimationStyle((onTop) ? R.style.Animations_PopUpMenu_Center : R.style.Animations_PopDownMenu_Center);
        } else {
            mWindow.setAnimationStyle((onTop) ? R.style.Animations_PopUpMenu_Right : R.style.Animations_PopDownMenu_Right);
        }

    }

    public void setOnDismissListener(OnDismissListener listener) {
        setOnDismissListener(this);

        mDismissListener = listener;
    }


    @Override
    public void onDismiss() {
        if (!mDidAction && mDismissListener != null) {
            mDismissListener.onDismiss();
        }
    }

    public interface OnActionItemClickListener {
        void onItemClick(ActionBarWindow source, int pos, int actionId);
    }

    public interface OnDismissListener {
        void onDismiss();
    }
}