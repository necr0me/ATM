package by.necr0me.repository;

import by.necr0me.entity.BankCard;

public interface BankCardRepository {
    BankCard findByNumber(String number);

    BankCard save(BankCard bankCard);
}
