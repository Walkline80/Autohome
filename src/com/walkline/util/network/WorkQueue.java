package com.walkline.util.network;

import java.util.Vector;

import com.walkline.util.Function;

public class WorkQueue
{
    private final int nThreads;
    private final PoolWorker[] threads;
    private final Vector queue;

    public WorkQueue(int nThreads)
    {
        this.nThreads = nThreads;
        queue = new Vector();
        threads = new PoolWorker[nThreads];

        for (int i=0; i<this.nThreads; i++) {
            threads[i] = new PoolWorker();
            threads[i].start();
        }
    }

    public void execute(Runnable r)
    {
        synchronized(queue)
        {
            queue.addElement(r);
            queue.notify();
        }
    }

    public void removeAll()
    {
    	synchronized (queue)
    	{
			queue.removeAllElements();
		}
    }

    private class PoolWorker extends Thread
    {
        public void run()
        {
            Runnable r;

            while (true)
            {
                synchronized(queue)
                {
                    while (queue.isEmpty())
                    {
                        try
                        {
                            queue.wait();
                        }  catch (InterruptedException ignored) {}
                    }

                    r = (Runnable) queue.elementAt(0);
                    queue.removeElementAt(0);
                }

                try {
                    r.run();
                } catch (RuntimeException e) {Function.errorDialog(e.toString());}
            }
        }
    }
}