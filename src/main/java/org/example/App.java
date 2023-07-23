package org.example;

import org.example.config.Config;
import org.example.model.Employee;
import org.example.model.Job;
import org.example.service.impl.EmployeeServiceImpl;
import org.example.service.impl.JobServiceImpl;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Config.getConnection();
        EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
        JobServiceImpl jobService = new JobServiceImpl();

        Scanner scanner = new Scanner(System.in);
        int num;
        while (true){
            System.out.println("""
                    1.Create table Job
                    2.Create table Employee
                    3.Add job
                    4.Add employee
                    5.Get job by ID
                    6.Sort job by Experience
                    7.Get job by Employee ID
                    8.Delete Description column
                    9.Update employee
                    10.Get all employees
                    11.Find employee by email
                    12.Get employee by ID
                    13.Get employee by position
                    14.Clean Employee table
                    15.Drop Employee table
                    """);
            num=scanner.nextInt();
            switch (num) {
                case 1 -> {
                    jobService.createJobTable();
                }
                case 2 -> {
                    employeeService.createEmployee();
                }
                case 3 -> {
                    jobService.addJob(new Job("Mentor", "JS", "Back-end", 6));
                }
                case 4 -> {
                    employeeService.addEmployee(new Employee("Januzak", "Anashov", 23, "januzak@mail.ru", 1));
                }
                case 5 -> {
                    System.out.println(jobService.getJobById(1L));
                }
                case 6 -> {
                    System.out.println(jobService.sortByExperience("desc"));
                }
                case 7 -> {
                    System.out.println(jobService.getJobByEmployeeId(1L));
                }
                case 8 -> {
                    jobService.deleteDescriptionColumn();
                }
                case 9 -> {
                    employeeService.updateEmployee(1L, new Employee("Januzak", "Anashov", 23, "januzak@mail.ru", 1));
                }
                case 10 -> {
                    employeeService.getAllEmployees().forEach(System.out::println);
                }
                case 11 -> {
                    System.out.println(employeeService.findByEmail("januzak@mail.ru"));
                }
                case 12 -> {
                    System.out.println(employeeService.getEmployeeById(1L));
                }
                case 13 -> {
                    System.out.println(employeeService.getEmployeeByPosition("Instructor"));
                }
                case 14 -> {
                    employeeService.cleanTable();
                }
                case 15 -> {
                    employeeService.dropTable();
                }
            }
        }
    }
}
