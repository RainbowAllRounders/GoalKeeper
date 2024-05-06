const rankPage = document.getElementById("rankpage");
const header = document.getElementById("header");
const hotgoal = document.getElementById("hotgoal");
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
            window.scroll({top: hotgoal.offsetTop-50, behavior: 'smooth'});
        }
    });
}, {threshold: 0.3});// threshold를 조정하여 얼마나 많은 부분이 보여졌을 때 이벤트를 발생시킬지 결정

observerDown.observe(rankPage);
observerUp.observe(rankArrow);
