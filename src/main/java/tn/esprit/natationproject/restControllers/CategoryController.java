package tn.esprit.natationproject.restControllers;

import tn.esprit.natationproject.dto.CategoryDTO;
import tn.esprit.natationproject.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@CrossOrigin(origins = "*")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Integer id) {
        return categoryService.getCategoryById(id)
                .map(category -> ResponseEntity.ok(category))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/with-results")
    public ResponseEntity<CategoryDTO> getCategoryByIdWithResults(@PathVariable Integer id) {
        return categoryService.getCategoryByIdWithResults(id)
                .map(category -> ResponseEntity.ok(category))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-event/{eventId}")
    public ResponseEntity<List<CategoryDTO>> getCategoriesByEventId(@PathVariable Integer eventId) {
        List<CategoryDTO> categories = categoryService.getCategoriesByEventId(eventId);
        return ResponseEntity.ok(categories);
    }
}
