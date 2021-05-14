using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class JobMenuController : MonoBehaviour
{
  private string baseURL = "http://k4b203.p.ssafy.io:8081/api/";
  public RectTransform uiGroup;
  PlayerController enterPlayer;

  [SerializeField]
  private GameObject GenericContent;

  [SerializeField]
  private GameObject GContentForm;

  [SerializeField]
  private GameObject BContentForm;

  [SerializeField]
  private Button BankerButton;

  private bool _isExit;
  public bool GetExitState()
  {
    return _isExit;
  }

  public void Enter(PlayerController player)
  {
    enterPlayer = player;

    uiGroup.anchoredPosition = Vector3.zero;
    _isExit = false;
  }

  public void Exit()
  {
    uiGroup.anchoredPosition = Vector3.down * -1000;
    // ObjectActive("GenericMember", -1);

    // if (noneAHClone != null) Destroy(noneAHClone);
    // if (AHClone != null)
    // {
    //   foreach (GameObject item in AHClone)
    //   {
    //     Destroy(item);
    //   }
    // }
    // if (noneFPClone != null) Destroy(noneFPClone);
    // if (FPClone != null)
    // {
    //   foreach (GameObject item in FPClone)
    //   {
    //     Destroy(item);
    //   }
    // }

    _isExit = true;
  }
}
