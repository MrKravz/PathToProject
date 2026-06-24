package by.ares.company_service.mapper;

public interface DtoMapper<T, D> {
    D map(T t);
    T remap(D t);
}
