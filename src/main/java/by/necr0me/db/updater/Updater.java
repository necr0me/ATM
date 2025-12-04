package by.necr0me.db.updater;

import java.util.List;

public interface Updater {
    void update(String fileName, List<String> lines);
}
