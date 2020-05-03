package com.company;

import java.util.concurrent.BlockingQueue;

public class Robot implements Runnable {
    private Subject subject;
    private BlockingQueue<Student> students;
    private static int tasksAmount = 0;

    public Robot(Subject subject, BlockingQueue<Student> students) {
        this.subject = subject;
        this.students = students;
    }

    @Override
    public void run() {
        try {
            while (!Hydrac.GENERATION_FINISHED || !students.isEmpty()) {
                final Student student = takeStudent(students, subject);

                if (student != null) {
                    final var tasks = student.getTasks();

                    for (var task : tasks) {
                        System.out.println(String.format("%1$d task %2$s is completed.", ++tasksAmount, subject.name()));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private synchronized static Student takeStudent(BlockingQueue<Student> students, Subject subject) throws InterruptedException {
        if (students.isEmpty()) {
            return null;
        }

        if (students.peek().getSubject().equals(subject)) {
            return students.take();
        }

        return null;
    }
}
