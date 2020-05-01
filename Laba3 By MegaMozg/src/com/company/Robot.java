package com.company;

public class Robot implements Runnable {
    private TaskHolder taskHolder;
    private Subject subject;
    private int tasksAmount;

    public Robot(Subject subject, TaskHolder taskHolder) {
        this.subject = subject;
        this.taskHolder = taskHolder;
        tasksAmount = 0;
    }

    @Override
    public void run() {
        try {
            final var tasks = taskHolder.getTasks(subject);

            while (taskHolder.isWorks() || !tasks.isEmpty()) {
                if (!tasks.isEmpty()) {
                    tasks.take();
                    System.out.println(String.format("%1$d task %2$s is completed.", ++tasksAmount, subject.name()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
