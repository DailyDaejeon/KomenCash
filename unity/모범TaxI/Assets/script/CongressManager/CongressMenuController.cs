using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.Networking;
using SimpleJSON;
[System.Serializable]
public class selectVoteDate
{
  public int studentId;
  public int voteId;
  public int voteItemNum;
}
public class CongressMenuController : MonoBehaviour
{
  public string baseURL = "https://k4b203.p.ssafy.io/api/";
  public RectTransform uiGroup;
  PlayerController enterPlayer;


  // UI 폼 담을 리스트
  private List<GameObject> objectList = new List<GameObject>();

  //UI 오브젝트
  [SerializeField]
  private GameObject GenericMember;
  [SerializeField]
  private GameObject JobMember;
  [SerializeField]
  private GameObject GenericContentForm;
  [SerializeField]
  private GameObject JobContentForm;
  [SerializeField]
  private Button JobButton;


  //투표 리스트
  private List<GameObject> VoteList = new List<GameObject>();

  //투표 종목
  private GameObject VoteItem;

  //후보 아이템
  [SerializeField]
  private GameObject VoteDetailItem;

  //후보 리스트
  private List<GameObject> VoteDetailItems = new List<GameObject>();

  //차트에 보여줄 후보 아이템
  [SerializeField]
  private GameObject VoteResultChart;

  //차트에 보여줄 후보 리스트
  private List<GameObject> VoteResultCharts = new List<GameObject>();

  // 투표 요청 버튼? 생성 버튼?
  [SerializeField]
  public GameObject VoteReqeustBtn;

  // 투표 추가 하기
  [SerializeField]
  public GameObject VoteCreate;
  [SerializeField]
  public GameObject Candidate;

  private List<GameObject> VoteCreateCandidates = new List<GameObject>();
  public List<GameObject> candidateCloneList = new List<GameObject>();

  int sId;
  int gId;
  int jId;
  string sNickname;
  bool _isExit = true;

  //생성 프리팹
  private GameObject noneItem;

  private GameObject lawTitleClone;
  private List<GameObject> LTList;

  private GameObject lawContentClone;
  private GameObject LCList;

  private GameObject voteIDIClone;
  private List<GameObject> vIDIList;

  private GameObject AddCloumnClone;
  private List<GameObject> ACList;

  void Start()
  {
    sId = DataController.GetStudentId();
    gId = DataController.GetGroupId();
    sNickname = DataController.GetStudentNickname();

    objectList.Add(GenericMember);
    objectList.Add(JobMember);
    objectList.Add(GenericContentForm);
    objectList.Add(JobContentForm);
  }

  public bool GetExitState()
  {
    return _isExit;
  }

  public void Enter(PlayerController player)
  {
    enterPlayer = player;
    /* !!!반장, 부반장일 때만 공무원 버튼 보이는 코드!!! 지우지 말기!!!  */
    // if (false == (DataController.LoadUserInfo().job.name.Equals("국회의장") || DataController.LoadUserInfo().job.name.Equals("국무총리")))
    // {
    //   JobButton.gameObject.SetActive(false);
    // }

    uiGroup.anchoredPosition = Vector3.zero;
    _isExit = false;
  }

  public void Exit()
  {
    uiGroup.anchoredPosition = Vector3.down * -1000;
    ObjectActive("GenericMember", -1);
    _isExit = true;
  }

  /* 국회 메뉴에 보여줄 데이터 받아오는 메서드들 */

