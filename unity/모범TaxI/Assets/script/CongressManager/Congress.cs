using System.Collections;
using System.Collections.Generic;
using System;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.Networking;
using SimpleJSON;
using UnityEngine.EventSystems;


public class Congress : MonoBehaviour
{
    public string baseURL = "https://k4b203.p.ssafy.io/api/";

    // 국회 화면
    [SerializeField]
    private List<GameObject> CongressList = new List<GameObject>();


    [SerializeField]
    private List<GameObject> VoteList = new List<GameObject>(); // 투표 리스트
    [SerializeField]
    private GameObject VoteItem;        // 투표 종목
    [SerializeField]
    private List<GameObject> VoteDetailItems = new List<GameObject>();   // 후보들
    [SerializeField]
    private GameObject VoteDetailItem;      // 후보 각각 하나

    [SerializeField]
    private List<GameObject> VoteResultCharts = new List<GameObject>();   // 후보들
    [SerializeField]
    private GameObject VoteResultChart;      // 후보 각각 하나
    [SerializeField]
    public GameObject VoteReqeustBtn;


    // 투표 추가 하기
    [SerializeField]
    public GameObject VoteCreate;
    [SerializeField]
    public GameObject Candidate;
    // [SerializeField] 
    // public List<GameObject> CandidateList = new List<CandidateList>();   // 후보들
    [SerializeField]
    private List<GameObject> VoteCreateCandidates = new List<GameObject>();
    [SerializeField]
    public List<GameObject> candidateCloneList = new List<GameObject>();
    int sId;
    int gId;
    int jId;
    string sNickname;
    void Start()
    {
        sId = 1;
        gId = 1;
        sNickname = "배상웅";
        string jname = "국회의장";
        // 직업 번호가 1번일 경우
        // 투표 건의하기 버튼 활성화
        CongressList.Add(GameObject.Find("VoteListContainer"));
        CongressList.Add(GameObject.Find("VoteDetail"));
        CongressList.Add(GameObject.Find("VoteCreate"));
        CongressList.Add(GameObject.Find("RequestLaw"));
        if(jname != "국회의장"){
            GameObject.Find("RequestLawBtn").SetActive(false);
        }

        StartCoroutine(GetVoteList(gId));
    }

    // Update is called once per frame
    void Update(){}

    public void onClickVoteListBtn(){
        
        ObjectActive("VoteListContainer", -1);
        foreach (GameObject item in VoteList)
        {
            Destroy(item);
        }
        StartCoroutine(GetVoteList(gId));
    }
    public void onClickVoteReigstBtn(){
        ObjectActive("VoteCreate", -1);
    }
    // 투표 리스트 보기
    public IEnumerator GetVoteList(int gId) {
        using (UnityWebRequest request = UnityWebRequest.Get(baseURL + "vote/list/"+gId)){
            yield return request.SendWebRequest();
            if(request.error != null) {
		        Debug.Log(request.error);
	        }
            else {
                string result =  request.downloadHandler.text;
                JSONNode root = JSON.Parse(result);

                Transform parent = GameObject.Find("VoteList").GetComponent<Transform>();
                VoteItem = Resources.Load("VoteItem") as GameObject;

                if (root.Count <= 0)
                {   
                    GameObject clone = Instantiate(VoteItem);
                    Text title = clone.transform.GetChild(1).GetComponent<Text>();
                    title.text = "No Data";
                    clone.transform.localPosition = Vector3.zero;
                    clone.transform.SetParent(parent);

                    VoteList.Add(clone);
                    Debug.Log("No Data");
                }
                else
                {
                    // list 생성
                    List<GameObject> cloneList = new List<GameObject>();
                    for (int i = 0; i < root.Count; i++)
                    {
                        GameObject clone = Instantiate(VoteItem);
                        RectTransform cloneRect = clone.GetComponent<RectTransform>();
                        Text voteId = clone.transform.GetChild(0).GetComponent<Text>();
                        Text title = clone.transform.GetChild(1).GetComponent<Text>();
                        Text studentNickname = clone.transform.GetChild(2).GetComponent<Text>();

                        Button detailBtn = clone.transform.GetChild(3).GetComponent<Button>();
                        
                        voteId.text = root[i]["id"].Value;
                        title.text = root[i]["title"].Value;
                        studentNickname.text = root[i]["studentNickname"].Value;

                        detailBtn.onClick.AddListener(delegate ()
                        {
                            Debug.Log("title : " + clone.transform.GetChild(1).GetComponent<Text>().text);
                            onClickDetailBtn(clone.transform.GetChild(0).GetComponent<Text>().text);
                            // showItemHistory(clone.transform.GetChild(0).GetComponent<Text>().text);

                        });



                        clone.transform.localPosition = Vector3.zero;
                        clone.transform.SetParent(parent);

                        cloneRect.offsetMax = new Vector2(-8, -10);
                        cloneRect.offsetMin = new Vector2(5, 0);
                        cloneRect.localScale =  new Vector3(1.0f, 1.0f, 1.0f);
                         
                        VoteList.Add(clone);
                        Debug.Log("root : " + root[i].ToString());
                        // Text 
                    }
                }
            }
        }
    }

