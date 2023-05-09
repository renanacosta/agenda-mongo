package dev.renan.tbd.contacts;

import java.net.UnknownHostException;
import java.util.List;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

/**
 * Renan Acosta
 */
public class Main {
    public static void main(String[] args) throws UnknownHostException {
        final var uri = new MongoClientURI("mongodb://localhost:27017");
        final var db = "contacts";

        try (final var mongo = new MongoClient(uri)) {
            final var repo = new ContactRepository(mongo, db);

            final var addr1 = new Address(
                    "Rio Grande",
                    "Rua 24 de Maio",
                    "Centro",
                    "1350",
                    "Casa");

            final var addr2 = new Address(
                    "Pelotas",
                    "Rua Dom Pedro II, 1342",
                    "Fragata",
                    "1342",
                    "Apto 101");

            final var addr3 = new Address(
                    "São José do Norte",
                    "Rua Marechal Floriano Peixoto",
                    "bairro 3",
                    "12",
                    null);
            final var num1 = new PhoneNumber(
                    "numero 1",
                    "91000000");

            final var num2 = new PhoneNumber(
                    "numero 2",
                    "92000000");

            final var num3 = new PhoneNumber(
                    "numero 2",
                    "8400000");

            final var contact1 = new Contact("contato 1", addr1, List.of(num1, num2));
            final var contact2 = new Contact("contato 2", addr2, List.of(num2));
            final var contact3 = new Contact("contato 3", addr3, List.of(num3));

            System.out.println("==> ADD CONTATOS <==");
            repo.save(contact1);
            repo.save(contact2);
            repo.save(contact3);

            System.out.println("=> CONTATOS:");
            for (final var contact : repo.find()) {
                System.out.println(" > Contato " + contact);
            }

            System.out.println("=> CONTATOS COM MAIS DE UM TELEFONE:");
            for (final var found : repo.findWithMultiplePhones()) {
                System.out.println(" > Encontrado " + found);
            }

            System.out.println("=> CONTATOS POR CIDADE:");
            for (final var entry : repo.groupByCity().entrySet()) {
                final var city = entry.getKey();
                System.out.println(" > Cidade: " + city + ":");

                for (final var found : entry.getValue()) {
                    System.out.println(" > Encontrado: " + found);
                }
            }

            System.out.println("=> ATUALIZANDO contato 1:");
            contact1.setName("Cibele");
            repo.save(contact1);

            System.out.println("=> CONTATOS:");
            for (final var contact : repo.find()) {
                System.out.println(" > Contato " + contact);
            }

            System.out.println("=> DELETANDO CONTATOS:");
            repo.delete(contact1.getId());
            repo.delete(contact2.getId());
            repo.delete(contact3.getId());

            System.out.println("=> CONTATOS:");
            for (final var contact : repo.find()) {
                System.out.println(" > Contato " + contact);
            }
        }
    }
}
