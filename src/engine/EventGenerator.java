package engine;

import events.*;

import java.util.List;
import java.util.Random;

public class EventGenerator {
    private final Random random = new Random();

    private final List<DormEvent> pool = List.of(
            new NoiseComplaintEvent(),
            new MaintenanceFailureEvent(),
            new CurfewViolationEvent(),
            new FireDrillEvent(),
            new InternetOutageEvent(),
            new LostItemEvent()
    );

    public DormEvent nextEvent() {
        return pool.get(random.nextInt(pool.size()));
    }
}
