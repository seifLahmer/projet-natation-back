package tn.esprit.natationproject.services;

import tn.esprit.natationproject.dto.EventDTO;
import tn.esprit.natationproject.Entite.Event;
import tn.esprit.natationproject.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private CategoryService categoryService;

    public List<EventDTO> getAllEvents() {
        return eventRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<EventDTO> getAllEventsWithCategories() {
        return eventRepository.findAllWithCategories().stream()
                .map(this::convertToDTOWithCategories)
                .collect(Collectors.toList());
    }

    public Optional<EventDTO> getEventById(Integer id) {
        return eventRepository.findById(id)
                .map(this::convertToDTO);
    }

    public Optional<EventDTO> getEventByIdWithDetails(Integer id) {
        return eventRepository.findByIdWithCategoriesAndResults(id)
                .map(this::convertToDTOWithCategories);
    }

    public Optional<EventDTO> getEventByName(String name) {
        return eventRepository.findByName(name)
                .map(this::convertToDTO);
    }

    private EventDTO convertToDTO(Event event) {
        return new EventDTO(event.getId(), event.getName());
    }

    private EventDTO convertToDTOWithCategories(Event event) {
        EventDTO dto = convertToDTO(event);
        if (event.getCategories() != null) {
            dto.setCategories(event.getCategories().stream()
                    .map(category -> categoryService.convertToDTO(category))
                    .collect(Collectors.toList()));
        }
        return dto;
    }
}
