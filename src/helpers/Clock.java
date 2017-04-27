package helpers;

import org.lwjgl.Sys;

public class Clock {
    private static boolean paused = false;
    private static long lastFrame, totalTime;
    private static float d = 0, multiplier = 1;

    public static long getTime() {
        return Sys.getTime() * 1000 / Sys.getTimerResolution();
    }

    public static float getDelta() {
        long currentTime = getTime();
        int delta = (int) (currentTime - lastFrame);
        lastFrame = getTime();
        if (delta * 0.001f > 0.05f)
            return 0.05f;
        return delta * 0.001f;
    }

    public static float Delta() {
        return paused ? 0 : d * multiplier;
    }

    public static void update() {
        d = getDelta();
        totalTime += d;
    }

    public static void Pause() {
        paused = !paused;
    }

}