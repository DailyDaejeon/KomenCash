using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class BankMenuController : MonoBehaviour
{
  public RectTransform uiGroup;
  PlayerController enterPlayer;

  [SerializeField]
  private GameObject GenericContent;
  [SerializeField]
  private GameObject BankerContent;

  [SerializeField]
  private GameObject GContentForm;

  [SerializeField]
  private GameObject BContentForm;

  private List<GameObject> objectList = new List<GameObject>();

  bool _isExit = true;

  void Start()
  {
    objectList.Add(GenericContent); //GenericMember
    objectList.Add(BankerContent);  //JobMember
    objectList.Add(GContentForm);   //GenericContentForms
    objectList.Add(BContentForm);
  }

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
    uiGroup.anchoredPosition = Vector3.down * 500;
    _isExit = true;
  }

  public void OnPressGenericButton()
  {
    ObjectActive("GenericMember", -1);
  }

  public void OnPressBankerButton()
  {
    ObjectActive("JobMember", -1);
  }

  public void OnPressMyAccountHistory()
  {
    ObjectActive("GenericContentForms", 0);
  }

  public void OnPressMyFinanProd()
  {
    ObjectActive("GenericContentForms", 1);
  }

  public void OnPressFinanProdList()
  {
    ObjectActive("GenericContentForms", 2);
  }

  public void OnPressMemberAccountSetting()
  {
    ObjectActive("JobContentForms", 0);
  }

  public void OnPressFinanProdRequestList()
  {
    ObjectActive("JobContentForms", 1);
  }

  public void OnPressExpirationFinanProdList()
  {
    ObjectActive("JobContentForms", 2);
  }

  private void ObjectActive(string ojName, int index)
  {
    if (index == -1)
    {
      foreach (GameObject item in objectList)
      {
        if (ojName.Equals(item.name))
        {
          item.SetActive(true);
        }
        else
        {
          item.SetActive(false);
        }
      }
    }
    else if (index > -1)
    {
      foreach (GameObject item in objectList)
      {
        if (ojName.Equals(item.name))
        {
          item.SetActive(true);
          for (int i = 0; i < item.transform.childCount; i++)
          {
            if (i == index) item.transform.GetChild(i).gameObject.SetActive(true);
            else item.transform.GetChild(i).gameObject.SetActive(false);
          }
        }
        else
        {
          item.SetActive(false);
        }
      }
    }
  }
}
