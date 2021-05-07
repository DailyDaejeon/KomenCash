using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using SimpleJSON;

public static class DataController
{
    public static StudentDTO student = new StudentDTO();

    //로그인 성공 시, 유저 정보(기본, 직업, 그룹, 선생님) 저장하는 메서드
    public static void setStudentInfo(JSONNode sInfo)
    {
        student.id = sInfo["id"].AsInt;
        student.nickname = sInfo["nickname"].Value;
        student.password = sInfo["password"].Value;
        student.code = sInfo["job"]["group"]["code"].Value;

        JSONNode sJob = sInfo["job"];

        /*
        Debug.Log("--------------- sJob ---------------");
        //JSONNode 출력 방법 1
        Debug.Log(sJob.ToString());
        
        //JSONNode 출력 방법 2
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

        //잘 들어갔나 확인용 출력
        //student.print();
    }

    public static void setStudentStatInfo(JSONNode sInfo)
    {
        student.balance = sInfo["balance"].AsInt;
        Debug.Log("통장 잔고 : " + student.balance);

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
            Debug.Log("자격증 없음");
        }
        student.certificateList = list;
    }

    //로그인 한 유저 정보 불러오는 메서드
    public static StudentDTO LoadUserInfo()
    {
        Debug.Log("저장된 유저 정보");
        student.print();
        return student;
    }
}
