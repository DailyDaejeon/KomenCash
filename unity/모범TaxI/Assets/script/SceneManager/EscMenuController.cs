using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class EscMenuController : MonoBehaviour
{
  public RectTransform uiGroup;
  PlayerController enterPlayer;

  bool _isExit;

  public bool GetExitState()
  {
    return _isExit;
  }

  public void Enter(PlayerController player)
  {
    enterPlayer = player;
    Debug.Log(uiGroup + "이 뭐냐");
    uiGroup.anchoredPosition = Vector3.zero;
    _isExit = false;
  }

  public void Exit()
  {
    uiGroup.anchoredPosition = Vector3.down * -1000;
    _isExit = true;
  }

  private void Logout()
  {
    string title = "";
    string message = "로그아웃 하시겠습니까?";
    AlertViewController.Show(title, message, new AlertViewOptions
    {
      okButtonTitle = "로그아웃",
      okButtonDelegate = () =>
      {
        DataController.clearUserInfo();
        SceneChangeController logout = new SceneChangeController();
        logout.GoToLoginForm();
      },
      cancelButtonTitle = "돌아가기",
      cancelButtonDelegate = () => { Exit(); }
    });
  }

  private void ExitGame()
  {
    string title = "";
    string message = "게임을 종료 하시겠습니까?";
    AlertViewController.Show(title, message, new AlertViewOptions
    {
      okButtonTitle = "종료하기",
      okButtonDelegate = () =>
      {
        Application.Quit();
      },
      cancelButtonTitle = "돌아가기",
      cancelButtonDelegate = () => { Exit(); }
    });
  }

  private void ReturnGame()
  {
    Exit();
  }

  public void OnPressReturnButton()
  {
    ReturnGame();
  }

  public void OnPressLogoutButton()
  {
    Logout();
  }

  public void OnPressGameExit()
  {
    ExitGame();
  }
}
