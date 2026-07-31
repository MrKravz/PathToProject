package by.ares.document_service.service;

public interface EventPublisher<T> {
    void subscribe(EventListener<T> eventListener);
    void unsubscribe(EventListener<T> eventListener);
    void invoke(T t);
}
