using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class AlertTestController : MonoBehaviour
{
    public void ShowAlertTest()
    {
        string title = "�г��� �Ǵ� ��й�ȣ�� �����̳���?";
        string message = "�г��� �Ǵ� ��й�ȣ�� �������� ���� �����Բ� �����ϼ���!";

        AlertViewController.Show(title, message);

        //��ư �ɼ��� �ְ� ���� ��
        /*
        AlertViewController.Show(title, message, new AlertViewOptions
        {
            cancelButtonTitle = "NO", cancelButtonDelegate = () =>
            {
                Debug.Log("No");
            },
            okButtonTitle = "YES", okButtonDelegate = () =>
            {
                Debug.Log("Yes");
            }
        });
        */
    }
}