    public void onClickDetailBtn(string voteId){
        foreach (GameObject item in VoteDetailItems)
        {
            Destroy(item);
        }
        foreach (GameObject item in VoteResultCharts)
        {
            Destroy(item);
        }
        // 상세 화면으로 넘어가기
        StartCoroutine(GetVoteDetail(voteId));
        ObjectActive("VoteDetail", -1);
    }

    /*
    *
    * 투표 상세 보기 / 투표하기
    *
    */
    public IEnumerator GetVoteDetail(string voteId){

        using (UnityWebRequest request = UnityWebRequest.Get(baseURL + "vote/detail/"+voteId)){
            yield return request.SendWebRequest();
            if(request.error != null) {
		        Debug.Log(request.error);
	        }
            else {
                string result =  request.downloadHandler.text;
                JSONNode root = JSON.Parse(result);
                // 없는 경우
                if (root.Count <= 0)
                {
                    Debug.Log(request.error);
                }
                
                else
                {
                    GameObject.Find("VoteDetailTitle").GetComponent<Text>().text = root[1];
                    GameObject.Find("VoteDetailContent").GetComponent<Text>().text = root[2];
                    GameObject.Find("VoteDetailId").GetComponent<Text>().text = root[0];
                    GameObject.Find("VoteDetailStudentNickname").GetComponent<Text>().text = root[4];
                    if(root[4] != sNickname){
                        GameObject.Find("DeleteVote").SetActive(false);
                    }
                    Transform parent = GameObject.Find("VoteDetailItems").GetComponent<Transform>();
                    VoteDetailItem = Resources.Load("VoteDetailItem") as GameObject;
                    VoteResultChart = Resources.Load("VoteResultChart") as GameObject;
                    Debug.Log(root[6]);
                    bool voted = false;
                    for(int j=0; j<root[6].Count; j++){
                        if(root[6][j][2]== sId){
                            voted = true;
                            Debug.Log("결과 보여주기");
                            break;
                        }
                    }
                    if(voted){
                        List<GameObject> cloneList = new List<GameObject>();
                        
                        for (int i = 0; i < root[5].Count; i++)
                        {   
                            GameObject clone = Instantiate(VoteResultChart);
                            float graphHeight = 1000f;
                            float yMaximum = 100f;

                            RectTransform rectTran = clone.GetComponent<RectTransform>();
                    
                            RectTransform cloneRect = clone.GetComponent<RectTransform>();
                            // Text voteSelectId = clone.transform.GetChild(0).GetComponent<Text>();
                            Text ChartContent = clone.transform.GetChild(1).GetComponent<Text>();
                            Text ChartResultCnt = clone.transform.GetChild(2).GetComponent<Text>();
                            ChartContent.text = root[5][i][2].Value;
                            ChartResultCnt.text = root[5][i][3].Value;

                            int ysize = int.Parse(ChartResultCnt.text);
                            rectTran.SetSizeWithCurrentAnchors(RectTransform.Axis.Vertical, (ysize/yMaximum)*graphHeight);
                            
                            Debug.Log(ysize);
                            Debug.Log((ysize/yMaximum)*graphHeight);
                            
                            clone.transform.localPosition = Vector3.zero;
                            clone.transform.SetParent(parent);

                            cloneRect.offsetMax = new Vector2(-8, -10);
                            cloneRect.offsetMin = new Vector2(5, 0);
                            cloneRect.localScale =  new Vector3(1.0f, 1.0f, 1.0f);
                            
                            RectTransform cloneRect1 = clone.transform.GetChild(0).GetComponent<RectTransform>();
                            cloneRect1.SetSizeWithCurrentAnchors(RectTransform.Axis.Vertical, 1.0f*(ysize/yMaximum)*graphHeight);
                            // cloneRect1.localScale =  new Vector3(1.0f, 1.0f*(ysize/yMaximum)*graphHeight, 1.0f);
                            
                            
                            VoteResultCharts.Add(clone);
                        }
                    }
                    else{
                        List<GameObject> cloneList = new List<GameObject>();
                        for (int i = 0; i < root[5].Count; i++)
                        {   
                            GameObject clone = Instantiate(VoteDetailItem);
                            RectTransform cloneRect = clone.GetComponent<RectTransform>();
                            // Text voteSelectId = clone.transform.GetChild(0).GetComponent<Text>();
                            Text VoteDetailItemNum = clone.transform.GetChild(0).GetComponent<Text>();
                            Text VoteDetailItenContent = clone.transform.GetChild(1).GetComponent<Text>();
                            Text VoteDetailResultCnt = clone.transform.GetChild(2).GetComponent<Text>();
                            Button VoteDetailItemBtn = clone.transform.GetChild(3).GetComponent<Button>();
                            Debug.Log(root[5][i][2]);
                            VoteDetailItemNum.text = root[5][i][1].Value;
                            VoteDetailItenContent.text = root[5][i][2].Value;
                            VoteDetailResultCnt.text = root[5][i][3].Value;
                            
                            VoteDetailItemBtn.onClick.AddListener(delegate ()
                            {
                                Debug.Log("content : " + clone.transform.GetChild(1).GetComponent<Text>().text);
                                onClickVoteBtn(int.Parse(voteId), clone.transform.GetChild(0).GetComponent<Text>().text);
                                ObjectActive("VoteCreate", -1);
                                // Vote(clone.transform.GetChild(0).GetComponent<Text>().text, hint);

                            });



                            clone.transform.localPosition = Vector3.zero;
                            clone.transform.SetParent(parent);

                            cloneRect.offsetMax = new Vector2(-8, -10);
                            cloneRect.offsetMin = new Vector2(5, 0);
                            cloneRect.localScale =  new Vector3(1.0f, 1.0f, 1.0f);
                            VoteDetailItems.Add(clone);
                        }
                    }
                }
            }
        }
    }

