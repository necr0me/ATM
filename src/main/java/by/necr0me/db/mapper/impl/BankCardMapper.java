package by.necr0me.db.mapper.impl;

import by.necr0me.db.mapper.Mapper;
import by.necr0me.entity.BankCard;
import by.necr0me.infrastructure.annotation.Singleton;

@Singleton(isLazy = true)
public class BankCardMapper implements Mapper<BankCard> {
    @Override
    public BankCard map(String... fields) {
        BankCard bankCard = new BankCard();
        bankCard.setCardNumber(fields[0]);
        bankCard.setPinCode(fields[1].toCharArray());
        bankCard.setBalance(Integer.parseInt(fields[2]));

        return bankCard;
    }
}
