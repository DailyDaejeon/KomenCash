using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class SceneChangeController : MonoBehaviour
{
    public void GoToJoinForm()
    {
        SceneManager.LoadScene("JoinScene");
    }

    public void GoToLoginForm()
    {
        SceneManager.LoadScene("LoginScene");
    }

    public void GoToMainScene()
    {
        SceneManager.LoadScene("MainScene");
    }
}
