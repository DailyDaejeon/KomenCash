using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class AlertTestController : MonoBehaviour
{
    public void ShowAlertTest()
    {
        string title = "닉네임 또는 비밀번호를 잊으셨나요?";
        string message = "닉네임 또는 비밀번호를 잊으셨을 때는 선생님께 문의하세요!";

        AlertViewController.Show(title, message);

        //버튼 옵션을 주고 싶을 때
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
