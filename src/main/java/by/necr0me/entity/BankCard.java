package by.necr0me.entity;

import by.necr0me.db.annotation.DbSet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@DbSet(name="cards")
@Getter
@Setter
public class BankCard {
    private String cardNumber;
    private char[] pinCode;
    private int balance;
}
