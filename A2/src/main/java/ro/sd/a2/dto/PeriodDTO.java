package ro.sd.a2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ro.sd.a2.utils.ApplicationUtils;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class PeriodDTO {

    private String startDate;

    private String endDate;

    public Date getStartDate() {
        return ApplicationUtils.getStartOfDay(startDate);
    }

    public Date getEndDate() {
        return ApplicationUtils.getEndOfDay(endDate);
    }

    public int compareDates() {
        return startDate.compareTo(endDate);
    }
}
