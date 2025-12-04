package by.necr0me.db.mapper;

public interface Mapper<T> {
    T map(String ...fields);
}
