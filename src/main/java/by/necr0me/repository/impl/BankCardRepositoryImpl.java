package by.necr0me.repository.impl;

import by.necr0me.db.Db;
import by.necr0me.entity.BankCard;
import by.necr0me.exception.BankCardNotFoundException;
import by.necr0me.infrastructure.annotation.InjectByType;
import by.necr0me.repository.BankCardRepository;

public class BankCardRepositoryImpl implements BankCardRepository {
    @InjectByType
    Db db;

    @Override
    public BankCard findByNumber(String number) throws BankCardNotFoundException {
        for (BankCard card : db.getBankCards()) {
            if(card.getCardNumber().equals(number)) {
                return card;
            }
        }

        throw new BankCardNotFoundException("Bank card with number " + number + " not found");
    }

    @Override
    public BankCard save(BankCard bankCard) {
        BankCard card = findByNumber(bankCard.getCardNumber());
        if(card != null) {
            card.setCardNumber(bankCard.getCardNumber());
            card.setPinCode(bankCard.getPinCode());
            card.setBalance(bankCard.getBalance());

            db.update(BankCard.class);
        }

        return bankCard;
    }
}
