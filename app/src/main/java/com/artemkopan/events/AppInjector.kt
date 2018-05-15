package com.artemkopan.events

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.artemkopan.core.tools.Logger
import com.artemkopan.presentation.base.Injectable

class AppInjector(private val application: EventsApp) {

    fun registerCallbacks() {
        application
                .registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
                    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                        handleActivity(activity)
                    }

                    override fun onActivityStarted(activity: Activity) {

                    }

                    override fun onActivityResumed(activity: Activity) {

                    }

                    override fun onActivityPaused(activity: Activity) {

                    }

                    override fun onActivityStopped(activity: Activity) {

                    }

                    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

                    }

                    override fun onActivityDestroyed(activity: Activity) {

                    }
                })
    }


    private fun handleActivity(activity: Activity) {
        if (activity is Injectable) {
            activity.inject(application.applicationProvider)
        } else {
            Logger.i("Your activity doesn't injectable %s.\n" +
                    "Implement Injectable interface if you want injectable activity ")
        }
        if (activity is androidx.fragment.app.FragmentActivity) {
            activity.supportFragmentManager
                    .registerFragmentLifecycleCallbacks(fragmentLifecycleCallbacks(), true)
        }
    }

    private fun fragmentLifecycleCallbacks(): androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks {
        return object : androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks() {
            override fun onFragmentPreAttached(fm: androidx.fragment.app.FragmentManager, f: androidx.fragment.app.Fragment, context: Context) {
                if (f is Injectable) {
                    f.inject(application.applicationProvider)
                } else {
                    Logger.i("Your fragment doesn't injectable %s.\n" +
                            "Implement Injectable interface if you want injectable activity ")
                }
            }
        }
    }
}
