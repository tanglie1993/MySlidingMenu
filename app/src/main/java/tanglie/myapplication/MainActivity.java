package tanglie.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import tanglie.myapplication.views.SlidingMenu;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test);
        initContentViews();
        SlidingMenu testViewGroup = SlidingMenu.getInstance(this);
        ViewGroup menu = (ViewGroup) findViewById(R.id.menu);
        initMenu(menu);
        testViewGroup.setContentView(this, menu);
    }



    private void initMenu(View menu) {
        ListView listView = (ListView) menu.findViewById(R.id.menuListView);
        String[] str_name = new String[] {"A", "B", "C"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1,
                str_name);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "menu onClick " + position, Toast.LENGTH_SHORT).show();
            }
        });
        menu.findViewById(R.id.menuButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "onClick", Toast.LENGTH_SHORT).show();
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "content onClick " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

}
