package command;

import info.RegForm;
import info.RegUsers;
import info.Result;
import info.Operation;

public class PayOperation implements Operation {
    private final String id;
    private final int cost;

    public PayOperation(String id, int cost) {
        this.id = id;
        this.cost = cost;
    }

    @Override
    public Result execute(RegUsers users) {
        RegForm form = users.getByID(this.id);
        if (form != null) {
            boolean res = form.payment(cost);
            if (res) {
                return new OperationResult(true, "Payment OK.");
            } else {
                return new OperationResult(false, "Low money.");
            }
        } else {
            return new OperationResult(false, "User was not found (" + this.id + ")");
        }
    }

    @Override
    public String toString() {
        return "PayOperation[id='" + id + "', cost=" + cost + "]";
    }
}