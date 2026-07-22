package by.ares.company_service.mapper;

public interface RequestMapper<T, D> {
    T map(D d);
}
