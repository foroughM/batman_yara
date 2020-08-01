package testproject.yara.batman;


import android.app.Application;

import testproject.yara.batman.data.injection.ApiModule;
import testproject.yara.batman.data.injection.ApplicationComponent;
import testproject.yara.batman.data.injection.DaggerApplicationComponent;
import testproject.yara.batman.data.injection.RoomModule;

public class BatmanApplication extends Application {

    ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public ApplicationComponent getApplicationComponent() {
        if (applicationComponent == null)
            applicationComponent = DaggerApplicationComponent.builder()
                    .apiModule(new ApiModule())
                    .roomModule(new RoomModule(getApplicationContext())).build();
        return applicationComponent;
    }
}
