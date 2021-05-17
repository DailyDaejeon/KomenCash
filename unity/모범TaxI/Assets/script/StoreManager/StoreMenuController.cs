using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.Networking;
using SimpleJSON;

[System.Serializable]
public class BuyItemData
{
  public int itemId;
  public int studentId;
}

[System.Serializable]
public class AddItemRequestData
{
  public int studentId;
  public string itemName;
  public string content;
}

public class StoreMenuController : MonoBehaviour
{
  private string baseURL = "https://k4b203.p.ssafy.io/api/";
  public RectTransform uiGroup;
  PlayerController enterPlayer;

  //메뉴창 버튼
  [SerializeField]
  private GameObject StoreMenu;
  [SerializeField]
  private GameObject StoreForm;

  //오브젝트 담을 리스트
  private List<GameObject> objectList = new List<GameObject>();

  //컨트롤러에서 사용하는 변수
  bool _isExit;
  int gId;
  int sId;

  //생성해야하는 prefab

  //상품 목록 보기
  private GameObject noneSIClone;
  private GameObject storeItem;
  private List<GameObject> SIClone;

  //상품 추가 요청 하기

  //상품 구매 내역 보기
  private GameObject noneMSHClone;
  private GameObject myStoreHistory;
  private List<GameObject> MSHClone;


  void Start()
  {
    objectList.Add(StoreMenu);
    objectList.Add(StoreForm);

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
    ObjectActive("StudentStoreMenu", -1);

    if (noneSIClone != null) Destroy(noneSIClone);
    if (SIClone != null)
    {
      foreach (GameObject item in SIClone)
      {
        Destroy(item);
      }
    }
    if (noneMSHClone != null) Destroy(noneMSHClone);
    if (MSHClone != null)
    {
      foreach (GameObject item in MSHClone)
      {
        Destroy(item);
      }
    }
    _isExit = true;
  }

  //상점 메뉴에 보여줄 데이터 받아오는 메서드들

