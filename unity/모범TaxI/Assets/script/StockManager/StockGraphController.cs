using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using CodeMonkey.Utils;

public class StockGraphController : MonoBehaviour
{

  [SerializeField] private static Sprite circleSprite;
  public static RectTransform graphContainer;
  public static GameObject graph;

  void Start()
  {
    graphContainer = transform.Find("GraphContainer").GetComponent<RectTransform>();
    graph = GameObject.Find("GraphContainer");

  }

  private static GameObject CreateCircle(Vector2 anchoredPosition)
  {
    GameObject gameObject = new GameObject("circle", typeof(Image));
    gameObject.transform.SetParent(graphContainer, false);
    gameObject.GetComponent<Image>().sprite = circleSprite;
    RectTransform rectTransform = gameObject.GetComponent<RectTransform>();
    rectTransform.anchoredPosition = anchoredPosition;
    rectTransform.sizeDelta = new Vector2(8, 8);
    rectTransform.anchorMin = new Vector2(0, 0);
    rectTransform.anchorMax = new Vector2(0, 0);
    return gameObject;
  }

  public static void ShowGraph(List<int> valueList)
  {
    if (graph.transform.childCount > 1)
    {
      for (int i = 1; i < graph.transform.childCount; i++)
      {
        Destroy(graph.transform.GetChild(i).gameObject);
      }
    }
    float graphHeight = graphContainer.sizeDelta.y;
    float yMaximum = 2000f;
    float xSize = 50f / 3;

    GameObject lastCircleGameObject = null;

    int ii = 0;
    if (valueList.Count > 10)
    {
      ii = valueList.Count - 10;
    }
    int sum = 0;
    int avg = 0;
    for (int i = ii; i < valueList.Count; i++)
    {
      sum += valueList[i];
    }
    avg = sum / valueList.Count - ii;
    yMaximum = avg * 2;

    for (int i = ii; i < valueList.Count; i++)
    {
      float xPosition = xSize + i * xSize;
      float yPosition = (valueList[i] / yMaximum) * graphHeight;
      GameObject circleGameObject = CreateCircle(new Vector2(xPosition, yPosition));
      if (lastCircleGameObject != null)
      {
        CreateDotConnection(lastCircleGameObject.GetComponent<RectTransform>().anchoredPosition, circleGameObject.GetComponent<RectTransform>().anchoredPosition);
      }
      lastCircleGameObject = circleGameObject;
    }
  }

  private static void CreateDotConnection(Vector2 dotPositionA, Vector2 dotPositionB)
  {
    GameObject gameObject = new GameObject("dotConnection", typeof(Image));
    gameObject.transform.SetParent(graphContainer, false);
    gameObject.GetComponent<Image>().color = new Color(1, 1, 1, .5f);
    RectTransform rectTransform = gameObject.GetComponent<RectTransform>();
    Vector2 dir = (dotPositionB - dotPositionA).normalized;
    float distance = Vector2.Distance(dotPositionA, dotPositionB);
    rectTransform.anchorMin = new Vector2(0, 0);
    rectTransform.anchorMax = new Vector2(0, 0);
    rectTransform.sizeDelta = new Vector2(distance, 3f);
    rectTransform.anchoredPosition = dotPositionA + dir * distance * .5f;
    rectTransform.localEulerAngles = new Vector3(0, 0, UtilsClass.GetAngleFromVectorFloat(dir));
  }

}
