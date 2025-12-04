package by.necr0me.db.config;

import by.necr0me.infrastructure.annotation.InjectProperty;
import by.necr0me.infrastructure.annotation.Singleton;
import lombok.Getter;

@Getter
@Singleton
public class DbConfig {
    @InjectProperty
    private String dbPath;
    @InjectProperty
    private String dbFilesExtension;
    @InjectProperty
    private String dbFilesDelimiter;
}
