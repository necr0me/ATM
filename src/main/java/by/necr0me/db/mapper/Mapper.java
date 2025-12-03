package by.necr0me.db.mapper;

import java.sql.ResultSet;

public interface Mapper<T> {
    T map(String ...fields);
}
