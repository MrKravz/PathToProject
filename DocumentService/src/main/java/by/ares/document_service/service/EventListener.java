package by.ares.document_service.service;

public interface EventListener<T> {
    void invoke(T t);
}
