package server;

import io.grpc.stub.StreamObserver;
import newsletter.EventNotification;
import newsletter.SubscriptionRequest;

import java.util.*;
import java.util.concurrent.ConcurrentMap;

public class RandomIntervalNotifier {
    private final ConcurrentMap<Integer, SubscriptionRequest> activeSubscriptions;
    private final ConcurrentMap<Integer, List<EventNotification>> pendingNotifications;
    private final ConcurrentMap<Integer, StreamObserver<EventNotification>> eventObservers;
    private final double minTimeBetweenEvents;
    private final double maxTimeBetweenEvents;
    private final double timeBetweenPendingNotifications;
    private final Random random = new Random();

    public RandomIntervalNotifier(ConcurrentMap<Integer, SubscriptionRequest> activeSubscriptions,
                                  ConcurrentMap<Integer, StreamObserver<EventNotification>> eventObservers,
                                  ConcurrentMap<Integer, List<EventNotification>> pendingNotifications,
                                  double minTimeBetweenEvents,
                                  double maxTimeBetweenEvents,
                                  double timeBetweenPendingNotifications) {
        this.activeSubscriptions = activeSubscriptions;
        this.pendingNotifications = pendingNotifications;
        this.eventObservers = eventObservers;
        this.minTimeBetweenEvents = minTimeBetweenEvents;
        this.maxTimeBetweenEvents = maxTimeBetweenEvents;
        this.timeBetweenPendingNotifications = timeBetweenPendingNotifications;
    }

    private void sleepRandomTime() throws InterruptedException {
        double randomTime = minTimeBetweenEvents + (maxTimeBetweenEvents - minTimeBetweenEvents) * random.nextDouble();
        Thread.sleep((long) (randomTime * 1000));
    }

    public void start() {
        // Start a thread to send random events notifications
        Thread randomEventsThread = new Thread(() -> {
            while (true) {
                try {
                    sleepRandomTime();
                    EventNotification event = RandomEventsGenerator.generateRandomEvent();
                    System.out.println("New event: " + event.getTitle() + " in " + event.getCity() + " with tags: " + event.getTagsList());
                    for (Map.Entry<Integer, SubscriptionRequest> entry : activeSubscriptions.entrySet()) {
                        SubscriptionRequest subscription = entry.getValue();
                        if (subscription.getCity().equals(event.getCity())
                                && subscription.getTagsList().stream().anyMatch(event.getTagsList()::contains)) {
                            StreamObserver<EventNotification> observer = eventObservers.get(entry.getKey());
                            if (observer != null) {
                                try {
                                    observer.onNext(event);
                                } catch (Exception e) {
                                    System.err.println("Error sending event to user: " + entry.getKey() + ", adding to pending notifications");
                                    pendingNotifications.computeIfAbsent(entry.getKey(), k -> new ArrayList<>()).add(event);
                                }
                            } else {
                                pendingNotifications.computeIfAbsent(entry.getKey(), k -> new ArrayList<>()).add(event);
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Start a second thread to send pending notifications
        Thread pendingNotificationsThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep((long) (timeBetweenPendingNotifications * 1000));
                    for (Map.Entry<Integer, List<EventNotification>> entry : pendingNotifications.entrySet()) {
                        Iterator<EventNotification> iterator = entry.getValue().iterator();
                        while (iterator.hasNext()) {
                            EventNotification event = iterator.next();
                            StreamObserver<EventNotification> observer = eventObservers.get(entry.getKey());
                            if (observer == null)
                                break;
                            try {
                                observer.onNext(event);
                                iterator.remove();
                                System.out.println("Resent pending notification to user: " + entry.getKey() + " - " + event.getTitle());
                            } catch (Exception e) {
                                System.err.println("Error resenting pending notification to user: " + entry.getKey());
                                eventObservers.remove(entry.getKey());
                                break;
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        randomEventsThread.start();
        pendingNotificationsThread.start();
    }
}
