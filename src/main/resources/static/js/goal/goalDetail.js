// 참여하기 나가기
const condition = document.getElementById('join');
// const condition = document.querySelector('#join');
console.log(condition);


window.addEventListener('load', function () {
    condition.addEventListener('click', function() {

        if(condition.id === 'join') {
            console.log('참여신청 완료')
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
                console.log('참여신청 취소')
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
    
});