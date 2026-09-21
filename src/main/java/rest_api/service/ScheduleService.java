package rest_api.service;

import org.springframework.stereotype.Service;
import rest_api.model.Schedule;
import rest_api.repository.ScheduleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    private final ScheduleRepository repository;

    public ScheduleService(ScheduleRepository repository) {
        this.repository = repository;
    }

    public List<Schedule> getAllSchedules() {
        return repository.findAll();
    }

    public Optional<Schedule> getScheduleById(Long id) {
        return repository.findById(id);
    }

    public Schedule createSchedule(Schedule schedule) {
        return repository.save(schedule);
    }

    public Optional<Schedule> updateSchedule(Long id, Schedule updatedSchedule) {
        return repository.findById(id).map(schedule -> {
            schedule.setTitle(updatedSchedule.getTitle());
            schedule.setDateTime(updatedSchedule.getDateTime());
            schedule.setStatus(updatedSchedule.getStatus());
            return repository.save(schedule);
        });
    }

    public boolean deleteSchedule(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }

        repository.deleteById(id);
        return true;
    }
}