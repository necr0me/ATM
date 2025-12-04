package by.necr0me.db.updater.impl;

import by.necr0me.db.config.DbConfig;
import by.necr0me.db.updater.Updater;
import by.necr0me.infrastructure.annotation.InjectByType;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class DbUpdater implements Updater {
    @InjectByType
    DbConfig dbConfig;

    @Override
    public void update(String fileName, List<String> lines) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Path.of(dbConfig.getDbPath(),
                fileName + dbConfig.getDbFilesExtension()).toString()))) {

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
