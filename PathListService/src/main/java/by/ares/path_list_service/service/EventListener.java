package by.ares.path_list_service.service;

public interface EventListener<T> {
    void invoke(T data);
}
