const rankPage = document.getElementById("rankpage");
const header = document.getElementById("header");
const rankArrow = document.getElementById("rankarrow");

let isScrollingDown = false;

// 랭크 페이지를 감시하는 observer (아래로 스크롤)
const observerDown = new IntersectionObserver((entries) => {
    entries.forEach((entry) => {
        if (entry.isIntersecting) {
            isScrollingDown = true;
            window.scroll({top: rankPage.offsetTop, behavior: 'smooth'});
        }
    });
});

// 화살표를 감시하는 observer (위로 스크롤)
const observerUp = new IntersectionObserver((entries) => {
    entries.forEach((entry) => {
        if (entry.isIntersecting && isScrollingDown) {
            isScrollingDown = false;  // 스크롤 방향 전환
            window.scroll({top: 0, behavior: 'smooth'});
        }
    });
}, {threshold: 0.3});// threshold를 조정하여 얼마나 많은 부분이 보여졌을 때 이벤트를 발생시킬지 결정

observerDown.observe(rankPage);
observerUp.observe(rankArrow);


//goalcard 상태
//모집, 진행, 종료
// goal 목록
const goalList = document.querySelectorAll(".goalcard");

goalList.forEach(function(goal) {
    let statusTag = goal.querySelector(".status");
    let status = statusTag.textContent.trim();

    if (status === "모집 중") {
        statusTag.classList.add("recruiting");
    } else if (status === "진행 중") {
        statusTag.classList.add("proceeding");
    } else if (status === "완료") {
        statusTag.classList.add("complete");
    }
});





// 좋아요
const likeIcons = document.querySelectorAll('.like_F, .like_T');

likeIcons.forEach(icon => {
    icon.addEventListener('click', function() {
        // 클래스 토글을 사용하여 하트 이미지 변경
        if (icon.classList.contains('like_F')) {
            icon.classList.replace('like_F', 'like_T');
            // icon.style.backgroundImage = 'url(../../images/main/redHeart.png)';
        } else {
            icon.classList.replace('like_T', 'like_F');
            // icon.style.backgroundImage = 'url(../../images/main/Heart.png)';
        }
    });
});
