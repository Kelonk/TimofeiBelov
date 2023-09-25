package model.zoo.enums;

public enum MoveTypes {
    Land("walk"), Water("swim"), Air("fly");

    private final String actionName;

    MoveTypes(String actionName){
        this.actionName = actionName;
    }

    public String GetName(){
        return this.actionName;
    }
}
