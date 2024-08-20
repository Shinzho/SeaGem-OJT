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
    private Integer id;
    private String fname;
    private String lname;
    private String email;
    private Double salary;
    private String department;
    private String designation;
    private Date hireDate;
    private String address;
    private String phone;
    private Date dob;
    private String status;
    private String role;
    
    public EmployeeData(Integer id, String fname, String lname, String email, Double salary, String department, String designation, Date hireDate, String address, String phone, Date dob, String status, String role){
        
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
    
    //getter
    public Integer getId(){
        return id;
    }
    
    public String getFname(){
        return fname;
    }
    public String getLname(){
        return lname;
    }
    public String getEmail(){
        return email;
    }
    public Double getSalary(){
        return salary;
    }
    public String getDepartment(){
        return department;
    }
    public String getDesignation(){
        return designation;
    }
    public Date getHireDate(){
        return hireDate;
    }
    public String getAddress(){
    return address;
    }
    public String getPhone(){
    return phone;
    }
    public Date getDob(){
        return dob;
    }
    public String getStatus(){
        return status;
    }
    public String getRole(){
        return role;
    }
    
    //setter
    public void setId(Integer id){
        this.id = id;
    }
    public void setFname(String fname){
        this.fname = fname;
    }
    public void setLname(String lname){
        this.lname = lname;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setSalary(Double salary){
        this.salary = salary;
    }
    public void setDepartment(String department){
        this.department = department;
    }
    public void setDesignation(String designation){
        this.designation = designation;
    }
    public void setHireDate(Date hireDate){
        this.hireDate = hireDate;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }
    public void setDob(Date dob){
        this.dob = dob;
    }
    public void setStatus(String status){
        this.status = status;
    }
    public void setRole(String role){
        this.role = role;
    }
    
    
}