  //1. 헌법 조회하기
  private IEnumerator GetLawList()
  {
    using (UnityWebRequest request = UnityWebRequest.Get(baseURL + "law/" + gId))
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
          Transform parent = GameObject.Find("LawListContents").GetComponent<Transform>();

          noneItem = NoneContentMsgController.Show("제정된 법이 없어요ㅜ.ㅜ").gameObject;
          noneItem.transform.SetParent(parent);

          RectTransform noneItemRect = noneItem.GetComponent<RectTransform>();
          noneItemRect.localScale = new Vector3(1.0f, 1.0f, 1.0f);
        }
        else
        {
          Transform lawListTitleParent = GameObject.Find("LawListTitle").GetComponent<Transform>();
          //   Transform lawListContentParent = GameObject.Find("LawListContents").GetComponent<Transform>();

          lawTitleClone = Resources.Load("LawListTitleItem") as GameObject;
          //   lawContentClone = Resources.Load("LawItem") as GameObject;

          for (int i = 0; i < root.Count; i++)
          {
            GameObject clone = Instantiate(lawTitleClone);

            Button getLawContentButton = clone.GetComponent<Button>();
            Text lawTitleTxt = clone.transform.GetChild(0).GetComponent<Text>();

            lawTitleTxt.text = root[i]["lawFindByLawTypeResponseDtoList"][0]["lawType"].Value;

            getLawContentButton.onClick.AddListener(delegate ()
            {
              showLawContent(root[i]);
            });

            lawTitleClone.transform.SetParent(lawListTitleParent);
            RectTransform cloneRect = clone.GetComponent<RectTransform>();
            cloneRect.localScale = new Vector3(1.0f, 1.0f, 1.0f);

            LTList.Add(clone);
          }
        }
      }
    }
  }

  private void showLawContent(JSONNode root)
  {
    Transform parent = GameObject.Find("LawListContents").GetComponent<Transform>();
    lawContentClone = Resources.Load("LawItem") as GameObject;

    for (int i = 0; i < root["lawFindByLawTypeResponseDtoList"].Count; i++)
    {
      GameObject clone = Instantiate(lawContentClone);

      Text lawId = clone.transform.GetChild(0).GetComponent<Text>();
      Text lawArticleParagraph = clone.transform.GetChild(1).GetComponent<Text>();
      Text lawContent = clone.transform.GetChild(2).GetComponent<Text>();

      lawId.text = root["lawFindByLawTypeResponseDtoList"][i]["id"].Value;
      lawArticleParagraph.text = "제 " + root["lawFindByLawTypeResponseDtoList"][i]["article"].Value + "조 " + root["lawFindByLawTypeResponseDtoList"][i]["paragraph"].Value + "항";
      lawContent.text = root["lawFindByLawTypeResponseDtoList"][i]["content"].Value;
    }
  }

  //2. 투표 조회, 참여하기
  public IEnumerator GetVoteList(int gId)
  {
    using (UnityWebRequest request = UnityWebRequest.Get(baseURL + "vote/list/" + gId))
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

        Transform parent = GameObject.Find("VoteListContent").GetComponent<Transform>();
        VoteItem = Resources.Load("VoteListItem") as GameObject;

        if (root.Count <= 0)
        {
          noneItem = NoneContentMsgController.Show("생성된 투표가 없어요!").gameObject;
          noneItem.transform.SetParent(parent);

          RectTransform noneItemRect = noneItem.GetComponent<RectTransform>();
          noneItemRect.localScale = new Vector3(1.0f, 1.0f, 1.0f);
        }
        else
        {
          // list 생성
          //   List<GameObject> cloneList = new List<GameObject>();
          for (int i = 0; i < root.Count; i++)
          {
            GameObject clone = Instantiate(VoteItem);
            RectTransform cloneRect = clone.GetComponent<RectTransform>();

            Text voteId = clone.transform.GetChild(0).GetComponent<Text>();
            Text title = clone.transform.GetChild(1).GetComponent<Text>();
            Text studentId = clone.transform.GetChild(2).GetComponent<Text>();
            Text studentNickname = clone.transform.GetChild(3).GetComponent<Text>();

            Button detailBtn = clone.transform.GetChild(4).GetComponent<Button>();

            voteId.text = root[i]["id"].Value;
            title.text = root[i]["title"].Value;
            studentId.text = root[i]["studentId"].Value;
            studentNickname.text = root[i]["studentNickname"].Value;

            detailBtn.onClick.AddListener(delegate ()
            {
              Debug.Log("title : " + clone.transform.GetChild(1).GetComponent<Text>().text);
              OnPressVoteDetailButton(clone.transform.GetChild(0).GetComponent<Text>().text, clone.transform.GetChild(2).GetComponent<Text>().text);
            });

            // clone.transform.localPosition = Vector3.zero;
            clone.transform.SetParent(parent);

            // cloneRect.offsetMax = new Vector2(-8, -10);
            // cloneRect.offsetMin = new Vector2(5, 0);
            cloneRect.localScale = new Vector3(1.0f, 1.0f, 1.0f);

            VoteList.Add(clone);
            // Debug.Log("root : " + root[i].ToString());
            // Text 
          }
        }
      }
    }
  }

  //2-1. 투표 상세보기 버튼 클릭
  public void OnPressVoteDetailButton(string voteId, string studentId)
  {
    foreach (GameObject item in VoteDetailItems)
    {
      Destroy(item);
    }
    foreach (GameObject item in VoteResultCharts)
    {
      Destroy(item);
    }
    // 상세 화면으로 넘어가기
    StartCoroutine(GetVoteDetail(voteId, studentId));
    ObjectActive("GenericContentForms", 2);
  }

  //2-2. 투표 상세 내용 조회
  public IEnumerator GetVoteDetail(string voteId, string studentId)
  {
    using (UnityWebRequest request = UnityWebRequest.Get(baseURL + "vote/detail/" + voteId))
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
          Transform gObject = GameObject.Find("Button2DetailContent").GetComponent<Transform>();

          Text DVoteTitle = gObject.transform.GetChild(1).GetComponent<Text>();
          Text DVoteContent = gObject.transform.GetChild(3).GetComponent<Text>();
          GameObject DeleteButton = gObject.transform.GetChild(4).GetComponent<GameObject>();

          DVoteTitle.text = root[1];
          DVoteContent.text = root[2];

          if (int.Parse(studentId) != sId)
          {
            DeleteButton.SetActive(false);
          }

          Transform parent = GameObject.Find("VoteContent").GetComponent<Transform>();
          VoteDetailItem = Resources.Load("VoteItem") as GameObject;

          for (int i = 0; i < root[6].Count; i++)
          {
            GameObject clone = Instantiate(VoteDetailItem);

            Button detailButton = clone.transform.GetChild(0).GetComponent<Button>();
            Text vNum = clone.transform.GetChild(0).GetChild(0).GetComponent<Text>();
            Text vContent = clone.transform.GetChild(0).GetChild(1).GetComponent<Text>();
            Text vResultCnt = clone.transform.GetChild(0).GetChild(2).GetComponent<Text>();

            List<JSONNode> selectList = new List<JSONNode>();

            vNum.text = root[6][i][2].Value;
            vContent.text = root[6][i][0].Value;
            vResultCnt.text = root[6][i][3].Value + " / " + root[5].Count;

            for (int j = 0; j < root[5].Count; j++)
            {
              if (root[5][j][1].AsInt == int.Parse(vNum.text))
              {
                selectList.Add(root[5][j]);
              }
            }

            detailButton.onClick.AddListener(delegate ()
            {
              GameObject.Find("VoteResultDetailForm").SetActive(true);
              vIDIList = new List<GameObject>();
              ShowVoteItemDetailInfo(selectList);
            });

            Button selectButton = clone.transform.GetChild(1).GetComponent<Button>();

            selectButton.onClick.AddListener(delegate ()
            {
              for (int k = 0; k < root[5].Count; k++)
              {
                if (root[5][k][0].AsInt == sId)
                {
                  string title = "";
                  string message = "이미 투표를 진행한 주제입니다.";

                  AlertViewController.Show(title, message);
                }
                else
                {
                  StartCoroutine(selectVote(voteId, studentId, vNum.text));
                  if (VoteDetailItems != null)
                  {
                    foreach (GameObject item in VoteDetailItems)
                    {
                      Destroy(item);
                    }
                  }
                  StartCoroutine(GetVoteDetail(voteId, studentId));
                }
              }
            });

            clone.transform.SetParent(parent);

            RectTransform cloneRect = clone.GetComponent<RectTransform>();
            cloneRect.localScale = new Vector3(1.0f, 1.0f, 1.0f);
            VoteDetailItems.Add(clone);
          }
        }
      }
    }
  }

  private void ShowVoteItemDetailInfo(List<JSONNode> selectList)
  {
    Transform rootParent = GameObject.Find("VoteResultDetailForm").GetComponent<Transform>();
    Transform parent = GameObject.Find("VoteDetailContent").GetComponent<Transform>();
    voteIDIClone = Resources.Load("SelectStudentItem") as GameObject;

    if (selectList.Count <= 0)
    {
      noneItem = NoneContentMsgController.Show("해당 항목에 투표한 친구들이 없어요ㅠ.ㅠ").gameObject;
      noneItem.transform.SetParent(parent);

      RectTransform noneItemRect = noneItem.GetComponent<RectTransform>();
      noneItemRect.localScale = new Vector3(1.0f, 1.0f, 1.0f);

    }
    else
    {
      for (int i = 0; i < selectList.Count; i++)
      {
        GameObject clone = Instantiate(voteIDIClone);

        Text studentName = clone.GetComponent<Text>();
        studentName.text = selectList[i]["studentNickname"].Value;

        clone.transform.SetParent(parent);
        RectTransform cloneRect = clone.GetComponent<RectTransform>();
        cloneRect.localScale = new Vector3(1.0f, 1.0f, 1.0f);

        vIDIList.Add(clone);
      }
    }

    Button returnButton = rootParent.transform.GetChild(0).GetComponent<Button>();

    returnButton.onClick.AddListener(delegate ()
    {
      if (vIDIList != null)
      {
        foreach (GameObject item in vIDIList)
        {
          Destroy(item);
        }
      }
      GameObject.Find("VoteResultDetailForm").SetActive(false);
    });
  }



  private IEnumerator selectVote(string voteId, string studentId, string vNum)
  {

    selectVoteDate data = new selectVoteDate();
    data.studentId = int.Parse(studentId);
    data.voteId = int.Parse(voteId);
    data.voteItemNum = int.Parse(vNum);

    string json = JsonUtility.ToJson(data);

    using (UnityWebRequest request = UnityWebRequest.Post(baseURL + "vote/choice", json))
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

          string title = "";
          string message = "성공적으로 투표했습니다.";

          AlertViewController.Show(title, message, new AlertViewOptions
          {
            okButtonTitle = "확인",
            okButtonDelegate = () =>
            {
              if (vIDIList != null)
              {
                foreach (GameObject item in vIDIList)
                {
                  Destroy(item);
                }
              }

              ObjectActive("GenericMember", -1);
            }
          });
        }
      }
    }
  }


  //3. 투표 칼럼 생성하기
  private void AddVoteColumn()
  {
    Transform parent = GameObject.Find("AddFormContent").GetComponent<Transform>();
    AddCloumnClone = Resources.Load("AddInputFieldItem") as GameObject;

    int cloneNum = ACList.Count;
    GameObject clone = Instantiate(AddCloumnClone);
    RectTransform cloneRect = clone.GetComponent<RectTransform>();

    InputField CandidateInput = clone.transform.GetChild(0).GetComponent<InputField>();
    Button DeleteCandidateBtn = clone.transform.GetChild(1).GetComponent<Button>();

    DeleteCandidateBtn.onClick.AddListener(delegate ()
    {
      Destroy(clone);
      candidateCloneList.RemoveAt(cloneNum);
    });

    clone.transform.SetParent(parent);
    cloneRect.localScale = new Vector3(1.0f, 1.0f, 1.0f);
    ACList.Add(clone);
  }

  //3-1. 투표 생성하기
  [System.Serializable]
  public class VoteAddUpdateRequestDto
  {
    public int studentId;
    public string title;
    public string content;
    public List<VoteItemList> voteItemList;
  }

  [System.Serializable]
  public class VoteItemList
  {
    public int itemNum;
    public string content;
  }

  private IEnumerator CreateVote()
  {
    string title = GameObject.Find("CVoteTitleInput").GetComponent<InputField>().text;
    string content = GameObject.Find("CVoteContentInput").GetComponent<InputField>().text;


    VoteAddUpdateRequestDto data = new VoteAddUpdateRequestDto();
    data.title = title;
    data.content = content;
    data.studentId = sId;

    List<VoteItemList> VoteItemAddUpdateRequestDtoList = new List<VoteItemList>();
    for (int i = 0; i < ACList.Count; i++)
    {
      VoteItemList dto = new VoteItemList();
      dto.itemNum = i + 1;
      dto.content = ACList[i].transform.GetChild(0).GetComponent<InputField>().text;
      VoteItemAddUpdateRequestDtoList.Add(dto);
    }
    data.voteItemList = VoteItemAddUpdateRequestDtoList;

    string json = JsonUtility.ToJson(data);

    using (UnityWebRequest request = UnityWebRequest.Post(baseURL + "vote", json))
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
          Debug.Log("성공띠");
          ShowSuccssCreateVoteAlert();
        }
        else
        {
          Debug.Log("실패!");
          ShowFailCreateVoteAlert();
        }
      }
    }
  }


  //4. 법률 제안서 작성하기

  /* 버튼 클릭 시 실행되는 메서드 - OnPress */

  //1. 개인 업무 버튼 클릭
  public void OnPressGenericButton()
  {
    if (noneItem != null) Destroy(noneItem);
    if (voteIDIClone != null) Destroy(voteIDIClone);
    if (vIDIList != null)
    {
      foreach (GameObject item in vIDIList)
      {
        Destroy(item);
      }
    }
    if (AddCloumnClone != null) Destroy(AddCloumnClone);
    if (ACList != null)
    {
      foreach (GameObject item in ACList)
      {
        Destroy(item);
      }
    }
    ObjectActive("GenericMember", -1);
  }

  //2. 공무원 업무 버튼 클릭
  public void OnPressJobButton()
  {
    if (noneItem != null) Destroy(noneItem);
    if (voteIDIClone != null) Destroy(voteIDIClone);
    if (vIDIList != null)
    {
      foreach (GameObject item in vIDIList)
      {
        Destroy(item);
      }
    }
    if (AddCloumnClone != null) Destroy(AddCloumnClone);
    if (ACList != null)
    {
      foreach (GameObject item in ACList)
      {
        Destroy(item);
      }
    }
    ObjectActive("JobMember", -1);
  }

  //3. 헌법 조회 버튼 클릭
  public void OnPressGetLawHistoryButton()
  {
    if (noneItem != null) Destroy(noneItem);

    ObjectActive("GenericContentForms", 0);
  }

  //4. 투표 조회, 참여 버튼 클릭
  public void OnPressGetVoteListButton()
  {
    if (noneItem != null) Destroy(noneItem);

    ObjectActive("GenericContentForms", 1);
  }

  //5. 투표 생성하기 버튼 클릭
  public void OnPressCreateVoteMenuButton()
  {
    if (noneItem != null) Destroy(noneItem);

    ObjectActive("GenericContentForms", 2);
  }

  //6. 투표 생성 버튼(Submit) 클릭
  public void OnPressCreateVoteButton()
  {
    StartCoroutine(CreateVote());
  }

  public void OnPressCancelForCreateVoteButton()
  {
    GameObject.Find("CVoteTitleInput").GetComponent<InputField>().text = "";
    GameObject.Find("CVoteContentInput").GetComponent<InputField>().text = "";
    if (ACList != null)
    {
      foreach (GameObject item in ACList)
      {
        Destroy(item);
      }
    }

    ObjectActive("GenericMember", -1);
  }

  //7. 법률 제안서 버튼 클릭

  //8. 법률 제안서 제출 버튼 클릭

  //9. 법률 제안서 작성 취소하기 버튼 클릭

  /* Alert View 메서드 */
  private void ShowSuccssCreateVoteAlert()
  {
    string title = "";
    string message = "투표가 성공적으로 생성되었습니다!";

    AlertViewController.Show(title, message, new AlertViewOptions
    {
      okButtonTitle = "확인",
      okButtonDelegate = () =>
      {
        foreach (GameObject item in ACList)
        {
          Destroy(item);
        }
        GameObject.Find("CVoteTitleInput").GetComponent<InputField>().text = "";
        GameObject.Find("CVoteContentInput").GetComponent<InputField>().text = "";

        ObjectActive("GenericMember", -1);
      }
    });
  }

  private void ShowFailCreateVoteAlert()
  {
    string title = "";
    string message = "투표 생성에 실패했습니다ㅜ.ㅜ";

    AlertViewController.Show(title, message);
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
