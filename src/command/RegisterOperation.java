package command;

import info.RegForm;
import info.RegUsers;
import info.Result;
import info.Operation;

import java.util.Collections;

public class RegisterOperation implements Operation {
    private final RegForm form;

    public RegisterOperation(RegForm form) {
        this.form = form;
    }

    @Override
    public Result execute(RegUsers users) {
        boolean res = users.register(this.form);
        if (res) {
            StringBuilder sb = new StringBuilder();
            sb.append(String.join("", Collections.nCopies(122, "-"))).append("\n");
            String headerFormat = "| %-10s | %-15s | %-15s | %-6s | %-12s | %-10s | %-20s |%n";
            String rowFormat = "| %-10s | %-15s | %-15s | %-6s | %-12s | %-10d | %-20s |%n";

            sb.append(String.format(headerFormat, "ID", "First Name", "Last Name", "Sex", "Birthday", "Balance", "Organization"));
            sb.append(String.join("", Collections.nCopies(122, "-"))).append("\n");

            sb.append(String.format(rowFormat,
                    form.idNumber(),
                    form.user().firstName(),
                    form.user().lastName(),
                    form.user().sex(),
                    form.user().birthday(),
                    form.money(),
                    form.organisation()
            ));

            sb.append(String.join("", Collections.nCopies(122, "-"))).append("\n");
            return new OperationResult(true, "User registered successfully. Details:\n" + sb.toString());
        } else {
            return new OperationResult(false, "User already exists: " + form.idNumber());
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("RegisterOperation Details:\n");
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
        return sb.toString();
    }
}
