package com.example.dataorbppp;
import com.example.dataorbppp.models.*;
import com.example.dataorbppp.services.PayrollProcessor;
import com.example.dataorbppp.utils.FileReaderUtil;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        String filePath = "C:\\Users\\VIKAS\\IdeaProjects\\DataOrbPPP\\src\\main\\resources\\payroll_events_new.csv";
        try {
            List<Event> events = FileReaderUtil.readEventsFromFile(filePath);
            PayrollProcessor processor = new PayrollProcessor();
            processor.processEvents(events);

            System.out.println("Total Employees: " + processor.getTotalEmployees());

            System.out.println("Employees Joined By Month: " + processor.getEmployeesJoinedByMonth());
            System.out.println("Employees Exited By Month: " + processor.getEmployeesExitedByMonth());
            System.out.println("Monthly Salary Report: " + processor.getMonthlySalaryReport());
            System.out.println("Employee Financial Report: " + processor.getEmployeeFinancialReport());
            System.out.println("Monthly Amount Released: " + processor.getMonthlyAmountReleased());
            System.out.println("Yearly Financial Report: " + processor.getYearlyFinancialReport());
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}

