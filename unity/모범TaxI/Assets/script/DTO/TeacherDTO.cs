using System.Collections;
using System.Collections.Generic;
using UnityEngine;

[System.Serializable]
public class TeacherDTO
{
    public int id;
    public string email;
    public string password;
    public string nickname;
    public string phoneNumber;

    public TeacherDTO() { }

    public TeacherDTO(int id, string email, string password, string nickname, string phoneNumber)
    {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
    }

    public void print()
    {
        Debug.Log("id : " + id);
        Debug.Log("email : " + email);
        Debug.Log("password : " + password);
        Debug.Log("nickname : " + nickname);
        Debug.Log("phoneNumber : " + phoneNumber);
    }
}
