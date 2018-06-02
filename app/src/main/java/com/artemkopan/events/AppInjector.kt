package com.artemkopan.events

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
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
            Logger.i("Your activity doesn't injectable $activity.\n" +
                    "Implement Injectable interface if you want injectable activity ")
        }
        if (activity is FragmentActivity) {
            activity.supportFragmentManager
                    .registerFragmentLifecycleCallbacks(fragmentLifecycleCallbacks(), true)
        }
    }

    private fun fragmentLifecycleCallbacks(): FragmentManager.FragmentLifecycleCallbacks {
        return object : FragmentManager.FragmentLifecycleCallbacks() {
            override fun onFragmentPreAttached(fm: FragmentManager?, f: Fragment?, context: Context?) {
                if (f is Injectable) {
                    f.inject(application.applicationProvider)
                } else {
                    Logger.i("Your fragment doesn't injectable $f.\n" +
                            "Implement Injectable interface if you want injectable activity ")
                }
            }
        }
    }
}
