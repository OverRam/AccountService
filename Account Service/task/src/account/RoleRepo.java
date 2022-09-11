package account;

public class RoleRepo {

    public static final Role ADMIN;
    public static final Role USER;
    public static final Role ACCOUNTANT;

    static {
        ADMIN = new Role("ADMIN");
        USER = new Role("USER");
        ACCOUNTANT = new Role("ACCOUNTANT");
    }
}
