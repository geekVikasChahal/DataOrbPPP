package com.example.dataorbppp;

import com.example.dataorbppp.services.PayrollProcessor;
import org.junit.jupiter.api.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import com.example.dataorbppp.models.*;
import org.junit.jupiter.api.Test;


public class PayrollProcessorTest {
    @Test
    public void testProcessEvents() {
        List<Event> events = Arrays.asList(
                new Event(1, "emp101", "ONBOARD", "Software Engineer", new Date(), "Bill Gates is going to join DataOrb on 1st November as a SE."),
                new Event(2, "emp102", "ONBOARD", "Architect", new Date(), "Steve Jobs joined DataOrb on 1st October as an Architect.")
        );
        PayrollProcessor payrollProcessor = new PayrollProcessor();
        payrollProcessor.processEvents(events);

        assertEquals(2, payrollProcessor.getTotalEmployees());
    }

    @Test
    public void testEmployeesJoinedByMonth() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        List<Event> events = Arrays.asList(
                new Event(1, "emp101", "ONBOARD", "Software Engineer", sdf.parse("01-11-2023"), "Bill Gates is going to join DataOrb on 1st November as a SE."),
                new Event(2, "emp102", "ONBOARD", "Architect", sdf.parse("01-10-2023"), "Steve Jobs joined DataOrb on 1st October as an Architect.")
        );
        PayrollProcessor payrollProcessor = new PayrollProcessor();
        payrollProcessor.processEvents(events);

        Map<String, List<Employee>> joinedByMonth = payrollProcessor.getEmployeesJoinedByMonth();
        assertEquals(1, joinedByMonth.getOrDefault("11-2023", new ArrayList<>()).size());
        assertEquals(1, joinedByMonth.getOrDefault("10-2023", new ArrayList<>()).size());
    }

    @Test
    public void testMonthlySalaryReport() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        List<Event> events = Arrays.asList(
                new Event(1, "emp101", "ONBOARD", "Software Engineer", sdf.parse("01-11-2022"), "Bill Gates is going to join DataOrb on 1st November as a SE."),
                new Event(2, "emp102", "ONBOARD", "Architect", sdf.parse("01-10-2022"), "Steve Jobs joined DataOrb on 1st October as an Architect."),
                new Event(3, "emp101", "SALARY", "4000", sdf.parse("01-10-2022"), "October salary"),
                new Event(4, "emp102", "SALARY", "3000", sdf.parse("01-10-2022"), "October salary")
        );
        PayrollProcessor payrollProcessor = new PayrollProcessor();
        payrollProcessor.processEvents(events);

        Map<String, Double> monthlySalary = payrollProcessor.getMonthlySalaryReport();
        assertEquals(7000.0, monthlySalary.get("10-2022"));
    }

    @Test
    public void testEmployeeFinancialReport() {
        List<Event> events = Arrays.asList(
                new Event(1, "emp101", "ONBOARD", "Software Engineer", new Date(), "Bill Gates is going to join DataOrb on 1st November as a SE."),
                new Event(2, "emp102", "ONBOARD", "Architect", new Date(), "Steve Jobs joined DataOrb on 1st October as an Architect."),
                new Event(3, "emp101", "SALARY", "4000", new Date(), "October salary"),
                new Event(4, "emp102", "SALARY", "3000", new Date(), "October salary"),
                new Event(5, "emp101", "BONUS", "1000", new Date(), "Yearly bonus"),
                new Event(6, "emp102", "REIMBURSEMENT", "200", new Date(), "Travel reimbursement")
        );
        PayrollProcessor payrollProcessor = new PayrollProcessor();
        payrollProcessor.processEvents(events);

        Map<String, Double> financialReport = payrollProcessor.getEmployeeFinancialReport();
        assertEquals(5000.0, financialReport.get("emp101"));
        assertEquals(3200.0, financialReport.get("emp102"));
    }

    @Test
    public void testMonthlyAmountReleased() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        List<Event> events = Arrays.asList(
                new Event(1, "emp101", "ONBOARD", "Software Engineer", sdf.parse("01-11-2022"), "Bill Gates is going to join DataOrb on 1st November as a SE."),
                new Event(2, "emp102", "ONBOARD", "Architect", sdf.parse("01-10-2022"), "Steve Jobs joined DataOrb on 1st October as an Architect."),
                new Event(3, "emp101", "SALARY", "4000", sdf.parse("01-10-2022"), "October salary"),
                new Event(4, "emp102", "SALARY", "3000", sdf.parse("01-10-2022"), "October salary"),
                new Event(5, "emp101", "BONUS", "1000", sdf.parse("01-10-2022"), "Yearly bonus"),
                new Event(6, "emp102", "REIMBURSEMENT", "200", sdf.parse("01-10-2022"), "Travel reimbursement")
        );
        PayrollProcessor payrollProcessor = new PayrollProcessor();
        payrollProcessor.processEvents(events);

        Map<String, Double> monthlyAmount = payrollProcessor.getMonthlyAmountReleased();
        assertEquals(8200.0, monthlyAmount.get("10-2022"));
    }

    @Test
    public void testYearlyFinancialReport() {
        List<Event> events = Arrays.asList(
                new Event(1, "emp101", "ONBOARD", "Software Engineer", new Date(), "Bill Gates is going to join DataOrb on 1st November as a SE."),
                new Event(2, "emp102", "ONBOARD", "Architect", new Date(), "Steve Jobs joined DataOrb on 1st October as an Architect."),
                new Event(3, "emp101", "SALARY", "4000", new Date(), "October salary"),
                new Event(4, "emp102", "SALARY", "3000", new Date(), "October salary"),
                new Event(5, "emp101", "BONUS", "1000", new Date(), "Yearly bonus"),
                new Event(6, "emp102", "REIMBURSEMENT", "200", new Date(), "Travel reimbursement")
        );
        PayrollProcessor payrollProcessor = new PayrollProcessor();
        payrollProcessor.processEvents(events);

        List<Event> yearlyReport = payrollProcessor.getYearlyFinancialReport();
        assertEquals(4, yearlyReport.size());
    }

    @Test
    public void testEmployeesExitedByMonth() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        List<Event> events = Arrays.asList(
                new Event(1, "emp101", "ONBOARD", "Software Engineer", sdf.parse("01-11-2022"), "Bill Gates is going to join DataOrb on 1st November as a SE."),
                new Event(2, "emp102", "ONBOARD", "Architect", sdf.parse("01-10-2022"), "Steve Jobs joined DataOrb on 1st October as an Architect."),
                new Event(3, "emp101", "EXIT", "", sdf.parse("31-12-2022"), "Bill Gates is exiting DataOrb on 31st December."),
                new Event(4, "emp102", "EXIT", "", sdf.parse("30-11-2022"), "Steve Jobs is exiting DataOrb on 30th November.")
        );
        PayrollProcessor payrollProcessor = new PayrollProcessor();
        payrollProcessor.processEvents(events);

        Map<String, List<Employee>> exitedByMonth = payrollProcessor.getEmployeesExitedByMonth();
        assertEquals(1, exitedByMonth.getOrDefault("12-2022", new ArrayList<>()).size());
        assertEquals(1, exitedByMonth.getOrDefault("11-2022", new ArrayList<>()).size());
    }
}
