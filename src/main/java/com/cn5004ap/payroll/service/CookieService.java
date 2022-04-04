package com.cn5004ap.payroll.service;

import com.cn5004ap.payroll.App;

import java.util.prefs.Preferences;

public final class CookieService
{
    // Making safe the use of same instance via multiple threads at the same time
    private static volatile CookieService instance;

    private final Preferences store;

    private CookieService()
    {
        // Prevent instantiation
        store = Preferences.userRoot().node(App.class.getName());
    }

    /**
     * Thread-safe singleton implementation
     * @return the instance
     */
    public static CookieService getInstance()
    {
        CookieService localInstance = instance;
        // Thread-safe singleton
        if (null == localInstance)
            synchronized (CookieService.class)
            {
                localInstance = instance;
                if (null == instance)
                    instance = localInstance = new CookieService();
            }

        return localInstance;
    }

    public Preferences getStore()
    {
        return store;
    }
}
