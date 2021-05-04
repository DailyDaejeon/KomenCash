using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.Networking;
using SimpleJSON;

[System.Serializable]
public class UserData
{
    public string nickname;
    public string password;
    public string code;
}

public class JoinController : MonoBehaviour
{
    public string baseURL = "http://k4b203.p.ssafy.io:8081/api/";
    public string userNickname;
    public string userPassword;
    public string userGroupcode;
    private SceneChangeController loginForm;

    public void setNickname(InputField inputNickname)
    {
        userNickname = inputNickname.text;
    }

    public void setPassword(InputField inputPassword)
    {
        userPassword = inputPassword.text;
    }

    public void setGroupCode(InputField inputGroupcode)
    {
        userGroupcode = inputGroupcode.text;
    }

    //ȸ������
    public void OnClickButton()
    {
        if (userNickname.Length == 0 || userPassword.Length == 0 || userGroupcode.Length == 0)
        {
            Debug.Log("��� �׸��� �ʼ� �Է°��Դϴ�.");
            return;
        } else if(userNickname.Length != 0 && userPassword.Length != 0 && userGroupcode.Length != 0)
        {
            StartCoroutine(Join());
        }
    }

    private IEnumerator Join()
    {
        UserData data = new UserData();
        data.nickname = userNickname;
        data.password = userPassword;
        data.code = userGroupcode;

        string json = JsonUtility.ToJson(data);
        Debug.Log("json : " + json);

        using (UnityWebRequest request = UnityWebRequest.Post(baseURL + "unity/student", json))
        {
            byte[] jsonToSend = new System.Text.UTF8Encoding().GetBytes(json);
            request.uploadHandler = new UploadHandlerRaw(jsonToSend);
            /*request.downloadHandler = (DownloadHandler)new DownloadHandlerBuffer();*/

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

                bool isTrue = root["error"];

                if (isTrue)
                {
                    Debug.Log(root["error"]);
                }
                else
                {
                    Debug.Log("���������� ���ԵǾ����ϴ�.");
                
                    //�α��� �������� �̵�
                    loginForm = GameObject.Find("JoinRestAPIRequester").GetComponent<SceneChangeController>();
                    loginForm.GoToLoginForm();
                }
            }
        }
    }
}
