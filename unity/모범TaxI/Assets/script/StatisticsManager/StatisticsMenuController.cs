using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.Networking;
using SimpleJSON;

[System.Serializable]
public class StatisticsData
{
  public int groupId;
  public string sumbmitContent;
  public string startDate;
  public string endDate;
}

[System.Serializable]
public class ModifyStatisticsStatusData
{
  public int statisticListId;
  public int studentId;
}

[System.Serializable]
public class SendStatisticsData
{
  public int statisticListId;
}

public class StatisticsMenuController : MonoBehaviour
{
  private static string baseURL = "https://k4b203.p.ssafy.io/api/";
  public RectTransform uiGroup;
  PlayerController enterPlayer;

  [SerializeField]
  private GameObject GenericContent;

  [SerializeField]
  private GameObject StatisticContent;

  [SerializeField]
  private GameObject GenericContentForm;

  [SerializeField]
  private GameObject StatisticsContentForm;

  //해당 영역 나가면 한 번에 지울 수 있게 오브젝트 담을 리스트
  private static List<GameObject> objectList = new List<GameObject>();

  private bool _isExit = true;
  private static int gId;
  private int sId;

  //생성해야하는 prefab

  //none 문구
  private GameObject noneItem;

  //통계 내역 조회
  private GameObject SHClone;
  private List<GameObject> SHList;

  //통계 상세 내역 조회
  private GameObject SHDClone;
  private List<GameObject> SHDList;

  //통계 자료 추가
  private Transform AddForm;

  //제출 내역 수정 리스트 조회
  private GameObject SIMClone;
  private List<GameObject> SIMList;

  //제출 내역 상태 수정
  private GameObject SIMDClone;
  private List<GameObject> SIMDList;

  //신관위에 자료 전송
  private GameObject SSClone;
  private List<GameObject> SSList;

  void Start()
  {
    objectList.Add(GenericContent);
    objectList.Add(StatisticContent);
    objectList.Add(GenericContentForm);
    objectList.Add(StatisticsContentForm);

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
    ObjectActive("GenericMember", -1);

    if (noneItem != null) Destroy(noneItem);
    if (SHList != null)
    {
      foreach (GameObject item in SHList)
      {
        Destroy(item);
      }
    }
    if (SHDList != null)
    {
      foreach (GameObject item in SHDList)
      {
        Destroy(item);
      }
    }
    if (SIMList != null)
    {
      foreach (GameObject item in SIMList)
      {
        Destroy(item);
      }
    }
    if (SIMDList != null)
    {
      foreach (GameObject item in SIMDList)
      {
        Destroy(item);
      }
    }
    if (SSList != null)
    {
      foreach (GameObject item in SSList)
      {
        Destroy(item);
      }
    }

    _isExit = true;
  }

  //통계청 메뉴에 보여줄 데이터 받아오는 api 메서드

