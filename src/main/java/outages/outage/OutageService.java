package outages.outage;

public interface OutageService {
    void checkNearBy(Long[] chatIds);

    void checkSizeAround(Long[] chatIds);
}
