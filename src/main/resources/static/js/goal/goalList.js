// 필터 ----------------------------------------
const filterList = document.querySelector('.filterList');
const filters = Array.from(document.querySelectorAll('.filter'));

// 검색 ----------------------------------------
const searchBox = document.querySelector('#searchBox');
const searchContent = searchBox.querySelector('input');
const magnifier = document.querySelector('#magnifier');

// goal 목록
const goalList = document.querySelectorAll(".goalCard");

// 더보기 버튼
// const seeMoreBtn = document.getElementById("seeMoreBtn");

// 페이지네이션
const pagination = document.querySelector(".pagination");

window.addEventListener('load', function() {

    // 모집 중/진행 중/완료 태그 색 변경
    goalList.forEach(function(goal) {
        let statusTag = goal.querySelector(".statusTag");
        let status = statusTag.textContent.trim();

        if (status === "모집 중") {
            statusTag.classList.add("recruiting");
        } else if (status === "진행 중") {
            statusTag.classList.add("proceeding");
        } else if (status === "완료") {
            statusTag.classList.add("complete");
        }
    });

    pagination.addEventListener('click', function (e) {
        e.preventDefault();
        e.stopPropagation();

        const target = e.target;

        const num = target.getAttribute("data-num");

        // 가져온 페이지 번호가 null 또는 undefined인 경우, 즉 data-num 속성이 없는 요소가 클릭된 경우
        if (num === null || num === undefined) {
            return; // 함수 종료
        }

        // 현재 URL 가져오기
        let currentUrl = window.location.href;

        // URL에 이미 페이지 번호가 포함되어 있는지 확인
        if (currentUrl.includes('?page=')) {
            // 페이지 번호가 이미 포함되어 있는 경우, 해당 부분을 새로 클릭된 페이지 번호로 교체
            currentUrl = currentUrl.replace(/page=\d+/g, 'page=' + num);
        } else {
            // 페이지 번호가 포함되어 있지 않은 경우, URL에 페이지 번호 추가
            currentUrl += '?page=' + num;
        }

        // 현재 활성화된 페이지 번호에 active 클래스를 제거합니다.
        const activeItem = pagination.querySelector('.page-item.active');
        if (activeItem) {
            activeItem.classList.remove('active');
        }

        // 클릭한 페이지 번호에 active 클래스를 추가합니다.
        target.closest('.page-item').classList.add('active');

        // 새로운 URL로 이동
        window.location.href = currentUrl;
    });

    // 필터 선택 시 효과 --------------------
    filters.forEach(function(filter) {
        filter.addEventListener('click', function() {

            // 모든 필터 색 초기화
            filters.forEach(function(f) {
                f.style.border = '1px solid #B0B0B0';
                f.style.backgroundColor = 'transparent';
                f.style.color = '#B0B0B0';
            });

            // 선택된 필터에 대한 스타일 변경
            filter.style.border = 'none';
            filter.style.backgroundColor = '#FFC933';
            filter.style.color = 'black';
        })
    });

    // 검색 --------------------
    window.searchGoals = () => {

        // 내용 비우기
        searchContent.value = '';
        /* 상세 페이지 이동 */
        this.location.href = 'goalDetail.html';
    }

    magnifier.addEventListener('click', searchGoals);

});