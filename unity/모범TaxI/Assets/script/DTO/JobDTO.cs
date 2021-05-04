using System.Collections;
using System.Collections.Generic;
using UnityEngine;

[System.Serializable]
public class JobDTO
{
    public int id;
    public string name;
    public int salary;
    public string role;
    public string qualification;
    public int personnel;
    public string recruitType;

    public JobDTO() { }

    public JobDTO(int id, string name, int salary, string role, string qualification, int personnel, string recruitType)
    {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.role = role;
        this.qualification = qualification;
        this.personnel = personnel;
        this.recruitType = recruitType;
    }

    public void print()
    {
        Debug.Log("id : " + id);
        Debug.Log("name : " + name);
        Debug.Log("salary : " + salary);
        Debug.Log("role : " + role);
        Debug.Log("qualification : " + qualification);
        Debug.Log("personnel : " + personnel);
        Debug.Log("recruitType : " + recruitType);
    }
   }
