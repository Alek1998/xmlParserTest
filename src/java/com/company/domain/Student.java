package com.company.domain;

import com.company.exeptions.AgeExeption;

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
    public Student(int id){
        this.id=id;
    }

    public Student(int id, String name, int age, String course) throws  AgeExeption {
        this.id = id;
        this.name = name;
        setAge(age);
        this.course = course;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) throws  AgeExeption {
        if (age <= 0) {
            throw new AgeExeption("The age should be grater than 0.");
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Student) {
            Student studentForEqual = (Student) o;
            return studentForEqual.getId() == this.getId()
                    && studentForEqual.getName().equals(this.getName())
                    && studentForEqual.getAge() == this.getAge()
                    && studentForEqual.getCourse().equals(this.getCourse());
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Student {");
        sb.append("[id] => ").append(id).append(", ")
                .append("[name] => ").append(name).append(", ")
                .append("[age] => ").append(age).append(", ")
                .append("[course] => ").append(course).append("}");
        return sb.toString();
    }
}
