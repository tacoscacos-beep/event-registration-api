package kz.iitu.registration.controller;

import kz.iitu.registration.model.EventInfo;
import kz.iitu.registration.service.EventService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RegistrationController {

    private final EventService eventService;

    @Value("${app.course:Web Technologies}")
    private String course;

    @Value("${app.semester:3}")
    private String semester;

    // Constructor-based Dependency Injection
    public RegistrationController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/hello")
    public Map<String, String> hello() {
        return Map.of("message", eventService.getWelcomeMessage());
    }

    @GetMapping("/events")
    public List<EventInfo> getEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/info")
    public Map<String, String> getInfo() {
        return Map.of(
                "course", course,
                "semester", semester
        );
    }

    // Bonus task (+10 points): Get event by ID
    @GetMapping("/events/{id}")
    public ResponseEntity<?> getEventById(@PathVariable int id) {
        EventInfo event = eventService.getEventById(id);
        if (event != null) {
            return ResponseEntity.ok(event);
        }
        return ResponseEntity.status(404).body(Map.of("error", "Event with ID " + id + " not found"));
    }
}