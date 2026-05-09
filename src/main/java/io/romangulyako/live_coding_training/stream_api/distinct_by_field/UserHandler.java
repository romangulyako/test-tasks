package io.romangulyako.live_coding_training.stream_api.distinct_by_field;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserHandler {
    public static List<String> getUniqueEmails(List<User> users) {
        return users.stream()
                .map(User::email)
                .distinct()
                .toList();
    }

    public static List<User> getUniqueUsersByEmail(List<User> users) {
        Set<String> uniqueUsers = new HashSet<>();

        return users.stream()
                .filter(user -> uniqueUsers.add(user.email()))
                .toList();
    }

    public static void main(String[] args) {
        List<User> users = List.of(new User(1, "a@test.com"),
                new User(2, "a@test.com"),
                new User(3, "b@test.com")
        );

        List<User> uniqueUsers = getUniqueUsersByEmail(users);

        System.out.println(uniqueUsers);
    }
}
