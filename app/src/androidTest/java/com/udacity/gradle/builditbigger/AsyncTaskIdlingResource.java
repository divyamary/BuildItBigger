package com.udacity.gradle.builditbigger;

import android.app.ActivityManager;
import android.content.Context;
import android.support.test.espresso.IdlingResource;

import java.util.List;

/**
 * Created by divyamary on 30-03-2016.
 */
public class AsyncTaskIdlingResource implements IdlingResource {

    ResourceCallback resourceCallback;
    private Context context;

    public AsyncTaskIdlingResource(Context context) {
        this.context = context;
    }

    @Override
    public String getName() {
        return AsyncTaskIdlingResource.class.getName();
    }

    @Override
    public boolean isIdleNow() {
        boolean idle = !isAsyncTaskRunning();
        if (idle && resourceCallback != null) {
            resourceCallback.onTransitionToIdle();
        }
        return idle;
    }

    private boolean isAsyncTaskRunning() {
        ActivityManager manager =
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        // Get all running services
        List<ActivityManager.RunningServiceInfo> runningServices =
                manager.getRunningServices(Integer.MAX_VALUE);
        // check if our task is running
        for (ActivityManager.RunningServiceInfo info : runningServices) {
            if (JokePullerTask.class.getName().equals(info.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
        this.resourceCallback = resourceCallback;
    }
}
