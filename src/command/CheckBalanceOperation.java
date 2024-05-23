package command;

import info.RegForm;
import info.RegUsers;
import info.Result;
import info.Operation;

public class CheckBalanceOperation implements Operation {
    private final String id;

    public CheckBalanceOperation(String id) {
        this.id = id;
    }

    @Override
    public Result execute(RegUsers users) {
        RegForm form = users.getByID(this.id);
        if (form != null) {
            int balance = form.money();
            if (balance > 0) {
                StringBuilder sb = new StringBuilder();
                sb.append(String.format("%-10s %-10s %-10s %-10s %-10s %-10s%n", "ID", "First Name", "Last Name", "Sex", "Birthday", "Balance"));
                sb.append(String.format("%-10s %-10s %-10s %-10s %-10s %-10d%n",
                        form.idNumber(),
                        form.user().firstName(),
                        form.user().lastName(),
                        form.user().sex(),
                        form.user().birthday(),
                        form.money()
                ));
                return new OperationResult(true, sb.toString());
            } else {
                return new OperationResult(false, "Current balance is zero.");
            }
        } else {
            return new OperationResult(false, "User not found: " + this.id);
        }
    }

    @Override
    public String toString() {
        return "CheckBalanceOperation[id='" + id + "']";
    }
}
