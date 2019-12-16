package com.tadiuzzz.forecast.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tadiuzzz.forecast.feature.MainViewModel
import com.tadiuzzz.forecast.di.scope.AppScope
import com.tadiuzzz.forecast.feature.changecity.ChangeCityFragment
import com.tadiuzzz.forecast.feature.changecity.ChangeCityViewModel
import com.tadiuzzz.forecast.feature.current.CurrentWeatherViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass

@AppScope
class ViewModelFactory @Inject constructor(
    private val viewModels:
    MutableMap<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {
    
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        viewModels[modelClass]?.get() as T
}

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun mainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CurrentWeatherViewModel::class)
    internal abstract fun currentWeatherViewModel(viewModel: CurrentWeatherViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChangeCityViewModel::class)
    internal abstract fun changeCityViewModel(viewModel: ChangeCityViewModel): ViewModel

}