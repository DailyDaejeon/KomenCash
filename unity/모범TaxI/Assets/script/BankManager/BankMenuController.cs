using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BankMenuController : MonoBehaviour
{
    public void ShowBankMenu()
    {
        MenuController.Show(new MenuButtonOptions
        {
            genericButtonTitle = "개인 업무",
            genericButtonDelegate = () =>
            {
                Debug.Log("개인업무임");
            },
            jobButtonTitle = "은행원 업무",
            jobButtonDelegate = () =>
            {
                Debug.Log("은행원 업무임");
            }
        });
    }
}
