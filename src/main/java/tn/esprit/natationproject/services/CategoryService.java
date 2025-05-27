package tn.esprit.natationproject.services;

import tn.esprit.natationproject.dto.CategoryDTO;
import tn.esprit.natationproject.Entite.Category;
import tn.esprit.natationproject.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ResultService resultService;

    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<CategoryDTO> getCategoryById(Integer id) {
        return categoryRepository.findById(id)
                .map(this::convertToDTO);
    }

    public Optional<CategoryDTO> getCategoryByIdWithResults(Integer id) {
        return categoryRepository.findByIdWithResults(id)
                .map(this::convertToDTOWithResults);
    }

    public List<CategoryDTO> getCategoriesByEventId(Integer eventId) {
        return categoryRepository.findByEventId(eventId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CategoryDTO convertToDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        if (category.getEvent() != null) {
            dto.setEventId(category.getEvent().getId());
            dto.setEventName(category.getEvent().getName());
        }
        return dto;
    }

    private CategoryDTO convertToDTOWithResults(Category category) {
        CategoryDTO dto = convertToDTO(category);
        if (category.getResults() != null) {
            dto.setResults(category.getResults().stream()
                    .map(result -> resultService.convertToDTO(result))
                    .collect(Collectors.toList()));
        }
        return dto;
    }
}
