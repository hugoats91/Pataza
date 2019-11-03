package com.app.pataza

import android.app.Application
import androidx.room.Room
import com.app.pataza.core.di.ApplicationComponent
import com.app.pataza.core.di.ApplicationModule
import com.app.pataza.core.di.DaggerApplicationComponent
import com.app.pataza.core.util.Prefs
import com.app.pataza.data.user.AppDataBase
import com.squareup.leakcanary.LeakCanary

class PatazaApp : Application() {

    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }

    companion object{
        lateinit var database: AppDataBase
        lateinit var prefs: Prefs
    }

    override fun onCreate() {
        super.onCreate()
        prefs = Prefs(applicationContext)
        this.injectMembers()
        //this.initializeLeakDetection()
        database = Room.databaseBuilder(this, AppDataBase::class.java, "pet-database").build()
    }

    private fun injectMembers() = appComponent.inject(this)

    private fun initializeLeakDetection() {
        if (BuildConfig.DEBUG) LeakCanary.install(this)
    }
}
