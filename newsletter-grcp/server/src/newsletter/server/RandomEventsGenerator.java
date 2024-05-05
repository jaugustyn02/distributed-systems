package newsletter.server;

import newsletter.EventNotification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class RandomEventsGenerator {
    private final static List<String> cities = Arrays.asList("New York", "San Francisco", "Chicago", "Los Angeles", "Seattle", "Boston", "Austin");
    private final static List<String> tags = Arrays.asList("Sports", "Politics", "Technology", "Business", "Science", "Health", "Entertainment");
    private final static List<String> titles = Arrays.asList("Super Bowl", "Presidential Election", "Oscars", "World Cup", "SpaceX Launch", "Tailor Swift Concert", "Apple Event");
    private final static List<String> descriptions = Arrays.asList("This is a description", "This is another description", "This is a third description", "This is a fourth description", "This is a fifth description");
    private final static Random random = new Random();

    public RandomEventsGenerator() {}

    public static EventNotification generateRandomEvent() {
        return EventNotification.newBuilder()
                .setType(EventNotification.NotificationType.NEW_EVENT)
                .setTitle(generateRandomTitle())
                .setDescription(generateRandomDescription())
                .setCity(generateRandomCity())
                .addAllTags(generateRandomTags())
                .build();
    }

    private static String generateRandomCity() {
        return cities.get(random.nextInt(cities.size()));
    }

    private static List<String> generateRandomTags() {
        List<String> randomTags = new ArrayList<>();
        for (int i = 0; i < tags.size(); i++) {
            if (random.nextFloat() < 2.0 / tags.size()) {
                randomTags.add(tags.get(i));
            }
        }
        if (randomTags.isEmpty()) {
            randomTags.add(tags.get(random.nextInt(tags.size())));
        }
        return randomTags;
    }

    private static String generateRandomTitle() {
        return titles.get(random.nextInt(titles.size()));
    }

    private static String generateRandomDescription() {
        return descriptions.get(random.nextInt(descriptions.size()));
    }
}
