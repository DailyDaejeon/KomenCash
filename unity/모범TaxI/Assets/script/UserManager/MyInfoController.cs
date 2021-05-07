using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using SimpleJSON;

public class MyInfoController : MonoBehaviour
{
    private StudentDTO data;
    public Text sNickname; //�г���
    public Text sJobName;  //������
    public Text sSalary;   //����
    public Text sBalance;  //�����ܰ�
    public Text sCertList; //�ڰ��� ���
    
    void Start()
    {
        data = DataController.LoadUserInfo();

        Debug.Log("�α��� ���� ���� : ");
        data.print();

        sNickname.text += data.nickname;
        sJobName.text += data.job.name;
        sSalary.text += data.job.salary;
        sBalance.text += data.balance;
        
        if(data.certificateList.Count != 0)
        {
            foreach(string cert in data.certificateList)
            {
                sCertList.text += cert + System.Environment.NewLine;
            }
        }else
        {
            sCertList.text += "���� �������� �ڰ����� �����ϴ٤�.��";
        }
    }
}
