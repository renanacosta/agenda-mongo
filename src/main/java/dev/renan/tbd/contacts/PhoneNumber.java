package dev.renan.tbd.contacts;

public class PhoneNumber {
    private String label;
    private String number;

    public PhoneNumber(String label, String number) {
        this.label = label;
        this.number = number;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "PhoneNumber [label=" + label + ", number=" + number + "]";
    }
}
