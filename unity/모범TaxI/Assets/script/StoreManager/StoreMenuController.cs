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
  private string baseURL = "http://k4b203.p.ssafy.io:8081/api/";
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
          Transform parent = GameObject.Find("StoreItemListContent").GetComponent<Transform>();
          for (int i = 0; i < root.Count; i++)
          {
            GameObject clone = Resources.Load("StoreItem") as GameObject;

            Text itemId = clone.transform.GetChild(0).GetComponent<Text>();
            Text itemName = clone.transform.GetChild(1).GetComponent<Text>();
            Text itemPrice = clone.transform.GetChild(2).GetComponent<Text>();

            itemId.text = root[i]["id"].Value;
            itemName.text = root[i]["name"].Value;
            itemPrice.text = root[i]["price"].Value;

            SIClone.Add(clone);

            Button itemBtn = clone.GetComponent<Button>();

            itemBtn.onClick.AddListener(delegate ()
            {
              OnPressBuyItemButton(itemId.text);
            });
          }
        }
      }
    }
  }

  //2. 상품 구매
  private IEnumerator BuyItem(string itemId)
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
    InputField itemName = GameObject.Find("AddItemRequest").transform.GetChild(1).GetComponent<InputField>();
    InputField itemReason = GameObject.Find("AddItemRequest").transform.GetChild(3).GetComponent<InputField>();
    Button submitButton = GameObject.Find("AddItemRequest").transform.GetChild(4).GetComponent<Button>();
    Button cancelButton = GameObject.Find("AddItemRequest").transform.GetChild(5).GetComponent<Button>();

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

  //버튼 클릭 시, 실행될 메서드 연결시키는 메서드
  public void OnPressShowStoreList()
  {
    SIClone = new List<GameObject>();
    StartCoroutine(GetStoreItemList());
    ObjectActive("GenericContentForms", 0);
  }

  private void OnPressBuyItemButton(string itemId)
  {
    StartCoroutine(BuyItem(itemId));
  }

  private void OnPressAddItemRequest()
  {
    if (noneSIClone != null) Destroy(noneSIClone);
    if (SIClone != null)
    {
      foreach (GameObject item in SIClone)
      {
        Destroy(item);
      }
    }
    ShowAddRequestForm();
    ObjectActive("GenericContentForms", 1);
  }

  //Alert 불러오는 메서드
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
        InputField itemName = GameObject.Find("AddItemRequest").transform.GetChild(1).GetComponent<InputField>();
        InputField itemReason = GameObject.Find("AddItemRequest").transform.GetChild(3).GetComponent<InputField>();

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