    public void onClickVoteBtn(int voteId, string itemId){
        StartCoroutine(Vote(voteId, itemId));
    }
    [System.Serializable]
    public class voteAttendAddRequestDto{
        public int studentId;
        public int voteId;
        public int voteItemNum;
    }
    public IEnumerator Vote(int voteId, string itemId){
        voteAttendAddRequestDto data = new voteAttendAddRequestDto();
        data.studentId = sId;
        data.voteId = voteId;
        data.voteItemNum = int.Parse(itemId);
        
        string json = JsonUtility.ToJson(data);
        
        using (UnityWebRequest request = UnityWebRequest.Post(baseURL + "vote/choice", json)){
            byte[] jsonToSend = new System.Text.UTF8Encoding().GetBytes(json);
            request.uploadHandler = new UploadHandlerRaw(jsonToSend);

            request.SetRequestHeader("Content-Type", "application/json;charset=utf-8");
            request.SetRequestHeader("Accept", "application/json, text/plain, */*");

            yield return request.SendWebRequest();
            if(request.error != null) {
		        Debug.Log(request.error);
	        }
            else {
                string result =  request.downloadHandler.text;
                JSONNode root = JSON.Parse(result);
                if (result.Equals("true"))
                {
                    onClickDetailBtn(voteId.ToString());
                }
                else{
                    Debug.Log("투표할때 무언가가 잘 못 되었다.");
                }
            }
        }
    }

