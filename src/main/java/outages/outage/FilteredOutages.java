package outages.outage;

import outages.pojo.Outage;
import java.util.List;

public interface FilteredOutages {
    List<Outage> filteredByStringInHeader(String filterString);
    List<Outage> filteredByRadius(Float x, Float y, Float radius);
}
