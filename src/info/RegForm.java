package info;

public interface RegForm {
    String idNumber();
    User user();
    String organisation();
    int money();
    void refill(int amount);
    boolean payment(int amount);

    String toPrintString();
}
