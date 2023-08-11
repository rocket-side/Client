const editAllButton = document.querySelector('.edit-all-button');
const editButtons = document.querySelectorAll('.edit-button');

editAllButton.addEventListener('click', () => {
    const checkboxContainers = document.querySelectorAll('.checkbox-container');

    checkboxContainers.forEach(container => {
        container.classList.toggle('d-none');
    });

    editButtons.forEach(button => {
        button.nextElementSibling.classList.add('d-none');
    });
});

editButtons.forEach(button => {
    button.addEventListener('click', () => {
        const checkboxContainer = button.parentElement.querySelector('.checkbox-container');
        checkboxContainer.classList.toggle('d-none');
    });
});