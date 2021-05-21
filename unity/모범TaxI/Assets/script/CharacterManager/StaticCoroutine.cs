using UnityEngine;
using System;
using System.Collections;

public class StaticCoroutine : MonoBehaviour
{

  private static StaticCoroutine mInstance = null;

  private static StaticCoroutine instance
  {
    get
    {
      if (mInstance == null)
      {
        mInstance = GameObject.FindObjectOfType(typeof(StaticCoroutine)) as StaticCoroutine;

        if (mInstance == null)
        {
          mInstance = new GameObject("StaticCoroutine").AddComponent<StaticCoroutine>();
        }
      }
      return mInstance;
    }
  }

  void Awake()
  {
    if (mInstance == null)
    {
      mInstance = this as StaticCoroutine;
    }
  }

  IEnumerator Perform(IEnumerator coroutine)
  {
    yield return StartCoroutine(coroutine);
    Die();
  }

  public static void DoCoroutine(IEnumerator coroutine)
  {
    //여기서 인스턴스에 있는 코루틴이 실행될 것이다.
    instance.StartCoroutine(instance.Perform(coroutine));
  }

  void Die()
  {
    mInstance = null;
    Destroy(gameObject);
  }

  void OnApplicationQuit()
  {
    mInstance = null;
  }
}