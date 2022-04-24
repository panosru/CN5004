package com.cn5004ap.payroll.common;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class Multiton
{
    private static final ConcurrentMap<String, ISingleton> instances = new ConcurrentHashMap<>();

    private Multiton()
    { }

    public static <T extends ISingleton> T getInstance(@NotNull Class<T> clazz)
    {
        if (!instances.containsKey(clazz.getName()))
            instances.computeIfAbsent(clazz.getName(), Multiton::createInstance);

        return (T) instances.get(clazz.getName());
    }

    private static @Nullable ISingleton createInstance(String s)
    {
        try
        {
            Class<?> clazz = Class.forName(s);
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            return (ISingleton) constructor.newInstance();
        }
        catch (ClassNotFoundException | NoSuchMethodException e)
        {
            e.printStackTrace();
        }
        catch (InvocationTargetException | InstantiationException | IllegalAccessException e)
        {
            throw new RuntimeException(e);
        }

        return null;
    }
}
