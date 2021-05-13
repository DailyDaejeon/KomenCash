using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.Networking;
using SimpleJSON;

public class BankMenuController : MonoBehaviour
{
  public string baseURL = "http://k4b203.p.ssafy.io:8081/api/";
  public RectTransform uiGroup;
  PlayerController enterPlayer;

  //메뉴창 버튼
  [SerializeField]
  private GameObject GenericContent;
  [SerializeField]
  private GameObject BankerContent;

  [SerializeField]
  private GameObject GContentForm;

  [SerializeField]
  private GameObject BContentForm;

  [SerializeField]
  private Button BankerButton;

  //오브젝트 담을 리스트
  private List<GameObject> objectList = new List<GameObject>();


  //컨트롤러에서 사용하는 변수
  bool _isExit = true;
  int sId;
  int gId;

  //생성해야하는 prefab
  [SerializeField]
  private GameObject noneAccountHistory;
  private GameObject noneAHClone;
  private RectTransform nAHRect;

  [SerializeField]
  private GameObject accountHistory;
  private List<GameObject> AHClone;
  private RectTransform AHRect;

  void Start()
  {
    objectList.Add(GenericContent); //GenericMember
    objectList.Add(BankerContent);  //JobMember
    objectList.Add(GContentForm);   //GenericContentForms
    objectList.Add(BContentForm);   //JobContentForms

    sId = DataController.GetStudentId();
    gId = DataController.GetGroupId();
  }

  public bool GetExitState()
  {
    return _isExit;
  }

  public void Enter(PlayerController player)
  {
    enterPlayer = player;
    /* !!!은행원 직업일 때만 은행원 버튼 보이는 코드!!! 지우지 말기!!!  */
    // if (false == (DataController.LoadUserInfo().job.name.Equals("은행원") || DataController.LoadUserInfo().job.name.Equals("대통령")))
    // {
    //   BankerButton.gameObject.SetActive(false);
    // }

    uiGroup.anchoredPosition = Vector3.zero;
    _isExit = false;
  }

  public void Exit()
  {
    uiGroup.anchoredPosition = Vector3.down * -1000;
    ObjectActive("GenericMember", -1);

    if (noneAHClone != null) Destroy(noneAHClone);
    if (AHClone != null)
    {
      foreach (GameObject item in AHClone)
      {
        Destroy(item);
      }
    }

    _isExit = true;
  }

  /* 은행 메뉴에 보여줄 데이터 받아오는 메서드들 */

  //1. 내 통장 내역 확인
  private IEnumerator GetMyAccountHistory()
  {
    using (UnityWebRequest request = UnityWebRequest.Get(baseURL + "ubank/myAccount/" + sId))
    {
      yield return request.SendWebRequest();

      if (request.error != null)
      {
        Debug.Log(request.error);
      }
      else
      {
        string result = request.downloadHandler.text;
        JSONNode root = JSON.Parse(result);

        if (root.Count <= 0)
        {
          //통장 내역이 없으면 noneAccountHistory 프리팹 생성해서 안내문 출력
          Transform parent = GameObject.Find("B1AHContent").GetComponent<Transform>();
          noneAHClone = Instantiate(noneAccountHistory);
          nAHRect = noneAHClone.GetComponent<RectTransform>();

          noneAHClone.transform.SetParent(parent);
        }
        else
        {
          //통장 내역이 있으면 for문 이용해서 accountHistory 프리팹 생성해서 내용 넣고 출력
          Transform parent = GameObject.Find("B1AHContent").GetComponent<Transform>();
          accountHistory = Resources.Load("AccountHistory") as GameObject;
          for (int i = root.Count - 1; i >= 0; i--)
          {
            GameObject clone = Instantiate(accountHistory);
            Text date = clone.transform.GetChild(0).GetComponent<Text>();
            Text content = clone.transform.GetChild(1).GetComponent<Text>();
            Text balanceChange = clone.transform.GetChild(2).GetComponent<Text>();
            Text balance = clone.transform.GetChild(3).GetComponent<Text>();

            string[] test = root[i]["createdDate"].Value.Split('T');
            date.text = test[0];
            content.text = root[i]["content"].Value;
            balanceChange.text = root[i]["balanceChange"].Value;
            balance.text = root[i]["balance"].Value;

            clone.transform.SetParent(parent);
            AHClone.Add(clone);
          }
        }
      }
    }
  }

  //2. 가입 금융 상품 보기
  private IEnumerator GetMyFinancialProdList()
  {
    using (UnityWebRequest request = UnityWebRequest.Get(baseURL + "bank/financial-product/student/" + sId))
    {
      yield return request.SendWebRequest();

      if (request.error != null)
      {
        Debug.Log(request.error);
      }
      else
      {
        string result = request.downloadHandler.text;
        JSONNode root = JSON.Parse(result);

        if (root.Count <= 0)
        {
          //가입한 금융 상품이 없다면 안내창 출력
        }
        else
        {
          //가입한 금융 상품이 있다면 해당 목록 출력
        }
      }
    }
  }

  //3. 금융 상품 중도 해지하기
  // private IEnumerator RevocFinanProd(){

  // }

  //4. 금융 상품 목록 조회
  private IEnumerator GetFinanProdList()
  {
    using (UnityWebRequest request = UnityWebRequest.Get(baseURL + "bank/" + gId + "/financial-product"))
    {
      yield return request.SendWebRequest();

      if (request.error != null)
      {
        Debug.Log(request.error);
      }
      else
      {
        Debug.Log(request.downloadHandler.text);
      }
    }
  }

  //5. 금융 상품 신청하기

  //6. 월급 확인 리스트
  private IEnumerator GetSCList() //Salary Confirm List
  {
    using (UnityWebRequest request = UnityWebRequest.Get(baseURL + "ubank/salary-payment/" + gId))
    {
      yield return request.SendWebRequest();

      if (request.error != null)
      {
        Debug.Log(request.error);
      }
      else
      {
        Debug.Log("response : " + request.downloadHandler.text);
      }
    }
  }

  //버튼 클릭하면 창 넘어가는 메서드들
  public void OnPressGenericButton()
  {
    if (noneAHClone != null) Destroy(noneAHClone);
    if (AHClone != null)
    {
      foreach (GameObject item in AHClone)
      {
        Destroy(item);
      }
    }
    ObjectActive("GenericMember", -1);
  }

  public void OnPressBankerButton()
  {
    if (noneAHClone != null) Destroy(noneAHClone);
    if (AHClone != null)
    {
      foreach (GameObject item in AHClone)
      {
        Destroy(item);
      }
    }
    ObjectActive("JobMember", -1);
  }

  public void OnPressMyAccountHistory()
  {
    AHClone = new List<GameObject>();
    StartCoroutine(GetMyAccountHistory());
    ObjectActive("GenericContentForms", 0);
  }

  public void OnPressMyFinanProd()
  {
    StartCoroutine(GetMyFinancialProdList());
    ObjectActive("GenericContentForms", 1);
  }

  public void OnPressFinanProdList()
  {
    StartCoroutine(GetFinanProdList());
    ObjectActive("GenericContentForms", 2);
  }

  public void OnPressSalarySetting()
  {
    StartCoroutine(GetSCList());
    ObjectActive("JobContentForms", 0);
  }

  public void OnPressFPRequestSetting()
  {
    ObjectActive("JobContentForms", 1);
  }

  public void OnPressFPRevocationSetting()
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
