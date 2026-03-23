package outages.outage;

import outages.pojo.Outage;

public interface ProcessResult {
    void processOutage(Outage outage);

    void processPlainText(String text);
}
