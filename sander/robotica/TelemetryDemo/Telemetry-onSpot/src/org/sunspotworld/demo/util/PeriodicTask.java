/*
 * Copyright (c) 2007-2009 Sun Microsystems, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

package org.sunspotworld.demo.util;

import com.sun.spot.peripheral.*;
import com.sun.spot.resources.Resource;
import com.sun.spot.util.*;
import com.sun.spot.service.IService;

/**
 * Framework to provide for running a task, such as taking samples, at a regular interval. 
 * Extend the {@link #doTask() doTask} method to specify what actions need to be performed.
 *<p>
 * Uses the AT91_TC Timer/counter for accurate timings provided task period is less than 2 seconds.
 * For task periods greater than 2 seconds the regular Thread.sleep() is used.
 *<p>
 * Note: for more details on the AT91_TC Timer/counter please refer to the 
 * Timer/Counter application note in the AppNotes folder.
 *<p>
 * Implements the com.sun.spot.service.IService interface.
 *
 * @author Ron Goldman<br>
 * Date: January 10, 2007
 *
 * @see com.sun.spot.service.IService
 * @see com.sun.spot.peripheral.IAT91_TC
 */
public abstract class PeriodicTask extends Resource implements IService {
    
    private static final int DEFAULT_PERIOD = 10;
    private static final double count_per_msec[] = { 7488, 1872, 468, 32.8 };
    private static final int max_duration[] = { 8, 35, 140, 2000 };
    private static final int clock[] = { TimerCounterBits.TC_CLKS_MCK8, 
                                         TimerCounterBits.TC_CLKS_MCK32,
                                         TimerCounterBits.TC_CLKS_MCK128,
                                         TimerCounterBits.TC_CLKS_SLCK };
    
    protected int taskPeriod;      // milliseconds between tasks
    protected int taskPeriodCount;
    protected int shiftTask = 0;
    protected int clk_index;
    protected int status = STOPPED;
    protected Thread thread = null;
    private String name = "Periodic Task Execution";
    protected int priority = Thread.NORM_PRIORITY;
    protected IAT91_TC timer = null;
    
    /**
     * Creates a new instance of PeriodicTask.
     * 
     * @param index specifies which timer to use (0-3)
     */
    public PeriodicTask(int index) {
        initialize(index, DEFAULT_PERIOD);
    }

    /**
     * Creates a new instance of PeriodicTask.
     * 
     * @param index specifies which timer to use (0-3) or -1 if just using Thread.sleep()
     * @param period in milliseconds between tasks
     */
    public PeriodicTask(int index, int period) {
        initialize(index, period);
    }

    /**
     * Creates a new instance of PeriodicTask.
     * 
     * @param index specifies which timer to use (0-3) or -1 if just using Thread.sleep()
     * @param period in milliseconds between tasks
     * @param priority the priority for the task loop thread
     */
    public PeriodicTask(int index, int period, int priority) {
        this.priority = priority;
        initialize(index, period);
    }

    private void initialize(int index, int period){
        if (index != -1) {
            if (index < 0 || index > 3) {
                throw new IllegalArgumentException("Timer index must be 0-3.");
            }
            try {
                timer = Spot.getInstance().getAT91_TC(index);
            } catch (SpotFatalException ex) {
                index = -1;     // probably running in Emulator -- just use Thread.sleep() 
            }
        }
        setTaskPeriod(period);
    }
    
    /**
     * Called once per task period to perform measurements.
     * Must be defined in classes that extend PeriodicTask.
     */
    abstract public void doTask();
    
    /**
     * Set the priority for the task loop thread.
     * 
     * @param priority the priority for the task loop thread
     */
    public void setTaskPriority(int priority) {
        this.priority = priority;
        if (isRunning()) {
            thread.setPriority(priority);
        }
    }
    
    /**
     * Return the current doTask period in milliseconds.
     * 
     * @return the doTask period in milliseconds
     */
    public int getTaskPeriod() {
        return taskPeriod;
    }
    
    /**
     * Return the current doTask period in clock cycles.
     * 
     * @return the doTask period in clock cycles
     */
    public int getTaskPeriodCount() {
        return taskPeriodCount;
    }

    /**
     * Set the current doTask period. 
     * Note: the task must not be currently running!
     * 
     * @param period the doTask period in milliseconds
     */
    public void setTaskPeriod(int period) {
        if (period <= 0) {
            throw new IllegalArgumentException("Task period must be greater than zero.");
        }
        if (isRunning()) {
            throw new IllegalStateException("Cannot change the task period for a running task loop.");
        }
        taskPeriod = period;
        taskPeriodCount = 0;
        clk_index = -1;
        if (timer != null) {
            for (int i = 0; i < clock.length; i++) {    // use fastest possible clock
                if (taskPeriod <= max_duration[i]) {
                    clk_index = i;
                    taskPeriodCount = (int)(taskPeriod * count_per_msec[clk_index]);
                    break;
                }
            }
        }
    }

