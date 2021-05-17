using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.Networking;
using SimpleJSON;

[System.Serializable]
public class ResumeData
{
  public int jobId;
  public string title;
  public string content;
  public int studentId;
}

[System.Serializable]
public class AddJobData
{
  public string content;
  public string jobName;
  public int studentId;
}


public class JobMenuController : MonoBehaviour
{
  private static string baseURL = "https://k4b203.p.ssafy.io/api/";
  public RectTransform uiGroup;
  PlayerController enterPlayer;

  [SerializeField]
  private GameObject GenericJobContent;

  [SerializeField]
  private GameObject PartTimeContent;

  [SerializeField]
  private GameObject JobResumeContent;
  public static GameObject resumeContent;

  [SerializeField]
  private Button AddJobButton; //직업 추가 요청 버튼

  [SerializeField]
  private GameObject AddJobRequestContent; //직업 추가 요청 폼

  //해당 영역 나가면 한 번에 지울 수 있게 오브젝트 담을 리스트
  private static List<GameObject> objectList = new List<GameObject>();

  private bool _isExit = true;
  private static int gId;
  private int sId;
  private static List<string> jobNameList;
  private static List<int> jobIdList;

  //생성해야하는 prefab

  //일반 직업 조회
  private static GameObject noneJobList;
  private static GameObject JIClone;
  private static List<GameObject> JobListClone;

  //일반 직업 상세 조회
  private static GameObject JobDetail;
  private static GameObject JDIClone;

  //아르바이트 조회
  private GameObject nonePTList;
  private GameObject PTIClone;
  private List<GameObject> PTListClone;


  void OnAfterDeserialize()
  {
    resumeContent = JobResumeContent;
  }

  void Start()
  {
    OnAfterDeserialize();

    objectList.Add(GenericJobContent);
    objectList.Add(PartTimeContent);
    objectList.Add(JobResumeContent);
    objectList.Add(AddJobRequestContent);

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

    JobListClone = new List<GameObject>();
    uiGroup.anchoredPosition = Vector3.zero;
    _isExit = false;
  }

  public void Exit()
  {
    uiGroup.anchoredPosition = Vector3.down * -1000;
    ObjectActive("GenericJobListForm", -1);

    if (noneJobList != null) Destroy(noneJobList);
    if (JobListClone != null)
    {
      foreach (GameObject item in JobListClone)
      {
        Destroy(item);
      }
    }
    if (JDIClone != null) Destroy(JDIClone);
    if (nonePTList != null) Destroy(noneJobList);
    if (PTListClone != null)
    {
      foreach (GameObject item in PTListClone)
      {
        Destroy(item);
      }
    }

    _isExit = true;
  }

  /* 인력사무소 메뉴에 보여줄 데이터 받아오는 메서드들 */

