package io.romangulyako.live_coding_training.employee_filter;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    Map<String, Employee> groupingEmployees(List<Employee> employees) {

        return employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparingInt(Employee::getSalary)),
                                opt -> opt.orElse(null)
                        )
                ));


    }


    Map<String, Integer> averageSalaryByDepartment(List<Employee> employees) {
        return employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment,
                        Collectors.collectingAndThen(
                                Collectors.averagingInt(Employee::getSalary),
                                avg -> (int)Math.round(avg)
                        )));

    }
    public static void main(String[] args) {

    }
}
