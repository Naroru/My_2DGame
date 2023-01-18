package org.OlegChukhlantsev.CommonManagers;

public class ThreadsWaiting {

    public static void wait(Thread thread)
    {
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void wait(int millisec)
    {
        try {
            Thread.sleep(millisec);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
