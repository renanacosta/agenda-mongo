package dev.renan.tbd.contacts;

public class Address {
    private String city;
    private String street;
    private String neighbourhood;
    private String number;
    private String complement;

    public Address(String city, String street, String neighbourhood, String number, String complement) {
        this.city = city;
        this.street = street;
        this.neighbourhood = neighbourhood;
        this.number = number;
        this.complement = complement;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNeighbourhood() {
        return neighbourhood;
    }

    public void setNeighbourhood(String neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    @Override
    public String toString() {
        return "Address [city=" + city + ", complement=" + complement + ", neighbourhood=" + neighbourhood + ", number=" + number
                + ", street=" + street + "]";
    }
}
