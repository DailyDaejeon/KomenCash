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
  public int principal;
  public int studentId;
}

[System.Serializable]
public class SalaryPaymentData
{
  public string id;
  public string student_id;
}

[System.Serializable]
public class FinanRequestListData
{
  public int historyId;
  public string productName;
  public int studentId;
  public string studentNickname;
  public int studentCreditGrade;
  public int principal;
  public double rate;
  public string status;
  public string startDate;
  public string endDate;
}

public class BankMenuController : MonoBehaviour
{
  private string baseURL = "https://k4b203.p.ssafy.io/api/";
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
  private GameObject noneAccountHistory;
  private GameObject noneAHClone;
  private RectTransform nAHRect;

  [SerializeField]
  private GameObject accountHistory;
  private List<GameObject> AHClone;

  //가입한 금융 상품 보기
  private GameObject noneMyFinanProd;
  private GameObject noneMyFPClone;

  private GameObject myFinanProdList;
  private List<GameObject> MyFPClone;

  private GameObject myFinanProdDetail;


  //금융 상품 목록 보기
  private GameObject noneFinanProd;
  private GameObject noneFPClone;

  private GameObject finanProdList;
  private List<GameObject> FPClone;
  private GameObject finanProdDetail;
  private GameObject FPDClone;

  //그룹원 월급 관리
  private GameObject noneSRClone;
  private GameObject SRClone;
  private List<GameObject> memberSalaryList;

  /* 금융 상품 요청 리스트 */
  private List<FinanRequestListData> registerList;
  private List<FinanRequestListData> terminationList;

  //금융 상품 신청 요청
  private GameObject noneRegRequest;
  private GameObject FPRClone;
  private List<GameObject> FPRList;

