document.addEventListener('DOMContentLoaded', function() {
    const editAllButton = document.querySelector('.edit-all-button');
    const teamMemberCards = document.querySelectorAll('.card-body.members'); // 모든 팀원 카드

    editAllButton.addEventListener('click', () => {
        teamMemberCards.forEach(card => {
            const deleteButton = card.querySelector('.delete-button');
            deleteButton.classList.toggle('d-none');
        });

        const buttonText = editAllButton.textContent;
        editAllButton.textContent = buttonText === '수정' ? '확인' : '수정';
    });
});