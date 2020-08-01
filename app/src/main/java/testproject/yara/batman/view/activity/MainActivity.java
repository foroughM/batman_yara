package testproject.yara.batman.view.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import testproject.yara.batman.R;
import testproject.yara.batman.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (activityMainBinding == null) {
            activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
            activityMainBinding.setLifecycleOwner(this);
        }
    }

}
