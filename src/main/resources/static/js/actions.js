$(function () {
    $('.delete-note').on('click', function () {
        let modal = $(this).closest('.single-note-item').find('.delete-note-modal');
        modal.modal('show');
    })
})