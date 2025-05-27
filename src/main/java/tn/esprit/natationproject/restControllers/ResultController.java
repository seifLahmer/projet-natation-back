package tn.esprit.natationproject.restControllers;


import tn.esprit.natationproject.dto.ResultDTO;
import tn.esprit.natationproject.services.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/results")
@CrossOrigin(origins = "http://localhost:4200")
public class ResultController {

    @Autowired
    private ResultService resultService;

    @GetMapping
    public ResponseEntity<List<ResultDTO>> getAllResults() {
        List<ResultDTO> results = resultService.getAllResults();
        return ResponseEntity.ok(results);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultDTO> getResultById(@PathVariable Integer id) {
        return resultService.getResultById(id)
                .map(result -> ResponseEntity.ok(result))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-category/{categoryId}")
    public ResponseEntity<List<ResultDTO>> getResultsByCategoryId(@PathVariable Integer categoryId) {
        List<ResultDTO> results = resultService.getResultsByCategoryId(categoryId);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/by-event/{eventId}")
    public ResponseEntity<List<ResultDTO>> getResultsByEventId(@PathVariable Integer eventId) {
        List<ResultDTO> results = resultService.getResultsByEventId(eventId);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/by-nation")
    public ResponseEntity<List<ResultDTO>> getResultsByNation(@RequestParam String nation) {
        List<ResultDTO> results = resultService.getResultsByNation(nation);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/by-club")
    public ResponseEntity<List<ResultDTO>> getResultsByClub(@RequestParam String club) {
        List<ResultDTO> results = resultService.getResultsByClub(club);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ResultDTO>> searchResultsByName(@RequestParam String name) {
        List<ResultDTO> results = resultService.searchResultsByName(name);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/by-event-category")
    public ResponseEntity<List<ResultDTO>> getResultsByEventAndCategory(
            @RequestParam Long eventId,
            @RequestParam Long categoryId) {
        List<ResultDTO> results = resultService.getResultsByEventAndCategory(Math.toIntExact(eventId), Math.toIntExact(categoryId));
        return ResponseEntity.ok(results);
    }
}
