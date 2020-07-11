package asfarus.example.cloudincrement.web;

import asfarus.example.cloudincrement.repository.IncrementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class IncrementController {
    private final IncrementRepository repository;

    @GetMapping
    public ResponseEntity<Long> get(){
        return ResponseEntity.ok(repository.get());
    }

    @PostMapping
    public ResponseEntity<Long> inc(){
        return ResponseEntity.ok(repository.incrementAndGet());
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void reset(){
        repository.reset();
    }
}
