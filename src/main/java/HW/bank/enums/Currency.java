package HW.bank.enums;

public enum Currency {
    Rubbles("Рубль"),
    Dollars("Доллар"),
    Euros("Евро"),
    Pounds("Фунт");

    private final String printName;

    Currency(String printName) {
        this.printName = printName;
    }

    public String getPrintName() {
        return printName;
    }
}
