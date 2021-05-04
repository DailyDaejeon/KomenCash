using System.Collections;
using System.Collections.Generic;
using UnityEngine;

[System.Serializable]
public class StudentDTO
{
    public int id; //�л� id
    public string nickname; //�г���
    public string password; //��й�ȣ
    public string code; //�׷��ڵ�
    public JobDTO job; //��������
    public GroupDTO group; //�׷�����
    public TeacherDTO teacher; //��������

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

    public StudentDTO(int id, string nickname, string password, string code, JobDTO job, GroupDTO group, TeacherDTO teacher)
    {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.code = code;
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
        Debug.Log("job info : " + job);
        Debug.Log("group info : " + group);
        Debug.Log("teacher info : " + teacher);
    }
}
