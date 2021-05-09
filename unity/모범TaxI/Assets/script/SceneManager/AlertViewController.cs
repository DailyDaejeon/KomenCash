using UnityEngine;
using UnityEngine.UI;

// alert 뷰의 표시 옵션을 지정하기 위한 클래스
public class AlertViewOptions
{
    public string cancelButtonTitle; //취소 버튼의 타이틀
    public System.Action cancelButtonDelegate; //취소 버튼을 눌렀을 때 실행되는 대리자
    public string okButtonTitle; //OK 버튼의 타이틀
    public System.Action okButtonDelegate; //OK 버튼을 눌렀을 때 실행되는 대리자
}

public class AlertViewController : MonoBehaviour
{
    public Text titleLabel; //타이틀을 표시
    public Text messageLabel; //메세지를 표시
    public Button cancelButton; //취소버튼
    public Button okButton; //ok버튼
    public Text cancelButtonLabel; //취소 버튼의 타이틀
    public Text okButtonLabel; //ok버튼의 타이틀

    private static GameObject prefab; //alert view prefab
    private System.Action cancelButtonDelegate; //취소 버튼 클릭 시 실행되는 대리자 지정
    private System.Action okButtonDelegate; //ok 버튼 클릭 시 실행되는 대리자 지정

    //알림 뷰를 표시하는 static 메서드
    public static AlertViewController Show(
        string title, string message, AlertViewOptions options = null)
    {
        if(prefab == null)
        {
            //프리팹을 읽어들인다.
            prefab = Resources.Load("AlertView") as GameObject;
        }

        GameObject go = Instantiate(prefab) as GameObject;
        AlertViewController alertView = go.GetComponent<AlertViewController>();
        alertView.UpdateContent(title, message, options);

        return alertView;
    }

    //alert view의 내용을 업데이트하는 메서드
    public void UpdateContent(
        string title, string message, AlertViewOptions options = null)
    {
        //타이틀과 메세지를 결정
        titleLabel.text = title;
        messageLabel.text = message;

        if (options != null)
        {
            //표시 옵션이 있을 때 옵션의 내용에 맞춰 버튼을 표시하거나 표시하지 않는것을 결정
            cancelButton.transform.gameObject.SetActive(
                options.cancelButtonTitle != null || options.okButtonTitle != null);

            cancelButton.gameObject.SetActive(options.cancelButtonTitle != null);
            cancelButtonLabel.text = options.cancelButtonTitle ?? "";
            cancelButtonDelegate = options.cancelButtonDelegate;

            okButton.gameObject.SetActive(options.okButtonTitle != null);
            okButtonLabel.text = options.okButtonTitle ?? "";
            okButtonDelegate = options.okButtonDelegate;
        }
        else
        {
            // 표시 옵션이 지정되어있지 않을 때, 기본 버튼을 표시
            cancelButton.gameObject.SetActive(false);
            okButton.gameObject.SetActive(true);
            okButtonLabel.text = "확인";
        }
    }

    //alert view를 닫는 메서드
    public void Dismiss()
    {
        Destroy(gameObject);
    }

    //취소 버튼을 눌렀을 때, 호출되는 메서드
    public void OnPressCancelButton()
    {
        if(cancelButtonDelegate != null)
        {
            cancelButtonDelegate.Invoke();
        }
        Dismiss();
    }

    //OK 버튼을 눌렀을 때, 호출되는 메서드
    public void OnPressOkButton()
    {
        if(okButtonDelegate != null)
        {
            okButtonDelegate.Invoke();
        }
        Dismiss();
    }
}
