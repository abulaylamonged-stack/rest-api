import com.fasterxml.jackson.databind.ObjectMapper;
import rest_api.model.Schedule;
import java.time.LocalDateTime;

public class CheckScheduleJson {
  public static void main(String[] args) throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    Schedule s = mapper.readValue("{\"title\":\"Database Lab\",\"dateTime\":\"2026-09-22T10:00:00\",\"status\":\"Scheduled\"}", Schedule.class);
    System.out.println(s.getTitle());
    System.out.println(s.getDateTime());
    System.out.println(s.getStatus());
  }
}
