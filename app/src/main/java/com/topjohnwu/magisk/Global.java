package com.topjohnwu.magisk;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.topjohnwu.magisk.module.Module;
import com.topjohnwu.magisk.module.Repo;
import com.topjohnwu.magisk.utils.CallbackHandler;
import com.topjohnwu.magisk.utils.Shell;
import com.topjohnwu.magisk.utils.Utils;
import com.topjohnwu.magisk.utils.ValueSortedMap;

import java.util.List;

public class Global {

    public static class Constant {
        // No global constants now
    }
    public static class Info {
        public static double magiskVersion;
        public static String magiskVersionString = "(none)";
        public static double remoteMagiskVersion = -1;
        public static String magiskLink;
        public static String releaseNoteLink;
        public static int SNCheckResult = -1;
        public static String bootBlock = null;
    }
    public static class Data {
        public static ValueSortedMap<String, Repo> repoMap = new ValueSortedMap<>();
        public static ValueSortedMap<String, Module> moduleMap = new ValueSortedMap<>();
        public static List<String> blockList;
    }
    public static class Events {
        public static final CallbackHandler.Event blockDetectionDone = new CallbackHandler.Event();
        public static final CallbackHandler.Event packageLoadDone = new CallbackHandler.Event();
        public static final CallbackHandler.Event reloadMainActivity = new CallbackHandler.Event();
        public static final CallbackHandler.Event moduleLoadDone = new CallbackHandler.Event();
        public static final CallbackHandler.Event repoLoadDone = new CallbackHandler.Event();
        public static final CallbackHandler.Event updateCheckDone = new CallbackHandler.Event();
        public static final CallbackHandler.Event safetyNetDone = new CallbackHandler.Event();
    }
    public static class Configs {
        public static boolean isDarkTheme;
        public static boolean shellLogging;
        public static boolean devLogging;
        public static int suRequestTimeout = 10;
        public static int suLogTimeout = 14;

    }

    public static void init(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Configs.isDarkTheme = prefs.getBoolean("dark_theme", false);
        Configs.devLogging = prefs.getBoolean("developer_logging", false);
        Configs.shellLogging = prefs.getBoolean("shell_logging", false);
        updateMagiskInfo();
    }

    static void updateMagiskInfo() {
        List<String> ret = Shell.sh("getprop magisk.version");
        if (!Utils.isValidShellResponse(ret)) {
            Info.magiskVersion = -1;
        } else {
            try {
                Info.magiskVersionString = ret.get(0);
                Info.magiskVersion = Double.parseDouble(ret.get(0));
            } catch (NumberFormatException e) {
                // Custom version don't need to receive updates
                Info.magiskVersion = Double.POSITIVE_INFINITY;
            }
        }
    }

}
