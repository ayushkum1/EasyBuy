package com.example.vineet.easybuy.Common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.vineet.easybuy.Model.Users;

public class Common {
    public static Users currentUser;

    public static boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null){
            NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
            if (networkInfos !=null){
                for (int i =0; i<networkInfos.length; i++){
                    if (networkInfos[i].getState() == NetworkInfo.State.CONNECTED)
                        return true;
                }
            }

        }

        return false;
    }
}
