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
    groupData.monetary_unit_name = sGroup["monetaryUnitName"].Value;
    groupData.tax_rate = sGroup["taxRate"].AsDouble;
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

    List<JSONNode> list = new List<JSONNode>();
    if (sInfo["certificates"].Count != 0)
    {
      foreach (JSONNode item in sInfo["certificates"])
      {
        list.Add(item);
      }
    }
    else
    {
      Debug.Log("자격증 없음");
    }
    student.certificateList = list;
  }

  public static void setSalary(int salary)
  {
    student.job.salary = salary;
  }

  public static void setBalance(int balance)
  {
    student.balance = balance;
  }

  public static void setTax(int tax)
  {
    student.group.tax = tax;
  }

  //로그인 한 유저 정보 불러오는 메서드
  public static StudentDTO LoadUserInfo()
  {
    return student;
  }

  public static int GetGroupId()
  {
    return student.group.id;
  }

  public static int GetStudentId()
  {
    return student.id;
  }

  public static string GetStudentNickname()
  {
    return student.nickname;
  }

  public static string GetMonetaryUnitName()
  {
    return student.group.monetary_unit_name;
  }

  public static int GetBalance()
  {
    return student.balance;
  }

  public static int GetTax()
  {
    return student.group.tax;
  }

  public static double GetTaxRate()
  {
    return student.group.tax_rate;
  }

  public static void clearUserInfo()
  {
    student.id = 0;
    student.nickname = "";
    student.password = "";
    student.code = "";
    student.balance = 0;
    student.certificateList = new List<JSONNode>();

    student.job = new JobDTO();
    student.group = new GroupDTO();
    student.teacher = new TeacherDTO();
  }
}
