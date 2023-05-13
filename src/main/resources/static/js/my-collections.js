$('#deleteCollModal').on('show.bs.modal', function (e) {
    const triggerElement = e.relatedTarget;
    const itemId = triggerElement.getAttribute('data-item-id');

    $('.deleteCollConfirm').on('click', function () {
        e.preventDefault();
        fetch('/api/v1/collections/' + itemId, {
            method: 'DELETE'
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Unable to delete collection.');
                }
                window.location.href = '/my-collections';
            })
            .catch(error => {
                console.error('Fetch error:', error);
            });
    });
});
$('#editCollModal').on('show.bs.modal', function (e) {
    let triggerElement = e.relatedTarget;
    let itemId = triggerElement.getAttribute('data-item-id');

    let collection = $('#collection'+itemId);

    const title = collection.find('.collection-title').text();
    const desc = collection.find('.collection-desc').text();

    $('#collectionNameEdit').val(title);
    $('#collectionDescEdit').val(desc);

    $('#edit-coll-submit').on('click', function () {
        e.preventDefault();
        let editFormData = new FormData(document.querySelector('#edit-coll-form'));
        editFormData.append("id", String(itemId));
        const data = Object.fromEntries(editFormData.entries());
        const dataJson = JSON.stringify(data);

        fetch('/api/v1/collections/' + itemId, {
            method: 'PUT',
            body: dataJson,
            headers: {"Content-Type": "application/json"},
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Unable to edit collection.');
                }
                window.location.href = '/my-collections';
            })
            .catch(error => {
                console.error('Fetch error:', error);
            });
    });
});

$('#createCollModal').on('show.bs.modal', function (e) {
    $('#create-coll-submit').on('click', function () {
        e.preventDefault();
        let editFormData = new FormData(document.querySelector('#create-coll-form'));
        const data = Object.fromEntries(editFormData.entries());
        const dataJson = JSON.stringify(data);

        fetch('/api/v1/collections', {
            method: 'POST',
            body: dataJson,
            headers: {"Content-Type": "application/json"},
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Unable to create collection.');
                }
                window.location.href = '/my-collections';
            })
            .catch(error => {
                console.error('Fetch error:', error);
            });
    });
});

$('#importGamesModal').on('show.bs.modal', function (e) {
    let importInto = $('#importInto');

    $('#importGamesSubmit').on('click', function () {
        e.preventDefault();
        let gamesFormData = new FormData(document.querySelector('#import-games-form'));
        const collectionId = importInto.val();
        fetch('/api/v1/collections/'+ collectionId + '/upload-games', {
            method: 'POST',
            body: gamesFormData,
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Unable to import games.');
                }
                window.location.href = '/my-collections';
            })
            .catch(error => {
                console.error('Fetch error:', error);
            });
    });
});