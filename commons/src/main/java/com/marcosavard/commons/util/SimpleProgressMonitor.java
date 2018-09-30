package com.marcosavard.commons.util;

/**
 * A simple implementation of ProgressMonitor
 * 
 * @author Marco
 */
public class SimpleProgressMonitor implements ProgressMonitor {
	private String taskName;
	private int totalWork;
	private boolean canceled = false;
	private long startTime;

	@Override
	public void beginTask(String name, int totalWork) {
		this.taskName = name;
		this.totalWork = totalWork;
		this.startTime = System.currentTimeMillis();
	}
	
	@Override
	public long getStartTime() {
		return startTime;
	}

	@Override
	public void worked(int work) {
	}
	
	@Override
	public void cancel() {
		canceled = true;
	}
	
	@Override
	public void done() {
	}

	@Override
	public boolean isCanceled() {
		return canceled;
	}
	
	protected String getTaskName() {
		return this.taskName;
	}
	
	protected int getTotalWork() {
		return this.totalWork;
	}
}
