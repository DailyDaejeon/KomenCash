using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class onClickTest : MonoBehaviour
{
  Camera _mainCam;
  private GameObject target;
  // private PlayerController menu;
  private int ClickCnt;

  private void Awake()
  {
    _mainCam = Camera.main;
  }

  private void Update()
  {
    // target = GetTargetPoint();
    // // Debug.Log("target : " + target + ", object : " + gameObject);
    // if (Input.GetMouseButton(0))
    // {
    //   ClickCnt += 1;
    //   if (target.Equals(gameObject) && ClickCnt == 1) OnClickBank();
    // }
  }

  // public void OnClickBank()
  // {
  //   menu = GameObject.Find("Character").GetComponent<PlayerController>();
  //   menu.ShowMenu();
  // }

  private GameObject GetTargetPoint()
  {
    //충돌이 감지된 영역
    RaycastHit hit;
    //찾은 오브젝트
    GameObject target = null;

    //마우스 포이트 근처 좌표를 만든다.
    Ray ray = _mainCam.ScreenPointToRay(Input.mousePosition);

    //마우스 근처에 오브젝트가 있는지 확인
    if (true == (Physics.Raycast(ray.origin, ray.direction * 10, out hit)))
    {
      //있다!

      //있으면 오브젝트를 저장한다.
      target = hit.collider.gameObject;
    }

    return target;
  }
}