  //1. 현재 구인중인 직업 목록 조회
  public static IEnumerator GetGenericJobList()
  {
    using (UnityWebRequest request = UnityWebRequest.Get(baseURL + "job/" + gId))
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

        if (root.Count <= 0 || (root.Count == 1 && root[0]["name"].Value.Equals("무직")))
        {
          Transform parent = GameObject.Find("GenericJobListForm").GetComponent<Transform>();
          noneJobList = NoneContentMsgController.Show("아직 생성된 직업이 없습니다!").gameObject;

          RectTransform noneJListRect = noneJobList.GetComponent<RectTransform>();
          noneJobList.transform.SetParent(parent);
          noneJListRect.offsetMin = new Vector2(0, 0);
          noneJListRect.offsetMax = new Vector2(0, 0);
        }
        else
        {
          Transform parent = GameObject.Find("JobListContent").GetComponent<Transform>();
          JIClone = Resources.Load("JobInfoItem") as GameObject;
          jobNameList = new List<string>();
          jobIdList = new List<int>();
          for (int i = 0; i < root.Count; i++)
          {
            if (!root[i]["name"].Value.Equals("무직") && !root[i]["name"].Value.Equals("국무총리") && !root[i]["name"].Value.Equals("부총리"))
            {
              jobNameList.Add(root[i]["name"].Value);
              jobIdList.Add(root[i]["id"]);
              GameObject clone = Instantiate(JIClone);
              Text jobId = clone.transform.GetChild(0).GetComponent<Text>();
              Text jobName = clone.transform.GetChild(1).GetComponent<Text>();
              Text salary = clone.transform.GetChild(2).GetComponent<Text>();
              Text personnel = clone.transform.GetChild(3).GetComponent<Text>();
              Button detailBtn = clone.transform.GetChild(4).GetComponent<Button>();

              jobId.text = root[i]["id"].Value;
              jobName.text = root[i]["name"].Value;
              salary.text = root[i]["salary"].Value;
              personnel.text = root[i]["personnel"].Value;

              clone.transform.SetParent(parent);

              detailBtn.onClick.AddListener(delegate ()
              {
                //각 직업 별 상세 조회창 보이기
                if (JDIClone != null) Destroy(JDIClone);
                StaticCoroutine.DoCoroutine(OnPressJobInfoDetail(jobId.text));
              });

              JobListClone.Add(clone);
            }
          }
        }
      }
    }
  }

  //2. 직업 별 상세 정보 조회
  private static IEnumerator OnPressJobInfoDetail(string jobId)
  {
    using (UnityWebRequest request = UnityWebRequest.Get(baseURL + "job/detail/" + jobId))
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

        Transform parent = GameObject.Find("GenericJobListForm").GetComponent<Transform>();
        JobDetail = Resources.Load("JobInfoDetailItem") as GameObject;

        JDIClone = Instantiate(JobDetail);
        Text jobDetailInfo = JDIClone.transform.GetChild(1).GetComponent<Text>();
        Button applyButton = JDIClone.transform.GetChild(2).GetComponent<Button>();
        Button cancelButton = JDIClone.transform.GetChild(3).GetComponent<Button>();

        jobDetailInfo.text = "직업 : " + root["name"].Value + System.Environment.NewLine;

        if (root["role"].Value.Equals("null")) jobDetailInfo.text += "하는 일 : 없음" + System.Environment.NewLine;
        else jobDetailInfo.text += "하는 일 : " + root["role"].Value + System.Environment.NewLine;

        if (root["qualification"].Value.Equals("null")) jobDetailInfo.text += "자격 조건 : 없음" + System.Environment.NewLine;
        else jobDetailInfo.text += "자격 조건 : " + root["qualification"].Value + System.Environment.NewLine;

        jobDetailInfo.text += "월급 : " + root["salary"].Value + System.Environment.NewLine +
                              "제한 인원 : " + root["personnel"].Value + System.Environment.NewLine;

        applyButton.onClick.AddListener(delegate ()
        {
          Destroy(JDIClone);
          if (JobListClone != null)
          {
            foreach (GameObject item in JobListClone)
            {
              Destroy(item);
            }
          }

          Dropdown jobList = resumeContent.transform.GetChild(1).GetChild(3).GetComponent<Dropdown>();

          jobList.options.Clear();
          for (int i = 0; i < jobNameList.Count; i++)//1부터 10까지
          {
            Dropdown.OptionData option = new Dropdown.OptionData();
            option.text = jobNameList[i];
            jobList.options.Add(option);
          }

          ObjectActive("GJobResumeForm", -1);
        });

        cancelButton.onClick.AddListener(delegate ()
        {
          Destroy(JDIClone);
        });

        JDIClone.transform.SetParent(parent);
        RectTransform cloneRect = JDIClone.GetComponent<RectTransform>();
        cloneRect.offsetMin = new Vector2(0, 0); //left, bottom
        cloneRect.offsetMax = new Vector2(0, -35);  //right, top
      }
    }
  }

  //3. 이력서 제출하기
  public IEnumerator SubmitResume()
  {
    InputField resumeTitle = JobResumeContent.transform.GetChild(0).GetChild(1).GetComponent<InputField>();
    InputField applicant = JobResumeContent.transform.GetChild(1).GetChild(1).GetComponent<InputField>();
    Dropdown jobName = JobResumeContent.transform.GetChild(1).GetChild(3).GetComponent<Dropdown>();
    InputField resumeContent = JobResumeContent.transform.GetChild(2).GetChild(1).GetComponent<InputField>();

    if (resumeTitle.text == "" || applicant.text == "" || resumeContent.text == "") AlertViewController.Show("", "입력하지 않은 곳이 있습니다!");
    else
    {
      ResumeData data = new ResumeData();
      data.jobId = jobIdList[jobName.value];
      data.title = resumeTitle.text;
      data.studentId = sId;
      data.content = resumeContent.text;

      string json = JsonUtility.ToJson(data);

      using (UnityWebRequest request = UnityWebRequest.Post(baseURL + "ustudent/request-job-change", json))
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
          string title = "";
          string message = "이력서를 등록했습니다!";

          AlertViewController.Show(title, message, new AlertViewOptions
          {
            okButtonTitle = "확인",
            okButtonDelegate = () =>
            {
              resumeTitle.text = "";
              applicant.text = "";
              resumeContent.text = "";

              StartCoroutine(GetGenericJobList());
              ObjectActive("GenericJobListForm", -1);
            }
          });
        }
      }
    }
  }

  //3. 현재 구인중인 아르바이트 목록 조회
  private IEnumerator GetPartTimeList()
  {
    using (UnityWebRequest request = UnityWebRequest.Get(baseURL + "job/part-time-list/" + gId))
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
          Transform parent = GameObject.Find("PartTimelistForm").GetComponent<Transform>();
          nonePTList = NoneContentMsgController.Show("등록된 아르바이트가 없습니다!").gameObject;

          RectTransform nonePTListRect = nonePTList.GetComponent<RectTransform>();
          nonePTList.transform.SetParent(parent);
          nonePTListRect.offsetMin = new Vector2(0, 0);
          nonePTListRect.offsetMax = new Vector2(0, 0);
        }
        else
        {
          Transform parent = GameObject.Find("PartTimeListContent").GetComponent<Transform>();
          PTIClone = Resources.Load("PartTimeListItem") as GameObject;

          for (int i = 0; i < root.Count; i++)
          {
            GameObject clone = Instantiate(PTIClone);
            Text ptId = clone.transform.GetChild(0).GetComponent<Text>();
            Text ptName = clone.transform.GetChild(1).GetComponent<Text>();
            Text ptContent = clone.transform.GetChild(2).GetComponent<Text>();
            Text allowance = clone.transform.GetChild(3).GetComponent<Text>();
            // Button applyBtn = clone.transform.GetChild(4).GetComponent<Button>();

            ptId.text = root[i]["id"].Value;
            ptName.text = root[i]["name"].Value;
            ptContent.text = root[i]["role"].Value;
            allowance.text = root[i]["salary"].Value;

            clone.transform.SetParent(parent);

            // applyBtn.onClick.AddListener(delegate ()
            // {
            //   //알바 신청하기
            //   StartCoroutine(OnPressPTApplyButton(ptId.text));
            // });

            PTListClone.Add(clone);
          }
        }
      }
    }
  }

  //4. 직업 추가 요청 보내기
  private IEnumerator AddJobRequest()
  {
    InputField jobName = AddJobRequestContent.transform.GetChild(0).GetChild(1).GetComponent<InputField>();
    InputField jobContent = AddJobRequestContent.transform.GetChild(1).GetChild(1).GetComponent<InputField>();

    AddJobData data = new AddJobData();
    data.jobName = jobName.text;
    data.content = jobContent.text;
    data.studentId = sId;

    string json = JsonUtility.ToJson(data);

    Debug.Log("job name : " + jobName.text + ", job content : " + jobContent.text + ", student id : " + sId);

    using (UnityWebRequest request = UnityWebRequest.Post(baseURL + "job/add-request", json))
    {
      byte[] jsonToSend = new System.Text.UTF8Encoding().GetBytes(json);
      request.uploadHandler = new UploadHandlerRaw(jsonToSend);

      request.SetRequestHeader("Content-Type", "application/json;charset=utf-8");
      request.SetRequestHeader("Accept", "application/json, text/plain, */*");

      yield return request.downloadHandler.text;

      if (request.error != null)
      {
        Debug.Log(request.error);
      }
      else
      {

        string result = request.downloadHandler.text;
        Debug.Log("result : " + result);

        if (result.Equals("true"))
        {
          ShowSuccessSendRequestAlert();
          jobName.text = "";
          jobContent.text = "";
        }
        else
        {
          ShowFailSendRequestAlert();
        }
      }
    }
  }


  //버튼 클릭하면 창 전환하는 메서드
  public void OnPressGenericButton()
  {
    if (GenericJobContent.activeSelf == false)
    {
      JobListClone = new List<GameObject>();
      if (noneJobList != null) Destroy(noneJobList);
      if (JobListClone != null)
      {
        foreach (GameObject item in JobListClone)
        {
          Destroy(item);
        }
      }
      if (JDIClone != null) Destroy(JDIClone);
      if (nonePTList != null) Destroy(noneJobList);
      if (PTListClone != null)
      {
        foreach (GameObject item in PTListClone)
        {
          Destroy(item);
        }
      }

      StartCoroutine(GetGenericJobList());
      ObjectActive("GenericJobListForm", -1);
    }
  }

  public void OnPressPartTimeButton()
  {
    if (PartTimeContent.activeSelf == false)
    {
      PTListClone = new List<GameObject>();
      if (noneJobList != null) Destroy(noneJobList);
      if (JobListClone != null)
      {
        foreach (GameObject item in JobListClone)
        {
          Destroy(item);
        }
      }
      if (JDIClone != null) Destroy(JDIClone);
      if (nonePTList != null) Destroy(noneJobList);
      if (PTListClone != null)
      {
        foreach (GameObject item in PTListClone)
        {
          Destroy(item);
        }
      }

      StartCoroutine(GetPartTimeList());
      ObjectActive("PartTimelistForm", -1);
    }
  }

  public void OnPressResumeSubmitButton()
  {
    StartCoroutine(SubmitResume());
  }

  public void OnPressAddJobRequestButton()
  {
    ObjectActive("AddJobRequestForm", -1);
  }

  public void OnPressSubmitJobRequestButton()
  {
    StartCoroutine(AddJobRequest());
  }

  public void OnPressCancelJobRequestButton()
  {
    InputField jobName = AddJobRequestContent.transform.GetChild(0).GetChild(1).GetComponent<InputField>();
    InputField jobContent = AddJobRequestContent.transform.GetChild(1).GetChild(1).GetComponent<InputField>();

    jobName.text = "";
    jobContent.text = "";
    OnPressGenericButton();
  }


  //Alert view
  private void ShowSuccessSendRequestAlert()
  {
    string title = "";
    string message = "요청이 성공적으로 전송되었습니다!";

    AlertViewController.Show(title, message, new AlertViewOptions
    {
      okButtonTitle = "확인",
      okButtonDelegate = () =>
      {
        OnPressGenericButton();
      }
    });
  }

  private void ShowFailSendRequestAlert()
  {
    string title = "";
    string message = "요청 전송 도중 오류가 발생했습니다ㅠ.ㅠ";

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
