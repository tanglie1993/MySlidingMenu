package tanglie.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import tanglie.myapplication.views.TestViewGroup;

public class TestActivity extends AppCompatActivity {

    private TestViewGroup testViewGroup;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test);
        initContentViews();


        testViewGroup = (TestViewGroup) LayoutInflater.from(this).inflate(R.layout.layout_test_view_group, null);
        View menu = getLayoutInflater().inflate(R.layout.test_menu_frame, null);
        initMenu(menu);

        testViewGroup.setMenu((ViewGroup) menu);

        ViewGroup contentParent = (ViewGroup) findViewById(android.R.id.content);
        View content = contentParent.getChildAt(0);
        contentParent.removeView(content);
        contentParent.addView(testViewGroup);
        testViewGroup.setContent(content);
    }

    private void initMenu(View menu) {
        ListView listView = (ListView) menu.findViewById(R.id.listView);
        String[] str_name = new String[] { "A", "B", "C"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1,
                str_name);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(TestActivity.this, "onClick", Toast.LENGTH_SHORT).show();
            }
        });
        menu.findViewById(R.id.menuButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TestActivity.this, "onClick", Toast.LENGTH_SHORT).show();
            }
        });
    }
//
    private void initContentViews() {
        ListView listView = (ListView) findViewById(R.id.listView);
        String[] str_name = new String[] { "A", "B", "C"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1,
                str_name);
        listView.setAdapter(arrayAdapter);
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

}
