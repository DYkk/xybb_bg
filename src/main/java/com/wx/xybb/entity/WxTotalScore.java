package com.wx.xybb.entity;

import java.io.Serializable;

public class WxTotalScore implements Serializable {
    private Long id;

    private String studentId;

    private Float jpa;

    private Integer jpaRank;

    private Integer numberPeople;

    private String grade;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId == null ? null : studentId.trim();
    }

    public Float getJpa() {
        return jpa;
    }

    public void setJpa(Float jpa) {
        this.jpa = jpa;
    }

    public Integer getJpaRank() {
        return jpaRank;
    }

    public void setJpaRank(Integer jpaRank) {
        this.jpaRank = jpaRank;
    }

    public Integer getNumberPeople() {
        return numberPeople;
    }

    public void setNumberPeople(Integer numberPeople) {
        this.numberPeople = numberPeople;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade == null ? null : grade.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", studentId=").append(studentId);
        sb.append(", jpa=").append(jpa);
        sb.append(", jpaRank=").append(jpaRank);
        sb.append(", numberPeople=").append(numberPeople);
        sb.append(", grade=").append(grade);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}