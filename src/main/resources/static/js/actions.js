$(function () {
    $('.delete-note, .delete-category').on('click',
        function () {
            let modal = $(this).closest('.single-note-item, .single-category-item')
                .find('.delete-note-modal, .delete-category-modal');
            modal.modal('show');
        });
})