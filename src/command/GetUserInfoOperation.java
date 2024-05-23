package command;

import info.RegForm;
import info.RegUsers;
import info.Result;
import info.Operation;

import java.util.Collections;

public class GetUserInfoOperation implements Operation {
    private final String id;

    public GetUserInfoOperation(String id) {
        this.id = id;
    }

    @Override
    public Result execute(RegUsers users) {
        RegForm form = users.getByID(this.id);
        if (form != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(String.join("", Collections.nCopies(110, "-"))).append("\n");
            String headerFormat = "| %-10s | %-15s | %-15s | %-6s | %-12s | %-10s | %-20s |%n";
            String rowFormat = "| %-10s | %-15s | %-15s | %-6s | %-12s | %-10d | %-20s |%n";

            sb.append(String.format(headerFormat, "ID", "First Name", "Last Name", "Sex", "Birthday", "Balance", "Organization"));
            sb.append(String.join("", Collections.nCopies(110, "-"))).append("\n");

            sb.append(String.format(rowFormat,
                    form.idNumber(),
                    form.user().firstName(),
                    form.user().lastName(),
                    form.user().sex(),
                    form.user().birthday(),
                    form.money(),
                    form.organisation()
            ));

            sb.append(String.join("", Collections.nCopies(110, "-"))).append("\n");
            return new OperationResult(true, sb.toString());
        } else {
            return new OperationResult(false, "User not found: " + this.id);
        }
    }

    @Override
    public String toString() {
        return "GetUserInfoOperation[id='" + id + "']";
    }
}