  //1. 상품 리스트 조회
  private IEnumerator GetStoreItemList()
  {
    using (UnityWebRequest request = UnityWebRequest.Get(baseURL + "store/list/" + gId))
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
          Transform parent = GameObject.Find("ShowStoreItemContent").GetComponent<Transform>();
          noneSIClone = NoneContentMsgController.Show("구매 가능한 상품이 없습니다ㅜ.ㅜ").gameObject;

          RectTransform noneSIRect = noneSIClone.GetComponent<RectTransform>();
          noneSIClone.transform.SetParent(parent);

          noneSIRect.offsetMin = new Vector2(0, 0);
          noneSIRect.offsetMax = new Vector2(0, 0);
        }
        else
        {
          Debug.Log("상품 리스트 있음!" + result);
          Transform parent = GameObject.Find("StoreItemListContent").GetComponent<Transform>();
          storeItem = Resources.Load("StoreItem") as GameObject;
          for (int i = 0; i < root.Count; i++)
          {
            GameObject clone = Instantiate(storeItem);

            Text itemId = clone.transform.GetChild(0).GetComponent<Text>();
            Text itemName = clone.transform.GetChild(1).GetComponent<Text>();
            Text itemPrice = clone.transform.GetChild(2).GetComponent<Text>();

            itemId.text = root[i]["id"].Value;
            itemName.text = root[i]["name"].Value;
            itemPrice.text = root[i]["price"].Value;

            clone.transform.SetParent(parent);
            SIClone.Add(clone);

            Button itemBtn = clone.GetComponent<Button>();

            itemBtn.onClick.AddListener(delegate ()
            {
              OnPressBuyItemButton(itemId.text, itemPrice.text);
            });
          }
        }
      }
    }
  }

  //2. 상품 구매
  private IEnumerator BuyItem(string itemId, string itemPrice)
  {
    BuyItemData data = new BuyItemData();
    data.itemId = int.Parse(itemId);
    data.studentId = sId;

    string json = JsonUtility.ToJson(data);

    using (UnityWebRequest request = UnityWebRequest.Post(baseURL + "store/purchase", json))
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
          int balance = DataController.GetBalance() - int.Parse(itemPrice);
          DataController.setBalance(balance);
          Text statBalance = GameObject.Find("balance").GetComponent<Text>();
          statBalance.text = "통장 잔액 : " + balance;
          ShowSuccessBuyItemAlert();
        }
        else
        {
          ShowFailBuyItemAlert();
        }
      }
    }
  }

  //3. 상품 추가 요청
  private void ShowAddRequestForm()
  {
    InputField itemName = StoreForm.transform.GetChild(1).GetChild(1).GetChild(1).GetComponent<InputField>();
    InputField itemReason = StoreForm.transform.GetChild(1).GetChild(1).GetChild(3).GetComponent<InputField>();
    Button submitButton = StoreForm.transform.GetChild(1).GetChild(1).GetChild(4).GetComponent<Button>();
    Button cancelButton = StoreForm.transform.GetChild(1).GetChild(1).GetChild(5).GetComponent<Button>();

    submitButton.onClick.AddListener(delegate ()
    {
      if (itemName.text == "" || itemReason.text == "") AlertViewController.Show("", "모든 항목을 적어주세요!");
      else StartCoroutine(AddItemRequest(itemName.text, itemReason.text));
    });

    cancelButton.onClick.AddListener(delegate ()
    {
      itemName.text = "";
      itemReason.text = "";

      ObjectActive("StudentStoreMenu", -1);
    });
  }

  private IEnumerator AddItemRequest(string iName, string iContent)
  {
    AddItemRequestData data = new AddItemRequestData();
    data.studentId = sId;
    data.itemName = iName;
    data.content = iContent;

    string json = JsonUtility.ToJson(data);

    using (UnityWebRequest request = UnityWebRequest.Post(baseURL + "store/add-request", json))
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
          ShowSuccessAddRequestAlert();
        }
        else
        {
          ShowFailAddRequestAlert();
        }
      }
    }
  }

  private IEnumerator ShowMyStoreHistory()
  {
    using (UnityWebRequest request = UnityWebRequest.Get(baseURL + "store/history/student/" + sId))
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
          Transform parent = GameObject.Find("StoreHistoryContent").GetComponent<Transform>();
          noneMSHClone = NoneContentMsgController.Show("상품을 구매한 이력이 없습니다!").gameObject;

          RectTransform noneMSHRect = noneMSHClone.GetComponent<RectTransform>();
          noneMSHClone.transform.SetParent(parent);

          noneMSHRect.offsetMin = new Vector2(0, 0);
          noneMSHRect.offsetMax = new Vector2(0, 0);
        }
        else
        {
          Transform parent = GameObject.Find("StoreHistoryContent").GetComponent<Transform>();
          myStoreHistory = Resources.Load("StoreHistoryItem") as GameObject;
          for (int i = root.Count - 1; i >= 0; i--)
          {
            GameObject clone = Instantiate(myStoreHistory);

            Text itemName = clone.transform.GetChild(0).GetComponent<Text>();
            Text itemPrice = clone.transform.GetChild(1).GetComponent<Text>();
            Text purchaseDate = clone.transform.GetChild(2).GetComponent<Text>();
            Text studentName = clone.transform.GetChild(3).GetComponent<Text>();

            itemName.text = root[i]["name"].Value;
            itemPrice.text = root[i]["price"].Value;
            purchaseDate.text = root[i]["perchaseDate"].Value.Split('T')[0];
            studentName.text = root[i]["studentNickname"].Value;

            clone.transform.SetParent(parent);
            MSHClone.Add(clone);
          }
        }
      }
    }
  }

  //버튼 클릭 시, 실행될 메서드 연결시키는 메서드
  public void OnPressBackButton()
  {
    if (StoreMenu.activeSelf == false)
    {
      if (noneSIClone != null) Destroy(noneSIClone);
      if (SIClone != null)
      {
        foreach (GameObject item in SIClone)
        {
          Destroy(item);
        }
      }
      if (noneMSHClone != null) Destroy(noneMSHClone);
      if (MSHClone != null)
      {
        foreach (GameObject item in MSHClone)
        {
          Destroy(item);
        }
      }
      ObjectActive("StudentStoreMenu", -1);
    }
  }

  public void OnPressShowStoreList()
  {
    SIClone = new List<GameObject>();
    StartCoroutine(GetStoreItemList());
    ObjectActive("GenericContentForms", 0);
  }

  private void OnPressBuyItemButton(string itemId, string itemPrice)
  {
    ShowConfirmBuyItemAlert(itemId, itemPrice);
  }

  public void OnPressAddItemRequest()
  {
    ShowAddRequestForm();
    ObjectActive("GenericContentForms", 1);
  }

  public void OnPressMyStoreHistory()
  {
    MSHClone = new List<GameObject>();
    StartCoroutine(ShowMyStoreHistory());
    ObjectActive("GenericContentForms", 2);
  }

  //Alert 불러오는 메서드
  private void ShowConfirmBuyItemAlert(string itemId, string itemPrice)
  {
    string title = "";
    string message = "상품을 구매하겠습니까?";

    AlertViewController.Show(title, message, new AlertViewOptions
    {
      okButtonTitle = "구매하기",
      okButtonDelegate = () =>
      {
        StartCoroutine(BuyItem(itemId, itemPrice));
      },
      cancelButtonTitle = "취소하기",
      cancelButtonDelegate = () => { }
    });
  }
  private void ShowSuccessBuyItemAlert()
  {
    string title = "";
    string message = "상품을 구매했어요!";
    AlertViewController.Show(title, message);
  }

  private void ShowFailBuyItemAlert()
  {
    string title = "";
    string message = "구매 도중 오류가 발생했어요ㅠ.ㅠ";

    AlertViewController.Show(title, message);
  }

  private void ShowSuccessAddRequestAlert()
  {
    string title = "";
    string message = "상품 추가 요청을 보냈어요!";

    AlertViewController.Show(title, message, new AlertViewOptions
    {
      okButtonTitle = "확인",
      okButtonDelegate = () =>
      {
        InputField itemName = StoreForm.transform.GetChild(1).GetChild(1).GetChild(1).GetComponent<InputField>();
        InputField itemReason = StoreForm.transform.GetChild(1).GetChild(1).GetChild(3).GetComponent<InputField>();

        itemName.text = "";
        itemReason.text = "";

        ObjectActive("StudentStoreMenu", -1);
      }
    });
  }

  private void ShowFailAddRequestAlert()
  {
    string title = "";
    string message = "상품 추가 요청 도중 오류가 발생했어요ㅠ.ㅠ";

    AlertViewController.Show(title, message);
  }

  //해당 오브젝트만 활성화 시키는 메서드
  public void ObjectActive(string ojName, int index)
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
