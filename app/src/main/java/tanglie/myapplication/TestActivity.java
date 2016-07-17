package tanglie.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import tanglie.myapplication.views.TestViewGroup;

public class TestActivity extends AppCompatActivity {

    private TestViewGroup testViewGroup;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TestActivity.this, "activity_test_button", Toast.LENGTH_SHORT).show();
            }
        });

        testViewGroup = (TestViewGroup) LayoutInflater.from(this).inflate(R.layout.layout_test_view_group, null);
        View menu = getLayoutInflater().inflate(R.layout.test_menu_frame, null);
        menu.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TestActivity.this, "menu_button", Toast.LENGTH_SHORT).show();
            }
        });
        testViewGroup.setMenu(menu);

        ViewGroup contentParent = (ViewGroup) findViewById(android.R.id.content);
        View content = contentParent.getChildAt(0);
        contentParent.removeView(content);
        contentParent.addView(testViewGroup);
        testViewGroup.setContent(content);
    }

    @Override
    protected void onResume(){
        super.onResume();
    }



//    private int getScrollDx(float velocityX) {
//        int absoluteValue = (int) (Math.sqrt(Math.abs(velocityX)) * 2); // 使用经验公式估算滑动距离
//        return velocityX > 0 ? absoluteValue: -absoluteValue;
//    }
}
