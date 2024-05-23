package command;

import info.RegForm;
import info.RegUsers;
import info.Result;
import info.Operation;

public class GetBalanceOperation implements Operation {
    private final String id;

    public GetBalanceOperation(String id) {
        this.id = id;
    }

    @Override
    public Result execute(RegUsers users) {
        RegForm form = users.getByID(this.id);
        if (form != null) {
            int balance = form.money();
            String message = "Current balance: " + balance;
            if (balance == 0) {
                message += " (No money on the card)";
            }
            return new OperationResult(true, message);
        } else {
            return new OperationResult(false, "User not found: " + this.id);
        }
    }

    @Override
    public String toString() {
        return "GetBalanceOperation[id='" + id + "']";
    }
}
