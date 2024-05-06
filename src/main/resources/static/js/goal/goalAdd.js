// 미션명
const goalTitleInput = document.getElementById('goalTitleInput');

// 인증횟수
const goalTimesInput = document.getElementById('goalTimesInput');
const goalTimes = goalTimesInput.querySelector('input');

// 미션 설명
const goalDescriptionText = document.getElementById('goalDescriptionText');

// 해시태그
const tagPlusBtn = document.querySelector('.tagPlusBtn');
const goalHashTag = document.querySelector('.goalHashTag');

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

    // 플러스 버튼 클릭 시 해시태그 추가
    tagPlusBtn.addEventListener('click', function () {
        const newGoalTag = document.createElement('div');
        newGoalTag.classList.add('goalTag', 'goalInputs');
        newGoalTag.innerHTML = '# <input type="text" maxlength="6">';

        tagPlusBtn.style.display = 'none';
        goalHashTag.appendChild(newGoalTag);
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

    // 등록 버튼 눌렀을 때 필수 입력값이 하나라도 입력되지 않았을 경우
    goalAddBtn.addEventListener('click', function () {
        const goalTitle = goalTitleInput.querySelector('input').value.trim();
        const goalTimesValue = goalTimes.value.trim();
        const goalDescription = goalDescriptionText.value.trim();
        const startDate = startDateInput.value.trim();
        const endDate = endDateInput.value.trim();
        const goalPeopleValue = goalPeople.value.trim();

        if (!goalTitle || !goalTimesValue || !goalDescription || !startDate || !endDate || !goalPeopleValue) {
            swal({
                title: '입력 오류',
                text: '모든 입력값을 입력해주세요.',
                type: 'error',
                confirmButtonText: '확인',
                confirmButtonColor: '#FF5065'
            });
        }
    });
});