package by.ares.path_list_service.service.impl;

import by.ares.path_list_service.dto.PathListDto;
import by.ares.path_list_service.service.EventListener;
import by.ares.path_list_service.service.OutboxEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OutboxEventPublisherImpl implements OutboxEventPublisher {

    private final List<EventListener<PathListDto>> eventListeners;

    @Override
    public void subscribe(EventListener<PathListDto> eventListener) {
        eventListeners.add(eventListener);
    }

    @Override
    public void unsubscribe(EventListener<PathListDto> eventListener) {
        eventListeners.remove(eventListener);
    }

    @Override
    public void invokeAll(PathListDto pathListDto) {
        eventListeners.forEach(x -> x.invoke(pathListDto));
    }
}
