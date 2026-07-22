package by.ares.path_list_service.mapper;

public interface DtoMapper<T, D> {
    D map(T t);
    T remap(D t);
}
