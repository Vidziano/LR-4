package command;

import java.io.Serializable;
import java.util.Collections;

import info.RegForm;
import info.Result;

public class OperationResult implements Result, Serializable {
    private static final long serialVersionUID = 1L;
    private final boolean status;
    private final Object result;

    public OperationResult(boolean status, Object result) {
        this.status = status;
        this.result = result;
    }

    @Override
    public boolean getStatus() {
        return this.status;
    }

    @Override
    public Object getResult() {
        return this.result;
    }

    @Override
    public String toString() {
        if (result instanceof RegForm) {
            RegForm form = (RegForm) result;
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
            return sb.toString();
        } else if (result instanceof String) {
            return (String) result;
        } else {
            return "OperationResult[Result:\n" + result + "]";
        }
    }
}
