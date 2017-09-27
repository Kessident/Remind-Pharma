package com.cdodge.remindPharm.Models;

import javax.persistence.Embeddable;

@Embeddable
public class Schedule {

    private Integer timesPerDay;
    private Integer numberOfDays;

    public Schedule() {
    }

    public Schedule(Integer timesPerDay, Integer numberOfDays) {
        this.timesPerDay = timesPerDay;
        this.numberOfDays = numberOfDays;
    }

    public Integer getTimesPerDay() {
        return timesPerDay;
    }

    public void setTimesPerDay(Integer timesPerDay) {
        this.timesPerDay = timesPerDay;
    }

    public Integer getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(Integer numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Schedule schedule = (Schedule) o;

        if (timesPerDay != null ? !timesPerDay.equals(schedule.timesPerDay) : schedule.timesPerDay != null)
            return false;
        return numberOfDays != null ? numberOfDays.equals(schedule.numberOfDays) : schedule.numberOfDays == null;
    }

    @Override
    public int hashCode() {
        int result = timesPerDay != null ? timesPerDay.hashCode() : 0;
        result = 31 * result + (numberOfDays != null ? numberOfDays.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "timesPerDay=" + timesPerDay +
                ", numberOfDays=" + numberOfDays +
                '}';
    }
}
