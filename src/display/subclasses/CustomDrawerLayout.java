package display.subclasses;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.EEN.Audit.R;

/**
 * Created by Steven Wance on 3/23/2015.
 */
public class CustomDrawerLayout extends DrawerLayout {
    public CustomDrawerLayout(Context context) {
        super(context);
    }

    public CustomDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomDrawerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    View mDrawerListView;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mDrawerListView = findViewById(R.id.drawer_layout);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        if(event.getX() > 30 && event.getAction() == MotionEvent.ACTION_DOWN){
            if(isDrawerOpen(mDrawerListView) || isDrawerVisible(mDrawerListView)){
                return true;
            } else{
                return false;
            }
        }

        return true;
    }


}
