package by.ares.path_list_service.service;

public interface EventManager<T> {

    void subscribe(EventListener<T> eventListener);

    void unsubscribe(EventListener<T> eventListener);

    void invokeAll(T t);
}
