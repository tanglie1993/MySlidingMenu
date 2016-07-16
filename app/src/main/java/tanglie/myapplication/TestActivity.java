package tanglie.myapplication;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tanglie.myapplication.slidingmenu.SlidingMenu;
import tanglie.myapplication.views.TestViewGroup;

public class TestActivity extends AppCompatActivity {

    private TestViewGroup testViewGroup;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test);

        testViewGroup = (TestViewGroup) LayoutInflater.from(this).inflate(R.layout.layout_test_view_group, null);
        testViewGroup.setMenu(getLayoutInflater().inflate(R.layout.test_menu_frame, null));

        ViewGroup contentParent = (ViewGroup) findViewById(android.R.id.content);
        View content = contentParent.getChildAt(0);
        contentParent.removeView(content);
        contentParent.addView(testViewGroup);
        testViewGroup.setContent(content);
    }

    @Override
    protected void onResume(){
        super.onResume();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                testViewGroup.forceLayout();
//            }
//        }, 2000);
    }
}
