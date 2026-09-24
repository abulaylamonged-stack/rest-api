package rest_api.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rest_api.model.ApiResponse;
import rest_api.model.Schedule;
import rest_api.service.ScheduleService;

@RestController
@RequestMapping("/api/schedules")
@CrossOrigin(origins = "*")
public class ScheduleController {

    private final ScheduleService service;

    public ScheduleController(ScheduleService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
        public ResponseEntity<ApiResponse<Page<Schedule>>> getAllSchedules(
            @PageableDefault(page = 0, size = 10) Pageable pageable) {
        return ResponseEntity.ok(new ApiResponse<>(
                true, "Schedules retrieved successfully", service.getAllSchedules(pageable)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ApiResponse<Schedule>> getSchedule(@PathVariable Long id) {
        return service.getScheduleById(id)
                .map(schedule -> ResponseEntity.ok(new ApiResponse<>(
                        true, "Schedule retrieved successfully", schedule)))
            .orElseThrow(() -> new ResourceNotFoundException("Schedule not found"));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Schedule>> createSchedule(
            @Valid @RequestBody Schedule schedule) {
        return ResponseEntity.ok(new ApiResponse<>(
                true, "Schedule created successfully", service.createSchedule(schedule)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Schedule>> updateSchedule(
            @PathVariable Long id,
            @Valid @RequestBody Schedule schedule) {
        return service.updateSchedule(id, schedule)
                .map(updatedSchedule -> ResponseEntity.ok(new ApiResponse<>(
                        true, "Schedule updated successfully", updatedSchedule)))
            .orElseThrow(() -> new ResourceNotFoundException("Schedule not found"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        if (service.deleteSchedule(id)) {
            return ResponseEntity.noContent().build();
        }

        throw new ResourceNotFoundException("Schedule not found");
    }
}