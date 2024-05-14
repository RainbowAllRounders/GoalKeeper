
// 참여하기 나가기
const condition = document.getElementById('join');

/**
 * 좋아요
 */
const like = document.querySelector('#like');
let likeImg = like.querySelector(".like_img");
let likeCount = like.querySelector(".like_count");

like.addEventListener('click', function () {
    fetch('like').then(response => response.json())
        .then(data => {
            console.log(data);
            console.log(data['liked'])
            if(data['liked']) {
                likeImg.src="/images/redHeart.png";
                // likeImg.src="../../static/images/redHeart.png";
            } else {
                likeImg.src="/images/grayHeart.png";
                // likeImg.src="../../static/images/grayHeart.png";
            }

            likeCount.textContent = data['likeCount'];
        })
});

    condition.addEventListener('click', function() {

        fetch('like').then(response => response.json())
            .then(data => {
                console.log(data);
                console.log(data['liked'])
                if(data['liked']) {
                    likeImg.src="/images/redHeart.png";
                    // likeImg.src="../../static/images/redHeart.png";
                } else {
                    likeImg.src="/images/grayHeart.png";
                    // likeImg.src="../../static/images/grayHeart.png";
                }

                likeCount.textContent = data['likeCount'];
            })

        if(condition.id === 'join') {

            swal({
                title : '참여신청 완료',
                text : '골키퍼들과 끝까지 미션을 완수하세요!',
                type : 'success',
                timer : 1500,
                showConfirmButton: false
            });

            condition.id = 'quit';
            condition.querySelector('span').textContent = '참여취소'
        } else if(condition.id === 'quit') {

            swal({
                title : '참여신청 취소',
                text : '더 좋은 미션에서 만나요!',
                type : 'info',
                timer : 1500,
                showConfirmButton: false
            });

            condition.id = 'join'
            condition.querySelector('span').textContent = '참여하기';
        }

    });

document.addEventListener('DOMContentLoaded', function() {
    const authBtn = document.querySelector('.auth_btn');
    const authImgContainer = document.querySelector('.auth_img');
    let isFirstClick = true;

    authBtn.addEventListener('click', function() {
        // 파일 입력(input type="file") 엘리먼트 생성
        const fileInput = document.createElement('input');
        fileInput.setAttribute('type', 'file');
        fileInput.setAttribute('accept', 'image/*'); // 이미지 파일만 허용

        // 파일 입력에 변화가 있을 때 실행될 이벤트 리스너 추가
        fileInput.addEventListener('change', function(event) {
            const file = event.target.files[0]; // 선택한 파일 가져오기
            const imageURL = URL.createObjectURL(file); // 파일의 URL 생성

            // 새로운 이미지 엘리먼트 생성
            const newImg = document.createElement('img');
            newImg.setAttribute('src', imageURL); // 이미지 URL 설정
            newImg.style.width = '160px'; // 가로 크기 160px로 설정
            newImg.style.height = '160px'; // 세로 크기 160px로 설정
            newImg.style.marginLeft = '20px'; // 왼쪽 마진 20px 추가
            newImg.style.marginTop = '40px'; // 위쪽 마진 20px 추가

            // 인증 이미지 컨테이너를 비우고 새로운 이미지 추가
            if (isFirstClick) {
                authImgContainer.innerHTML = '';
                isFirstClick = false;
            }
            authImgContainer.appendChild(newImg);

            // 파일 입력 창 닫기
            fileInput.remove();
        });

        // 파일 입력 엘리먼트를 클릭하여 파일 선택
        fileInput.click();
    });
});



