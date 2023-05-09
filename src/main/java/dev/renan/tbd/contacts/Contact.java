package dev.renan.tbd.contacts;

import java.util.List;
import java.util.UUID;

public class Contact {
    private String id;
    private String name;
    private Address address;
    private List<PhoneNumber> phoneNumbers;

    public Contact(String id, String name, Address address, List<PhoneNumber> phoneNumbers) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNumbers = phoneNumbers;
    }

    public Contact(String name, Address address, List<PhoneNumber> phoneNumbers) {
        this(UUID.randomUUID().toString(), name, address, phoneNumbers);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    @Override
    public String toString() {
        return "Contact [address=" + address + ", id=" + id + ", name=" + name + ", phoneNumbers=" + phoneNumbers + "]";
    }
}
