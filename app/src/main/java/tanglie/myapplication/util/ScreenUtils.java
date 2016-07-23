package tanglie.myapplication.util;

import android.content.Context;
import android.view.WindowManager;

/**
 * Created by Administrator on 2016/7/23 0023.
 */
public class ScreenUtils {
    public static final int getScreenWidth(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }
}
