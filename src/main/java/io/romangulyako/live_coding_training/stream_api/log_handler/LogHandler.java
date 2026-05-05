package io.romangulyako.live_coding_training.stream_api.log_handler;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class LogHandler {
    static List<String> userLogs = List.of(
            "user1,LOGIN",
            "user2,LOGOUT",
            "user1,UPLOAD",
            "user2,LOGIN",
            "user1,DOWNLOAD",
            "user2,UPLOAD",
            "user1,LOGIN",
            "user2,DOWNLOAD",
            "user3,LOGOUT"
    );

    static List<String> transactions = List.of(
            "user1,DEPOSIT,100",
            "user2,WITHDRAW,50",
            "user1,WITHDRAW,30",
            "user3,DEPOSIT,200",
            "user2,DEPOSIT,10"
    );

    static List<String> loginLogs = List.of(
            "user1,LOGIN",
            "user2,LOGOUT",
            "user1,UPLOAD",
            "user2,LOGIN",
            "user1,DOWNLOAD",
            "user2,UPLOAD"
    );

    static List<String> transactionalLogs = List.of(
            "user1,DEPOSIT,100",
            "user2,WITHDRAW,50",
            "user1,DEPOSIT,70",
            "user3,DEPOSIT,200",
            "user2,DEPOSIT,40",
            "user1,WITHDRAW,30",
            "user3,WITHDRAW,150"
    );

    static List<String> logsForMaxDepositSearch = List.of(
            "user1,DEPOSIT,100",
            "user2,DEPOSIT,50",
            "user1,DEPOSIT,70",
            "user3,DEPOSIT,200",
            "user2,DEPOSIT,40",
            "user1,DEPOSIT,30",
            "user3,DEPOSIT,150"
    );

    static List<String> logsForUploadGreaterThan100 = List.of(
            "user1,LOGIN,300",
            "user2,LOGIN,150",
            "user1,UPLOAD,200",
            "user3,LOGIN,500",
            "user2,UPLOAD,100",
            "user3,UPLOAD,50",
            "user1,LOGIN,100"
    );

    static Set<String> getUsersWithUploadGreaterThan100(List<String> logs) {
        return logs.stream()
                .map(log -> log.split(","))
                .filter(parts -> "UPLOAD".equals(parts[1]))
                .collect(Collectors.groupingBy(
                        parts -> parts[0],
                        Collectors.summingInt(parts -> Integer.parseInt(parts[2]))
                ))
                .entrySet().stream()
                .filter(entry -> entry.getValue() >= 100)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    static String getUserWithBestSumOfDeposit(List<String> userLogs) {
        return userLogs.stream()
                .map(log -> log.split(","))
                .filter(parts -> Objects.equals(parts[1], "DEPOSIT"))
                .collect(Collectors.groupingBy(
                        parts -> parts[0],
                        Collectors.summingInt(parts -> Integer.parseInt(parts[2]))
                )).entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

    }

    static Set<String> getUsersWithDepositSumGreaterThan100(List<String> userLogs) {
        return userLogs.stream()
                .map(log -> log.split(","))
                .filter(parts -> parts[1].equals("DEPOSIT"))
                .collect(Collectors.groupingBy(
                        parts -> parts[0],
                        Collectors.summingInt(parts -> Integer.parseInt(parts[2]))
                ))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > 100)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

    }

    static Map<String, Integer> getAmountByUser(List<String> transactions) {
        return transactions.stream()
                .map(log -> log.split(","))
                .collect(Collectors.groupingBy(
                        item -> item[0],
                        Collectors.summingInt(item -> {
                            int amount = Integer.parseInt(item[2]);
                            return item[1].equals("DEPOSIT") ? amount : -amount;
                        })
                ));
    }

    static Map<String, List<String>> getUsersHistory(List<String> logs) {
        return logs.stream()
                .map(log -> log.split(","))
                .collect(Collectors.groupingBy(
                        part -> part[0],
                        Collectors.mapping(
                                part -> part[1],
                                Collectors.toList())));
    }

    static Map<String, Set<String>> getUniqueLogs(List<String> logs) {
        return logs.stream()
                .map(log -> log.split(","))
                .filter(parts -> parts[1].equals("LOGIN") || parts[1].equals("UPLOAD"))
                .collect(Collectors.groupingBy(
                        parts -> parts[0],
                        Collectors.mapping(parts -> parts[1], Collectors.toSet())
                ));
    }


    public static void main(String[] args) {

        Map<String, Set<String>> usersUniqueLogs = getUniqueLogs(userLogs);
        System.out.println(usersUniqueLogs);

        Map<String, Integer> result = getAmountByUser(transactions);
        System.out.println(result);

        Map<String, List<String>> usersHistory = getUsersHistory(loginLogs);
        System.out.println(usersHistory);

        Set<String> usersWithDepositSumGreaterThan100 = getUsersWithDepositSumGreaterThan100(transactionalLogs);
        System.out.println(usersWithDepositSumGreaterThan100);

        String maxDeposit = getUserWithBestSumOfDeposit(logsForMaxDepositSearch);
        System.out.println(maxDeposit);

        Set<String> usersWithUploadGreaterThan100 = getUsersWithUploadGreaterThan100(logsForUploadGreaterThan100);
        System.out.println(usersWithUploadGreaterThan100);
    }
}
