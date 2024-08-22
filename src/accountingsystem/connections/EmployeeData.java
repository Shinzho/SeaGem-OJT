/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package accountingsystem.connections;

import java.util.Date;

/**
 *
 * @author User
 */
public class EmployeeData {

    private int id;
    private String fname;
    private String lname;
    private String email;
    private double salary;
    private String department;
    private String designation;
    private Date hireDate;
    private String address;
    private String phone;
    private Date dob;
    private String status;
    private String role;

    // New fields for working details
    private String empMonth;
    private Date startDate;
    private Date endDate;
    private int absences;
    private int allowances;
    private int overtime;
    private int lates;

  
    public EmployeeData(int id, String fname, String lname, String email, double salary,
                        String department, String designation, Date hireDate,
                        String address, String phone, Date dob, String status, String role) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.salary = salary;
        this.department = department;
        this.designation = designation;
        this.hireDate = hireDate;
        this.address = address;
        this.phone = phone;
        this.dob = dob;
        this.status = status;
        this.role = role;
    }

   
    public EmployeeData(int id, String fname, String lname, String email, double salary,
                        String department, String designation, Date hireDate,
                        String address, String phone, Date dob, String status, String role,
                        String empMonth, Date startDate, Date endDate, int absences,
                        int allowances, int overtime, int lates) {
        this(id, fname, lname, email, salary, department, designation, hireDate,
             address, phone, dob, status, role);
        this.empMonth = empMonth;
        this.startDate = startDate;
        this.endDate = endDate;
        this.absences = absences;
        this.allowances = allowances;
        this.overtime = overtime;
        this.lates = lates;
    }

   

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Getters and Setters for new fields

    public String getEmpMonth() {
        return empMonth;
    }

    public void setEmpMonth(String empMonth) {
        this.empMonth = empMonth;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getAbsences() {
        return absences;
    }

    public void setAbsences(int absences) {
        this.absences = absences;
    }

    public int getAllowances() {
        return allowances;
    }

    public void setAllowances(int allowances) {
        this.allowances = allowances;
    }

    public int getOvertime() {
        return overtime;
    }

    public void setOvertime(int overtime) {
        this.overtime = overtime;
    }

    public int getLates() {
        return lates;
    }

    public void setLates(int lates) {
        this.lates = lates;
    }
}



