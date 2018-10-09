package com.marcosavard.commons.util;

/**
 * 
 * The ProgressMonitor interface is implemented by objects that monitor the progress 
 * of an activity; the methods in this interface are invoked by code that performs 
 * the activity.
 * 
 * All activity is broken down into a linear sequence of tasks against which progress 
 * is reported. When a task begins, a beginTask(String, int) notification is reported, 
 * followed by any number and mixture of progress reports (worked()) and subtask 
 * notifications (subTask(String)). When the task is eventually completed, a done() 
 * notification is reported. After the done() notification, the progress monitor 
 * cannot be reused; i.e., beginTask(String, int) cannot be called again after the 
 * call to done().
 * 
 * A request to cancel an operation can be signaled using the setCanceled method. 
 * Operations taking a progress monitor are expected to poll the monitor 
 * (using isCanceled) periodically and abort at their earliest convenience. 
 * Operation can however choose to ignore cancelation requests.
 * 
 * Based on org.eclipse.core.runtime.IProgressMonitor. 
 *
 */
public interface ProgressMonitor {

	/**
	 * Notifies that the main task is beginning.
	 * 
	 * @param task name
	 * @param totalWork a value representing the total work
	 */
	public void beginTask(String task, int totalWork);
	
	/**
	 * Notifies that a given number of work unit of the main task has been completed.
	 * 
	 * @param work completed
	 */
	public void worked(int work);
	
	/**
	 * Cancel the running task.
	 */
	public void cancel();
	
	/**
	 * Returns whether cancellation of current operation has been requested.
	 * 
	 * @return true if is canceled
	 */
	public boolean isCanceled();
	
	/**
	 * Notifies that the work is done; that is, either the main task is completed or the 
	 * user canceled it.
	 */
	public void done();
	
	/**
	 * Get the time when beginTask() has been called.
	 * 
	 * @return the begin time in milliseconds.
	 */
	public long getStartTime();
}