    public void onClickDeleteVoteBtn(){
        StartCoroutine(DeleteVote());
    }
    public IEnumerator DeleteVote(){
        string num = GameObject.Find("VoteDetailId").GetComponent<Text>().text;
        using (UnityWebRequest request = UnityWebRequest.Delete(baseURL + "vote/"+num)){
            
            yield return request.SendWebRequest();
            if(request.error != null) {
		        Debug.Log(request.error);
	        }
            else {
                onClickVoteListBtn();
            }
        }
    }
    /*
    *
    *  투표 건의하기
    *
    */
    public void onClickVoteCreateBtn(){
       // 투표 건의 하기
       StartCoroutine(CreateVote());
       candidateCloneList = new List<GameObject>();
    //    foreach (GameObject item in CandidateList)
    //     {
    //         Destroy(item);
    //     }
    }

    public void onClickVoteCreateCandidateBtn(){
        Transform parent = GameObject.Find("CandidateList").GetComponent<Transform>();
        Candidate = Resources.Load("Candidate") as GameObject;
        
        int cloneNum = candidateCloneList.Count;
        Debug.Log(cloneNum);
        GameObject clone = Instantiate(Candidate);
        RectTransform cloneRect = clone.GetComponent<RectTransform>();
        InputField CandidateInput = clone.transform.GetChild(0).GetComponent<InputField>();
        Button DeleteCandidateBtn = clone.transform.GetChild(1).GetComponent<Button>();
        DeleteCandidateBtn.onClick.AddListener(delegate ()
        {
            Destroy(clone);
            candidateCloneList.RemoveAt(cloneNum);
        });
        clone.transform.localPosition = Vector3.zero;
        clone.transform.SetParent(parent);
        cloneRect.offsetMax = new Vector2(-8, -10);
        cloneRect.offsetMin = new Vector2(5, 0);
        cloneRect.localScale =  new Vector3(1.0f, 1.0f, 1.0f);      
        candidateCloneList.Add(clone);
        
    }
    [System.Serializable]
    public class VoteAddUpdateRequestDto
    {
        public int studentId;
        public string title;
        public string content;
        public List<VoteItemList> voteItemList;
    }

    [System.Serializable]
    public class VoteItemList
    {
        public int itemNum;
        public string content;
    }
    public IEnumerator CreateVote()
    {
        string title = GameObject.Find("VoteCreateTitle").GetComponent<InputField>().text;
        string content = GameObject.Find("VoteCreateContent").GetComponent<InputField>().text;
        Debug.Log(candidateCloneList.Count);
        VoteAddUpdateRequestDto data = new VoteAddUpdateRequestDto();
        data.title = title;
        data.content = content;
        data.studentId = sId;
        List<VoteItemList> VoteItemAddUpdateRequestDtoList = new List<VoteItemList>();
        for(int i=0; i<candidateCloneList.Count; i++){
            VoteItemList dto = new VoteItemList();
            dto.itemNum = i+1;
            dto.content = candidateCloneList[i].transform.GetChild(0).GetComponent<InputField>().text;
            VoteItemAddUpdateRequestDtoList.Add(dto);
        }
        data.voteItemList = VoteItemAddUpdateRequestDtoList;
        
        string json = JsonUtility.ToJson(data);
        Debug.Log(json);
        using (UnityWebRequest request = UnityWebRequest.Post(baseURL + "vote", json)){
            byte[] jsonToSend = new System.Text.UTF8Encoding().GetBytes(json);
            request.uploadHandler = new UploadHandlerRaw(jsonToSend);

            request.SetRequestHeader("Content-Type", "application/json;charset=utf-8");
            request.SetRequestHeader("Accept", "application/json, text/plain, */*");

            yield return request.SendWebRequest();
            if(request.error != null) {
		        Debug.Log(request.error);
	        }
            else {
                string result =  request.downloadHandler.text;
                JSONNode root = JSON.Parse(result);
                if (result.Equals("true"))
                {
                    Debug.Log("성공띠");
                }
                else{
                    Debug.Log("실패!");
                }
            }
        }

        Transform child = GameObject.Find("CandidateList").transform;
        for(int i=0; i<child.childCount; i++){
            Destroy(child.GetChild(i).gameObject);
        }
        GameObject.Find("VoteCreateTitle").GetComponent<InputField>().text = "";
        GameObject.Find("VoteCreateContent").GetComponent<InputField>().text = "";

    }

