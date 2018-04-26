package com.hostelmanager.hostelmaster;

public class BuySellSubject {

    private String department;
    private String semester;
    private String subject_name;

    public BuySellSubject(){}

    public BuySellSubject(String department, String semester, String subject_name){
        this.department = department;
        this.semester = semester;
        this.subject_name = subject_name;
    }

    public void setdepartment(String department){
        this.department = department;
    }

    public void setsemester(String semester){
        this.semester = semester;
    }

    public void setsubject_name(String subject_name){
        this.subject_name = subject_name;
    }

    public String getdepartment(){
        return this.department;
    }

    public String getsemester(){
        return this.semester;
    }

    public String getsubject_name(){
        return this.subject_name;
    }

}

