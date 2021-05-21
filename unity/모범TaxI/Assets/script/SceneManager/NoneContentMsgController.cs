using UnityEngine;
using UnityEngine.UI;

public class NoneContentMsgController : MonoBehaviour
{
  public Text msgLabel; //메세지를 표시

  private static GameObject prefab; //alert view prefab

  public static NoneContentMsgController Show(string message)
  {
    if (prefab == null)
    {
      //프리팹을 읽어들인다.
      prefab = Resources.Load("NoneMessage") as GameObject;
    }

    Debug.Log("msg : " + message);

    GameObject go = Instantiate(prefab) as GameObject;
    NoneContentMsgController noneContent = go.GetComponent<NoneContentMsgController>();
    noneContent.UpdateContent(message);

    return noneContent;
  }

  //alert view의 내용을 업데이트하는 메서드
  public void UpdateContent(string message)
  {
    //타이틀과 메세지를 결정
    msgLabel.text = message;
  }
}
