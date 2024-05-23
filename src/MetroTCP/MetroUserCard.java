package MetroTCP;

import info.RegForm;
import info.Sex;
import info.User;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Collections;

public class MetroUserCard implements RegForm, Serializable {
    private static final long serialVersionUID = 1L;
    private final String serNum;
    private final User user;
    private final String organization;
    private int money;

    public MetroUserCard(String serNum, User user, String organization) {
        this.serNum = serNum;
        this.user = user;
        this.organization = organization;
        this.money = 0;
    }

    @Override
    public String idNumber() {
        return this.serNum;
    }

    @Override
    public User user() {
        return this.user;
    }

    @Override
    public String organisation() {
        return this.organization;
    }

    @Override
    public int money() {
        return this.money;
    }

    @Override
    public void refill(int money) {
        this.money += money;
    }

    @Override
    public boolean payment(int money) {
        boolean res = false;
        if (this.money >= money) {
            this.money -= money;
            res = true;
        }
        return res;
    }

    public String toPrintString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.join("", Collections.nCopies(110, "-"))).append("\n");
        String headerFormat = "| %-10s | %-15s | %-15s | %-6s | %-12s | %-10s | %-20s |%n";
        String rowFormat = "| %-10s | %-15s | %-15s | %-6s | %-12s | %-10d | %-20s |%n";

        sb.append(String.format(headerFormat, "ID", "First Name", "Last Name", "Sex", "Birthday", "Balance", "Organization"));
        sb.append(String.join("", Collections.nCopies(110, "-"))).append("\n");

        sb.append(String.format(rowFormat,
                this.serNum,
                this.user.firstName(),
                this.user.lastName(),
                this.user.sex(),
                this.user.birthday(),
                this.money,
                this.organization));

        sb.append(String.join("", Collections.nCopies(110, "-"))).append("\n");
        return sb.toString();
    }

    @Override
    public String toString() {
        return String.format("MetroCard[serNum=%s, user=%s, organization=%s, balance=%d]",
                serNum, user, organization, money);
    }

    public static void main(String[] args) {
        try {
            RegForm mc = new MetroUserCard("333",
                    new MetroUser("Ivan", "Bodnar", Sex.MAN, "03.03.2003"),"KhNU");
            System.out.println(mc.toPrintString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
