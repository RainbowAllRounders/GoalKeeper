
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
            if(data['liked']) {
                likeImg.src="/images/redHeart.png";
            } else {
                likeImg.src="/images/grayHeart.png";
                // likeImg.src="../../static/images/grayHeart.png";
            }

            likeCount.textContent = data['likeCount'];
        })
});

condition.addEventListener('click', function() {

    fetch('join').then(response => response.json())
        .then(data => {
            if (data['join']) {
                if(data['role'] === true) {
                    swal({
                        title: '참여상태 변경 불가',
                        text: '미션 생성자는 참여상태를 변경할 수 없습니다!',
                        type: 'info',
                        timer: 1500,
                        showConfirmButton: false
                    });
                } else {
                    swal({
                        title: '참여신청 완료',
                        text: '골키퍼들과 끝까지 미션을 완수하세요!',
                        type: 'success',
                        timer: 1500,
                        showConfirmButton: false
                    });
                }
                condition.querySelector('span').textContent = '참여취소'
            } else if(data['join'] === true) {
                if(data['role'] === true) {
                    swal({
                        title: '참여상태 변경 불가',
                        text: '미션 생성자는 참여상태를 변경할 수 없습니다!',
                        type: 'info',
                        timer: 1500,
                        showConfirmButton: false
                    });
                } else {
                    swal({
                        title: '참여신청 취소',
                        text: '더 좋은 미션에서 만나요!',
                        type: 'info',
                        timer: 1500,
                        showConfirmButton: false
                    });
                }
                condition.querySelector('span').textContent = '참여하기'
            }
        })
});


// 사진 변경 미리보기
const modifyBtn = document.querySelector(".modify_btn");
const fileInput = document.querySelector(".fileInput");
const authImgUpload = document.querySelector(".authImg_upload");

modifyBtn.addEventListener("click", function () {
    fileInput.click();
});

fileInput.addEventListener("click", function(event) {
    event.stopPropagation(); // 이벤트 전파 방지
});

fileInput.addEventListener("change", function () {
    if (fileInput.files.length > 0) {
        const file = fileInput.files[0];
        const reader = new FileReader();

        reader.onload = function (e) {
            authImgUpload.src = e.target.result;
        };

        reader.readAsDataURL(file);
    }
});