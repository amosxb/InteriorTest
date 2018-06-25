package com.administrator.ysinteriortest.model;

/**
 * Created by Administrator on 2016/5/26.
 */
public class ClassMsg {
    private String schoolName;
    private String className;
    private String studentNum;
    private String teacherName;
    private String teacherPC;

    public ClassMsg(String schoolName, String className, String studentNum, String teacherName, String teacherPC) {
        this.schoolName = schoolName;
        this.className = className;
        this.studentNum = studentNum;
        this.teacherName = teacherName;
        this.teacherPC = teacherPC;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(String studentNum) {
        this.studentNum = studentNum;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherPC() {
        return teacherPC;
    }

    public void setTeacherPC(String teacherPC) {
        this.teacherPC = teacherPC;
    }

    @Override
    public String toString() {
        return "ClassMsg{" +
                "schoolName='" + schoolName + '\'' +
                ", className='" + className + '\'' +
                ", studentNum='" + studentNum + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", teacherPC='" + teacherPC + '\'' +
                '}';
    }
}
