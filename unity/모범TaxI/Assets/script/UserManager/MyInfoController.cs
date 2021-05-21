using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using SimpleJSON;
using System;

public class MyInfoController : MonoBehaviour
{
  private StudentDTO data;
  public Text sNickname; //닉네임
  public Text sJobName;  //직업명
  public Text sSalary;   //월급
  public Text sBalance;  //통장잔고
  // public Text sCertList; //자격증 목록
  private SceneChangeController loginForm;

  void Start()
  {
    setUserInfo();
  }

  public void setUserInfo()
  {
    try
    {
      data = DataController.LoadUserInfo();
      Debug.Log("로그인 유저 정보 : ");
      data.print();

      sNickname.text += data.nickname;
      sJobName.text += data.job.name;
      sSalary.text += GetThousandCommaText(data.job.salary) + " " + data.group.monetary_unit_name;
      sBalance.text += GetThousandCommaText(data.balance) + " " + data.group.monetary_unit_name;

      Transform parent = GameObject.Find("statCListContent").GetComponent<Transform>();
      GameObject certificateItem = Resources.Load("cList") as GameObject;
      if (data.certificateList.Count != 0)
      {
        Debug.Log("자격증 목록 : " + data.certificateList);
        foreach (JSONNode cert in data.certificateList)
        {
          GameObject clone = Instantiate(certificateItem);
          Text cItem = clone.transform.GetComponent<Text>();
          cItem.text = cert["name"].Value;

          cItem.transform.SetParent(parent);
        }
      }
      else
      {
        GameObject clone = Instantiate(certificateItem);
        Text cItem = clone.transform.GetComponent<Text>();
        cItem.text += "아직 보유중인 자격증이 없습니다ㅠ.ㅠ";
      }
    }
    catch (NullReferenceException ie)
    {
      Debug.Log("로그인 후 이용 가능한 서비스 입니다.");
      Debug.Log(ie);
      loginForm = GameObject.Find("MainManager").GetComponent<SceneChangeController>();
      loginForm.GoToLoginForm();
    }
  }

  public static string GetThousandCommaText(int data)
  {
    return string.Format("{0:#,###}", data);
  }
}
