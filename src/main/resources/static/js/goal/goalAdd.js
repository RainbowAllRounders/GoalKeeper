// 인증횟수
const goalTimesInput = document.getElementById('goalTimesInput');
const goalTimes = goalTimesInput.querySelector('input');

// 미션 설명
const goalDescriptionText = document.getElementById('goalDescriptionText');

// 해시태그
const tagPlusBtn = document.querySelector('.tagPlusBtn');
const goalHashTag = document.querySelector('.goalHashTag');

// 진행 기간

// 모집 인원
const goalPeopleInput = document.getElementById('goalPeopleInput');
const goalPeople = goalPeopleInput.querySelector('input');

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

    goalDescriptionText.addEventListener('input', function() {
        if(goalDescriptionText.value.length > 100) {
            swal({
                title: '입력값 오류',
                text: '최대 100자까지 입력할 수 있습니다.',
                type: 'error',
                confirmButtonText: '확인',
                confirmButtonColor: '#FF5065'
            });
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
});