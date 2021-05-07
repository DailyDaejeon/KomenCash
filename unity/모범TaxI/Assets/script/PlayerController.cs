using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlayerController : MonoBehaviour
{
    [SerializeField]
    private float walkSpeed; //�̵� �ӵ�

    [SerializeField]
    private float lookSensitivity; //�ΰ���

    [SerializeField]
    private float cameraRotationLimit;    //ī�޶� ���� ���� �̻����� ȸ����Ű�� ���ϰ� �ϴ� ���Ѱ�.
    private float currentCameraRotationX; //���� ī�޶� X�� ȸ����. default�� ������ �����ϴ� 0.

    [SerializeField]
    private Camera theCamera; //���� ī�޶�
    private Rigidbody myRigid; //Rigidbody ������Ʈ�� �Ҵ��� ����. private�ϱ� ������ ���� ���۽� GetComponent �Լ��� �Ҵ�

    void Start()
    {
        myRigid = GetComponent<Rigidbody>();
        Debug.Log("myRigid : " + myRigid);
    }

    void Update()
    {
        Move();               //Ű���� �Է¿� ���� ĳ���� ������
        CameraRotation();     //���콺 ���Ʒ�(Y) �����ӿ� ���� X�� ȸ��
        CharacterRotation();  //���콺 �¿�(X) �����ӿ� ���� ĳ���� Y�� ȸ��
    }

    private void Move() //����Ű �Ǵ� WASD ��ư �Է¿� ���� �̵�
    {
        /*  [A,D,W,S / ��,��,��,��]��ư�̳� ���̽�ƽ�� �Է°��� ���� -1, 0, 1�� ���� ������.
            1. Horizontal - �¿�
                A, �� ��ư�� �Է� => -1
                D, �� ��ư�� �Է� => 1
            2. Vertical - �յ�
                W, �� ��ư�� �Է� => 1
                S, �� ��ư�� �Է� => -1   */
        float _moveDirX = Input.GetAxisRaw("Horizontal"); //�¿� �̵� ũ��
        float _moveDirZ = Input.GetAxisRaw("Vertical");   //�յ� �̵� ũ��

        /*  Vector3 : 3���� ���Ϳ� ��ġ�� ǥ��. 3���� ���������� ��ġ�� ���͸� ǥ���ϱ� ���� ����Ѵ�.
            Transform : ���ӿ�����Ʈ�� ��ġ, ȸ��, �������� ��Ÿ����.
                      : ���� ��� ������Ʈ�� Ʈ�������� ������ -> ������Ʈ�� ��ġ, ȸ��, �������� �����ϰ� �ٷ�� ���ؼ�
            transform.right = ��(character)�� ������ ������ ��Ÿ���� ���� ����
            transform.forward = ��(character)�� ���� ������ ��Ÿ���� ���� ����   */
        Vector3 _moveHorizontal = transform.right * _moveDirX; //�¿� �̵� ���� ��
        Vector3 _moveVertical = transform.forward * _moveDirZ; //�յ� �̵� ���� ��

        Vector3 _velocity = new Vector3(0f,0f,0f);
        _velocity = (_moveHorizontal + _moveVertical).normalized * walkSpeed; //�ӵ� ���� : (�̵��� ���� * �ӵ� ũ��)�� character�� �ӵ��� ��Ÿ����.
        myRigid.MovePosition(transform.position + _velocity * Time.deltaTime); //(���� ��ġ + �ӵ� * deltaTime) ��ġ�� �̵�
    }

    private void CameraRotation() //���Ʒ� ī�޶� ȸ��(X�� ȸ��)
    {
        float _xRotation = Input.GetAxisRaw("Mouse Y");        //���콺 ���Ʒ� �̵� ũ��
        float _cameraRotationX = _xRotation * lookSensitivity; //ī�޶� X������ ȸ���Ҹ�ŭ�� �� (1�����Ӵ�)


        /*  currentCameraRotationX : ī�޶��� X�� ���� ȸ�� ��
              �� X�� �������� ȸ���� �� �Ʒ��� ���̴°� positive, ������ 0, ���� �����°� negative ����
              �� ���콺�� �Ʒ��� �����̸� Input.GetAxisRaw�� ���� ������ �Ǳ� ������ _cameraRotationX��ŭ�� �����ְ� �Ǹ� currentCameraRotationX ���� �����Ͽ� ī�޶� ���� ȸ���ϰ� �ȴ�.
              �� ���� -=�� ����� ���콺�� �Ʒ��� �������� ī�޶� ȸ�����⵵ �Ʒ��� ���Ѵ�.  */
        currentCameraRotationX -= _cameraRotationX;

        /*  Mathf.Clamp : �ּ�/�ִ밪�� �����Ͽ� float ���� ���� �̿��� ���� ���� �ʵ����Ѵ�.
        ���콺�� ���Ʒ��� ������ �� 360���� ȸ���ϴ� ���� �����ϱ� ���� Mathf.Clamp()�Լ��� ����Ͽ� ī�޶� X�� ȸ���� ������ �д�.  */
        currentCameraRotationX = Mathf.Clamp(currentCameraRotationX, -cameraRotationLimit, cameraRotationLimit);

        //ī�޶� ȸ������ X�ุ �����̵��� (currentCameraRotationX, 0, 0)���� �����Ѵ�.
        theCamera.transform.localEulerAngles = new Vector3(currentCameraRotationX, 0f, 0f);
    }

    private void CharacterRotation() //�¿� ĳ���� ȸ��(Y�� ȸ��)
    {
        float _yRotation = Input.GetAxisRaw("Mouse X");
        Vector3 _characterRotationY = new Vector3(0f, _yRotation, 0f) * lookSensitivity;

        //�� ���ʹϾ��� ȸ������ ���ϱ� ���ؼ��� �� ���ʹϾ� ���� ���ؾ��Ѵ�.
        myRigid.MoveRotation(myRigid.rotation * Quaternion.Euler(_characterRotationY));
    }

    /*
       ** ���ʹϾ�(Quaternion) **
       : ���ʹϾ��� ȸ���� ǥ���ϱ� ���� ���ȴ�.
       : ���ʹϾ��� 4���� ��(x,y,z,w)�� �̷������ �� ������ ���̳� ������ �ǹ��ϴ°� �ƴ϶�
            1. �ϳ��� ����(x,y,z)
            2. �ϳ��� ��Į��(w)
         �� �ǹ��Ѵ�.
       
       : ���Ͱ� ��ġ�Ӱ� ���ÿ� ����(direction)�̵�, ���ʹϾ��� ����(orientation)�Ӱ� ���ÿ� ȸ���̴�.
       : ���ʹϾ��� gimbal lock�� ������ ����, ����ϴµ� ��� ����� ���ٴ� ������ ������,
         ���ʹϾ��� �̿��� ȸ���� �ϳ��� ����(orientation)���� �ٸ� �������� �����Ǳ⿡ 
         180������ ū ȸ���� ǥ���� �� ������, ���������� �����ϱ� ����ٴ� ������ �ִ�.
       
       ** ���Ϸ� ��(Euler Angles) **
       : ������ ���Ϸ��� ����� ����. 3���� ������ ���� ��ǥ�� �������� ��ü�� ȸ���� �����ϴ� ���
       : x, y, z �� ���� ���� �������� ȸ���ϱ� ������ �������̸� �����ϱ� �����, 180���� �Ѵ� ȸ���� ǥ���� �� �ִٴ� ������ �ִ�.
       : ���Ϸ� ���� ����ϴµ� ��� ����� ũ��, gimbal lock �̽��� �ִٴ� ������ �ִ�.


       ** ���� ��(Gimbal Lock) **
       : 3���� ������ �̿��� ȸ���ϴ� 3�������� �� ���� ������ ������ ��� ȸ���� �ϳ��� ������� ����.
       : ������ �� ���� ���� �� �̻� ������ �� ���� �ȴٴ� �ǹ̰� �ƴ϶� �� �� ���� �ϳ��� ������ �������� ���� ȸ�����ѵ� ���� ���� �������� ȸ���ϰ� �ȴٴ� �ǹ��̴�.
       : ��ü�� ȸ���ϸ� ��ü�� ��ǥ�谡 �Բ� ȸ���ϰ� ���� ȸ���ϱ� ���� ��ǥ��� ȸ���� ���� ���� ��ġ�ϸ� �����Ͱ� �սǵȴ�.
       : ���Ϸ� ���� X, Y, Z�������� ȸ������ ������ � ������ ����Ǵ����� ���� ����� �޶�����.
         ��, ȸ�������� ���̷�Ű�� �� �� �ְ� ���� ù ��°�� ȸ���ϴ� ���� ���� ����� �Բ� ȸ����Ű�� �ȴ�.
         ���������� �� ��°�� ȸ���ϴ� ���� �� ��° ���� �Բ� �����̸� ���������� ȸ���ϴ� ���� ȥ�� ȸ���Ѵ�.
         �̷��� ȸ�� ����� ���� ȸ�� ������ �����ϴ� ����� ������ ������ �߻���Ų��.
         
         => ȸ�� �� 3�� �� 2���� �ϳ��� ���� �ְ� �Ǵ� ��(ȸ�� �� ������ �ս�)
       
       
       : ����Ƽ�� ���Ϸ� ���� �̿��� ȸ���� ���������� ������ �� �ֵ��� �ν����� �信 ǥ���ϰ� �ִ�.
         ������ ���������δ� ���ʹϾ� ������� ��ȯ�Ͽ� ȸ���� ����Ѵ�.
         ���� ����ڰ� ���������� ���Ϸ� �� ����� �̿��ϵ��� �������� �ʴ� �̻� ����Ƽ���� Gimbal Lock ������ �߻����� �ʴ´�.
     */
}
