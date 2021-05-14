using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.Networking;
using SimpleJSON;

[System.Serializable]
public class EarlyRedemptionData
{
  public string id;
}

[System.Serializable]
public class FinanProdRegistData
{
  public string financialProductDetailId;
  public int studentId;
}

public class BankMenuController : MonoBehaviour
{
  private string baseURL = "http://k4b203.p.ssafy.io:8081/api/";
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
  int gId;
  int sId;
  int sCreditGrade;

  //생성해야하는 prefab

  //내 통장 내역 보기
  [SerializeField]
  private GameObject noneAccountHistory;
  private GameObject noneAHClone;
  private RectTransform nAHRect;

  [SerializeField]
  private GameObject accountHistory;
  private List<GameObject> AHClone;

  //가입한 금융 상품 보기
  [SerializeField]
  private GameObject noneMyFinanProd;
  private GameObject noneMyFPClone;

  private GameObject myFinanProdList;
  private List<GameObject> MyFPClone;

  private GameObject myFinanProdDetail;


  //금융 상품 목록 보기
  [SerializeField]
  private GameObject noneFinanProd;
  private GameObject noneFPClone;

  private GameObject finanProdList;
  private List<GameObject> FPClone;
  private GameObject finanProdDetail;
  private GameObject FPDClone;


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
    if (noneMyFPClone != null) Destroy(noneMyFPClone);
    if (MyFPClone != null)
    {
      foreach (GameObject item in MyFPClone)
      {
        Destroy(item);
      }
    }
    if (noneFPClone != null) Destroy(noneFPClone);
    if (FPClone != null)
    {
      foreach (GameObject item in FPClone)
      {
        Destroy(item);
      }
    }
    if (FPDClone != null) Destroy(FPDClone);

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
          GContentForm.transform.GetChild(1).GetChild(0).gameObject.SetActive(false);
          GContentForm.transform.GetChild(1).GetChild(1).gameObject.SetActive(false);

          Transform parent = GameObject.Find("B2Content").GetComponent<Transform>();
          noneMyFPClone = Instantiate(noneMyFinanProd);

          RectTransform noneMyFPCRect = noneMyFPClone.GetComponent<RectTransform>();
          noneMyFPClone.transform.SetParent(parent);
          noneMyFPCRect.offsetMax = new Vector2(0, 0);
          noneMyFPCRect.offsetMin = new Vector2(0, 0);
          noneMyFPCRect.anchoredPosition = new Vector2(0, 0);

