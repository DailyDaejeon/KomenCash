﻿using System.Collections;
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
  public Text sCertList; //자격증 목록
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
      sSalary.text += data.job.salary;
      sBalance.text += data.balance;

      if (data.certificateList.Count != 0)
      {
        foreach (string cert in data.certificateList)
        {
          sCertList.text += cert + System.Environment.NewLine;
        }
      }
      else
      {
        sCertList.text += "아직 보유중인 자격증이 없습니다ㅠ.ㅠ";
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
}
