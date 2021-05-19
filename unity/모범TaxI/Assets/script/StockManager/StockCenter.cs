using System.Collections;
using System.Collections.Generic;
using System;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.Networking;
using CodeMonkey.Utils;
using SimpleJSON;
using UnityEngine.EventSystems;

public class StockCenter : MonoBehaviour
{
  public string baseURL = "http://k4b203.p.ssafy.io:8081/api/";
  public RectTransform uiGroup;
  public Animator anim;
  PlayerController enterPlayer;

  // 증권 화면
  [SerializeField]
  private List<GameObject> StockCenters = new List<GameObject>();

  // 주식 목록보기
  [SerializeField]
  private GameObject stockItem;
  [SerializeField]
  private List<GameObject> StockItems = new List<GameObject>(); // 주식 종목 리스트



  // 주식 상세 히스토리 보기
  [SerializeField]
  private List<GameObject> GetStockItem = new List<GameObject>();
  private GameObject Current_Status;
  private string currentStockId;

  // 해당 종목 현재 가격, 이전 가격과의 차이
  public GameObject Current_Value;
  public GameObject Increase_Value;
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

  int sId;
  int gId;
  void Start()
  {
    sId = 1;
    gId = 1;
    // BuyCount = GetComponent<InputField>();
    StockCenters.Add(GameObject.Find("StockList"));
    StockCenters.Add(GameObject.Find("MyStockItems"));
    StockCenters.Add(GameObject.Find("GetStockItem"));
    BuyCount.onValueChange.AddListener(delegate
    {
      ChangeBalance();
    });
    OnClickGetStockList();
  }
  void Update()
  {
  }

