using System.Collections;
using System.Collections.Generic;
using System;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.Networking;
using SimpleJSON;

public class StockMenuController : MonoBehaviour
{
  public string baseURL = "http://k4b203.p.ssafy.io:8081/api/";
  public RectTransform uiGroup;
  PlayerController enterPlayer;

  // 증권 화면
  [SerializeField]
  private List<GameObject> StockCenters = new List<GameObject>();

  // 주식 목록보기
  [SerializeField]
  private GameObject stockItem;
  [SerializeField]
  private List<GameObject> StockItems; // 주식 종목 리스트



  // 주식 상세 히스토리 보기
  [SerializeField]
  private List<GameObject> GetStockItem = new List<GameObject>();
  private GameObject Current_Status;
  private string currentStockId;

  // 해당 종목 현재 가격, 이전 가격과의 차이
  public GameObject Current_Value;
  public GameObject Increase_Value;
  public GameObject Value_Percent;
  // 구매 개수 입력
  public InputField BuyCount;
  // 내 잔액 부분
  public GameObject MyAccount;
  public GameObject MyAccountChange;
  // 매수 버튼
  public Button BuyBtn;


  // 내 주식 정보 부분
  [SerializeField]
  private GameObject MyStockItem;
  [SerializeField]
  private List<GameObject> MyStockItems = new List<GameObject>(); // 주식 종목 리스트


  // 그래프를 위한 데이터
  [SerializeField]
  private List<int> priceList = new List<int>();

  //form object
  [SerializeField]
  private GameObject ButtonForm;
  [SerializeField]
  private GameObject ButtonFormContent;

  int sId;
  int gId;

  //생성해야하는 프리팹
  private GameObject noneItem;


  void Start()
  {
    StockCenters = new List<GameObject>();
    sId = DataController.GetStudentId();
    gId = DataController.GetGroupId();
    StockCenters.Add(ButtonForm);
    StockCenters.Add(ButtonFormContent);

    BuyCount.onValueChange.AddListener(delegate
    {
      ChangeBalance();
    });
  }

  public void Enter(PlayerController player)
  {
    enterPlayer = player;
    uiGroup.anchoredPosition = Vector3.zero;
  }
  public void Exit()
  {
    if (noneItem != null) Destroy(noneItem);
    if (StockItems != null)
    {
      foreach (GameObject item in StockItems)
      {
        Destroy(item);
      }
    }

    uiGroup.anchoredPosition = Vector3.down * 1000;
  }

