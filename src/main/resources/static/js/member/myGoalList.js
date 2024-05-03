// 필터 ----------------------------------------
const filterList = document.querySelector('.filterList');
const filters = Array.from(document.querySelectorAll('.filter'));

// 검색 ----------------------------------------
const searchBox = document.querySelector('#searchBox');
const searchContent = searchBox.querySelector('input');
const magnifier = document.querySelector('#magnifier');

window.addEventListener('load', function() {

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