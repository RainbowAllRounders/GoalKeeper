// 미션명
const goalTitleInput = document.getElementById('goalTitleInput');

// 인증횟수
const goalTimesInput = document.getElementById('goalTimesInput');
const goalTimes = goalTimesInput.querySelector('input');

// 해시 태그
let hashtagInput = document.getElementById('hashtagInput');

// 미션 설명
const goalDescriptionText = document.getElementById('goalDescriptionText');

// 진행 기간
const startDateInput = document.querySelector('#startDateInput input[type="date"]');
const endDateInput = document.querySelector('#endDateInput input[type="date"]');

// 모집 인원
const goalPeopleInput = document.getElementById('goalPeopleInput');
const goalPeople = goalPeopleInput.querySelector('input');

// 등록 버튼
const goalAddBtn = document.getElementById('goalAddBtn');

// 현재 날짜 yyyy-mm-dd 형식 리턴 함수
function calcDate() {
    // 오늘 날짜
    const today = new Date();
    // 년도
    const year = today.getFullYear();
    // 월
    const month = (today.getMonth() + 1).toString().padStart(2, '0');
    // 일
    const day = today.getDate().toString().padStart(2, '0');
    // yyyy-mm-dd
    const yyyy_mm_dd = `${year}-${month}-${day}`;
    return yyyy_mm_dd;
}

// 파일 서버 업로드 및 제거
async function uploadToServer(formObj) {

    console.log("upload to server......")
    console.log(formObj)

    const response = await axios({
        method: 'post',
        url: '/upload',
        data: formObj,
        headers: {
            'Content-Type': 'multipart/form-data',
        },
    });

    return response.data
}

