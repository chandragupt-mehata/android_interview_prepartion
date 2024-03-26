package com.example.myhiltapplication.di

import com.example.myhiltapplication.test.TestDiHere
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Named

/**
 * what I felt that component and scope both can not work simuntanously
 * i..e if you have defined some component in module then you will be force
 * to define same related scope if you will change it, will give you compile time error.
 *
 * without module you can define only scope, can not define component like in TestDiIndependentHilt
 * Now in above class since component is not there whatever scope you will define, you will have to
 * inject that dependency only in such component which is having lower scope in else case
 * it will give you compile time error.
 *
 * ********
 * consumer(@Inject) - connector(@Component) - producer(@Module, @Provide, @Bind)
 * scope - tells same or diff instances will get created on each @Inject
 * DaggerMyComponent.build().inject(this) if activity wants to use @Inject field injection
 * If we are building this component in activity then scope would be against activity only. If we provide singleton scope for any dependency then if activity get
 * recreated then instance would be diff.
 * If we need to have same instance through out app. Then we will have to build this component in application class so that there would be only one component
 * created. component treats like a bag which will have all objects.
 * (getComponentfromApplication).inject(this) in activity.
 * Subcomponent - we can have some other component like at activity level which we can build in activity. But will have to provide dependencies to get it attached
 * with parent component.
 * to utilize subcompoenet - annotate that child compoenet as subcompoenet
 * subcompoenent can use objects of parent compoenet
 * @Singleton
 * @component
 * interface AppCompoennt {
 *  fun getUserRegistryComponennt(): UserRegisterComponent }
 *
 *  @ActivityScope
 *  @subcomponent (modules = [])
 *  interface UserRegisterComponent () {
 *  fun inject() }
 *
 *  https://www.youtube.com/watch?v=F5bvl_H5qGw&list=PLRKyZvuMYSIPwjYw1bt_7u7nEwe6vATQd&index=12
 */
@Module
@InstallIn(ActivityComponent::class)
class ApModule {

    @Provides
    @ActivityScoped
    @Named("two")
    fun provideTestDi(): TestDiHere {
        return TestDiHere()
    }

    @Provides
    @ActivityScoped
    @Named("one")
    fun provideTestDiN(): TestDiHere {
        return TestDiHere()
    }

}