package com.cdodge.remindPharm.Models;

import javax.persistence.*;

@Entity
@Table(name = "medications")
public class Medication {

    @Id
    @GeneratedValue
    private int medicationID;

    @Column
    private String name;

    @Column
    private DeliveryMethod deliveryMethod;

    @Column
    private Measure measure;

    @Column
    private int measureAmount;

    @Column
    private Schedule schedule;

    @Column
    private boolean shouldBeTakenWithFood;

    @Column
    private String description;

    public Medication() {
    }

    public Medication(String name, DeliveryMethod deliveryMethod, Measure measure, int measureAmount, Schedule schedule, boolean shouldBeTakenWithFood, String description) {
        this.name = name;
        this.deliveryMethod = deliveryMethod;
        this.measure = measure;
        this.measureAmount = measureAmount;
        this.schedule = schedule;
        this.shouldBeTakenWithFood = shouldBeTakenWithFood;
        this.description = description;
    }

    public int getMedicationID() {
        return medicationID;
    }

    public void setMedicationID(int medicationID) {
        this.medicationID = medicationID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DeliveryMethod getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(DeliveryMethod deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public Measure getMeasure() {
        return measure;
    }

    public void setMeasure(Measure measure) {
        this.measure = measure;
    }

    public int getMeasureAmount() {
        return measureAmount;
    }

    public void setMeasureAmount(int measureAmount) {
        this.measureAmount = measureAmount;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public boolean isShouldBeTakenWithFood() {
        return shouldBeTakenWithFood;
    }

    public void setShouldBeTakenWithFood(boolean shouldBeTakenWithFood) {
        this.shouldBeTakenWithFood = shouldBeTakenWithFood;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Medication that = (Medication) o;

        return medicationID == that.medicationID;
    }

    @Override
    public int hashCode() {
        return medicationID;
    }

    @Override
    public String toString() {
        return "Medication{" +
                "medicationID=" + medicationID +
                ", name='" + name + '\'' +
                ", deliveryMethod=" + deliveryMethod +
                ", measure=" + measure +
                ", measureAmount=" + measureAmount +
                ", schedule=" + schedule +
                ", shouldBeTakenWithFood=" + shouldBeTakenWithFood +
                ", description='" + description + '\'' +
                '}';
    }
}
