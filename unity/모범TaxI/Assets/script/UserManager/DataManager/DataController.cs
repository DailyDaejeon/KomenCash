using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using SimpleJSON;

public static class DataController
{
    public static StudentDTO student = new StudentDTO();

    //�α��� ���� ��, ���� ����(�⺻, ����, �׷�, ������) �����ϴ� �޼���
    public static void setStudentInfo(JSONNode sInfo)
    {
        student.id = sInfo["id"].AsInt;
        student.nickname = sInfo["nickname"].Value;
        student.password = sInfo["password"].Value;
        student.code = sInfo["job"]["group"]["code"].Value;

        JSONNode sJob = sInfo["job"];

        /*
        Debug.Log("--------------- sJob ---------------");
        //JSONNode ��� ��� 1
        Debug.Log(sJob.ToString());
        
        //JSONNode ��� ��� 2
        foreach (KeyValuePair<string, JSONNode> keyValue in sJob)
        {
            Debug.Log(keyValue.Key + " : " + keyValue.Value);
        }
        */

        JobDTO jobData = new JobDTO();
        jobData.id = sJob["id"].AsInt;
        jobData.name = sJob["name"].Value;
        jobData.salary = sJob["salary"].AsInt;
        jobData.role = sJob["role"].Value;
        jobData.qualification = sJob["qualification"].Value;
        jobData.personnel = sJob["personnel"].AsInt;
        jobData.recruitType = sJob["recruitType"].Value;
        student.job = jobData;

        JSONNode sGroup = sJob["group"];

        /*Debug.Log("--------------- sGroup ---------------");
        Debug.Log(sGroup.ToString());*/

        GroupDTO groupData = new GroupDTO();
        groupData.id = sGroup["id"].AsInt;
        groupData.code = sGroup["code"].Value;
        groupData.name = sGroup["name"].Value;
        groupData.monetary_unit_name = sGroup["monetary_unit_name"].Value;
        groupData.tax_rate = sGroup["tax_rate"].AsDouble;
        groupData.tax = sGroup["tax"].AsInt;
        groupData.inflationRate = sGroup["inflationRate"].AsDouble;
        student.group = groupData;

        JSONNode sTeacher = sGroup["teacher"];

        /*Debug.Log("--------------- sTeacher ---------------");
        Debug.Log(sTeacher.ToString());*/

        TeacherDTO teacherData = new TeacherDTO();
        teacherData.id = sTeacher["id"].AsInt;
        teacherData.email = sTeacher["email"].Value;
        teacherData.nickname = sTeacher["nickname"].Value;
        teacherData.phoneNumber = sTeacher["phoneNumber"].Value;
        student.teacher = teacherData;

        //�� ���� Ȯ�ο� ���
        //student.print();
    }

    public static void setStudentStatInfo(JSONNode sInfo)
    {
        student.balance = sInfo["balance"].AsInt;
        Debug.Log("���� �ܰ� : " + student.balance);

        List<string> list = new List<string>();
        if(sInfo["certificates"].Count != 0)
        {
            foreach(JSONNode item in sInfo["certificates"])
            {
                Debug.Log(item["name"].Value);
                list.Add(item);
            }
        }
        else
        {
            Debug.Log("�ڰ��� ����");
        }
        student.certificateList = list;
    }

    //�α��� �� ���� ���� �ҷ����� �޼���
    public static StudentDTO LoadUserInfo()
    {
        Debug.Log("����� ���� ����");
        student.print();
        return student;
    }
}
