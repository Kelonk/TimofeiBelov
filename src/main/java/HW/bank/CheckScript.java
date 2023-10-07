package HW.bank;

import HW.bank.enums.Currency;
import HW.bank.models.Bank;
import HW.bank.models.Client;

import java.util.HashMap;

public class CheckScript {

    public static void main(String[] args) {
        Bank bank = new Bank("BigBrother", null);
        CheckScript.printBankBalance(bank);
        Client client = new Client("John", "Super", "88005553535", bank, null);
        client.createAccount(Currency.Rubbles, 10000000000000f);
        printClientInfo(client);
        CheckScript.printBankBalance(bank);
        client.createAccount(Currency.Dollars, 1234f);
        client.createAccount(Currency.Rubbles, 9999f);
        printClientInfo(client);
        CheckScript.printBankBalance(bank);

        Client client2 = new Client("Anton", "Fish", "812433244", bank,
                new HashMap<Currency, Float>() {{
            put(Currency.Euros, 666.666f);
            put(Currency.Pounds, 500.5f);
            put(Currency.Rubbles, 1000000f);
        }});
        printClientInfo(client2);

        printBankBalance(bank);
        bank.deleteClient(client);
        printBankBalance(bank);

        printClientInfo(bank, "812433244");
        printClientInfo(bank, "123123");
    }

    public static void printBankBalance(Bank bank){
        if (bank == null) {return;}
        System.out.println("Bank " + bank.getName() + "\n" + Bank.prettyPrint(bank.getTotalBalance()));
    }

    public static void printClientInfo(Client client){
        if (client == null) {return;}
        System.out.println(client.clientInformation());
    }

    public static void printClientInfo(Bank bank, String phoneNumber){
        if (bank == null || phoneNumber == null) {return;}
        Client client = bank.getClient(phoneNumber);
        if (client == null) {
            System.out.printf("Bank %s can't find client with phone number %s%n", bank.getName(), phoneNumber);
        } else {
            CheckScript.printClientInfo(client);
        }
    }
}