    /**
     * Routine called when task execution is about to start up.
     */
    public void starting() {
        // override in child class if needed
    }
    
    /**
     * Routine called when task execution is finished.
     */
    public void stopping() {
        // override in child class if needed
    }

    /**
     * Shift when the task gets run by N milliseconds.
     * Sleep one time so task loop start is shifted.
     * Does nothing if the task loop is not running.
     *
     * @param shift number of milliseconds to shift by 
     */
    public void shiftStart(int shift) {
        if (isRunning()) {
            shiftTask = shift;
        }
    }
    
    /**
     * Return the current counter value.
     *
     * @return the current counter value
     */
    public int getCounter() {
        if (clk_index >= 0 && timer != null) { 
            return (timer.counter() & 0xffff);
        } else {
            return 0;
        }
    }
    
    /**
     * Basic task loop. This should not be called explicitly.
     */
    private void runTask() {
        shiftTask = 0;
        starting();
        long sleep_til = 0;
        if (clk_index >= 0 && timer != null) { 
            timer.disable();
            timer.configure(TimerCounterBits.TC_CAPT | TimerCounterBits.TC_CPCTRG | clock[clk_index]);
            timer.setRegC(taskPeriodCount);
            timer.enableAndReset();
            timer.status();
        } else {
            sleep_til = System.currentTimeMillis() + taskPeriod;
        }
        status = RUNNING;

        while (status == RUNNING && thread == Thread.currentThread()) {
            if (clk_index >= 0) {
                timer.enableIrq(TimerCounterBits.TC_CPCS);  // Enable RC Compare interrupt
                timer.waitForIrq();
                int status = timer.status();          // Clear interrupt pending flag
                if (shiftTask > 0) {                  // Need to shift when loop starts
                    timer.disable();
                    Utils.sleep(shiftTask);           // Delay extra bit as requested
                    shiftTask = 0;
                    timer.enableAndReset();
                    timer.status();
                }
            } else {
                long sleep_for = sleep_til - System.currentTimeMillis();
                if (sleep_for > 0) {
                    Utils.sleep(sleep_for);
                }
                if (shiftTask > 0) {                  // Need to shift when loop starts
                    Utils.sleep(shiftTask);           // Delay extra bit as requested
                    sleep_til += shiftTask;
                    shiftTask = 0;
                }
                sleep_til += taskPeriod;
            }
            
            doTask();               // Perform periodic task defined in subclass
            
        }
        if (clk_index >= 0) {
            timer.disable();
        }
        stopping();
        status = STOPPED;
    }


    ////////////////////////////////
    //
    // IService defined methods
    //
    ////////////////////////////////

    /**
     * Stop future task execution.
     *
     * @return true if will stop running
     */
    public boolean stop() {
        if (status != STOPPED) {
            status = STOPPING;
            shiftTask = 0;
            if (clk_index < 0) {
                thread.interrupt();
            }
        }
        return true;
    }

    /**
     * Start periodic task execution.
     *
     * @return true if will start running
     */
    public boolean start() {
        if (status == STOPPED || status == STOPPING) {
            status = STARTING;
            thread = new Thread() {
                public void run() {
                    runTask();
                }
            };
            thread.setPriority(priority);
            thread.start();
            Thread.yield();
        }
        return true;
    }

    /**
     * Pause the service, and return whether successful.
     *
     * Since there is no particular state associated with this service
     * then pause() can be implemented by calling stop().
     *
     * @return true if the service was successfully paused
     */
    public boolean pause() {
        return stop();
    }

    /**
     * Resume the service, and return whether successful.
     *
     * Since there was no particular state associated with this service
     * then resume() can be implemented by calling start().
     *
     * @return true if the service was successfully resumed
     */
    public boolean resume() {
        return start();
    }

    /**
     * Return service name
     *
     * @return the name of this service
     */
    public String getServiceName() {
        return name;
    }

    /**
     * Assign a name to this service.
     *
     * @param who the new name for this service
     */
    public void setServiceName(String who) {
        if (who != null) {
            name = who;
        }
    }

    /**
     * Return if currently running.
     *
     * @return true if currently running
     */
    public boolean isRunning() {
        return status == RUNNING;
    }

    /**
     * Return current task execution status.
     *
     * @return current task execution status: STOPPED, STARTING, RUNNING, or STOPPING.
     */
    public int getStatus() {
        return status;
    }

    /**
     * Return whether service is started automatically on reboot.
     *
     * @return false as this service is never started automatically on reboot
     */
    public boolean getEnabled() {
        return false;
    }

    /**
     * Enable/disable whether service is started automatically. Noop for task execution.
     *
     * @param enable ignored
     */
    public void setEnabled(boolean enable) {
        // ignore
    }    
    
}
