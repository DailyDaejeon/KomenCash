using System.Collections;
using System.Collections.Generic;
using System.Net;
using System.IO;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.Networking;

[System.Serializable]
public class UserData
{
    public string userId;
    public string userPw;
}

public class LoginController : MonoBehaviour
{
    public string baseURL = "http://k4b203.p.ssafy.io:8081/api/";
    public string nickname;
    public string password;

    public void getNickName(InputField inputNickName)
    {
        nickname = inputNickName.text;
    }

    public void getPassWord(InputField inputPassWord)
    {
        password = inputPassWord.text;
    }

    public void OnClickButton()
    {
        Debug.Log("버튼 클릭했음");
        Debug.Log("닉네임 : "+ nickname);
        Debug.Log("비밀번호 : "+ password);

        if (nickname.Length != 0 && password.Length != 0)
        {
            Debug.Log("둘 다 입력받음!");
            /*StartCoroutine(GetRequest());*/
            StartCoroutine(GetUserData());
        }
        else if(nickname.Length == 0 || password.Length == 0)
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

    private string GetUserData()
    {
        UserData data = new UserData();
        data.userId = nickname;
        data.userPw = password;

        //JsonUtility 사용 -> string, byte[]로 변환
        string str = JsonUtility.ToJson(data);
        var bytes = System.Text.Encoding.UTF8.GetBytes(str);

        //요청 보낼 주소와 세팅
        HttpWebRequest request = (HttpWebRequest)WebRequest.Create(baseURL + "group/group-list/1");
        request.Method = "GET";
        request.ContentType = "application/json";
        request.ContentLength = bytes.Length;

        //Stream 형식으로 데이터를 보냄
        using(var stream = request.GetRequestStream())
        {
            stream.Write(bytes, 0, bytes.Length);
            stream.Flush();
            stream.Close();
        }

        //응답 데이터를 StreamReader로 받음
        HttpWebResponse response = (HttpWebResponse)request.GetResponse();
        StreamReader reader = new StreamReader(response.GetResponseStream());
        string json = reader.ReadToEnd();

        //string값을 JsonUtility로 커스텀 클래스
        string info = JsonUtility.FromJson<string>(json);
        Debug.Log(info);

        yield return info;
    }
}