    public List<string> VoteCheckId = new List<string>();
    public void onClickRequestLawBtn(){
        Dropdown VoteCheck = GameObject.Find("VoteCheck").GetComponent<Dropdown>();
        VoteCheck.options.Clear();
        VoteCheckId = new List<string>();
        for(int i = 1; i < VoteList.Count; i++)
        {
            Dropdown.OptionData option =new Dropdown.OptionData();
            VoteCheckId.Add(VoteList[i].transform.GetChild(0).GetComponent<Text>().text);
            Debug.Log(VoteList[i].transform.GetChild(0).GetComponent<Text>().text);
            option.text = VoteList[i].transform.GetChild(1).GetComponent<Text>().text;
            VoteCheck.options.Add(option);
        }
        GameObject.Find("RequeatLawStudent").GetComponent<Text>().text = sNickname;
        ObjectActive("RequestLaw", -1);
    }
    public class LawAddReqAddRequestDto{
        public int studentId;
        public string title;
        public string content;
        public int voteId;
    }
    public void onClickRequestLaw(){
        StartCoroutine(RequestLaw());
    }
    public IEnumerator RequestLaw()
    {
        string title = GameObject.Find("RequestLawTitle").GetComponent<InputField>().text;
        string content = GameObject.Find("RequestLawContent").GetComponent<InputField>().text;
        
        Dropdown VoteCheck = GameObject.Find("VoteCheck").GetComponent<Dropdown>();
        int voteId = int.Parse(VoteCheckId[VoteCheck.value]);
        LawAddReqAddRequestDto data = new LawAddReqAddRequestDto();
        data.title = title;
        data.content = content;
        data.studentId = sId;
        data.voteId = voteId;
        string json = JsonUtility.ToJson(data);
        using (UnityWebRequest request = UnityWebRequest.Post(baseURL + "law/law-add-request", json)){
            byte[] jsonToSend = new System.Text.UTF8Encoding().GetBytes(json);
            request.uploadHandler = new UploadHandlerRaw(jsonToSend);

            request.SetRequestHeader("Content-Type", "application/json;charset=utf-8");
            request.SetRequestHeader("Accept", "application/json, text/plain, */*");

            yield return request.SendWebRequest();
            if(request.error != null) {
		        Debug.Log(request.error);
	        }
            else {
                string result =  request.downloadHandler.text;
                JSONNode root = JSON.Parse(result);
                if (result.Equals("true"))
                {
                    GameObject.Find("RequestLawTitle").GetComponent<InputField>().text = "";
                    GameObject.Find("RequestLawContent").GetComponent<InputField>().text ="";

                }
                else{
                    Debug.Log("요청 실패");
                }
            }
        }
    }
    private void ObjectActive(string ojName, int index)
    {
        if (index == -1)
        {
            foreach (GameObject item in CongressList)
            {
                if (ojName.Equals(item.name))
                {
                    item.SetActive(true);
                }
                    else
                {
                    item.SetActive(false);
                }
            }
        }
        else if (index > -1)
        {
            foreach (GameObject item in CongressList)
            {
                if (ojName.Equals(item.name))
                {
                    item.SetActive(true);
                    for (int i = 0; i < item.transform.childCount; i++)
                    {
                        if (i == index) item.transform.GetChild(i).gameObject.SetActive(true);
                        else item.transform.GetChild(i).gameObject.SetActive(false);
                    }
                }
                else
                {
                    item.SetActive(false);
                }
            }
        }
    }
}
