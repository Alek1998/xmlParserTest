package com.company.domain;

/**
 * Created by Alek on 17.01.2017.
 */
public class Student {
    private int id;
    private String name;
    private int age;
    private String course;

    public void setId(int id) {
        this.id = id;
    }

    public Student() {
    }

    public Student(int id, String name, int age, String course) throws Exception {
        this.id = id;
        this.name = name;
        setAge(age);
        this.course = course;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) throws Exception {
        if (age <= 0) {
            throw new IllegalArgumentException("The age should be grater than 0.");
        }
        this.age = age;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getCourse() {
        return course;
    }

}
