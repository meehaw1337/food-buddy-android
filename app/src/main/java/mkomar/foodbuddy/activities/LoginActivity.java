package mkomar.foodbuddy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import mkomar.foodbuddy.R;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_button)
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainMenuActivity.class);

            startActivity(intent);
        });
    }
}
