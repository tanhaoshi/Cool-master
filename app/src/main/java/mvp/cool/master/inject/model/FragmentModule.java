package mvp.cool.master.inject.model;

import android.support.v4.app.Fragment;

import dagger.Module;
import dagger.Provides;

/**
 * @version 1.0
 * @author TanHao
 * Created by Administrator on 2017/5/15.
 */
@Module
public class FragmentModule {

    private Fragment mFragment;

    public FragmentModule(Fragment fragment){
        this.mFragment = fragment;
    }

    @Provides
    public Fragment provideFragment(){
        return mFragment;
    }

}