  public void Enter(PlayerController player)
  {
    enterPlayer = player;
    uiGroup.anchoredPosition = Vector3.zero;

  }
  public void Exit()
  {
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
      // UnityWebRequest request = UnityWebRequest.Get("http://localhost:8081/ustudent/1"); 
      yield return request.SendWebRequest();
      if (request.error != null)
      {
        Debug.Log(request.error);
      }
      else
      {
        string result = request.downloadHandler.text;
        JSONNode root = JSON.Parse(result);

        Transform parent = GameObject.Find("StockList").GetComponent<Transform>();
        stockItem = Resources.Load("StockItem") as GameObject;
        Debug.Log(stockItem);
        if (root.Count <= 0)
        {
          GameObject clone = Instantiate(stockItem);
          Text name = clone.transform.GetChild(0).GetComponent<Text>();
          name.text = "No Data";
          clone.transform.localPosition = Vector3.zero;
          clone.transform.SetParent(parent);

          StockItems.Add(clone);
          Debug.Log("No Data");
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

            Button detailBtn = clone.transform.GetChild(5).GetComponent<Button>();

            id.text = root[i]["id"].Value;
            prePrice.text = root[i]["prePrice"].Value;
            price.text = root[i]["price"].Value;
            name.text = root[i]["name"].Value;
            string hint = root[i]["hint"].Value;
            detailBtn.onClick.AddListener(delegate ()
            {
              Debug.Log("name : " + clone.transform.GetChild(1).GetComponent<Text>().text);
              onClickDetailBtn();
              showItemHistory(clone.transform.GetChild(0).GetComponent<Text>().text, hint);

            });



            clone.transform.localPosition = Vector3.zero;
            clone.transform.SetParent(parent);

            cloneRect.offsetMax = new Vector2(-8, -10);
            cloneRect.offsetMin = new Vector2(5, 0);
            cloneRect.localScale = new Vector3(1.0f, 1.0f, 1.0f);
            StockItems.Add(clone);
            Debug.Log("root : " + root[i].ToString());
            // Text 
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
    Debug.Log("좀 살게~~");
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
          StartCoroutine(MyBalance(sId));
        }
        else
        {
          Debug.Log("돈없음");
        }
      }
    }
  }

  // 해당 주식 종목 History
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
        // 없는 경우
        if (root.Count <= 0)
        {
          Debug.Log(request.error);
        }
        else
        {
          for (int i = 0; i < root.Count; i++)
          {
            priceList.Add(int.Parse(root[i]["price"].Value));
            Debug.Log("root : " + root[i].ToString());
            // Text 
          }

          int a = 0;
          if (root.Count > 1)
          {
            a = root[root.Count - 1]["price"] - root[root.Count - 2]["price"];
            Debug.Log(root[root.Count - 1]["price"]);
            Debug.Log(a);
          }
          GameObject.Find("Hint").GetComponent<Text>().text = hint;
          Current_Value.GetComponent<Text>().text = root[root.Count - 1]["price"].Value;
          Increase_Value.GetComponent<Text>().text = a.ToString();

          Window_Graph_Test.ShowGraph(priceList);
        }
      }
    }

  }

  // 내가 가지고 있는 주식 목록
  public IEnumerator GetMyStockList()
  {

    using (UnityWebRequest request = UnityWebRequest.Get(baseURL + "/stock/deal/holding-status/" + sId))
    {
      // UnityWebRequest request = UnityWebRequest.Get("http://localhost:8081/ustudent/1"); 
      yield return request.SendWebRequest();
      if (request.error != null)
      {
        Debug.Log(request.error);
      }
      else
      {
        string result = request.downloadHandler.text;
        JSONNode root = JSON.Parse(result);

        Transform parent = GameObject.Find("StockItemDetailBackGround").GetComponent<Transform>();
        MyStockItem = Resources.Load("MyStockItem") as GameObject;

        if (root.Count <= 0)
        {
          GameObject clone = Instantiate(MyStockItem);
          Text id = clone.transform.GetChild(0).GetComponent<Text>();
          Text stockName = clone.transform.GetChild(1).GetComponent<Text>();
          Text curPrice = clone.transform.GetChild(2).GetComponent<Text>();
          Text avgDealPrice = clone.transform.GetChild(3).GetComponent<Text>();
          Text curAmount = clone.transform.GetChild(4).GetComponent<Text>();
          Text changePercent = clone.transform.GetChild(5).GetComponent<Text>();
          InputField SellAmount = clone.transform.GetChild(6).GetComponent<InputField>();
          Button Sell = clone.transform.GetChild(7).GetComponent<Button>();

          id.text = "No Data";
          stockName.text = "No Data";
          curPrice.text = "No Data";
          avgDealPrice.text = "No Data";
          curAmount.text = "No Data";
          changePercent.text = "No Data";
          clone.transform.localPosition = Vector3.zero;
          clone.transform.SetParent(parent);

          RectTransform cloneRect = clone.GetComponent<RectTransform>();
          cloneRect.localScale = new Vector3(1.0f, 1.0f, 1.0f);

          MyStockItems.Add(clone);
          Debug.Log("No Data");
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
            // Text 
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
          // MyStockItems[index].transform.GetChild(4).GetComponent<InputField>().text = (int.Parse(MyStockItems[index].transform.GetChild(4).GetComponent<InputField>().text)+data.amount).ToString();
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
        else
        {
          Debug.Log("개수가지고 장난질이냐");
        }
      }
    }
  }
  public void onClickDetailBtn()
  {
    StartCoroutine(MyBalance(sId));

    foreach (GameObject item in GetStockItem)
    {
      Destroy(item);
    }
    ObjectActive("GetStockItem", -1);
  }

  public void OnClickGetStockList()
  {
    foreach (GameObject item in StockItems)
    {
      Destroy(item);
    }
    StartCoroutine(GetStockList());
    ObjectActive("StockList", -1);
  }
  public void onClickGetMyStockList()
  {
    foreach (GameObject item in MyStockItems)
    {
      if (item != null)
      {
        Destroy(item);
      }
    }
    StartCoroutine(GetMyStockList());
    ObjectActive("MyStockItems", -1);
  }
  public void onClickSellBtn(string stockId)
  {
    StartCoroutine(SellMyStock(stockId));
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