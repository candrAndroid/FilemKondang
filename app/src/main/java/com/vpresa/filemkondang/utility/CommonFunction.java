package com.vpresa.filemkondang.utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.vpresa.filemkondang.view.activity.MainActivity;
import com.vpresa.filemkondang.view.activity.SplashActivity;

/**
 * Created by Dwicandra on 9/1/2017.
 */

public class CommonFunction {

    public static void moveActivity(Context from, Class dest, boolean isFinished) {
        moveActivity(from, dest, new Bundle(), isFinished);
    }

    public static void moveActivity(Context from, Class dest, Bundle bundle, boolean isFinished) {
        Intent i = new Intent(from, dest);
        i.putExtras(bundle);
        from.startActivity(i);
        if(isFinished) ((Activity) from).finish();
    }
}
