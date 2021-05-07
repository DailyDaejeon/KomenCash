using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlayerController : MonoBehaviour
{
    [SerializeField]
    private float walkSpeed; //이동 속도

    [SerializeField]
    private float lookSensitivity; //민감도

    [SerializeField]
    private float cameraRotationLimit;    //카메라를 일정 각도 이상으로 회전시키지 못하게 하는 제한값.
    private float currentCameraRotationX; //현재 카메라 X축 회전값. default는 정면을 보게하는 0.

    [SerializeField]
    private Camera theCamera; //메인 카메라
    private Rigidbody myRigid; //Rigidbody 컴포넌트를 할당할 변수. private하기 때문에 게임 시작시 GetComponent 함수로 할당

    void Start()
    {
        myRigid = GetComponent<Rigidbody>();
        Debug.Log("myRigid : " + myRigid);
    }

    void Update()
    {
        Move();               //키보드 입력에 따라 캐릭터 움직임
        CameraRotation();     //마우스 위아래(Y) 움직임에 따라 X축 회전
        CharacterRotation();  //마우스 좌우(X) 움직임에 따라 캐릭터 Y축 회전
    }

    private void Move() //방향키 또는 WASD 버튼 입력에 따라 이동
    {
        /*  [A,D,W,S / ←,→,↑,↓]버튼이나 조이스틱의 입력값에 따라 -1, 0, 1의 값을 가진다.
            1. Horizontal - 좌우
                A, ← 버튼을 입력 => -1
                D, → 버튼을 입력 => 1
            2. Vertical - 앞뒤
                W, ↑ 버튼을 입력 => 1
                S, ↓ 버튼을 입력 => -1   */
        float _moveDirX = Input.GetAxisRaw("Horizontal"); //좌우 이동 크기
        float _moveDirZ = Input.GetAxisRaw("Vertical");   //앞뒤 이동 크기

        /*  Vector3 : 3차원 벡터와 위치를 표현. 3차원 공간에서의 위치와 벡터를 표현하기 위해 사용한다.
            Transform : 게임오브젝트의 위치, 회전, 스케일을 나타낸다.
                      : 씬의 모든 오브젝트는 트랜스폼을 가진다 -> 오브젝트의 위치, 회전, 스케일을 저장하고 다루기 위해서
            transform.right = 나(character)의 오른쪽 방향을 나타내는 단위 벡터
            transform.forward = 나(character)의 앞쪽 방향을 나타내는 단위 벡터   */
        Vector3 _moveHorizontal = transform.right * _moveDirX; //좌우 이동 벡터 값
        Vector3 _moveVertical = transform.forward * _moveDirZ; //앞뒤 이동 벡터 값

        Vector3 _velocity = new Vector3(0f,0f,0f);
        _velocity = (_moveHorizontal + _moveVertical).normalized * walkSpeed; //속도 벡터 : (이동할 방향 * 속도 크기)로 character의 속도를 나타낸다.
        myRigid.MovePosition(transform.position + _velocity * Time.deltaTime); //(현재 위치 + 속도 * deltaTime) 위치로 이동
    }

    private void CameraRotation() //위아래 카메라 회전(X축 회전)
    {
        float _xRotation = Input.GetAxisRaw("Mouse Y");        //마우스 위아래 이동 크기
        float _cameraRotationX = _xRotation * lookSensitivity; //카메라가 X축으로 회전할만큼의 양 (1프레임당)


        /*  currentCameraRotationX : 카메라의 X축 방향 회전 값
              → X축 방향으로 회전할 때 아래로 숙이는게 positive, 정면이 0, 위로 젖히는게 negative 방향
              → 마우스가 아래로 움직이면 Input.GetAxisRaw에 의해 음수가 되기 때문에 _cameraRotationX만큼을 더해주게 되면 currentCameraRotationX 값이 감소하여 카메라가 위로 회전하게 된다.
              → 따라서 -=를 해줘야 마우스를 아래로 내릴수록 카메라 회전방향도 아래로 향한다.  */
        currentCameraRotationX -= _cameraRotationX;

        /*  Mathf.Clamp : 최소/최대값을 설정하여 float 값이 범위 이외의 값을 넘지 않도록한다.
        마우스를 위아래로 움직일 때 360도로 회전하는 것을 방지하기 위해 Mathf.Clamp()함수를 사용하여 카메라 X축 회전에 제한을 둔다.  */
        currentCameraRotationX = Mathf.Clamp(currentCameraRotationX, -cameraRotationLimit, cameraRotationLimit);

        //카메라 회전값은 X축만 움직이도록 (currentCameraRotationX, 0, 0)으로 설정한다.
        theCamera.transform.localEulerAngles = new Vector3(currentCameraRotationX, 0f, 0f);
    }

    private void CharacterRotation() //좌우 캐릭터 회전(Y축 회전)
    {
        float _yRotation = Input.GetAxisRaw("Mouse X");
        Vector3 _characterRotationY = new Vector3(0f, _yRotation, 0f) * lookSensitivity;

        //두 쿼터니언의 회전량을 더하기 위해서는 두 쿼터니언 값을 곱해야한다.
        myRigid.MoveRotation(myRigid.rotation * Quaternion.Euler(_characterRotationY));
    }

    /*
       ** 쿼터니언(Quaternion) **
       : 쿼터니언은 회전을 표현하기 위해 사용된다.
       : 쿼터니언은 4개의 수(x,y,z,w)로 이루어지며 각 성분은 축이나 각도를 의미하는게 아니라
            1. 하나의 벡터(x,y,z)
            2. 하나의 스칼라(w)
         를 의미한다.
       
       : 벡터가 위치임과 동시에 방향(direction)이듯, 쿼터니언은 방향(orientation)임과 동시에 회전이다.
       : 쿼터니언은 gimbal lock의 문제가 없고, 계산하는데 드는 비용이 적다는 장점이 있지만,
         쿼터니언을 이용한 회전은 하나의 방향(orientation)에서 다른 방향으로 측정되기에 
         180도보다 큰 회전을 표현할 수 없으며, 직관적으로 이해하기 힘들다는 단점이 있다.
       
       ** 오일러 각(Euler Angles) **
       : 수학자 오일러가 고안한 개념. 3차원 공간의 절대 좌표를 기준으로 물체의 회전을 측정하는 방식
       : x, y, z 세 개의 축을 기준으로 회전하기 때문에 직관적이며 조작하기 쉬우며, 180도가 넘는 회전도 표현할 수 있다는 장점이 있다.
       : 오일러 각은 계산하는데 드는 비용이 크며, gimbal lock 이슈가 있다는 단점이 있다.


       ** 짐벌 락(Gimbal Lock) **
       : 3개의 짐벌을 이용해 회전하는 3차원에서 두 개의 짐벌이 겹쳐질 경우 회전축 하나가 사라지는 현상.
       : 겹쳐진 두 개의 축을 더 이상 움직일 수 없게 된다는 의미가 아니라 그 두 개가 하나의 축으로 합쳐져서 각각 회전시켜도 같은 축을 기준으로 회전하게 된다는 의미이다.
       : 물체가 회전하면 물체의 좌표계가 함께 회전하고 만약 회전하기 전의 좌표축과 회전한 후의 축이 일치하면 데이터가 손실된다.
       : 오일러 각은 X, Y, Z축으로의 회전값이 같더라도 어떤 순서로 적용되는지에 따라 결과가 달라진다.
         즉, 회전순서를 하이러키로 볼 수 있고 따라서 첫 번째로 회전하는 축이 하위 축들을 함께 회전시키게 된다.
         마찬가지로 두 번째로 회전하는 축은 세 번째 축을 함께 움직이며 마지막으로 회전하는 축은 혼자 회전한다.
         이렇듯 회전 결과가 축의 회전 순서에 의존하는 방식은 짐벌락 문제를 발생시킨다.
         
         => 회전 축 3개 중 2개가 하나의 평면상에 있게 되는 것(회전 축 정보의 손실)
       
       
       : 유니티는 오일러 각을 이용해 회전을 직관적으로 조작할 수 있도록 인스펙터 뷰에 표시하고 있다.
         하지만 내부적으로는 쿼터니언 방식으로 변환하여 회전을 계산한다.
         따라서 사용자가 예외적으로 오일러 각 계산을 이용하도록 설정하지 않는 이상 유니티에서 Gimbal Lock 현상을 발생하지 않는다.
     */
}
