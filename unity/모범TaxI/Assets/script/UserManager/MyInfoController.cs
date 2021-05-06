using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using SimpleJSON;

public class MyInfoController : MonoBehaviour
{
    //private DataController myInfo;
    private StudentDTO data;
    
    void Start()
    {
        //myInfo = GameObject.Find("DataManager").GetComponent<DataController>();
        /*data = DataController.LoadUserInfo();

        Debug.Log("로그인 유저 정보 : ");
        data.print();*/
    }

    void Update()
    {
        
    }
}
