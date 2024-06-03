package com.example.dataorbppp.services;

import java.util.*;
import java.util.stream.Collectors;
import com.example.dataorbppp.models.*;
import java.text.SimpleDateFormat;


public class PayrollProcessor {
    private Map<String, Employee> employeeMap = new HashMap<>();
    private List<Event> events = new ArrayList<>();

    public void processEvents(List<Event> events) {
        for (Event event : events) {
            String empId = event.getEmpId();
            Employee employee;
            switch (event.getEventType()) {
                case "ONBOARD":
                    String[] details = event.getNotes().split(" ");
                    String firstName = details[0];
                    String lastName = details[1];
                    String designation = event.getValue();
                    employee = new Employee(empId, firstName, lastName, designation);
                    employee.setOnboardDate(event.getEventDate());
                    employeeMap.put(empId, employee);
                    break;
                case "EXIT":
                    employee = employeeMap.get(empId);
                    if (employee != null) {
                        employee.setExitDate(event.getEventDate());
                    }
                    break;
                case "SALARY":
                    employee = employeeMap.get(empId);
                    if (employee != null) {
                        employee.setSalary(employee.getSalary() + Double.parseDouble(event.getValue()));
                    }
                    break;
                case "BONUS":
                    employee = employeeMap.get(empId);
                    if (employee != null) {
                        employee.setBonus(employee.getBonus() + Double.parseDouble(event.getValue()));
                    }
                    break;
                case "REIMBURSEMENT":
                    employee = employeeMap.get(empId);
                    if (employee != null) {
                        employee.setReimbursement(employee.getReimbursement() + Double.parseDouble(event.getValue()));
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Unknown event type: " + event.getEventType());
            }
            this.events.add(event);
        }
    }

    public int getTotalEmployees() {
        return employeeMap.size();
    }

    public Map<String, List<Employee>> getEmployeesJoinedByMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-yyyy");
        return employeeMap.values().stream()
                .filter(e -> e.getOnboardDate() != null)
                .collect(Collectors.groupingBy(e -> sdf.format(e.getOnboardDate())));
    }

    public Map<String, List<Employee>> getEmployeesExitedByMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-yyyy");
        return employeeMap.values().stream()
                .filter(e -> e.getExitDate() != null)
                .collect(Collectors.groupingBy(e -> sdf.format(e.getExitDate())));
    }

    public Map<String, Double> getMonthlySalaryReport() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-yyyy");
        Map<String, Double> monthlySalary = new HashMap<>();
        for (Event event : events) {
            if ("SALARY".equals(event.getEventType())) {
                String month = sdf.format(event.getEventDate());
                monthlySalary.put(month, monthlySalary.getOrDefault(month, 0.0) + Double.parseDouble(event.getValue()));
            }
        }
        return monthlySalary;
    }

    public Map<String, Double> getMonthlyAmountReleased() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-yyyy");
        Map<String, Double> monthlyAmount = new HashMap<>();
        for (Event event : events) {
            if (Arrays.asList("SALARY", "BONUS", "REIMBURSEMENT").contains(event.getEventType())) {
                String month = sdf.format(event.getEventDate());
                monthlyAmount.put(month, monthlyAmount.getOrDefault(month, 0.0) + Double.parseDouble(event.getValue()));
            }
        }
        return monthlyAmount;
    }

    public Map<String, Double> getEmployeeFinancialReport() {
        Map<String, Double> financialReport = new HashMap<>();
        for (Employee employee : employeeMap.values()) {
            double total = employee.getSalary() + employee.getBonus() + employee.getReimbursement();
            financialReport.put(employee.getEmpId(), total);
        }
        return financialReport;
    }

    public List<Event> getYearlyFinancialReport() {
        return events.stream()
                .filter(e -> Arrays.asList("SALARY", "BONUS", "REIMBURSEMENT").contains(e.getEventType()))
                .collect(Collectors.toList());
    }
}
