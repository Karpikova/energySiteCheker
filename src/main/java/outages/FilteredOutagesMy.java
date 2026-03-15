package outages;


import outages.pojo.Outage;

import java.util.List;
import java.util.Locale;

public final class FilteredOutagesMy implements FilteredOutages {
    List<Outage> outages;

    public FilteredOutagesMy(List<Outage> outages) {
        this.outages = outages;
    }

    @Override
    public List<Outage> filteredByStringInHeader(String filterString) {
        return outages.stream().filter(outage -> outage.getBalloonContentHeader().toLowerCase(Locale.ROOT)
                .contains(filterString.toLowerCase())).toList();
    }

    @Override
    public List<Outage> filteredByRadius(Float x, Float y, Float radius) {
        return outages.stream().filter(q->(Math.abs(q.getGeometry().getX() - x)<radius
            && (Math.abs(q.getGeometry().getY() - y)<radius))).toList();

    }

}
