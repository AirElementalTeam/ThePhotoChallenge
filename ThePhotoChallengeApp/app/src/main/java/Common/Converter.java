package Common;

public class Converter {

    public String escapeEmail(String email) {
        email = email.toLowerCase();
        email = email.replace('.', ',');
        return email;
    }

    public String revertEmail(String email) {
        email = email.toLowerCase();
        email = email.replace(',', '.');
        return email;
    }
}
