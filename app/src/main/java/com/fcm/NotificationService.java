package com.fcm;

/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.lotus.ui.MyApplication;
import com.utilities.Utils;


public class NotificationService extends FirebaseInstanceIdService {


    private void printLog (String msg) {
        if (Utils.isDebugBuild(this))
            Log.e("NotificationService", msg);
    }


    @Override
    public void onTokenRefresh () {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        NotificationPrefs.getInstance(getApplicationContext()).saveNotificationToken(refreshedToken);
        printLog("Notification Token : " + refreshedToken);
    }

    public static void generateLatestToken () {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        NotificationPrefs.getInstance(MyApplication.getInstance()).saveNotificationToken(refreshedToken);
    }


}
