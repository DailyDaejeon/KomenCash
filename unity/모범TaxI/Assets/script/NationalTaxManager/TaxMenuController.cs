using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.Networking;
using SimpleJSON;

[System.Serializable]
public class AddTaxHistoryData
{
  public int id;
  public int groupId;
  public string content;
  public int balanceChange;
}

public class TaxMenuController : MonoBehaviour
{
  private static string baseURL = "https://k4b203.p.ssafy.io/api/";
  public RectTransform uiGroup;
  PlayerController enterPlayer;

  [SerializeField]
  private GameObject GenericContentForms;

  [SerializeField]
  private GameObject JobContentForms;

  //해당 영역 나가면 한 번에 지울 수 있게 오브젝트 담을 리스트
  private static List<GameObject> objectList = new List<GameObject>();

  private bool _isExit = true;
  private static int gId;
  private int sId;

  // private int currentTaxAmount = 0;

  //생성해야하는 prefab
  private static GameObject noneItem;

  //세금 내역 조회
  private static GameObject THClone;
  private static List<GameObject> THList;


  void Start()
  {
    objectList.Add(GenericContentForms);
    objectList.Add(JobContentForms);

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
    uiGroup.anchoredPosition = Vector3.zero;
    _isExit = false;
  }

  public void Exit()
  {
    uiGroup.anchoredPosition = Vector3.down * -1000;
    ObjectActive("GenericJobListForm", -1);

    if (noneItem != null) Destroy(noneItem);
    if (THList != null)
    {
      foreach (GameObject item in THList)
      {
        Destroy(item);
      }
    }
    _isExit = true;
  }

  /* 국세청 메뉴에 보여줄 데이터 받아오는 api 메서드 */

  //1. 세금 잔액 조회
  private static void GetGroupTax()
  {
    Text tax = GameObject.Find("TaxContent").GetComponent<Text>();
    tax.text = MyInfoController.GetThousandCommaText(DataController.GetTax()) + " " + DataController.GetMonetaryUnitName();
  }


  //2. 세율 조회
  private static void GetGroupTaxRate()
  {
    Text taxRate = GameObject.Find("TaxRateContent").GetComponent<Text>();
    taxRate.text = DataController.GetTaxRate().ToString() + " (%)";
  }

