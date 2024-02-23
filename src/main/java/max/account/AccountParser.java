package max.account;

public class AccountParser {
    
    public static Account to(AccountIn in) {
        return Account.builder()
            .name(in.name())
            .email(in.email())
            .hash(in.password())
            .build();
    }

    public static AccountOut to(Account account) {
        return AccountOut.builder()
            .id(account.id())
            .name(account.name())
            .email(account.email())
            .build();
    }

}
