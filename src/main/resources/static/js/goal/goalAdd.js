// 미션명
const goalTitleInput = document.getElementById('goalTitleInput');

// 인증횟수
const goalTimesInput = document.getElementById('goalTimesInput');
const goalTimes = goalTimesInput.querySelector('input');

// 해시 태그
let hashtagInput = document.querySelector("#goalHashTagInput input[type='text']");

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

const goalAddDTO = {
    title: [[${title}]],
    authCount: [[${authCount}]],
    content: [[${content}]],
    hashtagDTOs: [[${hashtagDTOs}]],
    startDate: [[${startDate}]],
    endDate: [[${endDate}]],
    maxPeople: [[${maxPeople}]]
}

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

// 미션 등록 함수
async function addGoal(goalAddDTO) {
    const response = await axios.post(`/goal/add`, goalAddDTO);
    return response.data;
}

window.addEventListener('load', function () {

    // 인증 횟수 1~100 숫자 제한
    goalTimes.addEventListener('input', function () {
        const enteredValue = parseInt(goalTimes.value);

        if (enteredValue < 1 || enteredValue > 100) {
            swal({
                title: '입력값 오류',
                text: '1부터 100까지의 숫자를 입력해주세요.',
                type: 'error',
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
            swal({
                title: '입력값 오류',
                text: '최대 3개의 해시태그만 입력할 수 있습니다.',
                type: 'error',
                confirmButtonText: '확인',
                confirmButtonColor: '#FF5065'
            });
            // 입력한 해시태그 중에서 넘치는 부분을 자른 후 다시 합칩니다.
            hashtagInput.value = "#" + hashtags.slice(0, 3).join("#");
        }
    });

    goalDescriptionText.addEventListener('input', function () {
        if (goalDescriptionText.value.length > 100) {
            swal({
                title: '입력값 오류',
                text: '최대 100자까지 입력할 수 있습니다.',
                type: 'error',
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
            swal({
                title: '입력 날짜 오류',
                text: '현재 날짜 이후의 시작일을 선택하세요.',
                type: 'error',
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
            swal({
                title: '입력 날짜 오류',
                text: '시작일을 먼저 입력해주세요.',
                type: 'error',
                confirmButtonText: '확인',
                confirmButtonColor: '#FF5065'
            });
            endDateInput.value = '';
        } else if (selectedEndDate <= selectedStartDate) {
            swal({
                title: '입력 날짜 오류',
                text: '시작일 이후의 날짜를 선택하세요.',
                type: 'error',
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
            swal({
                title: '입력값 오류',
                text: '1부터 100까지의 숫자를 입력해주세요.',
                type: 'error',
                confirmButtonText: '확인',
                confirmButtonColor: '#FF5065'
            });

            goalPeople.value = '';
        }
    });

    // 등록 버튼 눌렀을 때
    goalAddBtn.addEventListener('click', function () {
        const goalTitle = goalTitleInput.querySelector('input').value.trim();
        const goalTimesValue = goalTimes.value.trim();
        const goalDescription = goalDescriptionText.value.trim();
        const startDate = startDateInput.value.trim();
        const endDate = endDateInput.value.trim();
        const goalPeopleValue = goalPeople.value.trim();

        addGoal(goalAddDTO).then(result => {
            if(!goalTitle || !goalTimesValue || !goalDescription || !startDate || !endDate || !goalPeopleValue) {
                swal({
                    title: '입력 오류',
                    text: '모든 입력값을 입력해주세요.',
                    type: 'error',
                    confirmButtonText: '확인',
                    confirmButtonColor: '#FF5065'
                });
            } else {
                swal({
                    title: '미션 등록 완료',
                    text: '미션 등록이 성공적으로 완료되었습니다.',
                    type: 'success',
                    confirmButtonText: '확인',
                    confirmButtonColor: '#FF5065'
                });
            }

        }).catch(e => {
            swal({
                title: '미션 등록 오류',
                text: '미션 등록 중 오류가 발생했습니다. 다시 시도해주세요.',
                type: 'error',
                confirmButtonText: '확인',
                confirmButtonColor: '#FF5065'
            });
        });

        // // 필수 입력값이 하나라도 입력되지 않았을 경우
        // if (!goalTitle || !goalTimesValue || !goalDescription || !startDate || !endDate || !goalPeopleValue) {
        //     swal({
        //         title: '입력 오류',
        //         text: '모든 입력값을 입력해주세요.',
        //         type: 'error',
        //         confirmButtonText: '확인',
        //         confirmButtonColor: '#FF5065'
        //     });
        //
        // } else if("*{fail}" === '미션 등록 오류') {
        //     swal({
        //         title: '미션 등록 오류',
        //         text: '미션 등록 중 오류가 발생했습니다. 다시 시도해주세요.',
        //         type: 'error',
        //         confirmButtonText: '확인',
        //         confirmButtonColor: '#FF5065'
        //     });
        //
        // // 미션 등록 성공
        // } else if("*{success}" === '미션 등록 성공') {
        //     swal({
        //         title: '미션 등록 완료',
        //         text: '미션 등록이 성공적으로 완료되었습니다.',
        //         type: 'success',
        //         confirmButtonText: '확인',
        //         confirmButtonColor: '#FF5065'
        //     });
        // }
    });
});