  // 주식 구매 할 때 보여지는 내 돈 변화
  public void ChangeBalance()
  {
    if (BuyCount.text == "1")
    {
      Debug.Log("hello");
    }
    try
    {
      string a = MyAccount.GetComponent<Text>().text;
      string b = Current_Value.GetComponent<Text>().text;
      int accountChange = int.Parse(a) - (int.Parse(BuyCount.text) * int.Parse(b));
      MyAccountChange.GetComponent<Text>().text = accountChange.ToString();
    }
    catch (Exception e)
    {
      MyAccountChange.GetComponent<Text>().text = "잘못된 정보입니다.";
    }
  }
  // 주식 종목 보기
  public IEnumerator GetStockList()
  {
    using (UnityWebRequest request = UnityWebRequest.Get(baseURL + "stock/list/" + gId))
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

        Transform parent = GameObject.Find("StockListContent").GetComponent<Transform>();
        stockItem = Resources.Load("StockListItem") as GameObject;
        Debug.Log(stockItem);
        if (root.Count <= 0)
        {
          noneItem = NoneContentMsgController.Show("투자할 수 있는 주식 종목이 없습니다!").gameObject;

          RectTransform noneMSHRect = noneItem.GetComponent<RectTransform>();
          noneItem.transform.SetParent(parent);

          noneMSHRect.offsetMin = new Vector2(0, 0);
          noneMSHRect.offsetMax = new Vector2(0, 0);
          noneMSHRect.localScale = new Vector3(1.0f, 1.0f, 1.0f);
        }
        else
        {
          for (int i = 0; i < root.Count; i++)
          {
            GameObject clone = Instantiate(stockItem);
            RectTransform cloneRect = clone.GetComponent<RectTransform>();
            Text id = clone.transform.GetChild(0).GetComponent<Text>();
            Text name = clone.transform.GetChild(1).GetComponent<Text>();
            Text price = clone.transform.GetChild(2).GetComponent<Text>();
            Text prePrice = clone.transform.GetChild(3).GetComponent<Text>();
            Button detailBtn = clone.transform.GetChild(4).GetComponent<Button>();

            id.text = root[i]["id"].Value;
            name.text = root[i]["name"].Value;
            price.text = root[i]["price"].Value;

            float percent = ((float)(root[i]["price"].AsInt - root[i]["prePrice"].AsInt) / (float)root[i]["prePrice"].AsInt) * 100;
            int diff = root[i]["prePrice"].AsInt - root[i]["price"].AsInt;

            if (diff < 0)
            {
              prePrice.text = " + " + (-1) * diff + " (" + percent.ToString() + "%)";
            }
            else if (diff > 0)
            {
              prePrice.text = ((-1) * diff) + " (" + percent.ToString() + "%)";
            }
            else if (diff == 0)
            {
              prePrice.text = diff + " (" + percent.ToString() + "%)";
            }

            string hint = root[i]["hint"].Value;

            detailBtn.onClick.AddListener(delegate ()
            {
              onClickDetailBtn();
              showItemHistory(clone.transform.GetChild(0).GetComponent<Text>().text, hint);
            });

            clone.transform.localPosition = Vector3.zero;
            clone.transform.SetParent(parent);

            cloneRect.offsetMax = new Vector2(-8, -10);
            cloneRect.offsetMin = new Vector2(5, 0);
            cloneRect.localScale = new Vector3(1.0f, 1.0f, 1.0f);

            StockItems.Add(clone);
          }
        }
      }
    }
  }
  // 해당 주식 상세 보기 
  public void showItemHistory(string stockId, string hint)
  {
    currentStockId = stockId;
    StartCoroutine(StockItemHistory(stockId, hint));
  }
  public IEnumerator MyBalance(int sId)
  {
    using (UnityWebRequest request = UnityWebRequest.Get(baseURL + "/ubank/myAccount/" + sId))
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
        // 없는 경우
        if (root.Count <= 0)
        {
          Debug.Log(request.error);
        }
        else
        {
          int myBalance = root[root.Count - 1]["balance"];
          Debug.Log("내 잔액 : " + myBalance.ToString());
          MyAccount.GetComponent<Text>().text = myBalance.ToString();
        }
      }
    }
  }
  // 주식 구입 부분
  public void onClickBuyBtn()
  {
    StartCoroutine(MyBuyStockItem(currentStockId));
  }
  [System.Serializable]
  public class BuyStockItem
  {
    public int studentId;
    public int stockId;
    public int price;
    public int amount;
  }
  // 주식 구입
  public IEnumerator MyBuyStockItem(string currentStockId)
  {
    BuyStockItem data = new BuyStockItem();
    data.studentId = sId;
    data.stockId = int.Parse(currentStockId);
    data.price = int.Parse(Current_Value.GetComponent<Text>().text);
    data.amount = int.Parse(BuyCount.text);

    string json = JsonUtility.ToJson(data);

    using (UnityWebRequest request = UnityWebRequest.Post(baseURL + "stock/deal", json))
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
        JSONNode root = JSON.Parse(result);
        if (result.Equals("true"))
        {
          ShowSuccessBuyStockItem();
        }
        else
        {
          ShowFailBuyStockItem();
        }
      }
    }
  }

  public IEnumerator StockItemHistory(string stockId, string hint)
  {
    priceList = new List<int>();
    using (UnityWebRequest request = UnityWebRequest.Get(baseURL + "stock/history/" + stockId))
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
          Transform parent = GameObject.Find("GraphContainer").GetComponent<Transform>();
          noneItem = NoneContentMsgController.Show("저장된 주식 데이터가 없습니다").gameObject;

          noneItem.transform.SetParent(parent);

          RectTransform noneItemRect = noneItem.GetComponent<RectTransform>();
          noneItemRect.localScale = new Vector3(1.0f, 1.0f, 1.0f);
        }
        else
        {
          for (int i = 0; i < root.Count; i++)
          {
            priceList.Add(int.Parse(root[i]["price"].Value));
            Debug.Log("root : " + root[i]["price"].ToString());
          }

          int a = 0;
          if (root.Count > 1)
          {
            a = root[root.Count - 1]["price"] - root[root.Count - 2]["price"];
            Debug.Log(root[root.Count - 1]["price"]);
            Debug.Log(a);
          }
          GameObject.Find("TodayHint").GetComponent<Text>().text = hint;
          Current_Value.GetComponent<Text>().text = root[root.Count - 1]["price"].Value;
          if (a < 0)
          {
            Increase_Value.GetComponent<Text>().text = "<color=#0000ff> ▼ " + a.ToString() + "</color>";
          }
          else if (a == 0)
          {
            Increase_Value.GetComponent<Text>().text = " - " + a.ToString();
          }
          else if (a > 0)
          {
            Increase_Value.GetComponent<Text>().text = "<color=#ff0000> ▲ " + a.ToString() + "</color>";
          }
          Value_Percent.GetComponent<Text>().text = (((float)a / (float)root[root.Count - 2]["price"].AsInt) * 100).ToString().Substring(0, 2) + "(%)";

          StockGraphController.ShowGraph(priceList);
        }
      }
    }

  }

  // 내가 가지고 있는 주식 목록
  public IEnumerator GetMyStockList()
  {

    using (UnityWebRequest request = UnityWebRequest.Get(baseURL + "/stock/deal/holding-status/" + sId))
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

        Transform parent = GameObject.Find("MyStockListContent").GetComponent<Transform>();
        MyStockItem = Resources.Load("MyStockListItem") as GameObject;

        if (root.Count <= 0)
        {
          noneItem = NoneContentMsgController.Show("가지고 있는 주식이 없어요!ㅠ.ㅠ").gameObject;

          RectTransform noneItemRect = noneItem.GetComponent<RectTransform>();
          noneItemRect.localScale = new Vector3(1.0f, 1.0f, 1.0f);

          noneItem.transform.SetParent(parent);

        }
        else
        {
          for (int i = 0; i < root.Count; i++)
          {
            GameObject clone = Instantiate(MyStockItem);
            RectTransform cloneRect = clone.GetComponent<RectTransform>();
            Text id = clone.transform.GetChild(0).GetComponent<Text>();
            Text stockName = clone.transform.GetChild(1).GetComponent<Text>();
            Text curPrice = clone.transform.GetChild(2).GetComponent<Text>();
            Text avgDealPrice = clone.transform.GetChild(3).GetComponent<Text>();
            Text curAmount = clone.transform.GetChild(4).GetComponent<Text>();
            Text changePercent = clone.transform.GetChild(5).GetComponent<Text>();
            InputField SellAmount = clone.transform.GetChild(6).GetComponent<InputField>();
            Button Sell = clone.transform.GetChild(7).GetComponent<Button>();

            id.text = root[i]["stockId"].Value;
            stockName.text = root[i]["stockName"].Value;
            curPrice.text = root[i]["curPrice"].Value;
            avgDealPrice.text = root[i]["avgDealPrice"].Value;
            curAmount.text = root[i]["curAmount"].Value;
            changePercent.text = root[i]["changePercent"].Value;

            Sell.onClick.AddListener(delegate ()
            {
              onClickSellBtn(clone.transform.GetChild(0).GetComponent<Text>().text);
            });

            clone.transform.localPosition = Vector3.zero;
            clone.transform.SetParent(parent);

            cloneRect.offsetMax = new Vector2(-8, -10);
            cloneRect.offsetMin = new Vector2(5, 0);
            cloneRect.localScale = new Vector3(1.0f, 1.0f, 1.0f);
            MyStockItems.Add(clone);
            Debug.Log("root : " + root[i].ToString());
          }
        }
      }
    }
  }
  public IEnumerator SellMyStock(string currentStockId)
  {
    BuyStockItem data = new BuyStockItem();
    data.studentId = sId;
    data.stockId = int.Parse(currentStockId);
    int index = 0;
    for (int i = 0; i < MyStockItems.Count; i++)
    {
      if (MyStockItems[i] != null)
      {
        Debug.Log("확인 : " + MyStockItems[i].transform.GetChild(1).GetComponent<Text>().text);
        if (MyStockItems[i].transform.GetChild(0).GetComponent<Text>().text == currentStockId)
        {
          index = i;
          break;
        }
      }
    }
    Debug.Log(index);
    data.price = int.Parse(MyStockItems[index].transform.GetChild(2).GetComponent<Text>().text);
    data.amount = int.Parse(MyStockItems[index].transform.GetChild(6).GetComponent<InputField>().text) * -1;
    string json = JsonUtility.ToJson(data);

    using (UnityWebRequest request = UnityWebRequest.Post(baseURL + "stock/deal", json))
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
        JSONNode root = JSON.Parse(result);
        if (result.Equals("true"))
        {
          ShowSuccessSellStockItem();
        }
        else
        {
          ShowFailSellStockItem();
        }
      }
    }
  }

  public void onClickDetailBtn()
  {
    StartCoroutine(MyBalance(sId));
    if (noneItem != null) Destroy(noneItem);
    foreach (GameObject item in GetStockItem)
    {
      Destroy(item);
    }
    ObjectActive("GenericContentForms", 1);
  }

  public void OnClickGetStockList()
  {
    if (StockItems != null)
    {
      foreach (GameObject item in StockItems)
      {
        Destroy(item);
      }
    }
    if (noneItem != null) Destroy(noneItem);
    ObjectActive("GenericContentForms", 0);
    StartCoroutine(GetStockList());
  }
  public void onClickGetMyStockList()
  {
    if (MyStockItems != null)
    {
      foreach (GameObject item in MyStockItems)
      {
        if (item != null)
        {
          Destroy(item);
        }
      }
    }
    if (noneItem != null) Destroy(noneItem);
    ObjectActive("GenericContentForms", 2);
    StartCoroutine(GetMyStockList());
  }
  public void onClickSellBtn(string stockId)
  {
    StartCoroutine(SellMyStock(stockId));
  }

  public void OnClickB1CBackButton()
  {
    if (noneItem != null) Destroy(noneItem);
    foreach (GameObject item in MyStockItems)
    {
      if (item != null)
      {
        Destroy(item);
      }
    }
    ObjectActive("GenericMember", -1);
  }

  public void OnClickB1DCBackButton()
  {
    if (noneItem != null) Destroy(noneItem);
    foreach (GameObject item in MyStockItems)
    {
      if (item != null)
      {
        Destroy(item);
      }
    }
    ObjectActive("GenericContentForms", 0);
  }

  public void OnClickB2CBackButton()
  {
    if (noneItem != null) Destroy(noneItem);
    foreach (GameObject item in MyStockItems)
    {
      if (item != null)
      {
        Destroy(item);
      }
    }
    ObjectActive("GenericMember", -1);
  }

  //Alert view 메서드
  private void ShowSuccessBuyStockItem()
  {
    string title = "";
    string message = "주식을 구입했습니다!🎉🎉";

    AlertViewController.Show(title, message, new AlertViewOptions
    {
      okButtonTitle = "확인",
      okButtonDelegate = () =>
      {
        StartCoroutine(MyBalance(sId));
      }
    });
  }

  private void ShowFailBuyStockItem()
  {
    string title = "";
    string message = "주식 구입을 실패했어요ㅠ.ㅠ" + System.Environment.NewLine + "혹시 돈이 모자라진 않은지 확인해주세요!";

    AlertViewController.Show(title, message);
  }

  private void ShowSuccessSellStockItem()
  {
    string title = "";
    string message = "주식을 판매했어요!💲";

    AlertViewController.Show(title, message, new AlertViewOptions
    {
      okButtonTitle = "확인",
      okButtonDelegate = () =>
      {
        foreach (GameObject item in MyStockItems)
        {
          if (item != null)
          {
            Destroy(item);
          }
        }
        Debug.Log("true");
        StartCoroutine(GetMyStockList());
      }
    });
  }

  private void ShowFailSellStockItem()
  {
    string title = "";
    string message = "주식 판매에 실패했어요ㅜ.ㅜ" + System.Environment.NewLine + "혹시..판매 개수를 잘못 쓴건 아닐까요?🤔";

    AlertViewController.Show(title, message);
  }


  private void ObjectActive(string ojName, int index)
  {
    if (index == -1)
    {
      foreach (GameObject item in StockCenters)
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
      foreach (GameObject item in StockCenters)
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