  //3. 세금 내역 조회
  private static IEnumerator GetTaxHistory()
  {
    using (UnityWebRequest request = UnityWebRequest.Get(baseURL + "tax/detail/" + gId))
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
          Transform parent = GameObject.Find("TaxHistoryContent").GetComponent<Transform>();
          noneItem = NoneContentMsgController.Show("아직 세금 내역이 없습니다!").gameObject;

          noneItem.transform.SetParent(parent);
        }
        else
        {
          Transform parent = GameObject.Find("TaxHistoryContent").GetComponent<Transform>();
          THClone = Resources.Load("TaxHistoryItem") as GameObject;

          for (int i = root.Count - 1; i >= 0; i--)
          {
            GameObject clone = Instantiate(THClone);

            Text usageDate = clone.transform.GetChild(0).GetComponent<Text>();
            Text usageHistory = clone.transform.GetChild(1).GetComponent<Text>();
            Text balanceChange = clone.transform.GetChild(2).GetComponent<Text>();
            Text balance = clone.transform.GetChild(3).GetComponent<Text>();

            usageDate.text = root[i]["createdDate"].Value.Split('T')[0];
            usageHistory.text = root[i]["content"].Value;
            balanceChange.text = MyInfoController.GetThousandCommaText(root[i]["balanceChange"].AsInt);
            balance.text = MyInfoController.GetThousandCommaText(root[i]["balance"].AsInt);

            clone.transform.SetParent(parent);

            THList.Add(clone);
          }
        }
      }
    }
  }

  //4. 세금 내역 추가 - 뒤로가기 or 취소 버튼
  private void BackButton()
  {
    Transform background = GameObject.Find("AddTaxHistory").GetComponent<Transform>();

    InputField taxContent = background.transform.GetChild(1).GetComponent<InputField>();
    Button symbol = background.transform.GetChild(3).GetComponent<Button>();
    InputField taxAmount = background.transform.GetChild(4).GetComponent<InputField>();

    Transform calculator = GameObject.Find("TaxCalculator").GetComponent<Transform>();
    Text totalTax = calculator.transform.GetChild(1).GetComponent<Text>();
    Text currentTax = calculator.transform.GetChild(3).GetComponent<Text>();
    Text estimatedTax = calculator.transform.GetChild(4).GetComponent<Text>();

    taxContent.text = "";
    symbol.transform.GetChild(0).GetComponent<Text>().text = "+";
    taxAmount.text = "";

    totalTax.text = MyInfoController.GetThousandCommaText(DataController.GetTax()).ToString();
    currentTax.text = "0";
    estimatedTax.text = MyInfoController.GetThousandCommaText(DataController.GetTax()).ToString();

    ObjectActive("GenericContentForms", -1);
  }

  //4-1. 세금 내역 추가 - 계산기 금액
  public void OnChangeCurrentTax()
  {
    Transform background = GameObject.Find("AddTaxHistory").GetComponent<Transform>();
    InputField taxAmount = background.transform.GetChild(4).GetComponent<InputField>();

    Transform calculator = GameObject.Find("TaxCalculator").GetComponent<Transform>();
    Text currentTax = calculator.transform.GetChild(3).GetComponent<Text>();

    currentTax.text = taxAmount.text;
  }

  public void OnChangeEstimatedTax()
  {
    Transform background = GameObject.Find("AddTaxHistory").GetComponent<Transform>();
    Button symbol = background.transform.GetChild(3).GetComponent<Button>();
    Text symbolText = symbol.transform.GetChild(0).GetComponent<Text>();

    Transform calculator = GameObject.Find("TaxCalculator").GetComponent<Transform>();
    Text cSymbol = calculator.transform.GetChild(2).GetComponent<Text>();
    Text currentTax = calculator.transform.GetChild(3).GetComponent<Text>();
    Text estimatedTax = calculator.transform.GetChild(4).GetComponent<Text>();

    cSymbol.text = symbolText.text;
    if (symbolText.text.Equals("+"))
    {
      estimatedTax.text = MyInfoController.GetThousandCommaText(DataController.GetTax() + int.Parse(currentTax.text));
    }
    else
    {
      estimatedTax.text = MyInfoController.GetThousandCommaText(DataController.GetTax() - int.Parse(currentTax.text));
    }
  }


  //4-2. 기호 바꾸기
  private void ChangeSymbol()
  {
    Transform background = GameObject.Find("AddTaxHistory").GetComponent<Transform>();
    Button symbol = background.transform.GetChild(3).GetComponent<Button>();
    Text symbolText = symbol.transform.GetChild(0).GetComponent<Text>();

    if (symbolText.text.Equals("+")) symbolText.text = "-";
    else symbolText.text = "+";
  }

  //4-3. 세금 내역 추가하기
  private IEnumerator CreateTaxHistory()
  {
    Transform background = GameObject.Find("AddTaxHistory").GetComponent<Transform>();
    InputField taxContent = background.transform.GetChild(1).GetComponent<InputField>();
    Button symbol = background.transform.GetChild(3).GetComponent<Button>();
    Text symbolText = symbol.transform.GetChild(0).GetComponent<Text>();
    InputField taxAmount = background.transform.GetChild(4).GetComponent<InputField>();

    AddTaxHistoryData data = new AddTaxHistoryData();
    data.groupId = gId;
    data.content = taxContent.text;
    data.balanceChange = (symbolText.text.Equals("+") ? 1 : -1) * int.Parse(taxAmount.text);

    string json = JsonUtility.ToJson(data);

    using (UnityWebRequest request = UnityWebRequest.Post(baseURL + "tax", json))
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
        string result = request.downloadHandler.text;

        if (result.Equals("true"))
        {
          ShowAddTaxHistoryAlert("세금 내역이 추가되었어요!", 1);
        }
        else
        {
          ShowAddTaxHistoryAlert("세금 내역을 추가하던 도중 오류가 발생했어요ㅠ.ㅠ", -1);
        }
      }
    }
  }


  /* 창 전환하는 메서드 - OnPress */

  //1. 초기 화면 불러오기
  public static void ShowGroupTaxInfo()
  {
    ObjectActive("GenericContentForms", -1);
    THList = new List<GameObject>();
    GetGroupTax();
    GetGroupTaxRate();
    StaticCoroutine.DoCoroutine(GetTaxHistory());
  }

  public void OnPressAddTaxHistoryButton()
  {
    if (noneItem != null) Destroy(noneItem);
    if (THList != null)
    {
      foreach (GameObject item in THList)
      {
        Destroy(item);
      }
    }

    ObjectActive("JobContentForms", -1);
    Transform calculator = GameObject.Find("TaxCalculator").GetComponent<Transform>();
    Text totalTax = calculator.transform.GetChild(1).GetComponent<Text>();
    totalTax.text = DataController.GetTax().ToString();
  }

  public void OnPressAddTaxHistoryBackButton()
  {
    BackButton();
    ShowGroupTaxInfo();
  }

  public void OnPressTaxSymbolButton()
  {
    ChangeSymbol();
  }

  public void OnPressAddTaxHistorySubmitButton()
  {
    StartCoroutine(CreateTaxHistory());
  }

  /* Alert view 불러오는 메서드 */
  private void ShowAddTaxHistoryAlert(string msg, int num)
  {

    string title = "";
    if (num == 1)
    {
      AlertViewController.Show(title, msg, new AlertViewOptions
      {
        okButtonTitle = "확인",
        okButtonDelegate = () =>
        {
          OnPressAddTaxHistoryBackButton();
        }
      });
    }
    else
    {
      AlertViewController.Show(title, msg);
    }
  }

  //해당 오브젝트만 활성화 시키는 메서드
  private static void ObjectActive(string ojName, int index)
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