  //금융 상품 중도 해지 요청
  private GameObject noneTermRequest;
  private GameObject FPTClone;
  private List<GameObject> FPTList;

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
    if (noneSRClone != null) Destroy(noneSRClone.gameObject);
    if (memberSalaryList != null)
    {
      foreach (GameObject item in memberSalaryList)
      {
        Destroy(item);
      }
    }
    if (noneRegRequest != null) Destroy(noneRegRequest);
    if (FPRList != null)
    {
      foreach (GameObject item in FPRList)
      {
        Destroy(item);
      }
    }
    if (noneTermRequest != null) Destroy(noneRegRequest);
    if (FPTList != null)
    {
      foreach (GameObject item in FPTList)
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
          noneAHClone = NoneContentMsgController.Show("통장 내역이 없습니다ㅠ.ㅠ").gameObject;

          RectTransform noneAHCloneRect = noneAHClone.GetComponent<RectTransform>();

          noneAHClone.transform.SetParent(parent);
          noneAHCloneRect.localScale = new Vector3(1.0f, 1.0f, 1.0f);
        }
        else
        {
          //통장 내역이 있으면 for문 이용해서 accountHistory 프리팹 생성해서 내용 넣고 출력
          Transform parent = GameObject.Find("B1AHContent").GetComponent<Transform>();
          accountHistory = Resources.Load("AccountHistory") as GameObject;
          for (int i = root.Count - 1; i >= 0; i--)
          {
            GameObject clone = Instantiate(accountHistory);
            RectTransform cloneRect = clone.GetComponent<RectTransform>();
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
            cloneRect.localScale = new Vector3(1.0f, 1.0f, 1.0f);
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
          noneMyFPClone = NoneContentMsgController.Show("아직 가입한 금융 상품이 없습니다!").gameObject;

          RectTransform noneMyFPCRect = noneMyFPClone.GetComponent<RectTransform>();
          noneMyFPClone.transform.SetParent(parent);
          noneMyFPCRect.offsetMax = new Vector2(0, 0);
          noneMyFPCRect.offsetMin = new Vector2(0, 0);
          noneMyFPCRect.anchoredPosition = new Vector2(0, 0);

          noneMyFPCRect.localScale = new Vector3(1.0f, 1.0f, 1.0f);

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

            RectTransform cloneRect = clone.GetComponent<RectTransform>();
            cloneRect.localScale = new Vector3(1.0f, 1.0f, 1.0f);

            RectTransform itemCloneRect = itemClone.GetComponent<RectTransform>();
            itemCloneRect.localScale = new Vector3(1.0f, 1.0f, 1.0f);

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
          noneFPClone = NoneContentMsgController.Show("우리반에는 아직 금융 상품이 없습니다ㅜ.ㅜ").gameObject;

          RectTransform noneFPCRect = noneFPClone.GetComponent<RectTransform>();
          noneFPClone.transform.SetParent(parent);
          noneFPCRect.offsetMax = new Vector2(0, 0);
          noneFPCRect.offsetMin = new Vector2(0, 0);
          noneFPCRect.anchoredPosition = new Vector2(0, 0);
          noneFPCRect.localScale = new Vector3(1.0f, 1.0f, 1.0f);
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

            RectTransform cloneRect = clone.GetComponent<RectTransform>();
            cloneRect.localScale = new Vector3(1.0f, 1.0f, 1.0f);

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
        FPDCRect.offsetMax = new Vector2(0, 0);
        FPDCRect.offsetMin = new Vector2(0, 0);
        FPDCRect.localScale = new Vector3(1.0f, 1.0f, 1.0f);

        StartCoroutine(GetStudentCreditGrade());
        string myPDId = "";

        for (int i = 0; i < root["financialProductDetailResponse"].Count; i++)
        {
          JSONNode now = root["financialProductDetailResponse"][i];
          if (now["creditGrade"].AsInt == sCreditGrade)
          {
            myPDId = now["id"].Value;
          }
        }

        prodRegisterButton.onClick.AddListener(delegate ()
        {
          ShowInputPrincipalAlert(myPDId);
          // StartCoroutine(OnPressProdRegister(myPDId));
        });
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
  private IEnumerator OnPressProdRegister(string pdId, int principal)
  {
    FinanProdRegistData data = new FinanProdRegistData();
    data.financialProductDetailId = pdId;
    data.principal = principal;
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

  //7. 그룹원 월급 관리
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

        string result = request.downloadHandler.text;
        JSONNode root = JSON.Parse(result);

        if (root.Count <= 0)
        {
          Transform parent = GameObject.Find("GroupMemberList").GetComponent<Transform>();
          noneSRClone = NoneContentMsgController.Show("오늘은 월급날이 아닙니다!").gameObject;

          noneSRClone.transform.SetParent(parent);

          RectTransform noneSRCloneRect = noneSRClone.GetComponent<RectTransform>();
          noneSRCloneRect.localScale = new Vector3(1.0f, 1.0f, 1.0f);
        }
        else
        {
          Transform parent = GameObject.Find("GroupMemberList").GetComponent<Transform>();
          SRClone = Resources.Load("MemberSalaryItem") as GameObject;
          for (int i = 0; i < root.Count; i++)
          {
            if (root[i]["accept"].Equals("before_confirm"))
            {
              GameObject clone = Instantiate(SRClone);
              Text sId = clone.transform.GetChild(0).GetComponent<Text>();
              Text sMemId = clone.transform.GetChild(1).GetComponent<Text>();
              Text sMemName = clone.transform.GetChild(2).GetComponent<Text>();
              Text sMemSalary = clone.transform.GetChild(3).GetComponent<Text>();
              Text sMemTax = clone.transform.GetChild(4).GetComponent<Text>();
              Button sendSalaryBtn = clone.transform.GetChild(5).GetComponent<Button>();

              sId.text = root[i]["id"].Value;
              sMemId.text = root[i]["student_id"].Value;
              sMemName.text = root[i]["student_nickname"].Value;
              sMemSalary.text = root[i]["salary"].Value;
              sMemTax.text = root[i]["tax_loss"].Value;

              clone.transform.SetParent(parent);
              RectTransform cloneRect = clone.GetComponent<RectTransform>();
              cloneRect.localScale = new Vector3(1.0f, 1.0f, 1.0f);

              sendSalaryBtn.onClick.AddListener(delegate ()
              {
                if (sMemName.Equals(DataController.GetStudentNickname()))
                {
                  int salary = DataController.GetBalance() + int.Parse(sMemSalary.text) - int.Parse(sMemTax.text);
                  DataController.setBalance(salary);
                  Text statBalance = GameObject.Find("balance").GetComponent<Text>();
                  statBalance.text = "통장 잔액 : " + MyInfoController.GetThousandCommaText(salary) + " " + DataController.GetMonetaryUnitName();
                }
                StartCoroutine(OnPressSalaryPayment(sId.text, sMemId.text));
                Destroy(clone);
              });
              memberSalaryList.Add(clone);
            }
          }
        }
      }
    }
  }

  //8. 그룹원에게 월급 보내기
  private IEnumerator OnPressSalaryPayment(string sId, string sMemId)
  {
    // SalaryPaymentData data = new SalaryPaymentData();
    // data.id = sId;
    // data.student_id = sMemId;

    // string json = JsonUtility.ToJson(data);

    using (UnityWebRequest request = UnityWebRequest.Delete(baseURL + "ubank/salary-payment/" + sMemId + "/" + sId))
    {
      yield return request.SendWebRequest();

      if (request.error != null)
      {
        Debug.Log(request.error);
      }
      else
      {
        ShowSuccessSendSalaryAlert();
      }
    }
  }

