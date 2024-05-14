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

    // 페이지 변경 시 효과 적용
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
            if (currentUrl.includes('?')) {
                if (currentUrl.includes('&page=')) {
                    // URL에 page가 포함되어 있는 경우
                    currentUrl = currentUrl.replace(/page=\d+/g, 'page=' + num);
                } else {
                    // URL에 이미 다른 파라미터가 있는 경우
                    currentUrl += '&page=' + num;
                }
            } else {
                // URL에 파라미터가 없는 경우
                currentUrl += '?page=' + num;
            }
        }

        // 현재 활성화된 페이지 번호에 active 클래스를 제거
        pagination.querySelectorAll('.page-item').forEach(function(item) {
            item.classList.remove('active');
        });

        // 클릭한 페이지 번호에 active 클래스를 추가
        target.closest('.page-item').classList.add('active');

        // 새로운 URL로 이동
        window.location.href = currentUrl;
    });

    // 필터 선택 시 효과 --------------------
    filters.forEach(function(filter) {
        // 현재 URL에 해당 필터가 적용되어 있는지 확인
        const currentUrl = window.location.href;
        const filterType = filter.id;

        if (currentUrl.includes('type=' + filterType)) {
            // URL에 해당 필터가 적용되어 있는 경우
            filter.classList.add('clickedFilter');
        }

        filter.addEventListener('click', function() {
            // 클릭된 필터에 clickedFilter 클래스 추가
            filters.forEach(function(f) {
                f.classList.remove('clickedFilter');
            });
            filter.classList.add('clickedFilter');

            // 클릭된 필터에 해당하는 페이지로 이동
            const type = filter.id;
            const formObj = document.querySelector('#filterForm');
            formObj.action = '/goal/list';
            formObj.querySelector('#textInput').value = type;
            formObj.submit();
        })
    });

    document.querySelector('#r').addEventListener('click', (e)=>{
        e.stopPropagation();
        e.preventDefault();

        const formObj = document.querySelector('#filterForm');

        formObj.action = '/goal/list';
        formObj.querySelector('#textInput').value = 'r';
        formObj.submit();
    });

    document.querySelector('#p').addEventListener('click', (e)=>{
        e.stopPropagation();
        e.preventDefault();

        const formObj = document.querySelector('#filterForm');

        formObj.action = '/goal/list';
        formObj.querySelector('#textInput').value = 'p';
        formObj.submit();
    });

    document.querySelector('#c').addEventListener('click', (e)=>{
        e.stopPropagation();
        e.preventDefault();

        const formObj = document.querySelector('#filterForm');

        formObj.action = '/goal/list';
        formObj.querySelector('#textInput').value = 'c';
        formObj.submit();
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