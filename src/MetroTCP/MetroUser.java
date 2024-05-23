package MetroTCP;

import info.Sex;
import info.User;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Collections;

public class MetroUser implements User, Serializable {
    private static final long serialVersionUID = 1L;
    private final String name;
    private final String surName;
    private final Sex sex;
    private final Date birthday;
    private static final DateFormat procDate = new SimpleDateFormat("dd.MM.yyyy");

    public MetroUser(String name, String surName, Sex sex, String birthday) throws ParseException {
        this.name = name;
        this.surName = surName;
        this.sex = sex;
        this.birthday = procDate.parse(birthday);
    }

    @Override
    public String firstName() {
        return this.name;
    }

    @Override
    public String lastName() {
        return this.surName;
    }

    @Override
    public Sex sex() {
        return this.sex;
    }

    @Override
    public String birthday() {
        return procDate.format(birthday);
    }

    public String toPrintString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.join("", Collections.nCopies(61, "-"))).append("\n");
        String headerFormat = "| %-15s | %-15s | %-6s | %-12s |%n";
        String rowFormat = "| %-15s | %-15s | %-6s | %-12s |%n";

        sb.append(String.format(headerFormat, "First Name", "Last Name", "Sex", "Birthday"));
        sb.append(String.join("", Collections.nCopies(61, "-"))).append("\n");

        sb.append(String.format(rowFormat,
                this.name,
                this.surName,
                this.sex,
                procDate.format(birthday)
        ));

        sb.append(String.join("", Collections.nCopies(61, "-"))).append("\n");
        return sb.toString();
    }

    @Override
    public String toString() {
        return String.format("MetroUser[name=%s, surName=%s, sex=%s, birthday=%s]", name, surName, sex, procDate.format(birthday));
    }

    public static void main(String[] args) {
        try {
            MetroUser user = new MetroUser("Ivan", "Bodnar", Sex.MAN, "03.03.2003");
            System.out.println(user.toPrintString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
