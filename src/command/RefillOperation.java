package command;

import info.RegForm;
import info.RegUsers;
import info.Result;
import info.Operation;

public class RefillOperation implements Operation {
    private final String id;
    private final int amount;

    public RefillOperation(String id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    @Override
    public Result execute(RegUsers users) {
        RegForm form = users.getByID(this.id);
        if (form != null) {
            form.refill(amount);
            return new OperationResult(true, "Refill OK.");
        } else {
            return new OperationResult(false, "User not found: " + this.id);
        }
    }
    @Override
    public String toString() {
        return "RefillOperation[id='" + id + "', amount=" + amount + "]";
    }
}