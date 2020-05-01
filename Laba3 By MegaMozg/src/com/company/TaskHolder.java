package com.company;

import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class TaskHolder {
    private HashMap<Subject, BlockingQueue<String>> tasks;
    private volatile boolean works;

    public TaskHolder() {
        works = true;
        tasks = new HashMap<>();
        setUpTasks();
    }

    public BlockingQueue<String> getTasks(Subject subject) {
        return tasks.get(subject);
    }

    public void putTaskOf(String task, Subject subject) throws InterruptedException {
        tasks.get(subject).put(task);
    }

    public boolean isWorks() {
        return works;
    }

    public synchronized void stopTaskHolder() {
        works = false;
    }

    private void setUpTasks() {
        for (var subj : Subject.values()) {
            tasks.put(subj, new ArrayBlockingQueue<>(5));
        }
    }
}