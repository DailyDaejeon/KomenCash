using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlayerController : MonoBehaviour
{
    float span = 0.1f;
    float delta = 0;

    void Start()
    {
    }

    void Update()
    {
        this.delta += Time.deltaTime;
        if (this.delta > this.span)
        {
            this.delta = 0;
            if (Input.GetKey(KeyCode.UpArrow))
            {
                transform.Translate(0, 0, 2);
            }

            if (Input.GetKey(KeyCode.DownArrow))
            {
                transform.Translate(0, 0, -2);
            }

            if (Input.GetKey(KeyCode.LeftArrow))
            {
                transform.Translate(-2, 0, 0);
            }

            if (Input.GetKey(KeyCode.RightArrow))
            {
                transform.Translate(2, 0, 0);
            }
        }
    }
}
