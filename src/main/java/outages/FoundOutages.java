package outages;

import outages.pojo.Outage;

import java.time.LocalDate;
import java.util.List;

public interface FoundOutages {
    List<Outage> outages() throws Exception;
}
