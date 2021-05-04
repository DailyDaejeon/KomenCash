using System.Collections;
using System.Collections.Generic;
using UnityEngine;

[System.Serializable]
public class GroupDTO
{
    public int id;
    public string code;
    public string name;
    public string monetary_unit_name;
    public int tax_rate;
    public int tax;
    public int inflationRate;

    public GroupDTO() { }

    public GroupDTO(int id, string code, string name, string monetary_unit_name, int tax_rate, int tax, int inflationRate)
    {
        this.id = id;
        this.code = code;
        this.name = name;
        this.monetary_unit_name = monetary_unit_name;
        this.tax_rate = tax_rate;
        this.tax = tax;
        this.inflationRate = inflationRate;
    }

    public void print()
    {
        Debug.Log("id : " + id);
        Debug.Log("code : " + code);
        Debug.Log("name : " + name);
        Debug.Log("monetary_unit_name : " + monetary_unit_name);
        Debug.Log("tax_rate : " + tax_rate);
        Debug.Log("tax : " + tax);
        Debug.Log("inflationRate : " + inflationRate);
    }
}
