package by.ares.path_list_service.service.impl;

import by.ares.path_list_service.dto.PathListEventDto;
import by.ares.path_list_service.service.EventListener;
import by.ares.path_list_service.service.OutboxEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OutboxEventPublisherImpl implements OutboxEventPublisher {

    private final List<EventListener<PathListEventDto>> eventListeners;

    @Override
    public void subscribe(EventListener<PathListEventDto> eventListener) {
        eventListeners.add(eventListener);
    }

    @Override
    public void unsubscribe(EventListener<PathListEventDto> eventListener) {
        eventListeners.remove(eventListener);
    }

    @Override
    public void invokeAll(PathListEventDto pathListDto) {
        eventListeners.forEach(x -> x.invoke(pathListDto));
    }
}
