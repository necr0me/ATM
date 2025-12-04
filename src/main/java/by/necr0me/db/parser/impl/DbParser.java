package by.necr0me.db.parser.impl;

import by.necr0me.db.config.DbConfig;
import by.necr0me.db.parser.Parser;
import by.necr0me.infrastructure.annotation.InjectByType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import java.util.stream.Stream;

public class DbParser implements Parser {
    @InjectByType
    DbConfig dbConfig;

    @Override
    public List<String[]> parse(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(Path.of(dbConfig.getDbPath(),
                fileName + dbConfig.getDbFilesExtension()).toString()))) {
            Stream<String> lines = reader.lines();
            return lines.map(line -> line.split(dbConfig.getDbFilesDelimiter())).toList();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return List.of();
    }
}
