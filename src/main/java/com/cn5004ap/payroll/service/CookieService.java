package com.cn5004ap.payroll.service;

import com.cn5004ap.payroll.App;
import com.cn5004ap.payroll.common.ISingleton;

import java.util.prefs.Preferences;

public final class CookieService
    implements ISingleton
{
    private final Preferences store;

    private CookieService()
    {
        // Prevent instantiation
        store = Preferences.userRoot().node(App.class.getName());
    }

    public Preferences getStore()
    {
        return store;
    }
}
