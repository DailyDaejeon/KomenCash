using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.Networking;
using SimpleJSON;

public class StoreMenuController : MonoBehaviour
{
  private string baseURL = "http://k4b203.p.ssafy.io:8081/api/";
  public RectTransform uiGroup;
  PlayerController enterPlayer;

  //메뉴창 버튼
  [SerializeField]
  private GameObject StoreMenu;
  [SerializeField]
  private GameObject StoreForm;

  //오브젝트 담을 리스트
  private List<GameObject> objectList = new List<GameObject>();

  //컨트롤러에서 사용하는 변수
  bool _isExit;
  int gId;
  int sId;

  //생성해야하는 prefab

  //상품 목록 보기

  //상품 추가 요청 하기

  //상품 구매 내역 보기


  void Start()
  {

  }
  public bool GetExitState()
  {
    return _isExit;
  }

  public void Enter(PlayerController player)
  {
    enterPlayer = player;
    uiGroup.anchoredPosition = Vector3.zero;
    _isExit = false;
  }

  public void Exit()
  {
    uiGroup.anchoredPosition = Vector3.down * -1000;
    //ObjectActive();
    _isExit = true;
  }

  //상점 메뉴에 보여줄 데이터 받아오는 메서드들

}
