package com.wx.xybb.entity;

import java.io.Serializable;
import java.util.Date;

public class WxSchoolTimetable implements Serializable {
    private Long id;

    private String timetable;

    private String name;

    private String studentId;

    private Integer rubLesson;

    private Date time;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTimetable() {
        return timetable;
    }

    public void setTimetable(String timetable) {
        this.timetable = timetable == null ? null : timetable.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId == null ? null : studentId.trim();
    }

    public Integer getRubLesson() {
        return rubLesson;
    }

    public void setRubLesson(Integer rubLesson) {
        this.rubLesson = rubLesson;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", timetable=").append(timetable);
        sb.append(", name=").append(name);
        sb.append(", studentId=").append(studentId);
        sb.append(", rubLesson=").append(rubLesson);
        sb.append(", time=").append(time);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}