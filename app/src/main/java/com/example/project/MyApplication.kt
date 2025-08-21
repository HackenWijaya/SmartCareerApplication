package com.example.project

import android.app.Application
import com.example.project.basic_api.data.repository.KelolaRepository
import com.example.project.basic_api.data.repository.LamaranRepository
import com.example.project.basic_api.ui.viewmodel.KelolaViewModel
import com.example.project.basic_api.ui.viewmodel.LamaranViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(appModule, appModule1)
        }
    }
}


// Modul untuk mendefinisikan dependensi
val appModule = module {
    viewModel { LamaranViewModel(get()) }
    single { LamaranRepository() }
}
val appModule1 = module {
    // Definisikan singleton untuk repository
    single { KelolaRepository() }

    // Definisikan viewModel
    viewModel { KelolaViewModel(get()) }
}

