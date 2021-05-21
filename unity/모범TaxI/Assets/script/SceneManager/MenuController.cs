using UnityEngine;
using UnityEngine.UI;

// 메뉴창의 표시 옵션을 지정하기 위한 클래스
public class MenuButtonOptions
{
  //일반 업무, 직업군 업무 구별 버튼(탭)
  public string genericButtonTitle; //일반 업무 버튼의 Text
  public string jobButtonTitle;     //직업군 업무 버튼의 Text
  // public string cancelButtonTitle;  //닫기 버튼의 Text
  public System.Action genericButtonDelegate; //일반 업무 버튼 클릭 시 실행되는 대리자
  public System.Action jobButtonDelegate;     //직업군 업무 버튼 클릭 시 실행되는 대리자

  //메뉴 종류
  /*    public string button1Title; //첫 번째 버튼의 Text
      public string button2Title; //두 번째 버튼의 Text
      public string button3Title;  //세 번째 버튼의 Text
      public System.Action button1Delegate; //첫 번째 버튼 클릭 시 실행되는 대리자
      public System.Action button2Delegate; //두 번째 버튼 클릭 시 실행되는 대리자
      public System.Action button3Delegate; //세 번째 버튼 클릭 시 실행되는 대리자*/

  //메뉴에 맞는 정보를 출력할 창
}

public class MenuController : MonoBehaviour
{
  public Button genericButton; //일반 업무 버튼
  public Button jobButton;     //직업 업무 버튼
  public Button cancelButton;

  /*
  public Button button1;       //첫 번째 버튼
  public Button button2;       //두 번째 버튼
  public Button button3;       //세 번째 버튼
  */

  public Text genericButtonLabel; //일반 업무 버튼의 Text
  public Text jobButtonLabel;     //직업 업무 버튼의 Text
  public Text cancelButtonLabel;     //닫기 버튼의 Text
  /*    public Text button1ButtonLabel; //첫 번째 업무 버튼의 Text
      public Text button2ButtonLabel; //두 번째 업무 버튼의 Text
      public Text button3ButtonLabel; //세 번째 버튼의 Text
  */

  private static GameObject prefab; //메뉴창 프리팹
  private System.Action genericButtonDelegate; //일반 업무 클릭 시 실행되는 대리자 지정
  private System.Action jobButtonDelegate;     //직업 업무 클릭 시 실행되는 대리자 지정

  /*
  private System.Action button1Delegate; //첫 번째 메뉴 클릭 시 실행되는 대리자 지정
  private System.Action button2Delegate; //두 번째 메뉴 클릭 시 실행되는 대리자 지정
  private System.Action button3Delegate; //세 번째 메뉴 클릭 시 실행되는 대리자 지정
  */

  static GameObject go;

  public static MenuController Show(MenuButtonOptions options = null)
  {
    if (prefab == null)
    {
      Debug.Log("프리팹 만들기");
      prefab = Resources.Load("Menu") as GameObject;
    }
    Transform parent = GameObject.Find("Canvas").GetComponent<Transform>();
    go = Instantiate(prefab) as GameObject;

    go.transform.SetParent(parent);
    go.transform.localPosition = Vector3.zero;

    MenuController menu = go.GetComponent<MenuController>();
    menu.UpdateMenu(options);

    return menu;
  }

  //메뉴창의 메뉴 내용을 업데이트하는 메서드
  public void UpdateMenu(MenuButtonOptions options = null)
  {
    //옵션이 존재하면 옵션의 내용에 맞춰 버튼을 표시 또는 표시X를 결정
    if (options != null)
    {
      genericButton.transform.gameObject.SetActive(
          options.genericButtonTitle != null || options.jobButtonTitle != null);

      genericButton.gameObject.SetActive(options.genericButtonTitle != null);
      genericButtonLabel.text = options.genericButtonTitle ?? "";
      genericButtonDelegate = options.genericButtonDelegate;

      jobButton.gameObject.SetActive(options.jobButtonTitle != null);
      jobButtonLabel.text = options.jobButtonTitle ?? "";
      jobButtonDelegate = options.jobButtonDelegate;

      // cancelButton.gameObject.SetActive(true);
      // cancelButtonLabel.text = "X";
    }
    else
    {
      Debug.Log("옵션을 지정해주세요.");
    }
  }

  //메뉴창을 닫는 메서드
  public void Close()
  {
    Destroy(go);
  }

  //일반 업무를 클릭했을 때, 호출되는 메서드
  public void OnPressGenericButton()
  {
    if (genericButtonDelegate != null)
    {
      genericButtonDelegate.Invoke();
    }
  }

  //직업 업무를 클릭했을 때, 호출되는 메서드
  public void OnPressJobButton()
  {
    if (jobButtonDelegate != null)
    {
      jobButtonDelegate.Invoke();
    }
  }

  //X 버튼을 클릭했을 때, 호출되는 메서드
  public void OnPressCancelButton()
  {
    Close();
  }
}
