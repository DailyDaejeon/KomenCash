using UnityEngine;
using UnityEngine.UI;

// alert ���� ǥ�� �ɼ��� �����ϱ� ���� Ŭ����
public class AlertViewOptions
{
    public string cancelButtonTitle; //��� ��ư�� Ÿ��Ʋ
    public System.Action cancelButtonDelegate; //��� ��ư�� ������ �� ����Ǵ� �븮��
    public string okButtonTitle; //OK ��ư�� Ÿ��Ʋ
    public System.Action okButtonDelegate; //OK ��ư�� ������ �� ����Ǵ� �븮��
}

public class AlertViewController : MonoBehaviour
{
    public Text titleLabel; //Ÿ��Ʋ�� ǥ��
    public Text messageLabel; //�޼����� ǥ��
    public Button cancelButton; //��ҹ�ư
    public Button okButton; //ok��ư
    public Text cancelButtonLabel; //��� ��ư�� Ÿ��Ʋ
    public Text okButtonLabel; //ok��ư�� Ÿ��Ʋ

    private static GameObject prefab; //alert view prefab
    private System.Action cancelButtonDelegate; //��� ��ư Ŭ�� �� ����Ǵ� �븮�� ����
    private System.Action okButtonDelegate; //ok ��ư Ŭ�� �� ����Ǵ� �븮�� ����

    //�˸� �並 ǥ���ϴ� static �޼���
    public static AlertViewController Show(
        string title, string message, AlertViewOptions options = null)
    {
        if(prefab == null)
        {
            //�������� �о���δ�.
            prefab = Resources.Load("AlertView") as GameObject;
        }

        GameObject go = Instantiate(prefab) as GameObject;
        AlertViewController alertView = go.GetComponent<AlertViewController>();
        alertView.UpdateContent(title, message, options);

        return alertView;
    }

    //alert view�� ������ ������Ʈ�ϴ� �޼���
    public void UpdateContent(
        string title, string message, AlertViewOptions options = null)
    {
        //Ÿ��Ʋ�� �޼����� ����
        titleLabel.text = title;
        messageLabel.text = message;

        if (options != null)
        {
            //ǥ�� �ɼ��� ���� �� �ɼ��� ���뿡 ���� ��ư�� ǥ���ϰų� ǥ������ �ʴ°��� ����
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
            // ǥ�� �ɼ��� �����Ǿ����� ���� ��, �⺻ ��ư�� ǥ��
            cancelButton.gameObject.SetActive(false);
            okButton.gameObject.SetActive(true);
            okButtonLabel.text = "Ȯ��";
        }
    }

    //alert view�� �ݴ� �޼���
    public void Dismiss()
    {
        Destroy(gameObject);
    }

    //��� ��ư�� ������ ��, ȣ��Ǵ� �޼���
    public void OnPressCancelButton()
    {
        if(cancelButtonDelegate != null)
        {
            cancelButtonDelegate.Invoke();
        }
        Dismiss();
    }

    //OK ��ư�� ������ ��, ȣ��Ǵ� �޼���
    public void OnPressOkButton()
    {
        if(okButtonDelegate != null)
        {
            okButtonDelegate.Invoke();
        }
        Dismiss();
    }
}
