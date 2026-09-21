package rest_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rest_api.model.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}