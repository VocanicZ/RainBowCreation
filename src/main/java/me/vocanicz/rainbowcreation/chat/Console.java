package me.vocanicz.rainbowcreation.chat;

import me.vocanicz.rainbowcreation.Rainbowcreation;

public class Console {
    public static void info(String string) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        String callingClass = stackTraceElements[2].getClassName();
        String className = callingClass.substring(callingClass.lastIndexOf(".") + 1);
        String callingMethod = stackTraceElements[2].getMethodName();
        Rainbowcreation.getInstance().getLogger().info("(" + className + ") " + "[" + callingMethod + "] " + string);
    }
}