window.addEventListener('load', function () {

    // 인증 횟수 1~100 숫자 제한
    goalTimes.addEventListener('input', function () {
        const enteredValue = parseInt(goalTimes.value);

        if (enteredValue < 1 || enteredValue > 100) {
            Swal.fire({
                title: '입력값 오류',
                text: '1부터 100까지의 숫자를 입력해주세요.',
                icon: 'error',
                confirmButtonText: '확인',
                confirmButtonColor: '#FF5065'
            });

            goalTimes.value = '';
        }
    });

    // 해시태그 최대 3개까지
    hashtagInput.addEventListener("input", function () {
        let hashtags = hashtagInput.value.split("#").filter(function (tag) {
            return tag.trim() !== "";
        });

        if (hashtags.length > 3) {
            Swal.fire({
                title: '입력값 오류',
                text: '최대 3개의 해시태그만 입력할 수 있습니다.',
                icon: 'error',
                confirmButtonText: '확인',
                confirmButtonColor: '#FF5065'
            });
            // 입력한 해시태그 중에서 넘치는 부분을 자른 후 다시 합칩니다.
            hashtagInput.value = "#" + hashtags.slice(0, 3).join("#");
        }
    });

    goalDescriptionText.addEventListener('input', function () {
        if (goalDescriptionText.value.length > 100) {
            Swal.fire({
                title: '입력값 오류',
                text: '최대 100자까지 입력할 수 있습니다.',
                icon: 'error',
                confirmButtonText: '확인',
                confirmButtonColor: '#FF5065'
            });
        }
    });

    // 시작일 제한
    startDateInput.addEventListener('input', function () {
        const today = new Date(calcDate());
        const selectedStartDate = new Date(startDateInput.value);

        if (selectedStartDate <= today) {
            Swal.fire({
                title: '입력 날짜 오류',
                text: '현재 날짜 이후의 시작일을 선택하세요.',
                icon: 'error',
                confirmButtonText: '확인',
                confirmButtonColor: '#FF5065'
            });
            startDateInput.value = '';
        }
    });

    // 종료일 제한
    endDateInput.addEventListener('input', function () {
        const selectedStartDate = new Date(startDateInput.value);
        const selectedEndDate = new Date(endDateInput.value);
        if (!startDateInput.value) {
            Swal.fire({
                title: '입력 날짜 오류',
                text: '시작일을 먼저 입력해주세요.',
                icon: 'error',
                confirmButtonText: '확인',
                confirmButtonColor: '#FF5065'
            });
            endDateInput.value = '';
        } else if (selectedEndDate <= selectedStartDate) {
            Swal.fire({
                title: '입력 날짜 오류',
                text: '시작일 이후의 날짜를 선택하세요.',
                icon: 'error',
                confirmButtonText: '확인',
                confirmButtonColor: '#FF5065'
            });
            endDateInput.value = '';
        }
    });

    // 모집 인원 1~100 숫자 제한
    goalPeople.addEventListener('input', function () {
        const enteredValue = parseInt(goalPeople.value);

        if (enteredValue < 1 || enteredValue > 100) {
            Swal.fire({
                title: '입력값 오류',
                text: '1부터 100까지의 숫자를 입력해주세요.',
                icon: 'error',
                confirmButtonText: '확인',
                confirmButtonColor: '#FF5065'
            });

            goalPeople.value = '';
        }
    });

    // 등록 버튼 눌렀을 때
    goalAddBtn.addEventListener('click', function (e) {
        // e.preventDefault();

        const goalTitle = goalTitleInput.querySelector('input').value.trim();
        const goalTimesValue = goalTimes.value.trim();
        const goalDescription = goalDescriptionText.value.trim();
        const hashtags = hashtagInput.value.trim();
        const startDate = startDateInput.value.trim();
        const endDate = endDateInput.value.trim();
        const goalPeopleValue = goalPeople.value.trim();
        const fileInput = document.querySelector("input[name='imgPath']");
        const files = fileInput.files;
        console.log(`fileInput={fileIput}`);
        console.log("files.length=" + files.length);

        // 필수 입력값 검사------------------------------------------
        // 필수 입력값 하나라도 누락 시
        if (!goalTitle || !goalTimesValue || !goalDescription || !startDate || !endDate || !goalPeopleValue) {
            Swal.fire({
                title: '입력 오류',
                text: '모든 입력값을 입력해주세요.',
                icon: 'error',
                confirmButtonText: '확인',
                confirmButtonColor: '#FF5065'
            });
            return;
        }

        Swal.fire({
            title: '정말 등록하시겠습니까?',
            text: '등록 이후에는 수정 및 삭제가 불가능합니다.',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: '등록',
            confirmButtonColor: '#FF5065',
            cancelButtonText: '취소',
            cancelButtonColor: '#2A9AD9'
        }).then((result) => {
            if (result.isConfirmed) {

                let formData = new FormData();
                formData.append('title', goalTitle);
                formData.append('authCount', goalTimesValue);
                formData.append('content', goalDescription);
                formData.append('hashtagDTOs', hashtags);
                formData.append('startDate', startDate);
                formData.append('endDate', endDate);
                formData.append('maxPeople', goalPeopleValue);
                formData.append("files", files[0]);

                // if(files.length > 0){
                //     const formObj = new FormData();
                //     formObj.append("files", files[0]);
                //
                //     uploadToServer(formObj).then(result=>{
                //         console.log(result);
                //     }).catch(e=>{
                //         console.log(e);
                //     })
                // }


                if (files.length > 0) { console.log(files[0]);formData.append("imgPath", files[0].name); }


                const uploadPromise = files.length > 0 ? uploadToServer(formData) : Promise.resolve([]);


                uploadPromise.then(uploadResult => {
                    // AJAX 요청
                    $.ajax({
                        type: "POST",
                        url: "/goal/add",
                        data: formData,
                        processData: false,
                        contentType: false,
                        success: function (response) {

                            Swal.fire({
                                title: '미션 등록 완료',
                                text: '미션 등록이 성공적으로 완료되었습니다.',
                                icon: 'success',
                                confirmButtonText: '확인',
                                confirmButtonColor: '#FF5065'
                            }).then((result) => {
                                window.location.href = '/goal/list';
                            });
                        },
                        error: function (xhr, status, error) {
                            swal({
                                title: '미션 등록 오류',
                                text: '미션 등록 중 오류가 발생했습니다. 다시 시도해주세요.',
                                icon: 'error',
                                confirmButtonText: '확인',
                                confirmButtonColor: '#FF5065'
                            });
                        }
                    });
                }).catch(function(error) {
                    Swal.fire({
                        icon: "error",
                        title: "미션 등록 실패",
                        text: "다시 시도해 주세요."
                    });
                })
            }
        });
    });
});