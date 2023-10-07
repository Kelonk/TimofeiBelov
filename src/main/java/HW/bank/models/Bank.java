package HW.bank.models;

import HW.bank.enums.Currency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bank {
    private List<Client> clients;

    private String name;

    public Bank(String name, List<Client> clients) {
        this.name = name;
        this.clients = clients != null ? clients : new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addClient(Client client){
        if (client == null) { return; }

        if (getClient(client.getPhoneNumber()) == null){
            clients.add(client);
        } else {
            System.out.println("Client with phone number <" + client.getPhoneNumber() + "> already exists");
        }
    }

    public void deleteClient(Client client){
        if (client == null) { return; }

        if (getClient(client.getPhoneNumber()) != null){
            clients.remove(client);
        } else {
            System.out.println("Client with phone number <" + client.getPhoneNumber() + "> doesn't exist");
        }
    }

    public Client getClient(String phoneNumber){
        for (Client client: clients) {
            if (client.getPhoneNumber() == phoneNumber){
                return client;
            }
        }
        return null;
    }

    public HashMap<Currency, Float> getTotalBalance(){
        HashMap<Currency, Float> balances = new HashMap<>();
        for (Currency currency: Currency.values()) {
            balances.put(currency, 0f);
        }

        for (Client client: clients) {
            client.getAccounts().forEach((currency, value) ->
                    balances.put(currency, value + balances.get(currency)));
        }
        return balances;
    }

    public static String prettyPrint(HashMap<Currency, Float> balances){
        String answer = "";
        for (Map.Entry<Currency, Float> entry :
                balances.entrySet()) {
            answer += String.format("Currency: %s Value: %.1f%n", entry.getKey().getPrintName(), entry.getValue());
        }
        return answer;
    }
}
