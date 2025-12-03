package by.necr0me.db.parser.impl;

import by.necr0me.db.parser.Parser;
import by.necr0me.infrastructure.annotation.InjectProperty;
import by.necr0me.infrastructure.annotation.Singleton;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DbParser implements Parser {
    @InjectProperty
    private String dbPath;
    @InjectProperty
    private String dbFilesExtension;
    @InjectProperty
    private String dbFilesDelimiter;

    @Override
    public List<String[]> parse(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(Path.of(dbPath, fileName + dbFilesExtension).toString()))) {
            Stream<String> lines = reader.lines();
            return lines.map(line -> line.split(dbFilesDelimiter)).toList();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return List.of();
    }
}
