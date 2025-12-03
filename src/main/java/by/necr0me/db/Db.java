package by.necr0me.db;

import by.necr0me.db.annotation.DbSet;
import by.necr0me.db.mapper.impl.BankCardMapper;
import by.necr0me.db.parser.Parser;
import by.necr0me.entity.BankCard;
import by.necr0me.infrastructure.annotation.InjectByType;
import by.necr0me.infrastructure.annotation.PostConstruct;
import by.necr0me.infrastructure.annotation.Singleton;
import lombok.Getter;
import java.util.*;

@Singleton
public class Db {
    @Getter
    private final List<BankCard> bankCards = new ArrayList<>();

    @InjectByType
    private Parser parser;

    @InjectByType
    private BankCardMapper bankCardMapper;

    @PostConstruct
    public void init() {
        parseBankCards();
    }

    private void parseBankCards() {
        List<String[]> fileContents = parser.parse(getDbSetValue(BankCard.class));
        for (String[] fields : fileContents) {
            BankCard card = bankCardMapper.map(fields);
            bankCards.add(card);
            System.out.println(String.join(",", fields));
        }
    }

    private String getDbSetValue(Class<?> t) {
        return t.getAnnotation(DbSet.class).name();
    }
}

