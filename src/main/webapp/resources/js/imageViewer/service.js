function loadDocument(url, callback) {
	$.ajax({
		url: url,
		async: true,
		dataType: 'jsonp',
		type: "GET",
		cache: true,
		success: function(data, textStatus, xhr) {
			return callback(data, result.SUCCESS);
		},
		error: function(xhr, textStatus, errorThrown) {
			return callback ("", result.ERROR);
		}
	});
}

function postImage(url, callback) {
    $.ajax({
        url: url,
        async: true,
        dataType: 'jsonp',
        type: "POST",
        cache: true,
        success: function(data, textStatus, xhr) {
            return callback(data, result.SUCCESS);
        },
        error: function(xhr, textStatus, errorThrown) {
            return callback ("", result.ERROR);
        }
    });
}

function saveDocument(url,token,workspaceId, document, callback) {
	$.ajax({
		url: url,
		async: true,
		dataType: 'jsonp',
		type: "POST",
		cache: true,
		data: JSON.stringify({
			"token":token,
			"workspaceId":workspaceId,
			"document":document
		}),
		contentType: "application/json; charset=utf-8",
		success: function(data, textStatus, xhr) {
			return callback(data, result.SUCCESS);
		},
		error: function(xhr, textStatus, errorThrown) {
			return callback ("", result.ERROR);
		}
	});
}

function saveAndCloseImageViewer(url,token,workspaceId, workItemId,folderItemNo, callback) {
    $.ajax({
        url: url,
        async: true,
        dataType: 'jsonp',
        type: "POST",
        cache: true,
        data: JSON.stringify({
            "token":token,
            "workspaceId":workspaceId,
            "workItemId":workItemId,
            "folderItemNo":folderItemNo
        }),
        contentType: "application/json; charset=utf-8",
        success: function(data, textStatus, xhr) {
            return callback(data, result.SUCCESS);
        },
        error: function(xhr, textStatus, errorThrown) {
            return callback ("", result.ERROR);
        }
    });
}