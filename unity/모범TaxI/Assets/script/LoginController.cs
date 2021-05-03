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
        Debug.Log("��ư Ŭ������");
        Debug.Log("�г��� : "+ nickname);
        Debug.Log("��й�ȣ : "+ password);

        if (nickname.Length != 0 && password.Length != 0)
        {
            Debug.Log("�� �� �Է¹���!");
            /*StartCoroutine(GetRequest());*/
            StartCoroutine(GetUserData());
        }
        else if(nickname.Length == 0 || password.Length == 0)
        {
            Debug.Log("�г��� �Ǵ� ��й�ȣ�� �Է��ϼ���.");
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
            Debug.Log("�� �����");
            Debug.Log(request.downloadHandler.text);
        }
    }

    private string GetUserData()
    {
        UserData data = new UserData();
        data.userId = nickname;
        data.userPw = password;

        //JsonUtility ��� -> string, byte[]�� ��ȯ
        string str = JsonUtility.ToJson(data);
        var bytes = System.Text.Encoding.UTF8.GetBytes(str);

        //��û ���� �ּҿ� ����
        HttpWebRequest request = (HttpWebRequest)WebRequest.Create(baseURL + "group/group-list/1");
        request.Method = "GET";
        request.ContentType = "application/json";
        request.ContentLength = bytes.Length;

        //Stream �������� �����͸� ����
        using(var stream = request.GetRequestStream())
        {
            stream.Write(bytes, 0, bytes.Length);
            stream.Flush();
            stream.Close();
        }

        //���� �����͸� StreamReader�� ����
        HttpWebResponse response = (HttpWebResponse)request.GetResponse();
        StreamReader reader = new StreamReader(response.GetResponseStream());
        string json = reader.ReadToEnd();

        //string���� JsonUtility�� Ŀ���� Ŭ����
        string info = JsonUtility.FromJson<string>(json);
        Debug.Log(info);

        yield return info;
    }
}
