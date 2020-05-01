package com.company;

import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProcessorStudent {
    private volatile int processedStudents;

    private TaskHolder taskHolder;

    private final Object lock = new Object();

    private BlockingQueue<Student> students;

    private HashMap<Subject, BlockingQueue<Student>> separetedStudents;

    public ProcessorStudent(BlockingQueue<Student> students, TaskHolder taskHolder) {
        separetedStudents = new HashMap<>();

        this.taskHolder = taskHolder;
        this.students = students;
        this.processedStudents = 0;

        setUpSeparetedStudents();
    }

    public void process() {
        separeteStudents();
        processTasks();
    }

    private void separeteStudents() {
        new Thread(() -> {
            try {
                while (taskHolder.isWorks() || !students.isEmpty()) {
                    if (!students.isEmpty()) {
                        var student = students.take();
                        var subj = student.getSubject();

                        separetedStudents.get(subj).put(student);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void processTasks() {
        for (var subj : Subject.values()) {
            new Thread(() -> {
                try {
                    var subjectStudents = separetedStudents.get(subj);
                    while (taskHolder.isWorks() || !subjectStudents.isEmpty()) {
                        if (!subjectStudents.isEmpty()) {
                            var student = subjectStudents.take();
                            takeTasksBy(student);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    private void takeTasksBy(Student student) throws InterruptedException {
        var tasks = student.getTasks();
        var size = tasks.size();
        var subj = student.getSubject();

        for (int i = 0; i < size; i++) {
            taskHolder.putTaskOf(tasks.poll(), subj);
        }

        synchronized (lock) {
            processedStudents++;
            if (processedStudents == Hydrac.STUDENT_AMOUNT) {
                taskHolder.stopTaskHolder();
            }
        }
    }

    private void setUpSeparetedStudents() {
        for (var subj : Subject.values()) {
            separetedStudents.put(subj, new ArrayBlockingQueue<>(5));
        }
    }
}
