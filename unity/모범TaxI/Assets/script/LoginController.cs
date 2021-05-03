using System.Collections;
using System.Collections.Generic;
using System.Net;
using System.IO;
using System.Collections;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.Networking;

[System.Serializable]
public class UserData
{
    /*public string nickname;*/
    public string email;
    public string password;

    public void print()
    {
        Debug.Log("email : " + email);
        Debug.Log("password : " + password);
    }
}

public class LoginController : MonoBehaviour
{
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
        Debug.Log("닉네임 : "+ userId);
        Debug.Log("비밀번호 : "+ userPw);

        if (userId.Length != 0 && userPw.Length != 0)
        {
            Debug.Log("둘 다 입력받음!");
            /*StartCoroutine(GetRequest());*/
            StartCoroutine(Login());
        }
        else if(userId.Length == 0 || userPw.Length == 0)
        {
            Debug.Log("닉네임 또는 비밀번호를 입력하세요.");
        }

    }

    public IEnumerator GetRequest()
    {
        UnityWebRequest request = UnityWebRequest.Get(baseURL+"group/group-list/1");
        yield return request.SendWebRequest();
        if (request.error != null)
        {
            Debug.Log(request.error);
        } 
        
        else
        {
            // Response can be accessed through: request.downloadHandler.text
            Debug.Log("응 실행됨");
            Debug.Log(request.downloadHandler.text);
        }
    }

    private IEnumerator Login()
    {
        
        UserData data = new UserData();
        data.email = userId;
        data.password = userPw;

        string json = JsonUtility.ToJson(data);
        /*byte[] bytes = System.Text.Encoding.UTF8.GetBytes(json);*/
        Debug.Log("{0}" + json);

        /*UserData test = JsonUtility.FromJson<UserData>(str); //json -> Object 형변환*/


        using (UnityWebRequest request = UnityWebRequest.Post(baseURL + "teacher/login", json))
        {
            byte[] jsonToSend = new System.Text.UTF8Encoding().GetBytes(json);
            request.uploadHandler = new UploadHandlerRaw(jsonToSend);
            request.downloadHandler = (DownloadHandler)new DownloadHandlerBuffer();
            
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
            }
        }

            


    }
}