  //1. 통계 내역 조회
  private IEnumerator GetStatisticsHistory()
  {
    using (UnityWebRequest request = UnityWebRequest.Get(baseURL + "statistic/list/" + gId))
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

        if (root.Count <= 0)
        {
          Transform parent = GameObject.Find("StatisticsContent").GetComponent<Transform>();
          noneItem = NoneContentMsgController.Show("아직 제출 내역이 없습니다!").gameObject;

          noneItem.transform.SetParent(parent);

          RectTransform noneItemRect = noneItem.GetComponent<RectTransform>();
          noneItemRect.localScale = new Vector3(1.0f, 1.0f, 1.0f);
        }
        else
        {
          Transform parent = GameObject.Find("StatisticsContent").GetComponent<Transform>();
          SHClone = Resources.Load("StatisticsItem") as GameObject;

          for (int i = 0; i < root.Count; i++)
          {
            GameObject clone = Instantiate(SHClone);

            Text statisticsId = clone.transform.GetChild(0).GetComponent<Text>();
            Text statisticsContent = clone.transform.GetChild(1).GetComponent<Text>();
            Text startDate = clone.transform.GetChild(2).GetComponent<Text>();
            Text endDate = clone.transform.GetChild(3).GetComponent<Text>();
            Button sDetailButton = clone.GetComponent<Button>();

            statisticsId.text = root[i]["id"].Value;
            statisticsContent.text = root[i]["submitContent"].Value;
            startDate.text = root[i]["startDate"].Value.Split('T')[0];
            endDate.text = root[i]["endDate"].Value.Split('T')[0];

            sDetailButton.onClick.AddListener(delegate ()
            {
              OnPressGoToStatisticsDetail(statisticsId.text);
            });

            clone.transform.SetParent(parent);

            RectTransform cloneRect = clone.GetComponent<RectTransform>();
            cloneRect.localScale = new Vector3(1.0f, 1.0f, 1.0f);

            SHList.Add(clone);
          }
        }
      }
    }
  }

  //2. 통계 내역 상세 정보 보기
  private IEnumerator GetStatisticsDetail(string statisId)
  {
    using (UnityWebRequest request = UnityWebRequest.Get(baseURL + "statistic/detail/" + statisId))
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

        Transform DetailInfo = GenericContentForm.transform.GetChild(1).GetComponent<Transform>();
        Text statisticTitle = DetailInfo.transform.GetChild(0).GetChild(0).GetComponent<Text>();
        Text startDate = DetailInfo.transform.GetChild(1).GetChild(0).GetComponent<Text>();
        Text endDate = DetailInfo.transform.GetChild(3).GetChild(0).GetComponent<Text>();

        statisticTitle.text = root["submitContent"].Value + " 제출 이력";
        startDate.text = root["startDate"].Value.Split('T')[0];
        endDate.text = root["endDate"].Value.Split('T')[0];

        Transform parent = GameObject.Find("StatisticsDetailContent").GetComponent<Transform>();
        SHDClone = Resources.Load("StatisticsDetailItem") as GameObject;

        JSONNode status = root["statisticListDetailFindResponseDtos"];

        for (int i = 0; i < root["statisticListDetailFindResponseDtos"].Count; i++)
        {
          GameObject clone = Instantiate(SHDClone);

          Text studentName = clone.transform.GetChild(0).GetComponent<Text>();
          Text submitStatus = clone.transform.GetChild(1).GetComponent<Text>();

          studentName.text = status[i]["studentNickname"].Value;

          if (status[i]["submission"].Value.Equals("true"))
          {
            submitStatus.text = "<color=#0000ff>제출</color>";
          }
          else
          {
            submitStatus.text = "<color=#ff0000>미제출</color>";
          }
          clone.transform.SetParent(parent);

          RectTransform cloneRect = clone.GetComponent<RectTransform>();
          cloneRect.localScale = new Vector3(1.0f, 1.0f, 1.0f);

          SHDList.Add(clone);
        }
      }
    }
  }

  // 통계 자료 추가하기
  private IEnumerator CreateStatistics()
  {
    AddForm = GameObject.Find("StatisticsForm").GetComponent<Transform>();

    InputField statisticsContent = AddForm.transform.GetChild(0).GetChild(1).GetComponent<InputField>();

    InputField startYear = AddForm.transform.GetChild(1).GetChild(0).GetComponent<InputField>();
    InputField startMonth = AddForm.transform.GetChild(1).GetChild(1).GetComponent<InputField>();
    InputField startDay = AddForm.transform.GetChild(1).GetChild(2).GetComponent<InputField>();

    InputField endYear = AddForm.transform.GetChild(1).GetChild(3).GetComponent<InputField>();
    InputField endMonth = AddForm.transform.GetChild(1).GetChild(4).GetComponent<InputField>();
    InputField endDay = AddForm.transform.GetChild(1).GetChild(5).GetComponent<InputField>();

    StatisticsData data = new StatisticsData();
    data.groupId = gId;
    data.sumbmitContent = statisticsContent.text;
    data.startDate = startYear.text + "-" + startMonth.text + "-" + startDay.text;
    data.endDate = endYear.text + "-" + endMonth.text + "-" + endDay.text;

    string json = JsonUtility.ToJson(data);

    using (UnityWebRequest request = UnityWebRequest.Post(baseURL + "statistic", json))
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
          statisticsContent.text = "";
          startYear.text = "";
          startMonth.text = "";
          startDay.text = "";
          endYear.text = "";
          endMonth.text = "";
          endDay.text = "";

          ShowSuccessCreateItemAlert();
        }
        else
        {
          ShowFailCreateItemAlert();
        }
      }
    }
  }

  // 제출 내역 수정하기
  private IEnumerator GetStatisticHistoryJob()
  {
    using (UnityWebRequest request = UnityWebRequest.Get(baseURL + "statistic/list/" + gId))
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
          Transform parent = GameObject.Find("StatisticsContent").GetComponent<Transform>();
          noneItem = NoneContentMsgController.Show("아직 제출 내역이 없습니다!").gameObject;

          noneItem.transform.SetParent(parent);

          RectTransform noneItemRect = noneItem.GetComponent<RectTransform>();
          noneItemRect.localScale = new Vector3(1.0f, 1.0f, 1.0f);
        }
        else
        {
          Transform parent = GameObject.Find("StatisticsContent").GetComponent<Transform>();
          SIMClone = Resources.Load("StatisticsItem") as GameObject;

          for (int i = 0; i < root.Count; i++)
          {
            GameObject clone = Instantiate(SIMClone);

            Text statisticsId = clone.transform.GetChild(0).GetComponent<Text>();
            Text statisticsContent = clone.transform.GetChild(1).GetComponent<Text>();
            Text startDate = clone.transform.GetChild(2).GetComponent<Text>();
            Text endDate = clone.transform.GetChild(3).GetComponent<Text>();
            Button sDetailButton = clone.GetComponent<Button>();

            statisticsId.text = root[i]["id"].Value;
            statisticsContent.text = root[i]["submitContent"].Value;
            startDate.text = root[i]["startDate"].Value.Split('T')[0];
            endDate.text = root[i]["endDate"].Value.Split('T')[0];

            sDetailButton.onClick.AddListener(delegate ()
            {
              OnPressGoToModifyStatisticsStatus(statisticsId.text);
            });

            clone.transform.SetParent(parent);

            RectTransform cloneRect = clone.GetComponent<RectTransform>();
            cloneRect.localScale = new Vector3(1.0f, 1.0f, 1.0f);

            SIMList.Add(clone);
          }
        }
      }
    }
  }

  //상세 통계 정보 보기 - 통계청장 전용
  private IEnumerator ModifyStatisticsStatus(string statisId)
  {
    using (UnityWebRequest request = UnityWebRequest.Get(baseURL + "statistic/detail/" + statisId))
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

        Transform DetailInfo = GameObject.Find("Button2DetailContent").GetComponent<Transform>();
        Text statisticTitle = DetailInfo.transform.GetChild(0).GetChild(0).GetComponent<Text>();
        Text startDate = DetailInfo.transform.GetChild(1).GetChild(0).GetComponent<Text>();
        Text endDate = DetailInfo.transform.GetChild(3).GetChild(0).GetComponent<Text>();

        statisticTitle.text = root["submitContent"].Value + " 제출 이력";
        startDate.text = root["startDate"].Value.Split('T')[0];
        endDate.text = root["endDate"].Value.Split('T')[0];

        Transform parent = GameObject.Find("StatisticsDetailContent").GetComponent<Transform>();
        SIMDClone = Resources.Load("StudentStatisticStatusModifyItem") as GameObject;

        JSONNode status = root["statisticListDetailFindResponseDtos"];

        for (int i = 0; i < root["statisticListDetailFindResponseDtos"].Count; i++)
        {
          GameObject clone = Instantiate(SIMDClone);

          Text studentId = clone.transform.GetChild(0).GetComponent<Text>();
          Text studentName = clone.transform.GetChild(1).GetComponent<Text>();
          Button submitStatus = clone.transform.GetChild(2).GetComponent<Button>();
          Text buttonStatus = submitStatus.transform.GetChild(0).GetComponent<Text>();

          studentId.text = status[i]["studentId"].Value;
          studentName.text = status[i]["studentNickname"].Value;

          if (status[i]["submission"].Value.Equals("True"))
          {
            buttonStatus.text = "<color=#0000ff>O</color>";
          }
          else
          {
            buttonStatus.text = "<color=#ff0000>X</color>";
          }

          submitStatus.onClick.AddListener(delegate ()
          {
            StartCoroutine(ModifyStudentStatus(statisId, studentId.text));
            if (buttonStatus.text.Equals("<color=#0000ff>O</color>"))
            {
              buttonStatus.text = "<color=#ff0000>X</color>";
            }
            else
            {
              buttonStatus.text = "<color=#0000ff>O</color>";
            }
          });

          clone.transform.SetParent(parent);

          RectTransform cloneRect = clone.GetComponent<RectTransform>();
          cloneRect.localScale = new Vector3(1.0f, 1.0f, 1.0f);

          SIMDList.Add(clone);
        }
      }
    }
  }

  //제출 상태 변경하기
  private IEnumerator ModifyStudentStatus(string statisId, string studentId)
  {
    ModifyStatisticsStatusData data = new ModifyStatisticsStatusData();
    data.statisticListId = int.Parse(statisId);
    data.studentId = int.Parse(studentId);

    string json = JsonUtility.ToJson(data);

    using (UnityWebRequest request = UnityWebRequest.Put(baseURL + "statistic/detail", json))
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

        if (result.Equals("false"))
        {
          Debug.Log("제출 상태 변경 에러!");
        }
      }
    }
  }

  //신관위에 자료 전송하기
  private IEnumerator ShowSendToCreditGradeList()
  {
    using (UnityWebRequest request = UnityWebRequest.Get(baseURL + "statistic/list/" + gId))
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
          Transform parent = GameObject.Find("StatisticListContent").GetComponent<Transform>();
          noneItem = NoneContentMsgController.Show("전송할 제출물 목록이 없습니다.").gameObject;

          noneItem.transform.SetParent(parent);

          RectTransform noneItemRect = noneItem.GetComponent<RectTransform>();
          noneItemRect.localScale = new Vector3(1.0f, 1.0f, 1.0f);
        }
        else
        {
          Transform parent = GameObject.Find("StatisticListContent").GetComponent<Transform>();
          SSClone = Resources.Load("StatisticSubmitToCreditItem") as GameObject;

          for (int i = 0; i < root.Count; i++)
          {
            GameObject clone = Instantiate(SSClone);

            Text ssListIdx = clone.transform.GetChild(0).GetComponent<Text>();
            Text statisticsId = clone.transform.GetChild(1).GetComponent<Text>();
            Text statisticsContent = clone.transform.GetChild(2).GetComponent<Text>();
            Text startDate = clone.transform.GetChild(3).GetComponent<Text>();
            Text endDate = clone.transform.GetChild(4).GetComponent<Text>();
            Button sendButton = clone.transform.GetChild(5).GetComponent<Button>();

            statisticsId.text = root[i]["id"].Value;
            statisticsContent.text = root[i]["submitContent"].Value;
            startDate.text = root[i]["startDate"].Value.Split('T')[0];
            endDate.text = root[i]["endDate"].Value.Split('T')[0];
            ssListIdx.text = i.ToString();

            sendButton.onClick.AddListener(delegate ()
            {
              StartCoroutine(SendStatisticsButton(statisticsId.text, int.Parse(ssListIdx.text)));
            });

            clone.transform.SetParent(parent);

            RectTransform cloneRect = clone.GetComponent<RectTransform>();
            cloneRect.localScale = new Vector3(1.0f, 1.0f, 1.0f);

            SSList.Add(clone);
          }
        }
      }
    }
  }

  private IEnumerator SendStatisticsButton(string statisId, int idx)
  {
    SendStatisticsData data = new SendStatisticsData();
    data.statisticListId = int.Parse(statisId);

    string json = JsonUtility.ToJson(data);

    using (UnityWebRequest request = UnityWebRequest.Put(baseURL + "statistic/submit", json))
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
          Debug.Log(SSList[idx].transform.GetChild(0).GetComponent<Text>().text);
          Destroy(SSList[idx]);
          SSList.Remove(SSList[idx]);
          ShowSuccessSendListAlert();
        }
        else
        {
          ShowFailSendListAlert();
        }
      }
    }
  }

  //버튼 클릭하면 창 전환하는 메서드 - OnPress

  //일반 업무 탭으로 이동하는 버튼
  public void OnPressGenericButton()
  {
    if (noneItem != null) Destroy(noneItem);
    if (SHList != null)
    {
      foreach (GameObject item in SHList)
      {
        Destroy(item);
      }
    }
    if (SHDList != null)
    {
      foreach (GameObject item in SHDList)
      {
        Destroy(item);
      }
    }
    if (SIMList != null)
    {
      foreach (GameObject item in SIMList)
      {
        Destroy(item);
      }
    }
    if (SIMDList != null)
    {
      foreach (GameObject item in SIMDList)
      {
        Destroy(item);
      }
    }
    if (SSList != null)
    {
      foreach (GameObject item in SSList)
      {
        Destroy(item);
      }
    }

    ObjectActive("GenericMember", -1);
  }

  //통계청장 업무 탭으로 이동하는 버튼
  public void OnPressStatisticsJobButton()
  {
    if (noneItem != null) Destroy(noneItem);
    if (SHList != null)
    {
      foreach (GameObject item in SHList)
      {
        Destroy(item);
      }
    }
    if (SHDList != null)
    {
      foreach (GameObject item in SHDList)
      {
        Destroy(item);
      }
    }
    if (SIMList != null)
    {
      foreach (GameObject item in SIMList)
      {
        Destroy(item);
      }
    }
    if (SIMDList != null)
    {
      foreach (GameObject item in SIMDList)
      {
        Destroy(item);
      }
    }
    if (SSList != null)
    {
      foreach (GameObject item in SSList)
      {
        Destroy(item);
      }
    }

    ObjectActive("JobMember", -1);
  }

  //1. 일반 업무 - 통계 내역 확인하기 버튼
  public void OnPressStatisticsHistoryButton()
  {
    SHList = new List<GameObject>();
    ObjectActive("GenericContentForms", 0);
    StartCoroutine(GetStatisticsHistory());
  }

  //2. 상세 통계 내역 확인하기 버튼
  public void OnPressGoToStatisticsDetail(string statisId)
  {
    SHDList = new List<GameObject>();
    ObjectActive("GenericContentForms", 1);
    StartCoroutine(GetStatisticsDetail(statisId));
  }

  //3. 상세 통계 내역 뒤로 가기 버튼
  public void OnPressStatisticDetailBackButton()
  {
    if (SHDClone != null) Destroy(SHDClone);
    if (SHDList != null)
    {
      foreach (GameObject item in SHDList)
      {
        Destroy(item);
      }
    }
    ObjectActive("GenericContentForm", 0);
    OnPressStatisticsHistoryButton();
  }

  //4. 통계 목록 추가하기 버튼
  public void OnPressAddStatisticsButton()
  {
    ObjectActive("JobContentForms", 0);
  }

  //4-1. 통계 목록 추가 => 추가 버튼
  public void OnPressCreateStatisticsButton()
  {
    StartCoroutine(CreateStatistics());
  }

  //4-2. 통계 목록 추가 => 취소 버튼
  public void OnPressCancelCreateStatisticsButton()
  {
    AddForm.transform.GetChild(0).GetChild(1).GetComponent<InputField>().text = "";
    for (int j = 0; j < 6; j++)
    {
      AddForm.transform.GetChild(1).GetChild(j).GetComponent<InputField>().text = "";
    }

    ObjectActive("JobMember", -1);
  }

  //5. 그룹원 제출 상태 변경하기 버튼 - 목록 버튼
  public void OnPressModifyStatisticsStatusButton()
  {
    SIMList = new List<GameObject>();
    ObjectActive("JobContentForms", 1);
    StartCoroutine(GetStatisticHistoryJob());
  }

  //6. 그룹원 제출 상태 변경하기 - 학생 상태 변경 버튼
  public void OnPressGoToModifyStatisticsStatus(string statisId)
  {
    SIMDList = new List<GameObject>();
    ObjectActive("JobContentForms", 2);
    StartCoroutine(ModifyStatisticsStatus(statisId));
  }

  //7. 신관위에 자료 보내기 버튼
  public void OnPressSendToCreditGradePartButton()
  {
    SSList = new List<GameObject>();
    ObjectActive("JobContentForms", 3);
    StartCoroutine(ShowSendToCreditGradeList());
  }

  //Alert View 불러오는 메서드
  private void ShowSuccessCreateItemAlert()
  {
    string title = "";
    string message = "목록이 성공적으로 생성되었습니다.";

    AlertViewController.Show(title, message, new AlertViewOptions
    {
      okButtonTitle = "확인",
      okButtonDelegate = () =>
      {
        ObjectActive("JobMember", -1);
      }
    });
  }

  private void ShowFailCreateItemAlert()
  {
    string title = "";
    string message = "목록 생성 도중 오류가 발생했습니다ㅜ.ㅜ";

    AlertViewController.Show(title, message);
  }

  private void ShowSuccessSendListAlert()
  {
    string title = "";
    string message = "성공적으로 전송했습니다!";

    AlertViewController.Show(title, message);
  }

  private void ShowFailSendListAlert()
  {
    string title = "";
    string message = "전송 도중 오류가 발생했어요ㅠ.ㅠ";

    AlertViewController.Show(title, message);
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
