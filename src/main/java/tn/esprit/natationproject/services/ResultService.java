package tn.esprit.natationproject.services;



import tn.esprit.natationproject.dto.ResultDTO;
import tn.esprit.natationproject.Entite.Result;
import tn.esprit.natationproject.repositories.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ResultService {

    @Autowired
    private ResultRepository resultRepository;

    public List<ResultDTO> getAllResults() {
        return resultRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<ResultDTO> getResultById(Integer id) {
        return Optional.ofNullable(resultRepository.findByIdWithCategoryAndEvent(id))
                .map(this::convertToDTO);
    }

    public List<ResultDTO> getResultsByCategoryId(Integer categoryId) {
        return resultRepository.findByCategoryId(categoryId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ResultDTO> getResultsByEventId(Integer eventId) {
        return resultRepository.findByEventId(eventId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ResultDTO> getResultsByNation(String nation) {
        return resultRepository.findByNation(nation).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ResultDTO> getResultsByClub(String club) {
        return resultRepository.findByClub(club).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ResultDTO> searchResultsByName(String name) {
        return resultRepository.findByNameContaining(name).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ResultDTO> getResultsByEventAndCategory(Integer eventId, Integer categoryId) {
        return resultRepository.findByEventIdAndCategoryIdOrderByPlace(eventId, categoryId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ResultDTO convertToDTO(Result result) {
        ResultDTO dto = new ResultDTO();
        dto.setId(result.getId());
        dto.setPlace(result.getPlace());
        dto.setNamePrenom(result.getNamePrenom());
        dto.setNation(result.getNation());
        dto.setClub(result.getClub());
        dto.setTemps(result.getTemps());
        dto.setPoints(result.getPoints());
        dto.setTempsPassage(result.getTempsPassage());

        if (result.getCategory() != null) {
            dto.setCategoryId(result.getCategory().getId());
            dto.setCategoryName(result.getCategory().getName());

            if (result.getCategory().getEvent() != null) {
                dto.setEventId(result.getCategory().getEvent().getId());
                dto.setEventName(result.getCategory().getEvent().getName());
            }
        }

        return dto;
    }
}