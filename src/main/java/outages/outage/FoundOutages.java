package outages.outage;

import outages.pojo.Outage;

import java.util.List;

public interface FoundOutages {
    List<Outage> outages() throws Exception;
}
