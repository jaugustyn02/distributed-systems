package server;

import newsletter.EventNotification;
import newsletter.NewsletterServiceGrpc.NewsletterServiceImplBase;
import newsletter.SubscriptionRequest;
import newsletter.SubscriptionResponse;
import io.grpc.stub.StreamObserver;
import newsletter.UserRequest;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public class NewsletterServiceImpl extends NewsletterServiceImplBase{
    private final ConcurrentMap<Integer, SubscriptionRequest> activeSubscriptions = new ConcurrentHashMap<>();
    private final ConcurrentMap<Integer, StreamObserver<EventNotification>> eventObservers = new ConcurrentHashMap<>();
    private final ConcurrentMap<Integer, List<EventNotification>> pendingNotifications = new ConcurrentHashMap<>();

    public NewsletterServiceImpl() {
        RandomIntervalNotifier notifier = new RandomIntervalNotifier(
                activeSubscriptions,
                eventObservers,
                pendingNotifications,
                0,
                2,
                5
        );
        notifier.start();
    }

    @Override
    public void subscribe(SubscriptionRequest request, StreamObserver<SubscriptionResponse> responseObserver) {
        System.out.println("Subscription request received from user: " + request.getUserId());
        SubscriptionResponse response = handleSubscriptionRequest(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void unsubscribe(UserRequest request, StreamObserver<SubscriptionResponse> responseObserver) {
        System.out.println("Unsubscription request received from user: " + request.getUserId());
        SubscriptionResponse response = removeSubscription(request);
        pendingNotifications.remove(request.getUserId());
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getNotifications(UserRequest request, StreamObserver<EventNotification> responseObserver) {
        System.out.println("Notifications request received from user: " + request.getUserId());
        eventObservers.put(request.getUserId(), responseObserver);
    }

    private SubscriptionResponse handleSubscriptionRequest(SubscriptionRequest request) {
        if (activeSubscriptions.containsKey(request.getUserId())) {
            return updateSubscription(request);
        } else {
            return addSubscription(request);
        }
    }

    private SubscriptionResponse addSubscription(SubscriptionRequest request) {
        try {
            activeSubscriptions.put(request.getUserId(), request);
            return SubscriptionResponse.newBuilder().setSuccess(true).setMessage("Subscribed successfully").build();
        } catch (Exception e) {
            return SubscriptionResponse.newBuilder().setSuccess(false).setMessage("Failed to subscribe").build();
        }
    }

    private SubscriptionResponse updateSubscription(SubscriptionRequest request) {
        try {
            if (activeSubscriptions.get(request.getUserId()).getCity().equals(request.getCity())) {
                Set<String> tags = new HashSet<>(activeSubscriptions.get(request.getUserId()).getTagsList());
                tags.addAll(request.getTagsList());
                activeSubscriptions.get(request.getUserId()).toBuilder().clearTags().addAllTags(tags).build();
            } else {
                activeSubscriptions.put(request.getUserId(), request);
            }
            return SubscriptionResponse.newBuilder().setSuccess(true).setMessage("Subscription updated").build();
        } catch (Exception e) {
            return SubscriptionResponse.newBuilder().setSuccess(false).setMessage("Failed to update subscription").build();
        }
    }

    private SubscriptionResponse removeSubscription(UserRequest request) {
        try {
            activeSubscriptions.remove(request.getUserId());
            return SubscriptionResponse.newBuilder().setSuccess(true).setMessage("Unsubscribed successfully").build();
        } catch (Exception e) {
            return SubscriptionResponse.newBuilder().setSuccess(false).setMessage("Failed to unsubscribe").build();
        }
    }
}
