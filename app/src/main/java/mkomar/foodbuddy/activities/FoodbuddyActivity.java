package mkomar.foodbuddy.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import mkomar.foodbuddy.R;

public abstract class FoodbuddyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Settings")
                .setIcon(R.drawable.ic_settings_black_24dp)
                .setOnMenuItemClickListener(item -> {
                    Toast.makeText(FoodbuddyActivity.this, "Settings screen", Toast.LENGTH_SHORT).show();

                    return false;
                })
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return true;
    }
}
