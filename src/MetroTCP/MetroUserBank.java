package MetroTCP;

import info.RegUsers;
import info.RegForm;
import info.Sex;
import info.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.text.ParseException;
import java.util.List;

public class MetroUserBank implements RegUsers, Serializable {
    private final List<RegForm> store;

    public MetroUserBank(List<RegForm> store) {
        this.store = store;
    }

    public MetroUserBank() {
        this.store = new ArrayList<>();
    }

    @Override
    public boolean register(RegForm form) {
        for (RegForm existingForm : store) {
            if (existingForm.idNumber().equals(form.idNumber())) {
                return false; // User already exists
            }
        }
        this.store.add(form);
        return true;
    }

    @Override
    public boolean cancel(String id) {
        return store.removeIf(form -> form.idNumber().equals(id));
    }

    @Override
    public RegForm getByID(String id) {
        for (RegForm form : store) {
            if (form.idNumber().equalsIgnoreCase(id)) {
                return form;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.join("", Collections.nCopies(110, "-"))).append("\n");
        String headerFormat = "| %-10s | %-15s | %-15s | %-6s | %-12s | %-10s | %-20s |%n";
        String rowFormat = "| %-10s | %-15s | %-15s | %-6s | %-12s | %-10d | %-20s |%n";

        sb.append(String.format(headerFormat, "ID", "First Name", "Last Name", "Sex", "Birthday", "Balance", "Organization"));
        sb.append(String.join("", Collections.nCopies(110, "-"))).append("\n");

        for (RegForm form : store) {
            User user = form.user();
            sb.append(String.format(rowFormat,
                    form.idNumber(),
                    user.firstName(),
                    user.lastName(),
                    user.sex(),
                    user.birthday(),
                    form.money(),
                    form.organisation()
            ));
        }

        sb.append(String.join("", Collections.nCopies(110, "-"))).append("\n");
        return sb.toString();
    }

    public static void main(String[] args) {
        MetroUserBank bank = new MetroUserBank();
        try {
            bank.register(new MetroUserCard("111", new MetroUser("Olya", "Maslova", Sex.WOMAN, "08.06.2003"), "KhNU"));
            bank.register(new MetroUserCard("222", new MetroUser("Masha", "Shevchenko", Sex.WOMAN, "24.09.2005"), "KhPI"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(bank);
    }
}
