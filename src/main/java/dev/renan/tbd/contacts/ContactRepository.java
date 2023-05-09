package dev.renan.tbd.contacts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.ReplaceOptions;

public class ContactRepository {
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public ContactRepository(MongoClient client, String dbName) {
        this.database = client.getDatabase(dbName);
        this.collection = database.getCollection("contacts");
    }

    public void save(Contact contact) {
        final var filter = new Document("_id", contact.getId());
        final var document = asDocument(contact);
        final var options = new ReplaceOptions().upsert(true);
        collection.replaceOne(filter, document, options);
    }

    public List<Contact> find() {
        final var document = collection.find();
        return fromDocument(document);
    }

    public Contact findById(String id) {
        final var filter = new Document("_id", id);
        final var document = collection.find(filter).first();
        return contactFromDocument(document);
    }

    public List<Contact> findByName(String name) {
        final var filter = new Document("name", name);
        final var iterable = collection.find(filter);
        return fromDocument(iterable);
    }

    public List<Contact> findWithMultiplePhones() {
        final var filter = new Document("number_count", new Document("$gt", 1));
        final var iterable = collection.find(filter);
        return fromDocument(iterable);
    }

    public Map<String, List<Contact>> groupByCity() {
        final var query = List.of(new Document("$group",
                new Document("_id", "$address.city").append("contacts", new Document("$push", "$$ROOT"))));
        final var iterable = collection.aggregate(query);

        final var map = new HashMap<String, List<Contact>>();

        for (final var entry : iterable) {
            final var city = entry.getString("_id");
            final var contacts = entry.getList("contacts", Document.class).stream().map(this::contactFromDocument)
                    .toList();

            map.put(city, contacts);
        }

        return map;
    }

    public void delete(String id) {
        final var filter = new Document("_id", id);
        collection.deleteOne(filter);
    }

    private Document asDocument(Address address) {
        return new Document("city", address.getCity()).append("street", address.getStreet())
                .append("neighbourhood", address.getNeighbourhood()).append("number", address.getNumber())
                .append("complement", address.getComplement());
    }

    private Document asDocument(PhoneNumber number) {
        return new Document("label", number.getLabel()).append("number", number.getNumber());
    }

    private Document asDocument(Contact contact) {
        final var numbers = contact.getPhoneNumbers().stream().map((this::asDocument)).toList();
        final var numberCount = numbers.size();

        return new Document("_id", contact.getId()).append("name", contact.getName())
                .append("address", asDocument(contact.getAddress())).append("numbers", numbers)
                .append("number_count", numberCount);
    }

    private Address addressFromDocument(Document document) {
        final var city = document.getString("city");
        final var street = document.getString("street");
        final var neighbourhood = document.getString("neighbourhood");
        final var number = document.getString("number");
        final var complement = document.getString("complement");

        return new Address(city, street, neighbourhood, number, complement);
    }

    private PhoneNumber phoneNumberFromDocument(Document document) {
        final var label = document.getString("label");
        final var number = document.getString("number");

        return new PhoneNumber(label, number);
    }

    private Contact contactFromDocument(Document document) {
        final var id = document.getString("_id");
        final var name = document.getString("name");
        final var addressd = document.get("address", Document.class);
        final var phoneNumbersd = document.getList("numbers", Document.class);

        final var address = addressFromDocument(addressd);
        final var phoneNumbers = phoneNumbersd.stream().map(this::phoneNumberFromDocument).toList();

        return new Contact(id, name, address, phoneNumbers);
    }

    private List<Contact> fromDocument(FindIterable<Document> iterable) {
        final var contacts = new ArrayList<Contact>();
        for (final var doc : iterable) {
            contacts.add(contactFromDocument(doc));
        }

        return contacts;
    }
}
