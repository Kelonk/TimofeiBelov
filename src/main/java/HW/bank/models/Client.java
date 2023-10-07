package HW.bank.models;

import HW.bank.enums.Currency;

import java.util.HashMap;

public class Client {
    private String name;
    private String surname;
    private String phoneNumber;

    private Bank usersBank;

    private HashMap<Currency, Float> accounts;

    public Client(String name, String surname, String phoneNumber, Bank usersBank, HashMap<Currency, Float> accounts) {
        if (usersBank == null || name == null || surname == null || phoneNumber == null){
            throw new RuntimeException("Client has not full information");
        }
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.usersBank = usersBank;
        this.accounts = accounts != null ? accounts : new HashMap<>();
        initBankStatus();
    }

    private void initBankStatus(){
        if (usersBank == null) { throw new RuntimeException("Can't initialise bank-user relation"); }
        usersBank.addClient(this);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void createAccount(Currency currency, float value){
        accounts.put(currency, value + getAccountValue(currency));
        /*
        if (accounts.containsKey(currency)){
            accounts.replace(currency, value + getAccountValue(currency));
        }
        */
    }

    public float getAccountValue(Currency currency){
        if (currency == null) { throw new RuntimeException("Tried to get null currency"); }
        return accounts.getOrDefault(currency, 0f);
    }

    public HashMap<Currency, Float> getAccounts() {
        return accounts;
    }

    public String clientInformation(){
        return String.format("Client with phone number: %s%nHas following accounts:%n%s",
                this.phoneNumber, Bank.prettyPrint(accounts));
    }
}
