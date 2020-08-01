package testproject.yara.batman.data.injection;

import javax.inject.Singleton;

import dagger.Component;
import testproject.yara.batman.viewmodel.VideoDetailsViewModel;
import testproject.yara.batman.viewmodel.VideoListViewModel;

@Component(modules = {ApiModule.class, RoomModule.class})
@Singleton
public interface ApplicationComponent {

    void inject(VideoListViewModel batmanApplication);

    void inject(VideoDetailsViewModel videoDetailsViewModel);
}
