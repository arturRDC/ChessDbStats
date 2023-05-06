$('#deleteCollModal').on('show.bs.modal', function (e) {
    var triggerElement = e.relatedTarget; // The link tag that triggered the modal
    var itemId = triggerElement.getAttribute('data-item-id'); // Retrieve the data-item-id attribute

    var anchor = $('.deleteCollConfirm');
    var newHref = '/api/v1/collections/delete/' + itemId;
    anchor.attr('href', newHref);
});