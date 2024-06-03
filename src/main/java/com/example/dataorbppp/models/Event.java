package com.example.dataorbppp.models;
import java.util.Date;

public class Event {
    private int sequenceNo;
    private String empId;
    private String eventType;
    private String value;
    private Date eventDate;
    private String notes;

    public Event(int sequenceNo, String empId, String eventType, String value, Date eventDate, String notes) {
        this.sequenceNo = sequenceNo;
        this.empId = empId;
        this.eventType = eventType;
        this.value = value;
        this.eventDate = eventDate;
        this.notes = notes;
    }


    public int getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(int sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Event{" +
                "sequenceNo=" + sequenceNo +
                ", empId='" + empId + '\'' +
                ", eventType='" + eventType + '\'' +
                ", value='" + value + '\'' +
                ", eventDate=" + eventDate +
                ", notes='" + notes + '\'' +
                '}';
    }
}