  //9. 금융상품 가입/해지 요청 메서드
  private IEnumerator GetFinanProdRequestList()
  {
    using (UnityWebRequest request = UnityWebRequest.Get(baseURL + "ubank/financial-product/request-list/" + gId))
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

        Debug.Log(result);
        if (root.Count > 0)
        {
          FinanRequestListData list = new FinanRequestListData();
          foreach (JSONNode item in root)
          {
            list.historyId = item["historyId"].AsInt;
            list.productName = item["productName"].Value;
            list.studentId = item["studentId"].AsInt;
            list.studentNickname = item["studentNickname"].Value;
            list.studentCreditGrade = item["studentCreditGrade"].AsInt;
            list.principal = item["principal"].AsInt;
            list.rate = item["rate"].AsDouble;
            list.status = item["status"].Value;
            list.startDate = item["startDate"].Value.Split('T')[0];
            list.endDate = item["endDate"].Value.Split('T')[0];

            if (item["status"].Value.Equals("before_deposit"))
            {
              registerList.Add(list);
            }
            else if (item["status"].Value.Equals("before_termination"))
            {
              terminationList.Add(list);
            }
          }
        }
      }
    }
  }

  private void ShowRegList()
  {
    if (registerList.Count <= 0)
    {
      Transform parent = GameObject.Find("RegistRequestContent").GetComponent<Transform>();
      noneRegRequest = NoneContentMsgController.Show("상품 가입 요청이 없습니다!").gameObject;

      noneRegRequest.transform.SetParent(parent);

      RectTransform noneRegRequestRect = noneRegRequest.GetComponent<RectTransform>();
      noneRegRequestRect.localScale = new Vector3(1.0f, 1.0f, 1.0f);
    }
    else
    {
      Transform parent = GameObject.Find("RegistRequestContent").GetComponent<Transform>();
      FPRClone = Resources.Load("FinanRequestItem") as GameObject;

      for (int i = 0; i < registerList.Count; i++)
      {
        GameObject clone = Instantiate(FPRClone);
        Text finanProdHistoryId = clone.transform.GetChild(0).GetComponent<Text>();
        Text finanProdname = clone.transform.GetChild(1).GetComponent<Text>();
        Text studentId = clone.transform.GetChild(2).GetComponent<Text>();
        Text studentName = clone.transform.GetChild(3).GetComponent<Text>();
        Text principal = clone.transform.GetChild(4).GetComponent<Text>();
        Text startDate = clone.transform.GetChild(5).GetComponent<Text>();
        Button confirmButton = clone.transform.GetChild(6).GetComponent<Button>();

        finanProdHistoryId.text = registerList[i].historyId.ToString();
        finanProdname.text = registerList[i].productName;
        studentId.text = registerList[i].studentId.ToString();
        studentName.text = registerList[i].studentNickname;
        principal.text = registerList[i].principal.ToString();
        startDate.text = registerList[i].startDate;

        confirmButton.onClick.AddListener(delegate ()
        {
          StartCoroutine(ConfirmRegisterRequest(finanProdHistoryId.text));
          Destroy(clone);
        });

        clone.transform.SetParent(parent);
        RectTransform cloneRect = clone.GetComponent<RectTransform>();
        cloneRect.localScale = new Vector3(1.0f, 1.0f, 1.0f);

        FPRList.Add(clone);
      }
    }
  }
  private void ShowTermList()
  {
    if (terminationList.Count <= 0)
    {
      Transform parent = GameObject.Find("TermRequestContent").GetComponent<Transform>();
      noneTermRequest = NoneContentMsgController.Show("상품 중도 해지 요청이 없습니다!").gameObject;

      noneTermRequest.transform.SetParent(parent);

      RectTransform noneTermRequestRect = noneTermRequest.GetComponent<RectTransform>();
      noneTermRequestRect.localScale = new Vector3(1.0f, 1.0f, 1.0f);
    }
    else
    {
      Transform parent = GameObject.Find("TermRequestContent").GetComponent<Transform>();
      FPTClone = Resources.Load("FinanRequestItem") as GameObject;

      for (int i = 0; i < terminationList.Count; i++)
      {
        GameObject clone = Instantiate(FPTClone);
        Text finanProdHistoryId = clone.transform.GetChild(0).GetComponent<Text>();
        Text finanProdname = clone.transform.GetChild(1).GetComponent<Text>();
        Text studentId = clone.transform.GetChild(2).GetComponent<Text>();
        Text studentName = clone.transform.GetChild(3).GetComponent<Text>();
        Text principal = clone.transform.GetChild(4).GetComponent<Text>();
        Text endDate = clone.transform.GetChild(5).GetComponent<Text>();
        Button confirmButton = clone.transform.GetChild(6).GetComponent<Button>();

        finanProdHistoryId.text = terminationList[i].historyId.ToString();
        finanProdname.text = terminationList[i].productName;
        studentId.text = terminationList[i].studentId.ToString();
        studentName.text = terminationList[i].studentNickname;
        principal.text = terminationList[i].principal.ToString();
        endDate.text = terminationList[i].endDate;

        confirmButton.onClick.AddListener(delegate ()
        {
          StartCoroutine(ConfirmRegisterRequest(finanProdHistoryId.text));
          Destroy(clone);
        });

        clone.transform.SetParent(parent);
        RectTransform cloneRect = clone.GetComponent<RectTransform>();
        cloneRect.localScale = new Vector3(1.0f, 1.0f, 1.0f);

        FPTList.Add(clone);
      }
    }
  }

  private IEnumerator ConfirmRegisterRequest(string finanProdHistoryId)
  {
    FinanProdRegistData data = new FinanProdRegistData();

    string json = JsonUtility.ToJson(data);
    using (UnityWebRequest request = UnityWebRequest.Put(baseURL + "bank/financial-status-accept/" + finanProdHistoryId, json))
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
          ShowSuccessRequestConfirmAlert();
        }
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
    if (noneSRClone != null) Destroy(noneSRClone.gameObject);
    if (memberSalaryList != null)
    {
      foreach (GameObject item in memberSalaryList)
      {
        Destroy(item);
      }
    }
    if (noneRegRequest != null) Destroy(noneRegRequest);
    if (FPRList != null)
    {
      foreach (GameObject item in FPRList)
      {
        Destroy(item);
      }
    }
    if (noneTermRequest != null) Destroy(noneRegRequest);
    if (FPTList != null)
    {
      foreach (GameObject item in FPTList)
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
    if (noneSRClone != null) Destroy(noneSRClone.gameObject);
    if (memberSalaryList != null)
    {
      foreach (GameObject item in memberSalaryList)
      {
        Destroy(item);
      }
    }
    if (noneRegRequest != null) Destroy(noneRegRequest);
    if (FPRList != null)
    {
      foreach (GameObject item in FPRList)
      {
        Destroy(item);
      }
    }
    if (noneTermRequest != null) Destroy(noneRegRequest);
    if (FPTList != null)
    {
      foreach (GameObject item in FPTList)
      {
        Destroy(item);
      }
    }
    registerList = new List<FinanRequestListData>();
    terminationList = new List<FinanRequestListData>();
    StartCoroutine(GetFinanProdRequestList());
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
    memberSalaryList = new List<GameObject>();
    StartCoroutine(GetSCList());
    ObjectActive("JobContentForms", 0);
  }

  public void OnPressFPRequestSetting()
  {
    FPRList = new List<GameObject>();
    ObjectActive("JobContentForms", 1);
    ShowRegList();
  }

  public void OnPressFPRevocationSetting()
  {
    FPTList = new List<GameObject>();
    ObjectActive("JobContentForms", 2);
    ShowTermList();
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

  //alert 생성 메서드
  public void ShowSuccessElaryRedAlert()
  {
    string title = "";
    string message = "해지 신청이 완료되었습니다!";

    AlertViewController.Show(title, message);
  }

  public void ShowSuccessSendSalaryAlert()
  {
    string title = "";
    string message = "월급이 지급 되었습니다!";

    AlertViewController.Show(title, message);
  }

  public void ShowFailElaryRedAlert()
  {
    string title = "";
    string message = "해지 신청 중 오류가 발생했습니다.";

    AlertViewController.Show(title, message);
  }

  public void ShowInputPrincipalAlert(string myPDId)
  {
    string title = "입금 금액 입력하기";
    string message = "예금 상품에 넣을 금액을 입력해주세요!";

    AlertViewController.Show(title, message, new AlertViewOptions
    {
      cancelButtonTitle = "취소하기",
      cancelButtonDelegate = () => { },
      okButtonTitle = "신청하기",
      okButtonDelegate = () =>
      {
        Text pText = GameObject.Find("AlterViewInputFieldText").GetComponent<Text>();

        int principal = int.Parse(pText.text);
        // Debug.Log("예금 금액 : " + principal);
        StartCoroutine(OnPressProdRegister(myPDId, principal));
      }
    }, new InputFormOptions
    {
      inputFormTitle = "예금 금액을 숫자로만 입력하세요.(ex. 300)"
    });
  }
  public void ShowSuccessProdRegistAlert()
  {
    string title = "";
    string message = "상품 가입 신청이 완료되었습니다!";

    AlertViewController.Show(title, message);
  }

  private void ShowSuccessRequestConfirmAlert()
  {
    string title = "";
    string message = "요청이 처리되었습니다!";

    AlertViewController.Show(title, message);
  }
}
