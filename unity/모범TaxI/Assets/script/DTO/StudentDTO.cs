using System.Collections;
using System.Collections.Generic;
using UnityEngine;

[System.Serializable]
public class StudentDTO
{
    public int id; //학생 id
    public string nickname; //닉네임
    public string password; //비밀번호
    public string code; //그룹코드
    public int balance; //통장잔고
    public List<string> certificateList; //보유 자격증
    public JobDTO job; //직업정보
    public GroupDTO group; //그룹정보
    public TeacherDTO teacher; //교사정보

    public StudentDTO() {}

    public StudentDTO(string nickname, string password)
    {
        this.nickname = nickname;
        this.password = password;
    }

    public StudentDTO(string nickname, string password, string code)
    {
        this.nickname = nickname;
        this.password = password;
        this.code = code;
    }

    public StudentDTO(int id, string nickname, string password, string code, int balance, List<string> certificateList, JobDTO job, GroupDTO group, TeacherDTO teacher)
    {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.code = code;
        this.balance = balance;
        this.certificateList = certificateList;
        this.job = job;
        this.group = group;
        this.teacher = teacher;
    }

    public void print()
    {
        Debug.Log("id : " + id);
        Debug.Log("nickname : " + nickname);
        Debug.Log("password : " + password);
        Debug.Log("code : " + code);
        Debug.Log("balance : " + balance);
        string list = "";
        foreach (string key in certificateList)
        {
            list += key + " ";
        }
        Debug.Log("certificate list : " + list);
        Debug.Log("--- job info ---");
        job.print();
        Debug.Log("--- group info ---"); 
        group.print();
        Debug.Log("--- teacher info ---");
        teacher.print();
    }
}
