using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.Networking;
using SimpleJSON;


public class LoginController : MonoBehaviour
{
  StudentDTO dataController;
  private SceneChangeController mainScene;

  public string baseURL = "http://k4b203.p.ssafy.io:8081/api/";
  public string userId;
  public string userPw;

  public void getNickName(InputField inputNickName)
  {
    userId = inputNickName.text;
  }

  public void getPassWord(InputField inputPassWord)
  {
    userPw = inputPassWord.text;
  }

  public void OnClickButton()
  {
    Debug.Log("버튼 클릭했음");
    Debug.Log("닉네임 : " + userId);
    Debug.Log("비밀번호 : " + userPw);

    if (userId.Length != 0 && userPw.Length != 0)
    {
      Debug.Log("둘 다 입력받음!");
      StartCoroutine(Login());
    }
    else if (userId.Length == 0 || userPw.Length == 0)
    {
      Debug.Log("닉네임 또는 비밀번호를 입력하세요.");
    }

  }

  private IEnumerator Login()
  {
    UserData data = new UserData();
    data.nickname = userId;
    data.password = userPw;

    string json = JsonUtility.ToJson(data);
    Debug.Log("json : " + json);

    /*UserData test = JsonUtility.FromJson<UserData>(str); //json -> Object 형변환*/

    using (UnityWebRequest request = UnityWebRequest.Post(baseURL + "ustudent/login", json))
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

        bool isTrue = root["fail"];

        if (isTrue)
        {
          Debug.Log(root["fail"].Value);
        }
        else
        {
          //DataController에 학생id, 직업, 그룹, 담당교사 정보 저장
          DataController.setStudentInfo(root["success"]);

          //DataController에 학생 잔고, 월급, 자격 정보 저장
          string studentId = root["success"]["id"].Value;
          //Debug.Log("로그인 한 학생 id : " + studentId);
          //Debug.Log("type : " + studentId.GetType());
          StartCoroutine(fetchStatInfo(studentId));

          //메인 페이지로 이동
          mainScene = GameObject.Find("MoneyJamRestAPIRequester").GetComponent<SceneChangeController>();
          mainScene.GoToMainScene();
        }
      }
    }
  }

  private IEnumerator fetchStatInfo(string sId)
  {
    using (UnityWebRequest request = UnityWebRequest.Get(baseURL + "ustudent/" + sId))
    {
      request.SetRequestHeader("Content-Type", "application/json;charset=utf-8");
      request.SetRequestHeader("Accept", "application/json, text/plain, */*");

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

        DataController.setStudentStatInfo(root);
      }
    }
  }

  public void ShowFindIdPwAlert()
  {
    string title = "닉네임 또는 비밀번호를 잊어버렸나요?";
    string message = "닉네임 또는 비밀번호를 잊어버렸을 때는" + System.Environment.NewLine + "선생님께 문의하세요!";

    AlertViewController.Show(title, message);
  }
}