          //다른 페이지로 이동할때는 다시 true 처리 해줘야하나...?
        }
        else
        {
          //가입한 금융 상품이 있다면 해당 목록 출력
          Transform ProdParent = GameObject.Find("MyFinanProdList").GetComponent<Transform>();
          Transform DetailParent = GameObject.Find("MyFinanProdDetail").GetComponent<Transform>();
          myFinanProdList = Resources.Load("MyFinanProdItem") as GameObject;
          myFinanProdDetail = Resources.Load("MyFinanProdDetailItem") as GameObject;

          for (int i = 0; i < root.Count; i++)
          {
            GameObject clone = Instantiate(myFinanProdList);
            Text prodName = clone.transform.GetChild(0).GetComponent<Text>();
            Text prodDId = clone.transform.GetChild(1).GetComponent<Text>();

            prodName.text = root[i]["financialProductName"].Value;
            prodDId.text = root[i]["id"].Value;

            clone.GetComponent<Button>().onClick.AddListener(delegate ()
            {
              Debug.Log("first : " + clone.transform.GetChild(1).GetComponent<Text>().text);
              OnPressProdDetail(clone.transform.GetChild(1).GetComponent<Text>().text);
            });

            GameObject itemClone = Instantiate(myFinanProdDetail);
            RectTransform itemRect = itemClone.GetComponent<RectTransform>();
            Text prodDetailId = itemClone.transform.GetChild(0).GetComponent<Text>();
            Text prodDetailStaus = itemClone.transform.GetChild(1).GetComponent<Text>();
            Text prodDetail = itemClone.transform.GetChild(2).GetComponent<Text>();
            Button earlyRedButton = itemClone.transform.GetChild(3).GetComponent<Button>();
            Button waitToTerminate = itemClone.transform.GetChild(4).GetComponent<Button>();

            if (!root[i]["status"].Value.Equals("deposit"))
            {
              earlyRedButton.gameObject.SetActive(false);
              if (root[i]["status"].Value.Equals("before_termination"))
              {
                waitToTerminate.gameObject.SetActive(true);
                waitToTerminate.interactable = true;
              }
            }
            else
            {
              earlyRedButton.gameObject.SetActive(true);
              waitToTerminate.gameObject.SetActive(false);
              earlyRedButton.onClick.AddListener(delegate ()
              {
                StartCoroutine(RevocFinanProd(itemClone.transform.GetChild(0).GetComponent<Text>().text));
              });
            }

            prodDetailId.text = root[i]["id"].Value;
            prodDetailStaus.text = root[i]["status"].Value;
            prodDetail.text = "상품 이름 : " + root[i]["financialProductName"].Value + System.Environment.NewLine +
                              "이율 : " + root[i]["rate"].Value + "(%)" + System.Environment.NewLine +
                              "입금 금액 : " + root[i]["principal"].Value + " " + DataController.GetMonetaryUnitName() + System.Environment.NewLine +
                              "가입 날짜 : " + root[i]["startDate"].Value.Split('T')[0] + System.Environment.NewLine +
                              "만기 날짜 : " + root[i]["endDate"].Value.Split('T')[0] + System.Environment.NewLine +
                              System.Environment.NewLine +
                              "만기해지 시 받는 금액 : " + (int.Parse(root[i]["principal"].Value) + ((double.Parse(root[i]["rate"].Value) / 100) * int.Parse(root[i]["principal"].Value))).ToString().Split('.')[0] + " " + DataController.GetMonetaryUnitName();


            clone.transform.SetParent(ProdParent);
            itemClone.transform.SetParent(DetailParent);
            itemClone.transform.localPosition = Vector3.zero;
            itemRect.offsetMax = new Vector2(-8, -10);
            itemRect.offsetMin = new Vector2(5, 0);
            itemClone.SetActive(false);

            MyFPClone.Add(clone);
            MyFPClone.Add(itemClone);
          }
        }
      }
    }
  }

  //3. 금융 상품 중도 해지하기
  private IEnumerator RevocFinanProd(string str)
  {
    EarlyRedemptionData data = new EarlyRedemptionData();
    data.id = str;
    string json = JsonUtility.ToJson(data);

    using (UnityWebRequest request = UnityWebRequest.Put(baseURL + "ubank/financial-product/termination", json))
    {
      byte[] jsonToSend = new System.Text.UTF8Encoding().GetBytes(json);
      request.uploadHandler = new UploadHandlerRaw(jsonToSend);

      request.SetRequestHeader("Content-Type", "application/json;charset=utf-8");
      request.SetRequestHeader("Accept", "application/json, text/plain, */*");

      yield return request.SendWebRequest();

      if (request.error != null)
      {
        Debug.Log(request.error);
      }
      else
      {
        Debug.Log(request.downloadHandler.text);
        string result = request.downloadHandler.text;

        if (result.Equals("true"))
        {
          ShowSuccessElaryRedAlert();
          ChangeSetActive(str);
        }
        else
        {
          ShowFailElaryRedAlert();
        }
      }
    }
  }

  //중도해지 -> 중도해지 대기중 버튼으로 변경
  private void ChangeSetActive(string id)
  {
    for (int i = 1; i < MyFPClone.Count; i += 2)
    {
      if (MyFPClone[i].transform.GetChild(0).GetComponent<Text>().text.Equals(id))
      {
        MyFPClone[i].transform.GetChild(3).GetComponent<Button>().gameObject.SetActive(false);
        MyFPClone[i].transform.GetChild(4).GetComponent<Button>().gameObject.SetActive(true);
        MyFPClone[i].transform.GetChild(4).GetComponent<Button>().interactable = true;
      }
    }
  }

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

        string result = request.downloadHandler.text;
        JSONNode root = JSON.Parse(result);

        if (root.Count <= 0)
        {
          //등록한 금융 상품이 없다면 안내창 출력
          GContentForm.transform.GetChild(2).GetChild(0).gameObject.SetActive(false);
          GContentForm.transform.GetChild(2).GetChild(1).gameObject.SetActive(false);

          Transform parent = GameObject.Find("B3Content").GetComponent<Transform>();
          noneFPClone = Instantiate(noneFinanProd);

          RectTransform noneFPCRect = noneFPClone.GetComponent<RectTransform>();
          noneFPClone.transform.SetParent(parent);
          noneFPCRect.offsetMax = new Vector2(0, 0);
          noneFPCRect.offsetMin = new Vector2(0, 0);
          noneFPCRect.anchoredPosition = new Vector2(0, 0);
        }
        else
        {
          Transform ProdParent = GameObject.Find("FinanProdList").GetComponent<Transform>();
          finanProdList = Resources.Load("FinanProdItem") as GameObject;
          finanProdDetail = Resources.Load("FinanProdItemDetail") as GameObject;

          for (int i = 0; i < root.Count; i++)
          {
            GameObject clone = Instantiate(finanProdList);
            Text prodDId = clone.transform.GetChild(0).GetComponent<Text>();
            Text prodName = clone.transform.GetChild(1).GetComponent<Text>();

            prodDId.text = root[i]["id"].Value;
            prodName.text = root[i]["name"].Value;

            clone.transform.SetParent(ProdParent);

            /*
              버튼 클릭하면 상세 정보 api 불러오는 메서드 호출 -> 그 안에서 info 프리팹 생성해서 내용 채우기
            */
            clone.GetComponent<Button>().onClick.AddListener(delegate ()
            {
              if (FPDClone != null) Destroy(FPDClone);
              StartCoroutine(OnPressFinanProdDetail(clone.transform.GetChild(0).GetComponent<Text>().text));
            });

            FPClone.Add(clone);
          }
        }
      }
    }
  }

  //예금 상품 명 클릭하면 해당 상품의 정보를 불러오는 메서드
  private IEnumerator OnPressFinanProdDetail(string fpdId)
  {
    using (UnityWebRequest request = UnityWebRequest.Get(baseURL + "bank/financial-product/product/" + fpdId))
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

        finanProdDetail = Resources.Load("FinanProdItemDetail") as GameObject;
        Transform parent = GameObject.Find("FinanProdDetail").GetComponent<Transform>();

        FPDClone = Instantiate(finanProdDetail);
        Text detailInfo = FPDClone.transform.GetChild(0).GetComponent<Text>();
        Button prodRegisterButton = FPDClone.transform.GetChild(1).GetComponent<Button>();

        detailInfo.text = "상품 이름 : " + root["name"].Value + System.Environment.NewLine +
                          "예금 기간 : " + root["financialProductDetailResponse"][0]["rate"].Value + System.Environment.NewLine +
                          "--- 상세 이율 ---" + System.Environment.NewLine;

        for (int i = 0; i < root["financialProductDetailResponse"].Count; i++)
        {
          detailInfo.text += root["financialProductDetailResponse"][i]["creditGrade"].Value + "등급 : " + root["financialProductDetailResponse"][i]["rate"].Value + System.Environment.NewLine;
        }

        FPDClone.transform.SetParent(parent);
        RectTransform FPDCRect = FPDClone.GetComponent<RectTransform>();
        FPDClone.transform.SetParent(parent);
        FPDCRect.offsetMax = new Vector2(0, 0);
        FPDCRect.offsetMin = new Vector2(0, 0);

        //!!!!내 등급에 맞는 예금 id 뽑아서 넘겨주기!!!!!!
        GetStudentCreditGrade();
        string myPDId = "";

        for (int i = 0; i < root["financialProductDetailResponse"].Count; i++)
        {
          JSONNode now = root["financialProductDetailResponse"][i];

          if (now["creditGrade"].AsInt == sCreditGrade) myPDId = now["id"].Value;
        }

        //   if (myPDId.Equals(""))
        //   {
        //     for (int i = 0; i < root["financialProductDetailResponse"].Count - 1; i++)
        //     {
        //       JSONNode now = root["financialProductDetailResponse"][i];
        //       JSONNode next = root["financialProductDetailResponse"][i + 1];

        //       if (now["creditGrade"].AsInt < sCreditGrade && next["creditGrade"].AsInt > sCreditGrade) myPDId = now["id"].Value;
        //       else if (i == root["financialProductDetailResponse"].Count - 2 && next["creditGrade"].AsInt < sCreditGrade) myPDId = next["id"].Value;
        //     }
        //   }

        //   prodRegisterButton.onClick.AddListener(delegate ()
        //   {
        //     StartCoroutine(OnPressProdRegister(myPDId));
        //   });
      }
    }
  }

  //학생 신용등급 조회
  private IEnumerator GetStudentCreditGrade()
  {
    using (UnityWebRequest request = UnityWebRequest.Get(baseURL + "credit/student/" + sId))
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

        sCreditGrade = root["creditGrade"].AsInt;
      }
    }
  }

  //5. 금융 상품 신청하기
  private IEnumerator OnPressProdRegister(string pdId)
  {
    FinanProdRegistData data = new FinanProdRegistData();
    data.financialProductDetailId = pdId;
    data.studentId = sId;

    string json = JsonUtility.ToJson(data);

    using (UnityWebRequest request = UnityWebRequest.Post(baseURL + "ubank/product-regist", json))
    {
      byte[] jsonToSend = new System.Text.UTF8Encoding().GetBytes(json);
      request.uploadHandler = new UploadHandlerRaw(jsonToSend);

      request.SetRequestHeader("Content-Type", "application/json;charset=utf-8");
      request.SetRequestHeader("Accept", "application/json, text/plain, */*");
      yield return request.SendWebRequest();

      if (request.error != null)
      {
        Debug.Log(request.error);
      }
      else
      {
        ShowSuccessProdRegistAlert();
      }
    }
  }

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
    if (noneMyFPClone != null) Destroy(noneMyFPClone);
    if (MyFPClone != null)
    {
      foreach (GameObject item in MyFPClone)
      {
        Destroy(item);
      }
    }
    if (noneFPClone != null) Destroy(noneFPClone);
    if (FPClone != null)
    {
      foreach (GameObject item in FPClone)
      {
        Destroy(item);
      }
    }
    if (FPDClone != null) Destroy(FPDClone);
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
    if (noneMyFPClone != null) Destroy(noneMyFPClone);
    if (MyFPClone != null)
    {
      foreach (GameObject item in MyFPClone)
      {
        Destroy(item);
      }
    }
    if (noneFPClone != null) Destroy(noneFPClone);
    if (FPClone != null)
    {
      foreach (GameObject item in FPClone)
      {
        Destroy(item);
      }
    }
    if (FPDClone != null) Destroy(FPDClone);

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
    MyFPClone = new List<GameObject>();
    StartCoroutine(GetMyFinancialProdList());
    ObjectActive("GenericContentForms", 1);
  }

  public void OnPressFinanProdList()
  {
    FPClone = new List<GameObject>();
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

  //버튼 클릭하면 해당 아이디가 저장된 오브젝트 활성화 시키기
  private void OnPressProdDetail(string fpdId)
  {
    Debug.Log("id : " + fpdId);
    for (int i = 0; i < MyFPClone.Count; i += 2)
    {
      string prodId = MyFPClone[i].transform.GetChild(1).GetComponent<Text>().text;
      if (prodId.Equals(fpdId))
      {
        MyFPClone[i + 1].SetActive(true);
      }
      else
      {
        MyFPClone[i + 1].SetActive(false);
      }
    }
  }

  //해당 오브젝트만 활성화 시키는 메서드
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

  //해지 신청 완료 alert
  public void ShowSuccessElaryRedAlert()
  {
    string title = "";
    string message = "해지 신청이 완료되었습니다!";

    AlertViewController.Show(title, message);
  }

  public void ShowFailElaryRedAlert()
  {
    string title = "";
    string message = "해지 신청 중 오류가 발생했습니다.";

    AlertViewController.Show(title, message);
  }

  public void ShowSuccessProdRegistAlert()
  {
    string title = "";
    string message = "상품 가입 신청이 완료되었습니다!";

    AlertViewController.Show(title, message);
  }
}
