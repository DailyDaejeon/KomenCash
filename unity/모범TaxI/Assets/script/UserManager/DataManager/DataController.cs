using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using SimpleJSON;

public class DataController : MonoBehaviour
{
    StudentDTO student = new StudentDTO();

    public void setStudentInfo(JSONNode sInfo)
    {
        student.id = sInfo["id"].AsInt;
        student.nickname = sInfo["nickname"].Value;
        student.password = sInfo["password"].Value;
        student.code = sInfo["group"]["code"].Value;

        JSONNode sJob = sInfo["job"];

        JobDTO jobData = new JobDTO();
        jobData.id = sJob["id"].AsInt;
        jobData.name = sJob["name"].Value;
        jobData.salary = sJob["salary"].AsInt;
        jobData.role = sJob["role"].Value;
        jobData.qualification = sJob["qualification"].Value;
        jobData.personnel = sJob["personnel"].AsInt;
        jobData.recruitType = sJob["recruitType"].Value;
        student.job = jobData;

        JSONNode sGroup = sInfo["group"];

        GroupDTO groupData = new GroupDTO();
        groupData.id = sGroup["id"].AsInt;
        groupData.code = sGroup["code"].Value;
        groupData.name = sGroup["name"].Value;
        groupData.monetary_unit_name = sGroup["monetary_unit_name"].Value;
        groupData.tax_rate = sGroup["tax_rate"].AsInt;
        groupData.tax = sGroup["tax"].AsInt;
        groupData.inflationRate = sGroup["inflationRate"].AsInt;
        student.group = groupData;

        JSONNode sTeacher = sInfo["teacher"];

        TeacherDTO teacherData = new TeacherDTO();
        teacherData.id = sTeacher["id"].AsInt;
        teacherData.email = sTeacher["email"].Value;
        teacherData.nickname = sTeacher["nickname"].Value;
        teacherData.phoneNumber = sTeacher["phoneNumber"].Value;
        student.teacher = teacherData;
    }

    public void LoadUserInfo()
    {

    }
}
