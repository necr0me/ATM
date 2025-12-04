package by.necr0me.entity;

import by.necr0me.db.annotation.DbSet;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@DbSet(name="cards")
@Getter
@Setter
public class BankCard {
    private String cardNumber;
    private char[] pinCode;
    private int balance;

    @Override
    public String toString() {
        return String.join(" ", this.cardNumber, Arrays.toString(this.pinCode), String.valueOf(this.balance));
    }
}
