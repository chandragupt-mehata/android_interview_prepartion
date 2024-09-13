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
 * Scope defines how long a dependency should live and where it should be shared.
 * Component is a container that provides dependencies based on the defined scopes and manages their lifecycle.
 * So component is mandatory in module. If we don't define any scope in provided dependency in module then it will not be scoped to component which
 * meant for each @Inject it will create new instance. But if we define scope then it will be singleton wrt to that scope. For ex. if we define
 * @ActivityScoped to dependency then it will be single instance for each @Inject within the single activity but of course it will provide new
 * instance for activity change.
 * Roles of component (How long that component will live that depends upon type of component)
 * Injection Targets:
 * Components define where dependencies can be injected. For example, dependencies provided by ApplicationComponent can be injected anywhere
 * in the app, while dependencies provided by ActivityComponent are only available within the activity and its fragments.
 * Lifecycle Management:
 * Components help manage the lifecycle of dependencies, ensuring that they are properly created and destroyed in accordance with the
 * lifecycle of the context they are associated with.
 * This is essential for avoiding memory leaks and ensuring that resources are released appropriately.
 * Dependency Hierarchy:
 * Components create a hierarchical structure for dependencies. Higher-level components (like ApplicationComponent) can provide dependencies to
 * lower-level components (like ActivityComponent and FragmentComponent).
 * This allows for a clear and organized way to manage dependencies across different parts of the application.
 * Contextual Dependency Provision:
 * Components define the context (application, activity, fragment, etc.) in which dependencies are provided. This ensures that dependencies are created
 * and managed within the correct lifecycle context.
 * For example, an ApplicationComponent ensures that dependencies are available for the entire application's lifecycle, while an ActivityComponent ensures
 * that dependencies are tied to a specific activity's lifecycle
 * https://www.youtube.com/watch?v=T0ZTkeJbLso&list=PLHhegjHfl9--IBbefhNMgAzIMiqKQ6XTP&index=3 (cheezy code)
 * Lets say one fragment wants to get object using @Inject then it will ask dagger hilt to provide that. Now dagger/hilt will check
 * - whether that class is having any constructor injection if yes then it will provide the object
 * - if no then it will check fragment component first if there also it does not find then it will go for other component which is at higher level
 * like activityComponent, applicationComponent etc (refer the component hierarchy)
 *
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

/**
 * @Provides is responsible for creating and providing instances of dependencies, allowing you to write custom creation logic within the method.
 * @Binds is specifically used for binding an interface to its implementation, whereas @Provides can be used to provide instances of
 * any type, including interfaces, classes, or other objects.
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