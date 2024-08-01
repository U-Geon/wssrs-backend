package bootcamp.wssrs.domain.Day.service;


import bootcamp.wssrs.domain.Day.entity.Day;
import bootcamp.wssrs.domain.Day.repository.DayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DayService {
    private final DayRepository dayRepository;

    public Day create(List<String> dates) {
        Day day = Day.create();

        for (String date : dates) {
            switch (date) {
                case "월" -> day.setMonday(true);
                case "화" -> day.setTuesday(true);
                case "수" -> day.setWednesday(true);
                case "목" -> day.setThursday(true);
                case "금" -> day.setFriday(true);
                case "토" -> day.setSaturday(true);
                case "일" -> day.setSunday(true);
            }
        }

        dayRepository.save(day);
        return day;
    }
}
