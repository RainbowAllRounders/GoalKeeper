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
const seeMoreBtn = document.getElementById("seeMoreBtn");

window.addEventListener('load', function() {

    //
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

    // 더보기 버튼 --------------------

});